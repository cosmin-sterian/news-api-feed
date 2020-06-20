package ro.dummy.newsapifeed.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.LinkedList;
import java.util.List;

import ro.dummy.newsapifeed.data.local.Article;
import ro.dummy.newsapifeed.databinding.FragmentArticleListBinding;
import ro.dummy.newsapifeed.viewmodels.ArticlesListViewModel;
import timber.log.Timber;

/**
 * A fragment representing a list of Articles.
 */
public class ArticleFragment extends Fragment {

	private FragmentArticleListBinding binding;
	private SwipeRefreshLayout swipeRefreshLayout;
	private RecyclerView recyclerView;
	private RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> adapter;
//	private RecyclerView.LayoutManager layoutManager;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ArticleFragment() {
	}

	@SuppressWarnings("unused")
	public static ArticleFragment newInstance() {
		return new ArticleFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		// Do View Binding for easier access to the RecyclerView, at least for practice
		binding = FragmentArticleListBinding.inflate(inflater, container, false);
		swipeRefreshLayout = binding.swipeRefreshLayout;
		recyclerView = binding.recyclerView;

		// Create ViewModel for the RecyclerView and its items
		final ArticlesListViewModel articlesListViewModel = new ViewModelProvider(this).get(ArticlesListViewModel.class);
		adapter = new ArticleRecyclerViewAdapter(articlesListViewModel);
		recyclerView.setAdapter(adapter);

		// Notify the adapter when data is renewed
		articlesListViewModel
				.getArticlesListLiveData()
				.observe(getViewLifecycleOwner(),
						(Observer<List<Article>>) articles -> {
							Timber.d("articlesListObserver: new data observed, notifying RV adapter");
							adapter.notifyDataSetChanged();
							swipeRefreshLayout.setRefreshing(false);
						});

		/*
		 * Set swipe-to-refresh listener
		 * We might be abusing the fact that
		 * the LiveData of the newsService is final
		 * (updating sets the LiveData object which is already observed)
		 */
		swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) () -> {
			Timber.d("Swiped to refresh, updating articles");
			articlesListViewModel.updateArticles();
		});
		return binding.getRoot();
	}

//	@Override
//	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//		super.onViewCreated(view, savedInstanceState);
//		articleViewModel = new ViewModelProvider(requireActivity()).get(ArticleViewModel.class);
//	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		// Clean-up the binding references in order to prevent memory leaks
		binding.unbind();
		binding = null;
		recyclerView.setAdapter(null);
		recyclerView = null;
		adapter = null;
		// TODO: Check if must remove observer, but I don't think so
	}

	@SuppressLint("DefaultLocale")
	private static List<Article> getMockedData() {
		List<Article> result = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			result.add(Article.builder()
					.url(Integer.toString(i))
					.title(String.format("Article #%d", i))
					.description(String.format("Description #%d", i))
					.author(String.format("Author #%d", i))
					.category(String.format("Category #%d", i % 2))
					.build());
		}
		return result;
	}
}
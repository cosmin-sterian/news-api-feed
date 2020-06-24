package ro.dummy.newsapifeed.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.LinkedList;
import java.util.List;

import ro.dummy.newsapifeed.R;
import ro.dummy.newsapifeed.data.local.Article;
import ro.dummy.newsapifeed.data.remote.NewsApiConsumerService.NewsType;
import ro.dummy.newsapifeed.databinding.FragmentArticleListBinding;
import ro.dummy.newsapifeed.viewmodels.NewsFeedViewModel;
import ro.dummy.newsapifeed.viewmodels.NewsFeedViewModelFactory;
import timber.log.Timber;

/**
 * A fragment representing a list of Articles.
 */
public class NewsFeedFragment extends Fragment {

	public static final String NEWS_TYPE_KEY = "news-type";
	private FragmentArticleListBinding binding;
	private SwipeRefreshLayout swipeRefreshLayout;
	private RecyclerView recyclerView;
	private RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> adapter;
	private NewsType newsType;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public NewsFeedFragment() {
	}

	public static NewsFeedFragment newInstance(NewsType newsType) {
		NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
		Bundle bundle = new Bundle(1);
		bundle.putSerializable(NEWS_TYPE_KEY, newsType);
		newsFeedFragment.setArguments(bundle);
		return newsFeedFragment;
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		newsType = getArguments() != null
				? (NewsType) getArguments().getSerializable(NEWS_TYPE_KEY)
				: NewsType.TOP_HEADLINES;

		// Do View Binding for easier access to the RecyclerView, at least for practice
		binding = FragmentArticleListBinding.inflate(inflater, container, false);
		swipeRefreshLayout = binding.swipeRefreshLayout;
		recyclerView = binding.recyclerView;

		// Create ViewModel for the RecyclerView and its items
		final NewsFeedViewModel newsFeedViewModel = new ViewModelProvider(this, new NewsFeedViewModelFactory(newsType))
				.get(NewsFeedViewModel.class);
		binding.setLifecycleOwner(this);
		binding.setNewsFeedViewModel(newsFeedViewModel);
		adapter = new ArticleRecyclerViewAdapter(newsFeedViewModel);
		recyclerView.setAdapter(adapter);

		// Notify the adapter when data is renewed
		newsFeedViewModel
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
			newsFeedViewModel.updateArticles();
		});

		/*
		 * Set onTouchListener if showing search bar
		 * in order to unfocus the EditText when tapping outside
		 */
		if (newsType == NewsType.EVERYTHING) {
			binding.getRoot().setOnTouchListener((view, motionEvent) -> {
				if (view.getId() != R.id.edittext_query_text) {
					EditText searchEditText = view.findViewById(R.id.edittext_query_text);
					hideSoftKeyboard();
					searchEditText.clearFocus();
				}
				view.performClick();
				return true;
			});
		}

		newsFeedViewModel.updateArticles();
		return binding.getRoot();
	}

	@Override
	public void onDestroyView() {
		// Clean-up the binding references in order to prevent memory leaks
		binding.unbind();
		binding = null;
		recyclerView.setAdapter(null);
		recyclerView = null;
		adapter = null;
		// TODO: Check if must remove observer, but I don't think so
		super.onDestroyView();
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

	private void hideSoftKeyboard() {
		final Activity activity = getActivity();
		if (activity == null || activity.getCurrentFocus() == null) {
			Timber.d("activity is null or nothing focused");
			return;
		}
		InputMethodManager inputMethodManager =
				(InputMethodManager) activity
						.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if (inputMethodManager == null) {
			Timber.d("no InputMethodManager found");
			return;
		}
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
}
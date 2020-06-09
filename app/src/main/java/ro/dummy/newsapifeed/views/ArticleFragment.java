package ro.dummy.newsapifeed.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ro.dummy.newsapifeed.R;
import ro.dummy.newsapifeed.data.Article;
import ro.dummy.newsapifeed.databinding.FragmentArticleListBinding;
import ro.dummy.newsapifeed.views.dummy.DummyContent;

/**
 * A fragment representing a list of Articles.
 */
public class ArticleFragment extends Fragment {

	private FragmentArticleListBinding binding;
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
		recyclerView = binding.recyclerView;
		adapter = new ArticleRecyclerViewAdapter(getMockedData());
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
		return binding.getRoot();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		// Clean-up the binding references in order to prevent memory leaks
		binding = null;
		recyclerView.setAdapter(null);
		recyclerView = null;
		adapter = null;
	}

	@SuppressLint("DefaultLocale")
	private static List<Article> getMockedData() {
		List<Article> result = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			result.add(Article.builder()
					.id(i)
					.title(String.format("Article #%d", i))
					.description(String.format("Description #%d", i))
					.author(String.format("Author #%d", i))
					.category(String.format("Category #%d", i % 2))
					.build());
		}
		return result;
	}
}
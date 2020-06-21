package ro.dummy.newsapifeed.views;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import ro.dummy.newsapifeed.data.local.Article;
import ro.dummy.newsapifeed.databinding.CardviewArticleBinding;
import ro.dummy.newsapifeed.viewmodels.ArticleViewModel;
import ro.dummy.newsapifeed.viewmodels.ArticlesListViewModel;
import ro.dummy.newsapifeed.views.articledetails.ArticleDetailsActivity;
import timber.log.Timber;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Article}.
 */
public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> {
//	private final List<Article> articleList;
	private final ArticlesListViewModel articlesListViewModel;

//	public ArticleRecyclerViewAdapter(List<Article> articleList) {
//		this.articleList = articleList;
//	}

	public ArticleRecyclerViewAdapter(ArticlesListViewModel articlesListViewModel) {
		this.articlesListViewModel = articlesListViewModel;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		CardviewArticleBinding cardviewArticleBinding =
				CardviewArticleBinding.inflate(
						LayoutInflater.from(parent.getContext()),
						parent, false);
		return new ViewHolder(cardviewArticleBinding);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
//		Article article = articleList.get(position);
		Article article = articlesListViewModel.getArticles().get(position);
		holder.cardviewArticleBinding.setArticleViewModel(new ArticleViewModel(article));
	}

	@Override
	public int getItemCount() {
//		return articleList.size();
		return articlesListViewModel.getArticles().size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		public final CardviewArticleBinding cardviewArticleBinding;

		public ViewHolder(CardviewArticleBinding cardviewArticleBinding) {
			super(cardviewArticleBinding.getRoot());
			this.cardviewArticleBinding = cardviewArticleBinding;

			// Add click listener
			cardviewArticleBinding.getRoot().setOnClickListener(view -> {
				ArticleViewModel articleViewModel = cardviewArticleBinding.getArticleViewModel();
				if (articleViewModel == null || articleViewModel.getArticle() == null) {
					Timber.w("RecyclerView Article VM not initialised");
					Toast.makeText(view.getContext(), "Article not initialised", Toast.LENGTH_SHORT).show();
					return;
				}
				Article article = articleViewModel.getArticle();
				Intent intent = new Intent(view.getContext(), ArticleDetailsActivity.class);
				intent.putExtra(ArticleDetailsActivity.ARTICLE_KEY, article);
				view.getContext().startActivity(intent);
			});
		}

		@Override
		public String toString() {
			return super.toString() +
					" '" +
					cardviewArticleBinding.getArticleViewModel().toString() +
					"'";
		}
	}
}
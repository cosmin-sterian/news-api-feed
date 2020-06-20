package ro.dummy.newsapifeed.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import ro.dummy.newsapifeed.data.local.Article;
import ro.dummy.newsapifeed.databinding.CardviewArticleBinding;
import ro.dummy.newsapifeed.viewmodels.ArticleViewModel;
import ro.dummy.newsapifeed.viewmodels.ArticlesListViewModel;

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
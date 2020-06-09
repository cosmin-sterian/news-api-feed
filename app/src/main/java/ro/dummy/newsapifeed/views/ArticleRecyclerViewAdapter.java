package ro.dummy.newsapifeed.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.dummy.newsapifeed.data.Article;
import ro.dummy.newsapifeed.databinding.CardviewArticleBinding;
import ro.dummy.newsapifeed.viewmodels.ArticleViewModel;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Article}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> {
	private final List<Article> articleList;

	public ArticleRecyclerViewAdapter(List<Article> articleList) {
		this.articleList = articleList;
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
		Article article = articleList.get(position);
		holder.cardviewArticleBinding.setArticleViewModel(new ArticleViewModel(article));
	}

	@Override
	public int getItemCount() {
		return articleList.size();
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
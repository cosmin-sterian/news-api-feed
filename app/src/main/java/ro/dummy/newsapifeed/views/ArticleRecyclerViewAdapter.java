package ro.dummy.newsapifeed.views;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import ro.dummy.newsapifeed.data.local.Article;
import ro.dummy.newsapifeed.databinding.CardviewArticleBinding;
import ro.dummy.newsapifeed.viewmodels.ArticleViewModel;
import ro.dummy.newsapifeed.viewmodels.NewsFeedViewModel;
import ro.dummy.newsapifeed.views.articledetails.ArticleDetailsActivity;
import timber.log.Timber;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Article}.
 */
public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> {
	private final NewsFeedViewModel newsFeedViewModel;


	public ArticleRecyclerViewAdapter(NewsFeedViewModel newsFeedViewModel) {
		this.newsFeedViewModel = newsFeedViewModel;
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
		Article article = newsFeedViewModel.getArticles().get(position);
		holder.cardviewArticleBinding.setArticleViewModel(new ArticleViewModel(article));
	}

	@Override
	public int getItemCount() {
		return newsFeedViewModel.getArticles().size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		public final CardviewArticleBinding cardviewArticleBinding;

		public ViewHolder(CardviewArticleBinding cardviewArticleBinding) {
			super(cardviewArticleBinding.getRoot());
			this.cardviewArticleBinding = cardviewArticleBinding;

			// Add click listener
			cardviewArticleBinding.getRoot().setOnClickListener(this::cardOnClickListener);
		}

		@Override
		public String toString() {
			return super.toString() +
					" '" +
					cardviewArticleBinding.getArticleViewModel().toString() +
					"'";
		}

		private void cardOnClickListener(View view) {
			ArticleViewModel articleViewModel = cardviewArticleBinding.getArticleViewModel();
			if (articleViewModel == null || articleViewModel.getArticle() == null) {
				Timber.w("RecyclerView Article VM not initialised");
				Toast.makeText(view.getContext(), "Article not initialised", Toast.LENGTH_SHORT).show();
				return;
			}
			Article article = articleViewModel.getArticle();
			Intent intent = new Intent(view.getContext(), ArticleDetailsActivity.class);
			intent.putExtra(ArticleDetailsActivity.ARTICLE_KEY, article);
			// Prepare animation
			final CardView articleCardView = cardviewArticleBinding.cvArticle;
			ActivityOptions options = ActivityOptions
					.makeSceneTransitionAnimation((Activity) view.getContext(),
							articleCardView, view.getTransitionName());
			view.getContext().startActivity(intent, options.toBundle());
		}
	}
}
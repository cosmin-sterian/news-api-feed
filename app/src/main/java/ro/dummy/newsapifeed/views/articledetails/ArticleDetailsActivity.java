package ro.dummy.newsapifeed.views.articledetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import ro.dummy.newsapifeed.BaseActivity;
import ro.dummy.newsapifeed.data.local.Article;
import ro.dummy.newsapifeed.data.local.ArticleSource;
import ro.dummy.newsapifeed.databinding.ActivityArticleDetailsBinding;
import ro.dummy.newsapifeed.viewmodels.ArticleViewModel;
import ro.dummy.newsapifeed.viewmodels.ArticleViewModelFactory;
import timber.log.Timber;

public class ArticleDetailsActivity extends BaseActivity {
	public static String ARTICLE_KEY = "article";
	private ActivityArticleDetailsBinding binding;
	private Article article;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityArticleDetailsBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initArticle(savedInstanceState);
		binding.setArticleViewModel(
				new ViewModelProvider(this, new ArticleViewModelFactory(article))
						.get(ArticleViewModel.class));

		final ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	protected void onDestroy() {
		// CLean-up binding in order to prevent memory leaks
		binding.unbind();
		binding = null;
		super.onDestroy();
	}

	private void initArticle(Bundle savedInstanceState) {
		Intent parentIntent = getIntent();
		Article intentArticle = (Article) parentIntent.getSerializableExtra(ARTICLE_KEY);
		if (intentArticle != null) {
			article = intentArticle;
			Timber.d("Article restored from intent");
			return;
		}
		Article savedInstanceArticle = (Article) savedInstanceState.get(ARTICLE_KEY);
		if (savedInstanceArticle != null) {
			article = savedInstanceArticle;
			Timber.d("Article restored from saved instance state");
			return;
		}
		Timber.w("No article provided by intent or saved instance state, building dummy article");
		article = new Article("dummy", "dummy", "dummy",
				"dummy", "dummy", "dummy", "dummy",
				new ArticleSource("dummy", "dummy"));
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
			default:
			return super.onOptionsItemSelected(item);
		}
	}
}

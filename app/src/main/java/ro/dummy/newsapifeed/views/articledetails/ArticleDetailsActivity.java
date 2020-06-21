package ro.dummy.newsapifeed.views.articledetails;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import ro.dummy.newsapifeed.BaseActivity;
import ro.dummy.newsapifeed.R;
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

		initActionBar();
		loadImage();
		binding.buttonReadMore.setOnClickListener(this::readMoreOnClickListener);
		setUpTransitions();
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
				"dummy", "dummy", "dummy",
				"dummy", null,
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

	private void loadImage() {
		Picasso.get()
				.load(article.getUrlToImage())
				.into(binding.ivDetailsImage);
	}

	private void readMoreOnClickListener(View view) {
		final Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
		final PackageManager packageManager = getPackageManager();
		if (webIntent.resolveActivity(packageManager) != null) {
			startActivity(webIntent);
		} else {
			Timber.d("No app can handle the intent action");
			Toast.makeText(this, "Url can't be opened, are there any web browsers installed?", Toast.LENGTH_LONG).show();
		}
	}

	private void setUpTransitions() {
		final Window window = getWindow();
//		window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//		window.setSharedElementEnterTransition(new Fade());
//		window.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
		window.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.transition_article_card));
		window.setAllowEnterTransitionOverlap(true);
	}
}

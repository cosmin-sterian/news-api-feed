package ro.dummy.newsapifeed.views;

import android.os.Bundle;
import android.transition.Fade;
import android.view.Window;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ro.dummy.newsapifeed.BaseActivity;
import ro.dummy.newsapifeed.R;
import ro.dummy.newsapifeed.data.remote.NewsApiConsumerService.NewsType;
import timber.log.Timber;

public class MainActivity extends BaseActivity {
	private BottomNavigationView bottomNavigationView;
	private static final int FRAGMENTS_CONTAINER_ID = R.id.fragments_container;
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bottomNavigationView = findViewById(R.id.bottom_navigation_view);
		bottomNavigationView.setItemIconTintList(null);

		fragmentManager = getSupportFragmentManager();

		bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
			switch (item.getItemId()) {
				case R.id.menu_item_top_headlines:
					Timber.d("Top headlines pressed");
					fragmentManager.beginTransaction()
							.replace(FRAGMENTS_CONTAINER_ID, NewsFeedFragment.newInstance(NewsType.TOP_HEADLINES))
							.commit();
					return true;
				case R.id.menu_item_all_news:
					Timber.d("All news pressed");
					fragmentManager.beginTransaction()
							.replace(FRAGMENTS_CONTAINER_ID, NewsFeedFragment.newInstance(NewsType.EVERYTHING))
							.commit();
					return true;
				default:
					return true;
			}
		});
		setUpTransitions();
	}

	private void setUpTransitions() {
		final Window window = getWindow();
//		window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
		window.setSharedElementExitTransition(new Fade());
//		window.setAllowReturnTransitionOverlap(true);
	}
}
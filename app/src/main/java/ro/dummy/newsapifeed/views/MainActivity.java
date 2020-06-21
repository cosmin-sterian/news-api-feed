package ro.dummy.newsapifeed.views;

import android.os.Bundle;
import android.transition.Fade;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ro.dummy.newsapifeed.BaseActivity;
import ro.dummy.newsapifeed.R;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

	private BottomNavigationView bottomNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bottomNavigationView = findViewById(R.id.bottom_navigation_view);
		bottomNavigationView.setItemIconTintList(null);

		bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
			switch (item.getItemId()) {
				case R.id.menu_item_top_headlines:
					Timber.d("Top headlines pressed");
					return true;
				case R.id.menu_item_all_news:
					Timber.d("All news pressed");
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
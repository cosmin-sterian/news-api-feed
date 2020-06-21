package ro.dummy.newsapifeed.views;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

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

		bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
			}
		});
	}
}
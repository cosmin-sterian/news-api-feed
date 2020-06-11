package ro.dummy.newsapifeed.views;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ro.dummy.newsapifeed.R;

public class MainActivity extends AppCompatActivity {

	private BottomNavigationView bottomNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
			getWindow().setNavigationBarDividerColor(getResources().getColor(R.color.colorPrimary, getTheme()));
			getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent, getTheme()));
		}

		bottomNavigationView = findViewById(R.id.bottom_navigation_view);
		bottomNavigationView.setItemIconTintList(null);
//		bottomNavView.getMenu().findItem(R.id.menu_item_top_headlines).setIcon(R.mipmap.ic_top_headlines);

		bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()) {
					case R.id.menu_item_top_headlines:
						Log.d("sda", "top_headlines_pressed");
//						item.setIcon(R.drawable.ic_burning_newspaper_compressed_checked);
						return true;
					case R.id.menu_item_all_news:
						Log.d("sda", "all_news_pressed");
//						deselectTopHeadlines();
						return true;
					default:
						return true;
				}
			}
		});
	}

//	private void deselectTopHeadlines() {
//		bottomNavigationView
//				.getMenu()
//				.findItem(R.id.menu_item_top_headlines)
//				.setIcon(R.drawable.ic_top_headers);
//	}
//
//	private void selectTopHeadlines() {
//		MenuItem item = bottomNavigationView
//				.getMenu()
//				.findItem(R.id.menu_item_top_headlines)
//				.setIcon(R.drawable.ic_burning_newspaper_compressed_checked);
//	}
}
package ro.dummy.newsapifeed;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
			getWindow().setNavigationBarDividerColor(getResources().getColor(R.color.colorPrimary, getTheme()));
			getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent, getTheme()));
		}
	}
}

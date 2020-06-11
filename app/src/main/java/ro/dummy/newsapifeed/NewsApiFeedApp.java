package ro.dummy.newsapifeed;

import android.app.Application;

import timber.log.Timber;

public class NewsApiFeedApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
	}
}

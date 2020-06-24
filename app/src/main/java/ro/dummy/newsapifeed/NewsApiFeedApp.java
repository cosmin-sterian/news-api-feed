package ro.dummy.newsapifeed;

import android.app.Application;

import androidx.room.Room;

import ro.dummy.newsapifeed.data.local.ArticleDatabase;
import timber.log.Timber;

public class NewsApiFeedApp extends Application {

	private static NewsApiFeedApp INSTANCE;
	private ArticleDatabase articleDatabase;

	@Override
	public void onCreate() {
		super.onCreate();

		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}

		articleDatabase = Room.databaseBuilder(this, ArticleDatabase.class, "news")
				.build();

		INSTANCE = this;
	}

	public static NewsApiFeedApp getInstance() {
		return INSTANCE;
	}

	public ArticleDatabase getArticleDatabase() {
		return articleDatabase;
	}
}

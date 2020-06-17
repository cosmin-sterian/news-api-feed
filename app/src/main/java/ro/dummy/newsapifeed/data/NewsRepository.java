package ro.dummy.newsapifeed.data;

import androidx.lifecycle.LiveData;

import java.util.List;

import ro.dummy.newsapifeed.data.local.Article;
import ro.dummy.newsapifeed.data.remote.NewsApiConsumerService;
import ro.dummy.newsapifeed.data.remote.NewsApiConsumerService.NewsQuery;

public class NewsRepository {
	// TODO: Get data from local or network, expose it as LiveData (eventually)
	private final NewsApiConsumerService newsApiConsumerService;
	private static NewsRepository instance;

	private NewsRepository() {
		newsApiConsumerService = NewsApiConsumerService.getInstance();
	}

	@Deprecated
	public LiveData<List<Article>> getArticles() {
		return newsApiConsumerService.getArticlesListLiveData();
	}

	public LiveData<List<Article>> getTopHeadlines() {
		final NewsQuery newsQuery = NewsQuery.builder()
				.category("technology")
				.build();
		return newsApiConsumerService.getArticles(newsQuery, NewsApiConsumerService.NewsType.TOP_HEADLINES);
	}

	public static synchronized NewsRepository getInstance() {
		if (instance == null) {
			instance = new NewsRepository();
		}
		return instance;
	}
}

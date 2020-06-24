package ro.dummy.newsapifeed.data;

import androidx.lifecycle.LiveData;

import java.util.List;

import ro.dummy.newsapifeed.data.local.Article;
import ro.dummy.newsapifeed.data.remote.NewsApiConsumerService;
import ro.dummy.newsapifeed.data.remote.NewsApiConsumerService.EverythingQuery;
import ro.dummy.newsapifeed.data.remote.NewsApiConsumerService.TopHeadlinesQuery;

public class NewsRepository {
	// TODO: Get data from local or network, expose it as LiveData (eventually)
	private final NewsApiConsumerService newsApiConsumerService;
	private static NewsRepository instance;

	private NewsRepository() {
		newsApiConsumerService = NewsApiConsumerService.getInstance();
	}

	public LiveData<List<Article>> getArticlesListLiveData() {
		return newsApiConsumerService.getArticlesListLiveData();
	}

	public LiveData<List<Article>> getTopHeadlines(TopHeadlinesQuery topHeadlinesQuery) {
		return newsApiConsumerService.getTopHeadlines(topHeadlinesQuery);
	}

	public LiveData<List<Article>> getEverything(EverythingQuery everythingQuery) {
		return newsApiConsumerService.getEverything(everythingQuery);
	}

	public static synchronized NewsRepository getInstance() {
		if (instance == null) {
			instance = new NewsRepository();
		}
		return instance;
	}
}

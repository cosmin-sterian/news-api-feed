package ro.dummy.newsapifeed.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ro.dummy.newsapifeed.data.NewsRepository;
import ro.dummy.newsapifeed.data.local.Article;

public class ArticlesListViewModel extends ViewModel {
	private NewsRepository newsRepository;
	private LiveData<List<Article>> articlesListLiveData;

	public ArticlesListViewModel() {
		newsRepository = NewsRepository.getInstance();
		updateArticles();
	}

	public List<Article> getArticles() {
		return articlesListLiveData.getValue();
	}

	public LiveData<List<Article>> getArticlesListLiveData() {
		return articlesListLiveData;
	}

	public void updateArticles() {
//		articlesListLiveData = newsRepository.getArticles();
		articlesListLiveData = newsRepository.getTopHeadlines();
	}
}

package ro.dummy.newsapifeed.viewmodels;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ro.dummy.newsapifeed.data.NewsRepository;
import ro.dummy.newsapifeed.data.local.Article;
import ro.dummy.newsapifeed.data.remote.NewsApiConsumerService.EverythingQuery;
import ro.dummy.newsapifeed.data.remote.NewsApiConsumerService.NewsType;
import ro.dummy.newsapifeed.data.remote.NewsApiConsumerService.TopHeadlinesQuery;
import timber.log.Timber;

public class NewsFeedViewModel extends ViewModel {
	public MutableLiveData<String> searchInput;
	private static final String SEARCH_INPUT_ERROR = "This field cannot be blank";
	private NewsRepository newsRepository;
	private LiveData<List<Article>> articlesListLiveData;
	private NewsType newsType;
	private MutableLiveData<String> searchInputError;

	public NewsFeedViewModel(NewsType newsType) {
		newsRepository = NewsRepository.getInstance();
		articlesListLiveData = newsRepository.getArticlesListLiveData();
		this.newsType = newsType;
		searchInput = new MutableLiveData<>();
		searchInputError = new MutableLiveData<>(SEARCH_INPUT_ERROR);
	}

	public List<Article> getArticles() {
		return articlesListLiveData.getValue();
	}

	public int getSearchBarVisibility() {
		if (newsType == NewsType.TOP_HEADLINES) {
			return View.GONE;
		}
		return View.VISIBLE;
	}

	public LiveData<String> getSearchInputError() {
		return searchInputError;
	}

	public LiveData<List<Article>> getArticlesListLiveData() {
		return articlesListLiveData;
	}

	public void onSearchInputChanged(CharSequence sequence, int start, int before, int count) {
		if (count == 0 && searchInputError.getValue() == null) {
			searchInputError.setValue(SEARCH_INPUT_ERROR);
		} else if (count > 0 && searchInputError.getValue() != null) {
			searchInputError.setValue(null);
		}
		Timber.d("Search input changed, count: %d, error: %s", count, searchInputError.getValue());
	}

	public void onClickSearch(@SuppressWarnings("unused") View view) {
		final String searchInputString = searchInput.getValue();
		if (searchInputString == null || searchInputString.length() == 0) {
			Timber.d("Cannot search with empty input, error: %s", searchInputError.getValue());
			// TODO: Show some error
			return;
		}
		updateArticles();
	}

	public void updateArticles() {
		switch (newsType) {
			case TOP_HEADLINES:
				getTopHeadlines();
				return;
			case EVERYTHING:
				getEverything();
				return;
			default:
				Timber.w("Invalid NewsType value");
		}
	}

	private void getTopHeadlines() {
		// TODO: Get query data from user input
		final TopHeadlinesQuery topHeadlinesQuery = TopHeadlinesQuery.builder()
				.category("technology")
				.build();
		Timber.d("Retrieving top headlines from newsRepository");
		articlesListLiveData = newsRepository.getTopHeadlines(topHeadlinesQuery);
	}

	private void getEverything() {
		// TODO: Get query data from user input
		final EverythingQuery everythingQuery = EverythingQuery.builder()
				.language("en")
				.q(searchInput.getValue())
				.build();
		Timber.d("Retrieving everything from newsRepository");
		articlesListLiveData = newsRepository.getEverything(everythingQuery);
	}
}

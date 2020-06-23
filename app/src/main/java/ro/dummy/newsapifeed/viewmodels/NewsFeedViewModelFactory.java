package ro.dummy.newsapifeed.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ro.dummy.newsapifeed.data.remote.NewsApiConsumerService.NewsType;

public class NewsFeedViewModelFactory implements ViewModelProvider.Factory {
	private final NewsType newsType;

	public NewsFeedViewModelFactory(NewsType newsType) {
		this.newsType = newsType;
	}

	@SuppressWarnings("unchecked")
	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		if (modelClass.isAssignableFrom(NewsFeedViewModel.class)) {
			return (T) new NewsFeedViewModel(newsType);
		}
		throw new IllegalStateException("Can only create ArticleViewModels");
	}
}
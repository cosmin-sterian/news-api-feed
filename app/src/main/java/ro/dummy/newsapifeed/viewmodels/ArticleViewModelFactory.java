package ro.dummy.newsapifeed.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ro.dummy.newsapifeed.data.local.Article;

public class ArticleViewModelFactory implements ViewModelProvider.Factory {
	private final Article article;

	public ArticleViewModelFactory(Article article) {
		this.article = article;
	}

	@SuppressWarnings("unchecked")
	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		if (modelClass.isAssignableFrom(ArticleViewModel.class)) {
			return (T) new ArticleViewModel(article);
		}
		throw new IllegalStateException("Can only create ArticleViewModels");
	}
}

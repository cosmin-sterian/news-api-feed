package ro.dummy.newsapifeed.viewmodels;

import androidx.lifecycle.ViewModel;

import ro.dummy.newsapifeed.data.local.Article;

public class ArticleViewModel extends ViewModel {
	private final Article article;

	public ArticleViewModel(Article article) {
		this.article = article;
	}

	public ArticleViewModel(int articleId) {

	}

	public String getTitle() {
		return article.getTitle();
	}

	public String getDescription() {
		return article.getDescription();
	}

	public String getAuthor() {
		return article.getAuthor();
	}

	public String getCategory() {
		return article.getCategory();
	}

	@Override
	public String toString() {
		return article.toString();
	}

	/*
	 * TODO: Data should be retrieved here (not in the fragment)
	 *  and eventually returned by the index of the RecyclerView
	 *  which should be set in the VM's constructor
	 */

	/*
	 * TODO: If reusing this ViewModel for both the RecyclerView
	 *  and the CardView, consider Factory Pattern
	 */
}

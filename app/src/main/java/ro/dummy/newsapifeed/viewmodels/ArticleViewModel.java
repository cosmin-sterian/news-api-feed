package ro.dummy.newsapifeed.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import ro.dummy.newsapifeed.data.local.Article;

public class ArticleViewModel extends ViewModel {
	private Article article;

	public ArticleViewModel(Article article) {
		this.article = article;
	}

	public String getTitle() {
		return article.getTitle();
	}

	public String getDescription() {
		return article.getDescription();
	}

	public String getAuthor() {
		String author = article.getAuthor();
		if (author == null || author.length() == 0) {
			author = "Unknown author";
		}
		return "by " + author;
	}

	public String getSourceName() {
		return article.getSource().getName();
	}

	public String getCategory() {
		return article.getCategory();
	}

	public String getPublishedAt() {
		final String publishedAt = article.getPublishedAt();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
		final String dateTimeWithoutTimezone = publishedAt.substring(0, publishedAt.indexOf('Z'));
		final LocalDateTime localDateTime = LocalDateTime.parse(dateTimeWithoutTimezone);
		return localDateTime.format(dateTimeFormatter);
		/*
		 * TODO: Take a look at LocalDateTime.until,
		 *  maybe I could compute "x mins/hrs/days ago"
		 */
	}

	@NonNull
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

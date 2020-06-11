package ro.dummy.newsapifeed.data.remote;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import ro.dummy.newsapifeed.data.local.Article;

@AllArgsConstructor
public class ArticleDto {
	public final String title;
	@SerializedName("description")
	public final String description;
	public final String author;
	public final String url;
	public final Source source;

	@AllArgsConstructor
	public static class Source {
		public final String id;
		public final String name;
	}

	public Article toDomainArticle() {
		// TODO: Update category
		return new Article(url, title, description, author, null);
	}
}

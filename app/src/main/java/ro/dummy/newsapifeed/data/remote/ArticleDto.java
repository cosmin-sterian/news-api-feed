package ro.dummy.newsapifeed.data.remote;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import ro.dummy.newsapifeed.data.local.Article;
import ro.dummy.newsapifeed.data.local.ArticleSource;

@AllArgsConstructor
public class ArticleDto {
	public final String title;
	@SerializedName("description")
	public final String description;
	public final String author;
	public final String url;
	public final String publishedAt;
	public final ArticleSourceDto source;

	@AllArgsConstructor
	public static class ArticleSourceDto {
		public final String id;
		public final String name;

		public ArticleSource toDomainArticleSource() {
			return new ArticleSource(id, name);
		}
	}

	public Article toDomainArticle() {
		// TODO: Update category
		return new Article(url, title, description, author,
				null, publishedAt, source.toDomainArticleSource());
	}
}

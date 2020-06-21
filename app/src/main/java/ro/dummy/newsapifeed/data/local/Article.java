package ro.dummy.newsapifeed.data.local;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Article implements Serializable {
	@EqualsAndHashCode.Include
	private final String url;
	private final String title;
	private final String description;
	private final String author;
	private final String category;
	private final String publishedAt;
	private final String content;
	private final ArticleSource source;
}

package ro.dummy.newsapifeed.data.local;

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
public class Article {
	@EqualsAndHashCode.Include
	private final String url;
	private final String title;
	private final String description;
	private final String author;
	private final String category;
}

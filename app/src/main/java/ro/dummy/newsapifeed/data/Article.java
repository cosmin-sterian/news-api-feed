package ro.dummy.newsapifeed.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Article {
	@EqualsAndHashCode.Include
	private int id;
	private String title;
	private String description;
	private String author;
	private String category;
}

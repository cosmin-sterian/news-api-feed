package ro.dummy.newsapifeed.data;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Article {
	private int id;
	private String title;
	private String description;
	private String author;
	private String category;
}

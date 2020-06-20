package ro.dummy.newsapifeed.data.remote;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ArticlesResponse {
	public final String status;
	public final int totalResults;
	public final List<ArticleDto> articles;
}

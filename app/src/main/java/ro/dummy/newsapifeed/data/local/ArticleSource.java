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
public class ArticleSource {
	@EqualsAndHashCode.Include
	private final String id;
	private final String name;
}

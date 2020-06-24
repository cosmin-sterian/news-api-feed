package ro.dummy.newsapifeed.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Builder;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ro.dummy.newsapifeed.data.local.Article;
import timber.log.Timber;

public class NewsApiConsumerService {
	private static final String API_KEY = "e414868ebc1941c28554f7404913cbf9";
	private static final String API_BASE_URL = "https://newsapi.org/v2/";
	private static NewsApiConsumerService instance;
	private final NewsApi newsApi;
	private final MutableLiveData<List<Article>> articlesLiveData;
	private Disposable articlesResponseDisposable;

	private NewsApiConsumerService() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(s -> Timber.d(s));
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		newsApi = new Retrofit.Builder()
				.baseUrl(API_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
				.client(new OkHttpClient.Builder()
						.addInterceptor(interceptor)
						.build())
				.build()
				.create(NewsApi.class);
		articlesLiveData = new MutableLiveData<>(Collections.emptyList());
	}

	public LiveData<List<Article>> getArticlesListLiveData() {
		return articlesLiveData;
	}

	public LiveData<List<Article>> getTopHeadlines(TopHeadlinesQuery query) {
		collectResultFromArticlesResponse(
				newsApi.fetchTopHeadlines(API_KEY,
						query.getQ(), query.getCountry(),
						query.getCategory(), query.getSources()));
		return articlesLiveData;
	}

	public LiveData<List<Article>> getEverything(EverythingQuery query) {
		collectResultFromArticlesResponse(
				newsApi.fetchEverything(API_KEY,
						query.getQ(), query.getQInTitle(),
						query.getSources(), query.getLanguage()));
		return articlesLiveData;
	}

	private void collectResultFromArticlesResponse(Single<ArticlesResponse> articlesResponseSingle) {
		tryDisposeArticlesResponse();
		articlesResponseDisposable = articlesResponseSingle
				.subscribeOn(Schedulers.io())
				.map(articlesResponse -> articlesResponse.articles)
				.map(NewsApiConsumerService::articleDtoListToArticleList)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						articles -> {
							Timber.d("Articles retrieved successfully");
							articlesLiveData.setValue(articles);
							tryDisposeArticlesResponse();
						},
						throwable -> {
							Timber.w("Failed to retrieve articles");
							// TODO: Set a status LiveData to indicate error so UI shows toast
						}
				);
		/*
		 * TODO: Figure out when should it be disposed,
		 *  because it can't be disposed here (sort of a race condition)
		 */
//				.dispose();
	}

	private static List<Article> articleDtoListToArticleList(List<ArticleDto> articleDtos) {
		return articleDtos.stream()
				.map(ArticleDto::toDomainArticle)
				.collect(Collectors.toList());
	}

	private void tryDisposeArticlesResponse() {
		if (articlesResponseDisposable != null) {
			articlesResponseDisposable.dispose();
			articlesResponseDisposable = null;
		}
	}

	public static synchronized NewsApiConsumerService getInstance() {
		if (instance == null) {
			instance = new NewsApiConsumerService();
		}
		return instance;
	}

	@Builder
	@Getter
	public static class TopHeadlinesQuery {
		private String q;
		private String country;
		private String category;
		private Set<String> sources;

		public List<String> getSources() {
			return sources == null ? null : new LinkedList<>(sources);
		}
	}

	@Builder
	@Getter
	public static class EverythingQuery {
		private String q;
		private String qInTitle;
		private Set<String> sources;
		private Set<String> domains;
		private Set<String> excludeDomains;
		//		private Date from;
//		private Date to;
		private String language;
		private String sortBy;

		public List<String> getSources() {
			return sources == null ? null : new LinkedList<>(sources);
		}
	}

	public enum NewsType {
		TOP_HEADLINES, EVERYTHING
	}

	private interface NewsApi {

		@GET("everything")
		Single<ArticlesResponse> fetchEverything(@Query("apiKey") String apiKey,
												 @Query("q") String query,
												 @Query("qInTitle") String queryInTitle,
												 @Query("sources") List<String> sources,
												 @Query("language") String language);

		@GET("top-headlines")
		Single<ArticlesResponse> fetchTopHeadlines(@Query("apiKey") String apiKey,
												   @Query("q") String query,
												   @Query("country") String country,
												   @Query("category") String category,
												   @Query("sources") List<String> sources);
	}
}

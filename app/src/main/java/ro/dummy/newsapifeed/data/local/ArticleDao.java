package ro.dummy.newsapifeed.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface ArticleDao {
	@Query("SELECT * FROM Article")
	public Observable<List<Article>> loadArticles();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public Completable insertAllArticles(List<Article> articleEntities);
}

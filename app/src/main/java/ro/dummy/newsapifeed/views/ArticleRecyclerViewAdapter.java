package ro.dummy.newsapifeed.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.dummy.newsapifeed.R;
import ro.dummy.newsapifeed.data.Article;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Article}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> {

	private final List<Article> articleList;

	public ArticleRecyclerViewAdapter(List<Article> articleList) {
		this.articleList = articleList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.cardview_article, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		holder.article = articleList.get(position);
		holder.tvTitle.setText(articleList.get(position).getTitle());
		holder.tvDescription.setText(articleList.get(position).getDescription());
	}

	@Override
	public int getItemCount() {
		return articleList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		public final View mView;
		public final TextView tvTitle;
		public final TextView tvDescription;
		public Article article;

		public ViewHolder(View view) {
			super(view);
			mView = view;
			tvTitle = (TextView) view.findViewById(R.id.item_number);
			tvDescription = (TextView) view.findViewById(R.id.content);
		}

		@Override
		public String toString() {
			return super.toString() + " '" + tvDescription.getText() + "'";
		}
	}
}
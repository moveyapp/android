package il.co.moveyorg.movey.ui.main.feed;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.data.model.Post;

/**
 * Created by eladk on 1/13/18.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.PostViewHolder>  {

  private List<Post> posts;

  @Inject
  FeedAdapter() {
    posts = new ArrayList<>();
  }

  @Override
  public FeedAdapter.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(FeedAdapter.PostViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.view_hex_color)
    View hexColorView;
    @BindView(R.id.text_name)
    TextView nameTextView;
    @BindView(R.id.text_email) TextView emailTextView;

    public PostViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

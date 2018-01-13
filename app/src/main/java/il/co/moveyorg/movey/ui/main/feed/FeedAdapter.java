package il.co.moveyorg.movey.ui.main.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.PostViewHolder> {

  private List<Post> posts;

  @Inject
  FeedAdapter() {
    posts = new ArrayList<>();
  }

  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }


  @Override
  public FeedAdapter.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.movey_post_item, parent, false);
    return new FeedAdapter.PostViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(FeedAdapter.PostViewHolder holder, int position) {
    Post post = posts.get(position);
    holder.content.setText(post.getContent());
    holder.username.setText(post.getUserName());
  }

  @Override
  public int getItemCount() {
    return posts.size();
  }

  class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.post_item_username)
    TextView username;
    @BindView(R.id.post_item_content)
    TextView content;

    @BindView(R.id.post_item_like_btn)
    Button likeBtn;

    @BindView(R.id.post_item_comment_btn)
    Button commentBtn;

    PostViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

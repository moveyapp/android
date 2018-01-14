package il.co.moveyorg.movey.ui.main.feed.post_page;

/**
 * Created by eladk on 1/14/18.
 */

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
import il.co.moveyorg.movey.data.model.Comment;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>{

  private List<Comment> comments;
  private OnCommentReplyClickListener onCommentReplyClickListener;

  @Inject
  CommentsAdapter() {
    comments = new ArrayList<>();
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public void setOnCommentReplyClickListener(OnCommentReplyClickListener onCommentReplyClickListener) {
    this.onCommentReplyClickListener = onCommentReplyClickListener;
  }

  @Override
  public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.layout_comment_item, parent, false);
    return new CommentViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(CommentViewHolder holder, int position) {
    Comment comment = comments.get(position);
    holder.content.setText(comment.getContent());
    holder.username.setText(comment.getUserName());
    holder.replyBtn.setOnClickListener(view -> onCommentReplyClickListener.onCommentReplyClick(comments.get(position)));
  }

  @Override
  public int getItemCount() {
    return comments.size();
  }

  public void addComment(Comment comment) {
    comments.add(0,comment);
  }

  class CommentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.comment_item_username_textview)
    TextView username;

    @BindView(R.id.comment_item_content_textview)
    TextView content;

    @BindView(R.id.comment_item_like_btn)
    Button likeBtn;

    @BindView(R.id.comment_item_reply_btn)
    Button replyBtn;

    CommentViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  interface OnCommentReplyClickListener {
    void onCommentReplyClick(Comment comment);
  }


}

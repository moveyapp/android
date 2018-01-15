package il.co.moveyorg.movey.ui.main.feed.post_page;

/**
 * Created by eladk on 1/14/18.
 */

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import il.co.moveyorg.movey.data.firebase.FirebaseDbHelper;
import il.co.moveyorg.movey.data.model.Comment;
import il.co.moveyorg.movey.data.model.Post;
import il.co.moveyorg.movey.ui.base.BasePresenter;
import il.co.moveyorg.movey.ui.main.feed.FeedMvpView;


public class PostPresenter extends BasePresenter<PostMvpView> {

  private Post post;

  @Inject
  PostPresenter() {
  }

  public void setPost(Post post) {
    this.post = post;
  }

  public void loadComments() {
    if (post == null) return;

    FirebaseDbHelper.Comments.getRef().addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Comment comment = dataSnapshot.getValue(Comment.class);
        String postId = post.getId();
        if (postId != null && postId.equals(comment.getPostId())){
          getMvpView().addComment(comment);
        }
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {

      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  public void createNewComment(String content) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String createdAt = dateFormat.format(new Date());
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user == null) {
      return;
    }
    Comment newComment = new Comment();
    newComment.setContent(content);
    newComment.setCreatedAt(createdAt);
    newComment.setUserName(user.getDisplayName());
    newComment.setUserId(user.getUid());
    FirebaseDbHelper.Comments.saveComment(newComment);
    getMvpView().dismissCreateComment();
  }
}

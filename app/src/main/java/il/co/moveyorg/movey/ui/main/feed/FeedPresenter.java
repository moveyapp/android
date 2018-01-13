package il.co.moveyorg.movey.ui.main.feed;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import il.co.moveyorg.movey.data.firebase.FirebaseDbHelper;
import il.co.moveyorg.movey.data.model.Post;
import il.co.moveyorg.movey.data.model.User;
import il.co.moveyorg.movey.ui.base.BasePresenter;
import il.co.moveyorg.movey.util.RxEventBus;

/**
 * Created by eladk on 11/27/17.
 */

public class FeedPresenter extends BasePresenter<FeedMvpView> {



  @Inject
  FeedPresenter() {

  }


  public void loadFeed() {
    FirebaseDbHelper.Posts.getRef().addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Post post = dataSnapshot.getValue(Post.class);
        getMvpView().addPost(post);
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

  public void createNewPost(String content) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String createdAt = dateFormat.format(new Date());
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if(user == null) {
      return;
    }
    Post newPost = new Post();
    newPost.setContent(content);
    newPost.setCreatedAt(createdAt);
    newPost.setUserName(user.getDisplayName());
    newPost.setUserId(user.getUid());
    FirebaseDbHelper.Posts.savePost(newPost);
  }
}

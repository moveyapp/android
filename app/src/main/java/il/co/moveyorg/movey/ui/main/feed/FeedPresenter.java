package il.co.moveyorg.movey.ui.main.feed;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import il.co.moveyorg.movey.data.firebase.FirebaseDbHelper;
import il.co.moveyorg.movey.data.model.Post;
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
}

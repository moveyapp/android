package il.co.moveyorg.movey.data.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;
import javax.inject.Singleton;

import il.co.moveyorg.movey.data.model.Comment;
import il.co.moveyorg.movey.data.model.Post;
import il.co.moveyorg.movey.data.model.User;

/**
 * Created by eladk on 11/30/17.
 */

@Singleton
public class FirebaseDbHelper {
     private static final String MAIN_SOCIAL_PATH = "social";
     private static final String DB_USERS_PATH = "social/users";
     private static final String DB_POSTS_PATH = "social/posts";
     private static final String DB_COMMENTS_PATH = "social/comments";

    @Inject
    public FirebaseDbHelper() {
    }

    public static class Users {
        public static DatabaseReference getUserDbRef(String uid) {
            return FirebaseDatabase.getInstance()
                    .getReference(DB_USERS_PATH)
                    .child(uid);
        }

        public static ValueEventListener getUser(String uid, ValueEventListener valueEventListener) {
           return getUserDbRef(uid).addValueEventListener(valueEventListener);
        }

        public static void saveUser(User user) {
            if (user != null){
                getUserDbRef(user.getId()).setValue(user);
            }
        }
    }

    public static class Posts {

        public static DatabaseReference getRef(){
            return FirebaseDatabase.getInstance()
                .getReference(DB_POSTS_PATH);
        }

        public static DatabaseReference getPostDbRef(String uid) {
            return getRef()
                .child(uid);
        }

//        public static ValueEventListener getUser(String uid, ValueEventListener valueEventListener) {
//            return getUserDbRef(uid).addValueEventListener(valueEventListener);
//        }


        public static void savePost(Post post) {
            if (post != null){
                getRef().push().setValue(post);
            }
        }
    }


  public static class Comments {

      public static DatabaseReference getRef(){
          return FirebaseDatabase.getInstance()
              .getReference(DB_COMMENTS_PATH);
      }

      public static DatabaseReference getCommentsOfPost(String postId) {
          return null;
      }

      public static void saveComment(Comment comment) {
          if (comment != null){
              getRef().push().setValue(comment);
          }
      }


    }
}

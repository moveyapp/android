package il.co.moveyorg.movey.data.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;
import javax.inject.Singleton;

import il.co.moveyorg.movey.data.model.User;

/**
 * Created by eladk on 11/30/17.
 */

@Singleton
public class FirebaseDbHelper {
     private static final String MAIN_SOCIAL_PATH = "social";
     private static final String DB_USERS_PATH = "social/users";

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


}

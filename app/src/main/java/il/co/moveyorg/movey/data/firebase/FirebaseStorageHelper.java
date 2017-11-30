package il.co.moveyorg.movey.data.firebase;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kelvinapps.rxfirebase.RxFirebaseStorage;

import rx.Observable;


/**
 * Created by eladk on 11/30/17.
 */

public class FirebaseStorageHelper {
    private static final String STORAGE_USERS_PATH = "users";
    private static final String STORAGE_USER_AVATAR = "avatar";

    public static class Users {

        static StorageReference getUserStorageRef(String uid) {
            return FirebaseStorage.getInstance()
                    .getReference(STORAGE_USERS_PATH)
                    .child(uid);
        }

        static StorageReference getUserAvatarRef(String uid) {
            return getUserStorageRef(uid).child(STORAGE_USER_AVATAR);
        }

        public static Observable<UploadTask.TaskSnapshot> uploadUserProfileImage(String uid, Uri file) {
            return RxFirebaseStorage.putFile(getUserAvatarRef(uid), file);
        }
    }
}

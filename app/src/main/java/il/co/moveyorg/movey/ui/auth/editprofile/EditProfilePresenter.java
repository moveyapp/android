package il.co.moveyorg.movey.ui.auth.editprofile;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import javax.inject.Inject;

import il.co.moveyorg.movey.data.firebase.FirebaseDbHelper;
import il.co.moveyorg.movey.data.firebase.FirebaseStorageHelper;
import il.co.moveyorg.movey.data.model.User;
import il.co.moveyorg.movey.ui.base.BasePresenter;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;


/**
 * Created by eladk on 11/30/17.
 */

public class EditProfilePresenter extends BasePresenter<EditProfileMvpView> implements ValueEventListener {

    @Inject
    FirebaseAuth firebaseAuth;

    FirebaseUser currentUser;

    Uri imageUriToUpload;
    private User currentUserModel;

    @Inject
    public EditProfilePresenter() {
    }

    public void setImageUriToUpload(Uri uri) {
        imageUriToUpload = uri;
    }

    public void init() {
        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            FirebaseDbHelper.Users.getUser(currentUser.getUid(), this);
        }
    }

    void syncUserModel(String username,String firstname, String lastname, String country){
        currentUserModel.setUserName(username);
        currentUserModel.setFirstName(firstname);
        currentUserModel.setLastName(lastname);
        currentUserModel.setCountry(country);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        currentUserModel = dataSnapshot.getValue(User.class);
        if (currentUserModel != null) {
            getMvpView().showProfile(currentUserModel);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    }

    public void saveProfile() {

        if (currentUser != null) {

            if(imageUriToUpload != null) {
                FirebaseStorageHelper.Users
                        .uploadUserProfileImage(currentUser.getUid(), imageUriToUpload)
                        .subscribe(new Observer<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onCompleted() {
                                FirebaseDbHelper.Users.saveUser(currentUserModel);
                                getMvpView().onSaveProfile();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(UploadTask.TaskSnapshot taskSnapshot) {
                                Uri firebaseUploadedUrl = taskSnapshot.getDownloadUrl();
                                if (firebaseUploadedUrl != null) {
                                    currentUserModel.setProfileImageUrl(firebaseUploadedUrl.toString());
                                }
                                FirebaseDbHelper.Users.saveUser(currentUserModel);
                                getMvpView().onSaveProfile();
                            }
                        });
            } else {
                FirebaseDbHelper.Users.saveUser(currentUserModel);
                getMvpView().onSaveProfile();
            }
        }

    }
}

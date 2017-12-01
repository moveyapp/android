package il.co.moveyorg.movey.ui.auth.login;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import il.co.moveyorg.movey.ui.base.BasePresenter;

/**
 * Created by eladk on 11/30/17.
 */

public class LoginPresenter extends BasePresenter<LoginMvpView> {

    @Inject
    FirebaseAuth firebaseAuth;

    FirebaseUser currentUser;

    @Inject
    public LoginPresenter() {
    }

    public void init() {
        currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null) {
            getMvpView().onAlreadyLoggedIn();
        }
    }

    public void login(FragmentActivity context, String email, String password){
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            getMvpView().showToast("Please enter email");
            return;
        }

        if(TextUtils.isEmpty(password)){
            getMvpView().showToast("Please enter password");
            return;
        }

        getMvpView().showLoading();
        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(context, task -> {
                    getMvpView().hideLoading();
                    if(task.isSuccessful()){
                        getMvpView().onLoginSuccessful();
                    }
                });

    }
}

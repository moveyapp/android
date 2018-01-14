package il.co.moveyorg.movey.ui.auth.login;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Patterns;

import com.google.android.gms.common.api.GoogleApiClient;
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

  private static final String TAG = "SignInActivity";
  private static final int RC_SIGN_IN_GOOGLE = 9001;

  private GoogleApiClient mGoogleApiClient;

  @Inject
  public LoginPresenter() {
  }

  public void init() {
    currentUser = firebaseAuth.getCurrentUser();
    if (currentUser != null) {
      getMvpView().onAlreadyLoggedIn();
    }
  }

  public final static boolean isValidEmail(String email) {
    return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
  }

  public boolean isEmail(String email) {
    if (TextUtils.isEmpty(email)) {
      return true;
    }
    return false;
  }

  public boolean isPassword(String password) {
    if (TextUtils.isEmpty(password)) {
      getMvpView().showToast("Please enter password");
      return true;
    }
    return false;
  }

  public void login(FragmentActivity context, String email, String password) {
    if (isEmail(email)) {
      getMvpView().showToast("Please enter email");
      return;
    }
    if (isPassword(password)) {
      getMvpView().showToast("Please enter password");
      return;
    }

    getMvpView().showLoading();

    firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(context, task -> {
          getMvpView().hideLoading();
          if (task.isSuccessful()) {
            getMvpView().onLoginSuccessful();
          }
        });

  }


}

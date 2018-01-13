package il.co.moveyorg.movey.ui.auth.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.ui.base.BaseActivity;
import il.co.moveyorg.movey.ui.base.BasePresenter;

/**
 * Created by eladk on 11/30/17.
 */

public class LoginPresenter extends BasePresenter<LoginMvpView> {

  @Inject
  public FirebaseAuth firebaseAuth;

  public FirebaseUser currentUser;

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


  public void login(FragmentActivity context, String email, String password) {
    if (TextUtils.isEmpty(email)) {
      getMvpView().showToast("Please enter email");
      return;
    }

    if (TextUtils.isEmpty(password)) {
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

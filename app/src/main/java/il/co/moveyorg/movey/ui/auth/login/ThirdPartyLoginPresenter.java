package il.co.moveyorg.movey.ui.auth.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.data.firebase.FirebaseDbHelper;
import il.co.moveyorg.movey.data.model.User;
import il.co.moveyorg.movey.ui.auth.AuthMvpView;
import il.co.moveyorg.movey.ui.base.BaseActivity;
import il.co.moveyorg.movey.ui.base.BasePresenter;

/**
 * Created by eladk on 1/5/18.
 */

public class ThirdPartyLoginPresenter extends BasePresenter<AuthMvpView> implements GoogleApiClient.OnConnectionFailedListener {

  @Inject
  FirebaseAuth firebaseAuth;

  private static final int RC_SIGN_IN_GOOGLE = 9001;

  private GoogleApiClient mGoogleApiClient;
  private FirebaseUser currentUser;

  @Inject
  public ThirdPartyLoginPresenter() {
  }

  public void initGoogleSignIn(BaseActivity baseActivity) {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(baseActivity.getString(R.string.default_web_client_id))
        .requestEmail()
        .build();


    mGoogleApiClient = new GoogleApiClient.Builder(baseActivity)
        .enableAutoManage(baseActivity, this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();

  }

  public void loginWithGoogle(BaseActivity loginFragment) {
    getMvpView().showLoading();
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    loginFragment.startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
  }


  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    getMvpView().onAuthFailed();
  }

  public void handleGoogleLoginResult(Intent data) {

    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

    if (result.isSuccess()) {
      GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
      AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
      firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
        getMvpView().hideLoading();

        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
          User user = new
              User.Builder(currentUser.getUid(), currentUser.getEmail())
              .build();

          FirebaseDbHelper.Users.saveUser(user);
        }
        if (task.isSuccessful()) {
          getMvpView().onAuthSuccessful();
        } else {
          getMvpView().onAuthFailed();
        }
      });
    } else {
      getMvpView().onAuthFailed();
    }
  }
}


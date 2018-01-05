package il.co.moveyorg.movey.ui.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.ui.auth.login.LoginPresenter;
import il.co.moveyorg.movey.ui.auth.login.ThirdPartyLoginPresenter;
import il.co.moveyorg.movey.ui.auth.register.RegisterFragment;
import il.co.moveyorg.movey.ui.base.BaseActivity;

public class AuthActivity extends BaseActivity implements AuthMvpView, View.OnClickListener {


  private static final int RC_SIGN_IN_GOOGLE = 9001;

  @BindView(R.id.auth_activity_skip_btn)
  Button skipBtn;

  @BindView(R.id.auth_activity_btn_google_sign_in)
  SignInButton buttonGoogleSignIn;

  @Inject ThirdPartyLoginPresenter presenter;

  private FirebaseAuth firebaseAuth;
  private ActionBar actionBar;
  private ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth);
    ButterKnife.bind(this);

    activityComponent().inject(this);
    presenter.attachView(this);
    presenter.initGoogleSignIn(this);

    firebaseAuth = FirebaseAuth.getInstance();
    actionBar = getSupportActionBar();

    checkForCurrentUser();

    progressDialog = new ProgressDialog(this);
    skipBtn.setOnClickListener(this);
    buttonGoogleSignIn.setOnClickListener(this);

  }

  public void setActionBarTitle(String title) {
    if (actionBar != null) {
      actionBar.setTitle(title);
    }
  }

  void checkForCurrentUser(){
    if (firebaseAuth.getCurrentUser() == null) {
      FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      fragmentTransaction.replace(R.id.activity_auth_fragment_container, new RegisterFragment());
      fragmentTransaction.commit();
    } else {
      finish();
    }
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.auth_activity_skip_btn: {
        finish();
        break;
      }
      case R.id.auth_activity_btn_google_sign_in: {
        presenter.loginWithGoogle(this);
        break;
      }
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == RC_SIGN_IN_GOOGLE){
      presenter.handleGoogleLoginResult(data);
    }
  }

  @Override
  public void onAuthSuccessful() {
    finish();
  }

  @Override
  public void onAuthFailed() {

  }

  @Override
  public void showLoading() {
    progressDialog.setMessage("Authenticating in please wait...");
    progressDialog.show();
  }

  @Override
  public void hideLoading() {
    progressDialog.dismiss();
  }

  @Override
  public void showToast(String msg) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }
}
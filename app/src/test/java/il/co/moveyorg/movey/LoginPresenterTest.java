package il.co.moveyorg.movey;

import android.support.v4.app.FragmentActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import il.co.moveyorg.movey.ui.auth.login.LoginMvpView;
import il.co.moveyorg.movey.ui.auth.login.LoginPresenter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by eladk on 1/11/18.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

  @Mock
  LoginMvpView mMockLoginMvpView;
  @Mock
  FragmentActivity fragmentActivity;

  private LoginPresenter loginPresenter;


  @Before
  public void setUp() {
    loginPresenter = new LoginPresenter();
    loginPresenter.attachView(mMockLoginMvpView);
    loginPresenter.firebaseAuth = FirebaseAuth.getInstance();
    loginPresenter.init();
  }

  @After
  public void tearDown() {
    loginPresenter.detachView();
  }

  @Test
  public void loginTriggersLoginSuccessful() {
    loginPresenter.login(fragmentActivity,"elad.636@gmail.com","9352221");
    verify(mMockLoginMvpView).onLoginSuccessful();
  }

}



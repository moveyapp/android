package il.co.moveyorg.movey.ui.auth.login;

import il.co.moveyorg.movey.ui.base.MvpView;

/**
 * Created by eladk on 11/30/17.
 */

public interface LoginMvpView extends MvpView{
    void onLoginSuccessful();
    void onLoginFailed();
}

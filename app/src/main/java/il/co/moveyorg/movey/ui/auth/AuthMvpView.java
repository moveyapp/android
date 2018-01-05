package il.co.moveyorg.movey.ui.auth;


import il.co.moveyorg.movey.ui.base.MvpView;

/**
 * Created by eladk on 1/5/18.
 */

public interface AuthMvpView extends MvpView{
    void onAuthSuccessful();
    void onAuthFailed();
    void showLoading();
    void hideLoading();
    void showToast(String msg);

}

package il.co.moveyorg.movey.ui.auth.register;

import il.co.moveyorg.movey.ui.base.MvpView;

/**
 * Created by eladk on 11/30/17.
 */

public interface RegisterMvpView extends MvpView{
    void onRegisterationSuccessful();
    void onRegisterationFailed();
    void showLoading();
    void onAlreadyRegistered();
    void hideLoading();
    void showToast(String msg);
}

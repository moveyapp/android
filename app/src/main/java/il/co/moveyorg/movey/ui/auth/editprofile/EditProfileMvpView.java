package il.co.moveyorg.movey.ui.auth.editprofile;

import il.co.moveyorg.movey.data.model.User;
import il.co.moveyorg.movey.ui.base.MvpView;

/**
 * Created by eladk on 11/30/17.
 */

public interface EditProfileMvpView extends MvpView {
    void showProfile(User user);
    void onSaveProfile();
    void onError();
    void showToast(String msg);
}

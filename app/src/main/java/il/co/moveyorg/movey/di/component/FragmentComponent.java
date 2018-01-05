package il.co.moveyorg.movey.di.component;

/**
 * Created by eladk on 11/27/17.
 */

import dagger.Subcomponent;
import il.co.moveyorg.movey.di.PerActivity;
import il.co.moveyorg.movey.di.module.FragmentModule;
import il.co.moveyorg.movey.ui.auth.login.LoginFragment;
import il.co.moveyorg.movey.ui.auth.register.RegisterFragment;
import il.co.moveyorg.movey.ui.main.feed.FeedFragment;
import il.co.moveyorg.movey.ui.main.profile.ProfileFragment;

/**
 * This component inject dependencies to all fragments across the application
 */
@PerActivity
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(FeedFragment feedFragment);
    void inject(ProfileFragment profileFragment);
    void inject(RegisterFragment registerFragment);
    void inject(LoginFragment loginFragment);
}

package il.co.moveyorg.movey.di.component;

import dagger.Subcomponent;
import il.co.moveyorg.movey.di.PerActivity;
import il.co.moveyorg.movey.di.module.ActivityModule;
import il.co.moveyorg.movey.ui.auth.AuthActivity;
import il.co.moveyorg.movey.ui.auth.editprofile.EditProfileActivity;
import il.co.moveyorg.movey.ui.ribot.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(MainActivity mainActivity);

  void inject(EditProfileActivity editProfileActivity);

  void inject(AuthActivity authActivity);
}

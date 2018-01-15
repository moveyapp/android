package il.co.moveyorg.movey.di.component;

import dagger.Subcomponent;
import il.co.moveyorg.movey.di.PerActivity;
import il.co.moveyorg.movey.di.module.ActivityModule;
import il.co.moveyorg.movey.ui.auth.AuthActivity;
import il.co.moveyorg.movey.ui.auth.editprofile.EditProfileActivity;
import il.co.moveyorg.movey.ui.main.feed.post_page.PostActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {


  void inject(EditProfileActivity editProfileActivity);

  void inject(AuthActivity authActivity);

  void inject(PostActivity postActivity);
}

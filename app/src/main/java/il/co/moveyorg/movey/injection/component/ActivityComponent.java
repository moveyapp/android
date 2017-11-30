package il.co.moveyorg.movey.injection.component;

import dagger.Subcomponent;
import il.co.moveyorg.movey.injection.PerActivity;
import il.co.moveyorg.movey.injection.module.ActivityModule;
import il.co.moveyorg.movey.ui.auth.editprofile.EditProfileActivity;
import il.co.moveyorg.movey.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(EditProfileActivity editProfileActivity);
}

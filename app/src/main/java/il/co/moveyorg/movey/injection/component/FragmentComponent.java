package il.co.moveyorg.movey.injection.component;

/**
 * Created by eladk on 11/27/17.
 */

import dagger.Subcomponent;
import il.co.moveyorg.movey.injection.PerActivity;
import il.co.moveyorg.movey.injection.module.FragmentModule;
import il.co.moveyorg.movey.ui.movey.feed.FeedFragment;
import il.co.moveyorg.movey.ui.movey.profile.ProfileFragment;

/**
 * This component inject dependencies to all fragments across the application
 */
@PerActivity
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(FeedFragment feedFragment);
    void inject(ProfileFragment profileFragment);
}

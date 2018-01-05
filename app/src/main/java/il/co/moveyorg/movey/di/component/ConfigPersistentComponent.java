package il.co.moveyorg.movey.di.component;

import dagger.Component;
import il.co.moveyorg.movey.di.ConfigPersistent;
import il.co.moveyorg.movey.di.module.ActivityModule;
import il.co.moveyorg.movey.di.module.FragmentModule;
import il.co.moveyorg.movey.ui.base.BaseActivity;

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroyed during configuration changes. Check {@link BaseActivity} to see how this components
 * survives configuration changes.
 * Use the {@link ConfigPersistent} scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

    FragmentComponent fragmentComponent(FragmentModule fragmentModule);
}
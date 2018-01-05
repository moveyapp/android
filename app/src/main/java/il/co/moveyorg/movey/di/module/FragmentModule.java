package il.co.moveyorg.movey.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
import il.co.moveyorg.movey.di.ActivityContext;

/**
 * Created by eladk on 11/27/17.
 */

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mFragment.getActivity();
    }
}

package il.co.moveyorg.movey.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LongSparseArray;

import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.atomic.AtomicLong;

import timber.log.Timber;
import il.co.moveyorg.movey.MoveyApplication;
import il.co.moveyorg.movey.di.component.ConfigPersistentComponent;
import il.co.moveyorg.movey.di.component.DaggerConfigPersistentComponent;
import il.co.moveyorg.movey.di.component.FragmentComponent;
import il.co.moveyorg.movey.di.module.FragmentModule;

/**
 * Created by eladk on 11/27/17.
 */
/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */

public class BaseFragment extends Fragment {


        private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
        private static final AtomicLong NEXT_ID = new AtomicLong(0);
        private static final LongSparseArray<ConfigPersistentComponent>
                sComponentsMap = new LongSparseArray<>();

        private FragmentComponent mFragmentComponent;
        private long mActivityId;
    private FirebaseAuth firebaseAuth;

    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            firebaseAuth = FirebaseAuth.getInstance();

            // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
            // being called after a configuration change.
            mActivityId = savedInstanceState != null ?
                    savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();

            ConfigPersistentComponent configPersistentComponent = sComponentsMap.get(mActivityId, null);

            if (configPersistentComponent == null) {
                Timber.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
                configPersistentComponent = DaggerConfigPersistentComponent.builder()
                        .applicationComponent(MoveyApplication.get(getActivity()).getComponent())
                        .build();
                sComponentsMap.put(mActivityId, configPersistentComponent);
            }
            mFragmentComponent = configPersistentComponent.fragmentComponent(new FragmentModule(this));
        }

    protected boolean isUserLoggedIn() {
        return firebaseAuth.getCurrentUser() == null;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public FragmentComponent fragmentComponent() {
            return mFragmentComponent;
        }

    }



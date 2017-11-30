package il.co.moveyorg.movey.injection.component;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Singleton;

import dagger.Component;
import il.co.moveyorg.movey.data.DataManager;
import il.co.moveyorg.movey.data.SyncService;
import il.co.moveyorg.movey.data.firebase.FirebaseDbHelper;
import il.co.moveyorg.movey.data.local.DatabaseHelper;
import il.co.moveyorg.movey.data.local.PreferencesHelper;
import il.co.moveyorg.movey.data.remote.RibotsService;
import il.co.moveyorg.movey.injection.ApplicationContext;
import il.co.moveyorg.movey.injection.module.ApplicationModule;
import il.co.moveyorg.movey.util.RxEventBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    RibotsService ribotsService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();
    FirebaseAuth firebaseAuth();
    FirebaseDbHelper firebaseDbHelper();
}

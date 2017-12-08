package il.co.moveyorg.movey.ui.auth.register;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.eventbus.Subscribe;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.reactivestreams.Subscription;

import java.util.function.Consumer;

import javax.inject.Inject;

import il.co.moveyorg.movey.data.firebase.FirebaseDbHelper;
import il.co.moveyorg.movey.data.model.User;
import il.co.moveyorg.movey.ui.base.BasePresenter;
import il.co.moveyorg.movey.util.RxEventBus;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import rx.Observer;
import rx.Subscriber;
import rx.observers.TestSubscriber;

/**
 * Created by eladk on 11/30/17.
 */

public class RegisterPresenter extends BasePresenter<RegisterMvpView> {

    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    FirebaseDbHelper firebaseDbHelper;

    FirebaseUser currentUser;

    @Inject
    public RegisterPresenter() {
    }

    @Override
    public void attachView(RegisterMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }



    void init() {
        currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null) {
            getMvpView().onAlreadyRegistered();
        }
    }

    void register(FragmentActivity context, String email, String password) {
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            getMvpView().showToast("Please enter email");
            return;
        }

        if(TextUtils.isEmpty(password)){
            getMvpView().showToast("Please enter password");
            return;
        }

        getMvpView().showLoading();
        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(context, task -> {
                    //checking if success
                    if(task.isSuccessful()){
                        getMvpView().onRegisterationSuccessful();

                        currentUser = firebaseAuth.getCurrentUser();
                        if(currentUser != null) {
                            User user = new
                            User.Builder(currentUser.getUid(),currentUser.getEmail())
                                .build();

                            FirebaseDbHelper.Users.saveUser(user);
                        }
                    }
                    else{
                        getMvpView().onRegisterationFailed();
                    }
                    getMvpView().hideLoading();
                });
    }


}

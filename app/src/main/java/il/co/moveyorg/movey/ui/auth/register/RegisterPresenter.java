package il.co.moveyorg.movey.ui.auth.register;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import il.co.moveyorg.movey.ui.base.BasePresenter;

/**
 * Created by eladk on 11/30/17.
 */

public class RegisterPresenter extends BasePresenter<RegisterMvpView> {

    @Inject
    FirebaseAuth firebaseAuth;

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
        if(firebaseAuth.getCurrentUser() != null) {
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
                .addOnCompleteListener(context,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            getMvpView().onRegisterationSuccessful();
//                            User user = new
//                                    User.Builder(firebaseAuth.getCurrentUser().getUid(),firebaseAuth.getCurrentUser().getEmail())
//                                    .build();
//                            firebaseDb.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
//
                        }
                        else{
                            //display some message here
                            getMvpView().onRegisterationFailed();
                        }
                        getMvpView().hideLoading();
                    }
                });
    }


}

package il.co.moveyorg.movey.ui.auth;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.ui.auth.register.RegisterFragment;
import il.co.moveyorg.movey.ui.base.BaseActivity;

public class AuthActivity extends BaseActivity {



    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser() == null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.activity_auth_fragment_container, new RegisterFragment());
            fragmentTransaction.commit();
        } else {
            finish();
        }
    }


}
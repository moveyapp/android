package il.co.moveyorg.movey.ui.auth;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.ui.auth.register.RegisterFragment;
import il.co.moveyorg.movey.ui.base.BaseActivity;

public class AuthActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.auth_activity_skip_btn)
    Button skipBtn;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        actionBar = getSupportActionBar();

        if(firebaseAuth.getCurrentUser() == null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.activity_auth_fragment_container, new RegisterFragment());
            fragmentTransaction.commit();
        } else {
            finish();
        }

        skipBtn.setOnClickListener(this);
    }

    public void setActionBarTitle(String title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auth_activity_skip_btn: {
                finish();
                break;
            }
        }
    }
}
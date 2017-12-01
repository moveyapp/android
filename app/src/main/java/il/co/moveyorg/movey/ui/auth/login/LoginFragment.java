package il.co.moveyorg.movey.ui.auth.login;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.ui.auth.register.RegisterFragment;
import il.co.moveyorg.movey.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener, LoginMvpView {


    @BindView(R.id.buttonSignin)
    Button buttonSignIn;

    @BindView(R.id.editTextEmail)
     EditText editTextEmail;

    @BindView(R.id.editTextPassword)
     EditText editTextPassword;

    @BindView(R.id.textViewSignUp)
    TextView textViewSignup;

    private ProgressDialog progressDialog;

    @Inject
    LoginPresenter presenter;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this,view);

        progressDialog = new ProgressDialog(getContext());

        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);

        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        presenter.attachView(this);
        presenter.init();
        progressDialog = new ProgressDialog(getContext());
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn){
            String email = editTextEmail.getText().toString().trim();
            String password  = editTextPassword.getText().toString().trim();

            presenter.login(getActivity(),email,password);
        }

        if(view == textViewSignup){
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.activity_auth_fragment_container, new RegisterFragment());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onLoginSuccessful() {
        getActivity().finish();
    }

    @Override
    public void onLoginFailed() {

    }

    @Override
    public void showLoading() {
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
    }

    @Override
    public void onAlreadyLoggedIn() {
        getActivity().finish();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}



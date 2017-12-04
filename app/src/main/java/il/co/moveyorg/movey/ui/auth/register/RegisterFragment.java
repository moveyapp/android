package il.co.moveyorg.movey.ui.auth.register;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.ui.auth.AuthActivity;
import il.co.moveyorg.movey.ui.auth.editprofile.EditProfileActivity;
import il.co.moveyorg.movey.ui.auth.login.LoginFragment;
import il.co.moveyorg.movey.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment implements RegisterMvpView, View.OnClickListener {


    //defining view objects
    @BindView(R.id.editTextEmail)
    EditText editTextEmail;

    @BindView(R.id.editTextPassword)
    EditText editTextPassword;

    @BindView(R.id.buttonSignup)
    Button buttonSignup;

    @BindView(R.id.textViewSignin)
    TextView textViewSignin;

    private ProgressDialog progressDialog;

    @Inject
    RegisterPresenter presenter;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        presenter.attachView(this);
        presenter.init();
        progressDialog = new ProgressDialog(getContext());
        ((AuthActivity) getActivity()).setActionBarTitle("Register");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        ButterKnife.bind(this,view);
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignup){
            String email = editTextEmail.getText().toString().trim();
            String password  = editTextPassword.getText().toString().trim();

            presenter.register(getActivity(),email,password);
        }
        if(view == textViewSignin){
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.activity_auth_fragment_container, new LoginFragment());
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onAlreadyRegistered(){
        getActivity().finish();
    }

    @Override
    public void onRegisterationSuccessful() {
        Toast.makeText(getActivity(),"Registration successful",Toast.LENGTH_LONG).show();
        startActivity(new Intent(getActivity(),EditProfileActivity.class));
        getActivity().finish();
    }

    @Override
    public void onRegisterationFailed() {
        Toast.makeText(getActivity(),"Registration Error",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }
}

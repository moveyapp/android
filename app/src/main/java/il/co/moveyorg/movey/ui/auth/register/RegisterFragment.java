package il.co.moveyorg.movey.ui.auth.register;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.data.model.User;
import il.co.moveyorg.movey.ui.auth.EditUserDetailsActivity;
import il.co.moveyorg.movey.ui.auth.login.LoginFragment;
import il.co.moveyorg.movey.ui.base.BaseFragment;
import il.co.moveyorg.movey.ui.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment implements RegisterMvpView, View.OnClickListener {


    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private TextView textViewSignin;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        //initializing views
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) view.findViewById(R.id.textViewSignin);

        buttonSignup = (Button) view.findViewById(R.id.buttonSignup);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignup){
            //getting email and password from edit texts
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
        startActivity(new Intent(getActivity(),EditUserDetailsActivity.class));
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

//package il.co.moveyorg.movey.ui.auth.login;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.firebase.auth.FirebaseAuth;
//import il.co.moveyorg.movey.R;
//
///**
// * Created by shlomi on 1/5/2018.
// */
//
//public class GoogleLogInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
//
//    SignInButton signInButton;
//    TextView statusTextView;
//    GoogleApiClient mGoogleApiClient;
//    private static final String TAG = "SignInActivity";
//    private static final int RC_SIGN_IN = 9001;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate((savedInstanceState));
//        setContentView(R.layout.fragment_login);
//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//        statusTextView = findViewById(R.id.status_textview);
//        signInButton = findViewById(R.id.sign_in_button);
//        signInButton.setOnClickListener(this);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.sign_in_button:
//                signIn();
//                break;
//            case R.id.sign_out_button:
//                signOut();
//                break;
//        }
//    }
//
//    private void signOut() {
//        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(status -> statusTextView.setText("Sign Out"));
//    }
//
//
//    @Override
//    public void onActivityResult(int reqCode, int resCode, Intent data){
//        super.onActivityResult(reqCode, resCode, data);
//
//        if(reqCode == RC_SIGN_IN){
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//    }
//
//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.d(TAG,"onConnectionFailed:" + connectionResult);
//    }
//
//
//    private void handleSignInResult(GoogleSignInResult result){
//        Log.d(TAG,"handleSignInResult:" + result.isSuccess());
//        if(result.isSuccess()) {
//            GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
//            statusTextView.setText("Hello, " + googleSignInAccount.getDisplayName());
//        }else {}
//    }
//}

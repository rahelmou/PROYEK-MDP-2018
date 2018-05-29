package stts.edu.tourbook;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPass;
    Button btnLogin, btnForget, btnSignUp;
    CallbackManager callbackManager = CallbackManager.Factory.create();

//    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build();
//    GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    MD5 md5encrypter = new MD5();

    String urllocalhost = "10.212.12.194";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        btnForget = findViewById(R.id.btnForget);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        Typeface regfont = Typeface.createFromAsset(getAssets(),  "fonts/latoregular.ttf");

        etEmail.setTypeface(regfont);
        etPass.setTypeface(regfont);
        btnLogin.setTypeface(regfont);
        btnForget.setTypeface(regfont);
        btnSignUp.setTypeface(regfont);

        //GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();



//        SignInButton signInButton = findViewById(R.id.sign_in_button);
//        //signInButton.setSize(SignInButton.SIZE_STANDARD);
//        signInButton.setOnClickListener((View.OnClickListener) this);


        //facebook
        final String EMAIL = "email";

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(getApplicationContext(), "Facebook login success.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });



        LoginManager.getInstance().registerCallback(callbackManager,
            new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                }
            });


    }

    @Override
    protected void onStart() {
        super.onStart();

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        //updateUI(account);
//
//        if (account != null) {
//            Intent it = new Intent(getApplicationContext(), HomeActivity.class);
//            //it.putStringArrayListExtra("userdata", userdata);
//            startActivity(it);
//        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
    }

    //google
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.sign_in_button:
//                signIn();
//                break;
//            // ...
//        }
    }

    private void signIn() {
        //Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    protected void btnLoginClick(View v) {

        final String email = etEmail.getText().toString();
        final String password = md5encrypter.md5(etPass.getText().toString());
        final ArrayList<String> userdata = new ArrayList<String>();

        RequestQueue myqueue;
        myqueue = Volley.newRequestQueue(this);
        String urlgetusers = "http://" + urllocalhost + "/tourbook_login_api/getusers.php";

        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, urlgetusers, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean cek = false;
                            //JSONObject respon = response;
                            for (int i=0; i< response.length(); i++)
                            {
                                JSONObject respon = response.getJSONObject(i);
                                String emaildb = respon.getString("email");
                                String passworddb = respon.getString("password");


                                if (email.equals(emaildb) && password.equals(passworddb))
                                {
                                    userdata.add(respon.getString("id_user"));
                                    userdata.add(respon.getString("nama_user"));
                                    userdata.add(respon.getString("email"));
                                    userdata.add(respon.getString("password"));
                                    userdata.add(respon.getString("gender"));
                                    userdata.add(respon.getString("tanggal_lahir"));
                                    userdata.add(respon.getString("role"));
                                    cek = true;
                                    break;
                                }
                                else if (email.equals(emaildb))
                                {
                                    Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Email unregistered", Toast.LENGTH_SHORT).show();
                                }


                            }
                            if (cek)
                            {
                                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                Intent it = new Intent(getApplicationContext(), HomeActivity.class);
                                it.putStringArrayListExtra("userdata", userdata);
                                startActivity(it);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        myqueue.add(jar);
    }

    public void btnForgetClick(View v)
    {

    }

    public void btnSignUpClick(View v)
    {
        Intent it = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(it);
    }
}

package stts.edu.tourbook;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    MD5 md5encrypter = new MD5();

    EditText etEmail, etPassword;
    Button btnSignUp;

    RequestQueue myqueue;
    ArrayList<String> useremails = new ArrayList<String>();
    String newemail, newpass;
    int role;

    String urllocalhost = "10.212.12.194";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Typeface regfont = Typeface.createFromAsset(getAssets(),  "fonts/latoregular.ttf");

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUpNext);

        etEmail.setTypeface(regfont);
        etPassword.setTypeface(regfont);
        btnSignUp.setTypeface(regfont);
    }

    public void rbRoleClick(View v)
    {
        boolean checked = ((RadioButton)v).isChecked();
        switch (v.getId())
        {
            case R.id.rbTourGuide:
                if (checked) {
                    role = 0;
                }
                break;
            case R.id.rbTourist:
                if (checked) {
                    role = 1;
                }
                break;
        }
    }

    public void btnSignUpNextClick(View v)
    {
        newemail = etEmail.getText().toString();
        newpass = etPassword.getText().toString();
        newpass = md5encrypter.md5(newpass);
        myqueue = Volley.newRequestQueue(this);
        String urlgetusers = "http://" + urllocalhost + "/tourbook_login_api/getusers.php";

        useremails.clear();
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

                                useremails.add(emaildb);


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

        if (newemail.length() == 0)
        {
            etEmail.setError("Email is required!");
        }
        if (newpass.length() == 0)
        {
            etPassword.setError("Password is required!");
        }
        else if (newpass.length() < 8)
        {
            etPassword.setError("Password should be at least 8 characters!");
        }

        if (newemail.length() > 0 && newpass.length() >= 8)
        {
            if (useremails.contains(newemail)) {
                etEmail.setError("Username has been registered");
            }
            else
            {
                Intent it = new Intent(getApplicationContext(), SignUp2Activity.class);
                it.putExtra("newemail", newemail);
                it.putExtra("newpass", newpass);
                it.putExtra("role", role);
                startActivity(it);
            }
        }
    }


}

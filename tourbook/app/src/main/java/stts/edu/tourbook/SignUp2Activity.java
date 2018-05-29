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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp2Activity extends AppCompatActivity {


    EditText etName, etPhone, etTTL;
    Button btnSignUpNew;
    RadioButton rbFemale, rbMale;

    String newemail, newpass;
    String name, phone, ttl, gender, photo;
    int role;

    RequestQueue myqueue;
    String urllocalhost = "10.212.12.194";
    String urlsignup = "http://" + urllocalhost + "/tourbook_login_api/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_2);

        Intent th = getIntent();
        newemail = th.getStringExtra("newemail");
        newpass = th.getStringExtra("newpass");
        role = th.getIntExtra("role", 0);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etTTL = findViewById(R.id.etTTL);
        btnSignUpNew = findViewById(R.id.btnSignUpNew);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        Typeface regfont = Typeface.createFromAsset(getAssets(),  "fonts/latoregular.ttf");

        etName.setTypeface(regfont);
        etPhone.setTypeface(regfont);
        etTTL.setTypeface(regfont);
        btnSignUpNew.setTypeface(regfont);
        rbMale.setTypeface(regfont);
        rbFemale.setTypeface(regfont);
    }

    public void rbClick(View v)
    {
        boolean checked = ((RadioButton)v).isChecked();
        switch (v.getId())
        {
            case R.id.rbMale:
                if (checked) {
                    gender = "M";
                }
                break;
            case R.id.rbFemale:
                if (checked) {
                    gender = "F";
                }
                break;
        }
    }

    public void btnChangePPClick(View v)
    {

    }

    public void btnSignUpNewClick(View v)
    {
        final boolean[] reg = {false};
        photo = "defpp";
        name = etName.getText().toString();
        phone = etPhone.getText().toString();
        ttl = etTTL.getText().toString();

        if (name.length() == 0)
        {
            etName.setError("Name is required!");
        }
        if (phone.length() == 0)
        {
            etPhone.setError("Phone number is required!");
        }
        if (ttl.length() == 0)
        {
            etTTL.setError("Birthday date is required");
        }

        myqueue = Volley.newRequestQueue(this);
        if (name.length() > 0 && phone.length() > 0 && ttl.length() > 0)
        {
            Map<String, String> params= new HashMap<String, String>();
            //try {
                params.put("nama_user", name);
                params.put("password", newpass);
                params.put("email", newemail);
                params.put("phone", phone);
                params.put("gender", gender);
                params.put("ttl", ttl);
                params.put("role", Integer.toString(role));
                params.put("photo", photo);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, urlsignup, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String msg = null;
                            try {
                                msg = response.getString("message").toString();
                                if (msg=="Register success")
                                    reg[0] = true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };
            myqueue.add(jor);

            if (reg[0])
            {
                Intent it = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(it);
            }
        }

    }
}

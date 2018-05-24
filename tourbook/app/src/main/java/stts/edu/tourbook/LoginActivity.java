package stts.edu.tourbook;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);

        Typeface regfont = Typeface.createFromAsset(getAssets(),  "fonts/latoregular.ttf");

        etEmail.setTypeface(regfont);
        etPass.setTypeface(regfont);

        //GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

    }

    public String md5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    protected void btnLoginClick(View v) {

        final String email = etEmail.getText().toString();
        final String password = md5(etPass.getText().toString());
        final ArrayList<String> userdata = new ArrayList<String>();

        RequestQueue myqueue;
        myqueue = Volley.newRequestQueue(this);
        String urllogin = "http://192.168.1.4/tourbook_login_api/login.php";
        String urlgetusers = "http://192.168.1.4/tourbook_login_api/getusers.php";

//        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.POST, urllogin, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            int code = response.getJSONObject(0).getInt("code");
//                            String message = response.getJSONObject(1).getString("message");
//                            JSONObject user;
//                            if (response.length()==3) {
//                                user = response.getJSONObject(2);
//                                userdata.add(user.getString("id_user"));
//                                userdata.add(user.getString("nama_user"));
//                                userdata.add(user.getString("email"));
//                                userdata.add(user.getString("password"));
//                                userdata.add(user.getString("gender"));
//                                userdata.add(user.getString("tanggal_lahir"));
//                                userdata.add(user.getString("role"));
//                            }
//                            else
//                                user = null;
//
//                            if (code == 0 || code == -1)
//                            {
//                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                            }
//                            else
//                            {
//                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
//                                i.putStringArrayListExtra("userdata", userdata);
//                                startActivity(i);
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            protected Map<String, String> getParams() {
//                Map<String, String> MyData = new HashMap<String, String>();
//                MyData.put("email", email); //Add the data you'd like to send to the server.
//                MyData.put("password", password); //Add the data you'd like to send to the server.
//                return MyData;
//            }
//        };
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
}

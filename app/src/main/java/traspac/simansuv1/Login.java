package traspac.simansuv1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener
{

    AppCompatButton btnLogin;
    EditText etUsername,etPassword;
    SessionManager session;
    private ProgressDialog pd;
    TextView etLupa;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final String TAG = "myApp";
    public String oke;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        try{
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    //Check type of intent filter
                    if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                        //Registration success
                        String token = intent.getStringExtra("token");
                        Toast.makeText(getApplicationContext(), "GCM token:" + token, Toast.LENGTH_LONG).show();
                        Log.w("myApp",token);
                    } else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){
                        //Registration error
                        Toast.makeText(getApplicationContext(), "GCM registration error!!!", Toast.LENGTH_LONG).show();
                    } else {
                        //Tobe define
                    }
                }
            };

            //Check status of Google play service in device
            int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
            if(ConnectionResult.SUCCESS != resultCode) {
                //Check type of error
                if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                    //So notification
                    GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
                } else {
                    Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
                }
            } else {
                //Start service
                Intent itent = new Intent(this, GCMRegistrationIntentService.class);
                startService(itent);
            }
        }catch (Exception e){
            Log.w("myapp","yah");

        }
        session = new SessionManager(getApplicationContext());

        if(session.isLogin()==true){
            Toast.makeText(getApplicationContext(),
                    "Berhasil Login" , Toast.LENGTH_LONG) .show();
        }
//        if(session.isLogin()==false){
//            Toast.makeText(getApplicationContext(),
//                    "Loggin Terlebih Dahulu" , Toast.LENGTH_LONG) .show();
//        }
        Config.checkKoneksi(getApplicationContext());
        session.cekLogin();

        btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etLupa = (TextView) findViewById(R.id.txtLupa);
        btnLogin.setOnClickListener(this);
        etLupa.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        if ( v == btnLogin ) {
            proses_login();

        }
        if (v == etLupa){
            new AlertDialog.Builder(this)
                    .setTitle("Perhatian")
                    .setMessage("Jika anda lupa kata sandi hubungi TU")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                        }
                    }).create().show();
        }


    }

    private void proses_login()
    {

        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        pd = ProgressDialog.show(Login.this,"Proses","Mengambil data...",false,false);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String token = intent.getStringExtra("token");

                Toast.makeText(getApplicationContext(), "GCM token:" + token, Toast.LENGTH_LONG).show();
                Log.w("myApp",token);

            }
        };
        StringRequest sr = new StringRequest(Request.Method.POST,Config.URL_LOGIN,
                new Response.Listener<String>() {





                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String success = json.getString(Config.LOGIN_SUCCESS);
                            if (success.equals("true")) {
                                JSONObject data_user = json.getJSONObject(Config.TAG_JSON_ARRAY);
                                String userid = data_user.getString(Config.TAG_ID_USER);
                                String fullname = data_user.getString(Config.TAG_FULLNAME);
                                Log.d("Data","Data User " + data_user.toString());
                                session.createSessionLogin(userid,fullname);
                                Intent intent = new Intent(getApplicationContext(),MainMenu.class);

                                startActivity(intent);
                            }else{
                                String message = json.getString("message");
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.e("Data", "JSON "+e.getMessage());
                            e.printStackTrace();
                        }
                        pd.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        Log.e("Data",error.getMessage());
                        pd.dismiss();
                    }
                }
        )

        {
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Config.TAG_USERNAME,username);
                params.put(Config.TAG_PASSWORD,password);
                return params;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(Login.this);
        rq.add(sr);

    }
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

}

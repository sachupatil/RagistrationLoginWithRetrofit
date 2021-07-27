package com.inovant.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.inovant.com.RestApi.APIService;
import com.inovant.com.RestApi.RetrofitClient;
import com.inovant.com.RestApi.User;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextInputEditText fname, password, lname, email, code;
    TextInputLayout fnameTL, passwordTL, lnameTL, emailTL, codeTL;
    CheckBox checkBox;
    TextView signin;
    ImageView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fnameTL = (TextInputLayout) findViewById(R.id.nameTL);
        passwordTL = (TextInputLayout) findViewById(R.id.passwordTL);
        lnameTL = (TextInputLayout) findViewById(R.id.lastnameTL);
        emailTL = (TextInputLayout) findViewById(R.id.emailTL);
        codeTL = (TextInputLayout) findViewById(R.id.codeTL);
        fname = (TextInputEditText) findViewById(R.id.name);
        password = (TextInputEditText) findViewById(R.id.password);
        lname = (TextInputEditText) findViewById(R.id.lastname);
        email = (TextInputEditText) findViewById(R.id.email);
        code = (TextInputEditText) findViewById(R.id.code);
        checkBox=(CheckBox)findViewById(R.id.checkbox);
        register=(ImageView) findViewById(R.id.register);
        signin=(TextView)findViewById(R.id.signin);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                removeError();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                removeError();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        lname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                removeError();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                removeError();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                removeError();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String versionName = "1.0";//BuildConfig.VERSION_NAME;
                    String deviceModel = "SM-G615F";//android.os.Build.MODEL;
                    String device_token="";
                    String device_type="Android";
                    String getEmail=email.getText().toString();
                    String getFname=fname.getText().toString();
                    String getLname=lname.getText().toString();
                    String os_version="8.1.0";
                    String getPassword=password.getText().toString();
                    String getCode="";//code.getText().toString();
                    Log.i("versionName",deviceModel);
                    Log.i("deviceModel",versionName);;
                    Call<User> call = RetrofitClient.getInstance().getApi().createUser(versionName,deviceModel,device_token,device_type,
                            getEmail,getFname,getLname,os_version,getPassword,getCode);

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                User user=response.body();
                                String msg=user.getMessage();
                                Log.d("TAG", response.body().toString());
                                Log.d("msg", msg);

                               Toast.makeText(MainActivity.this, "Your Registration Successfull", Toast.LENGTH_LONG).show();
                               alertDialog(msg);
                            } else {
                                Log.e("TAG", "Error in getGenericJson:" + response.code() + " " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                        Log.e("Api Error",t.getMessage());
                            Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
    }
    public void removeError(){
        emailTL.setError(null);
        fnameTL.setError(null);
        lnameTL.setError(null);
        passwordTL.setError(null);
        codeTL.setError(null);
    }
    private void alertDialog(String massage) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage(massage);
        dialog.setTitle("Inovant Solution");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent i = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }



    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    boolean validate() {
        boolean b = false;
        if (email.getText().toString().trim().equals("")) {
            b = false;
            emailTL.setError("Required Field...!");
        }else if (!isValidEmail(email.getText().toString().trim())) {
            b = false;
            emailTL.setError("Invalide Email Address...!");
        } else if (password.getText().toString().trim().equals("")) {
            b = false;
            passwordTL.setError("Required Field...!");
        } else if (password.getText().toString().trim().equals("")) {
            b = false;
            passwordTL.setError("Required Field...!");
        } else if (password.getText().toString().trim().length() < 6) {
            b = false;
            passwordTL.setError("Use at lease 6 charasters of password...!");
        } else if (password.getText().toString().trim().length() > 16) {
            b = false;
            passwordTL.setError("Password should not be more than 16 characters...!");
        } else if (fname.getText().toString().trim().equals("")) {
            b = false;
            fnameTL.setError("Required Field...!");
        } else if (lname.getText().toString().trim().equals("")) {
            b = false;
            lnameTL.setError("Required Field...!");
        } else if (code.getText().toString().trim().equals("")) {
            b = false;
            codeTL.setError("Required Field...!");
        } else {
            b = true;

        }
        return b;
    }
}
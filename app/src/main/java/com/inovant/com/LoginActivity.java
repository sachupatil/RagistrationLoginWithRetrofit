package com.inovant.com;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.inovant.com.RestApi.RetrofitClient;
import com.inovant.com.RestApi.User;


public class LoginActivity extends AppCompatActivity {

    TextInputEditText password,email;
    TextInputLayout passwordTL,emailTL;
    TextView createacc;
    ImageView signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        passwordTL = (TextInputLayout) findViewById(R.id.passwordTL);
        password = (TextInputEditText) findViewById(R.id.password);
        emailTL = (TextInputLayout) findViewById(R.id.emailTL);
        email = (TextInputEditText) findViewById(R.id.email);
        signin=(ImageView)findViewById(R.id.signin);
        createacc=(TextView)findViewById(R.id.createacc);

        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String getEmail=email.getText().toString();
                    String getPassword=password.getText().toString();

                    Call<User> call = RetrofitClient.getInstance().getApi().getLogin(getEmail,getPassword);

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                User user=response.body();
                                String msg=user.getMessage();
                                String username=user.getData().getFirst_name();
                                Log.d("TAG", response.body().toString());
                                Log.d("message", msg);
                                Log.e("TAG", "response 200: "+new Gson().toJson(response.body()) );
                                String jsondata=new Gson().toJson(response.body());
                                Log.d("jsondata", jsondata);
                                Toast.makeText(LoginActivity.this, "Your Login Successfull", Toast.LENGTH_LONG).show();
                                alertDialog(username,msg,jsondata);
                            } else {
                                Log.e("TAG", "Error in getGenericJson:" + response.code() + " " + response.message());
                            }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("Api Error",t.getMessage());
                            Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
    }
    private void alertDialog(String  usernameme,String massage,String jsondata) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage(massage);
        dialog.setTitle("User Name:"+usernameme);
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent i = new Intent(LoginActivity.this,ResponseActivity.class);
                        i.putExtra("jsondata",jsondata);
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
        }else {
            b = true;

        }
        return b;
    }
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
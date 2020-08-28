package com.example.foodapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "userID";
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_login)
    EditText _loginText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    SQLiteDatabase mDb;

    String email;
    String password;
    int mode=0;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        intent = new Intent(LoginActivity.this, MainActivity.class);

        mDb = ((MyApplication) this.getApplication()).getmDb();

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Аунтификация...");
        progressDialog.show();

        email = _loginText.getText().toString();
        password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        Cursor cursor = mDb.rawQuery("Select * from users where (email=? or login=?) " +
                "and password=?", new String[]{email, email, password});
        cursor.moveToFirst();
        if (cursor.getCount() > 0 && cursor.getCount() < 2) {
            if (cursor.getInt(2)==1) {
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                Cursor cursor2 = mDb.rawQuery("Select * from users " +
                                        "where (email=? or login=?) and password=?",
                                        new String[]{email, email, password});
                                cursor2.moveToFirst();
                                intent.putExtra("mode",1);
                                mode=1;
                                intent.putExtra(EXTRA_MESSAGE, cursor2.getInt(0));
                                cursor2.close();
                                onLoginSuccess();
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }
            if(cursor.getInt(2)==2){
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    Cursor cursor2 = mDb.rawQuery("Select * from users " +
                                            "where (email=? or login=?) and password=?",
                                            new String[]{email, email, password});
                                    cursor2.moveToFirst();
                                    intent.putExtra("mode",2);
                                    mode=2;
                                    intent.putExtra(EXTRA_MESSAGE, cursor2.getInt(0));
                                    System.out.println(cursor2.getInt(0));
                                    cursor2.close();
                                    onLoginSuccess();
                                    progressDialog.dismiss();
                                }
                            }, 3000);
                }
        }
        else {
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            onLoginFailed();
                            progressDialog.dismiss();
                        }
                    }, 3000);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                Cursor cursor = mDb.rawQuery("select id_users from users " +
                        "where (email=? or login=?) and password=?",
                        new String[]{email, email, password});
                intent.putExtra("mode",mode);
                intent.putExtra(EXTRA_MESSAGE, cursor.getInt(0));
                startActivity(intent);
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Ошибка входа", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String login = _loginText.getText().toString();
        String password = _passwordText.getText().toString();

        if (login.isEmpty() || login.length() < 5) {
            _loginText.setError("Логин содержит более 5 символов");
            valid = false;
        } else {
            _loginText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("от 4 до 10 символов");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
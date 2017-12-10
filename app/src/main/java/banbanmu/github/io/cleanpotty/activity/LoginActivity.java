package banbanmu.github.io.cleanpotty.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import banbanmu.github.io.cleanpotty.R;
import banbanmu.github.io.cleanpotty.retrofit.helper.AuthHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_email)
    TextInputEditText inputEmail;
    @BindView(R.id.input_password)
    TextInputEditText inputPassword;

    private AuthHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        helper = new AuthHelper();
    }

    @OnClick(R.id.btn_signin)
    void onClickSignin() {
        inputEmail.setText(inputEmail.getText().toString().trim());
        inputPassword.setText(inputPassword.getText().toString().trim());
        helper.signIn(inputEmail.getText().toString(), inputPassword.getText().toString())
                .subscribe(token -> {
                    if(!token.equals("")) {
                        SharedPreferences pref = getSharedPreferences("mySharedPreferences", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("Token", token);
                        editor.commit();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "이메일이나 비밀번호를 확인해주세요", Toast.LENGTH_LONG);
                    }
                }, t -> Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT));
    }

    @OnClick(R.id.btn_signup)
    void onClickSignUp() {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }
}

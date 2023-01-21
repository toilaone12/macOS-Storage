package com.example.signinapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    EditText edtUN, edtPW;
    Button btnClick;
    CheckBox ckbRemember;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mapping();
        sharedPreferences = getSharedPreferences("sign in",MODE_PRIVATE);
        addAction();
        edtUN.setText(sharedPreferences.getString("UN",""));
        edtPW.setText(sharedPreferences.getString("PW",""));
        ckbRemember.setChecked(sharedPreferences.getBoolean("remember",false));
    }

    private void addAction() {
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenTK = edtUN.getText().toString();
                String mK = edtPW.getText().toString();
                boolean isCheck = ckbRemember.isChecked();
                if(tenTK.equals("") || mK.equals("")){
                    Toast.makeText(SignInActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                }else if(tenTK.equals("bokazem") && mK.equals("bean2001")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(isCheck){
                        Toast.makeText(SignInActivity.this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                        editor.putBoolean("remember",isCheck);
                        editor.putString("UN",tenTK);
                        editor.putString("PW",mK);
                        editor.commit();
                    }else{
                        editor.remove("remember");
                        editor.remove("UN");
                        editor.remove("PW");
                        editor.commit();
                    }

                }
            }
        });
    }


    private void mapping() {
        edtUN = findViewById(R.id.edtUN);
        edtPW = findViewById(R.id.edtPW);
        btnClick = findViewById(R.id.btnClick);
        ckbRemember = findViewById(R.id.ckbRemember);
    }
}
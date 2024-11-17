package com.example.alzpal.useractivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alzpal.R;

public class RegisterActivity extends AppCompatActivity {

    EditText edUsername, edEmailId, edPassword, edConfirm;
    RadioGroup genderRadioGroup;
    Button btn;
    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id.editTextRegUsername);
        edEmailId = findViewById(R.id.editTextRegEmailId);
        edPassword = findViewById(R.id.editTextRegPassword);
        edConfirm = findViewById(R.id.editTextRegConfirmPassword);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        btn = findViewById(R.id.buttonSignUp);
        TV = findViewById(R.id.textViewExistingUser);

        TV.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        btn.setOnClickListener(v -> {
            String Username = edUsername.getText().toString();
            String Password = edPassword.getText().toString();
            String Confirm = edConfirm.getText().toString();
            String EmailId = edEmailId.getText().toString();
            int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();

            if (selectedGenderId == -1) {
                Toast.makeText(getApplicationContext(), "Please Select a Gender", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedGenderButton = findViewById(selectedGenderId);
            String Gender = selectedGenderButton.getText().toString();

            Database db = new Database(getApplicationContext());

            if (Username.isEmpty() || EmailId.isEmpty() || Password.isEmpty() || Confirm.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Fill all Details", Toast.LENGTH_SHORT).show();
            } else {
                if (Password.equals(Confirm)) {
                    if (isValid(Password)) {
                        db.register(Username, EmailId, Password, Gender);
                        Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("gender", Gender);
                        editor.apply();

                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters, having Letter, Digit, and Special Symbol", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Password and Confirm Password did not Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isValid(String password) {
        int hasLetter = 0, hasDigit = 0, hasSpecial = 0;

        if (password.length() < 8) {
            return false;
        } else {
            for (int i = 0; i < password.length(); i++) {
                if (Character.isLetter(password.charAt(i))) {
                    hasLetter = 1;
                }
            }

            for (int i = 0; i < password.length(); i++) {
                if (Character.isDigit(password.charAt(i))) {
                    hasDigit = 1;
                }
            }

            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if ((c >= 33 && c <= 46) || c == 64) {
                    hasSpecial = 1;
                }
            }

            return hasLetter == 1 && hasDigit == 1 && hasSpecial == 1;
        }
    }
}

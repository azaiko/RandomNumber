package com.example.randomnumber;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int comp_num = 0;
    int attempts = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        comp_num = GuessNum.rndCompNum();
        Button restBtn;
        restBtn = (Button) findViewById(R.id.restart);
        TextView msg_show_txt;
        msg_show_txt = findViewById(R.id.msg_show_txt);
        TextView hint_show_txt;
        hint_show_txt = findViewById(R.id.hint_show_txt);
        TextView attempts_left_txt;
        attempts_left_txt = findViewById(R.id.attempts_left_txt);

        Toast t = Toast.makeText(this, R.string.wishYou, Toast.LENGTH_LONG);
        Button check = findViewById(R.id.confirm);

        View.OnClickListener clckLstnr = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.num_user_txt);
                String number = editText.getText().toString();
                if (!number.isEmpty()) {
                    int num = Integer.parseInt(number);
                    if (num != comp_num && attempts > 1) {
                        msg_show_txt.setText("Не угадал!");
                        String hint = num > comp_num ? "Загаданное число меньше введённого!" : "Загаданное число больше введённого!";
                        hint_show_txt.setText(hint);
                        attempts -= 1;
                        attempts_left_txt.setText(String.valueOf(attempts));
                    } else if (num == comp_num) {
                        msg_show_txt.setText("Ты угадал! Поздравляю!");
                        attempts_left_txt.setText(String.valueOf(attempts));
                        t.show();
                        check.setText(R.string.applied);
                        check.setEnabled(false);
                    } else {
                        attempts_left_txt.setText(String.valueOf(attempts));
                        msg_show_txt.setText("Попытки закончились!");
                        hint_show_txt.setText("Загаданное число: " + String.valueOf(comp_num));
                        check.setEnabled(false);
                    }
                }
            }
        };
        check.setOnClickListener(clckLstnr);

    }

    public void restart(View view) {
        TextView msg_show_txt;
        msg_show_txt = findViewById(R.id.msg_show_txt);
        msg_show_txt.setText(R.string.msg_show_str);
        TextView hint_show_txt;
        hint_show_txt = findViewById(R.id.hint_show_txt);
        hint_show_txt.setText(R.string.hint_show_str);
        TextView attempts_left_txt;
        attempts_left_txt = findViewById(R.id.attempts_left_txt);
        EditText editText;
        editText = findViewById(R.id.num_user_txt);
        editText.setText(R.string.num_users_str);
        attempts_left_txt.setText(R.string.attempts_left);
        attempts =5;
        Button check = findViewById(R.id.confirm);
        comp_num = GuessNum.rndCompNum();
        check.setText(R.string.confirm_str);
        check.setEnabled(true);
    }
}
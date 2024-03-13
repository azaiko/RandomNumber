package com.example.randomnumber;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GameModeDialog.NoticeDialogListener {
    private GameMode runtime_game_mode = GameMode.DEFAULT;
    private int random_number;
    private int attempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchEventHandler();
        checkEventHandler();
        initVariables(2);
    }

    private void initVariables(int digits) {
        switch (digits) {
            case 2:
                random_number = GuessNumber.randomNumber(digits);
                setTextView(R.id.hint_show_txt, R.string.hint_show2_str);
                attempts = 5;
                break;
            case 3:
                random_number = GuessNumber.randomNumber(digits);
                setTextView(R.id.hint_show_txt, R.string.hint_show3_str);
                attempts = 7;
                break;
            case 4:
                random_number = GuessNumber.randomNumber(digits);
                setTextView(R.id.hint_show_txt, R.string.hint_show4_str);
                attempts = 10;
                break;
            default:
                break;
        }
    }

    private void switchEventHandler() {
        Switch toggle = findViewById(R.id.gameModeSwitch);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    runtime_game_mode = GameMode.HARD;
                    GameModeDialog modal = new GameModeDialog();
                    modal.show(getSupportFragmentManager(), "Modal");
                }
                else {
                    runtime_game_mode = GameMode.DEFAULT;
                    initVariables(2);
                }
            }
        });
    }

    private void checkEventHandler() {
        Button check = findViewById(R.id.confirm);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.num_user_txt);

                String input = editText.getText().toString();
                int number = checkInputValue(input);

                if (number != -1) {
                    guessLogic(number);
                }
                else {
                    setTextView(R.id.hint_show_txt, R.string.input_error_str);
                }
            }
        });
    }

    @Override
    public void onDialogPositiveClick(int selected) {
        initVariables(selected + 2);
    }

    @Override
    public void onDialogNegativeClick() {
        Button restart_btn = findViewById(R.id.restartButton);
        restart_btn.performClick();
    }

    private int checkInputValue(String input) {
        if (!input.isEmpty()) {
            int number;

            try {
                number = Integer.parseInt(input);
            }
            catch (NumberFormatException ex) {
                number = -1;
            }
            return number;
        }
        else {
            return -1;
        }
    }

    private void guessLogic(int number) {
        attempts--;
        setTextView(R.id.attempts_left_txt, String.valueOf(attempts));

        if (number != random_number && attempts >= 1) {
            int hint = number > random_number ? R.string.number_is_lower_str : R.string.number_is_greater_str;
            setTextView(R.id.hint_show_txt, hint);
        } else if (number == random_number) {
            setTextView(R.id.hint_show_txt, R.string.win_str);
            changeCheckButtonState(false);
        } else {
            String result = getResources().getString(R.string.lose_str);
            setTextView(R.id.hint_show_txt, result + String.valueOf(random_number));
            changeCheckButtonState(false);
        }
    }

    private void setTextView(int target, int content) {
        TextView textBox = findViewById(target);
        textBox.setText(content);
    }

    private void setTextView(int target, String content) {
        TextView textBox = findViewById(target);
        textBox.setText(content);
    }

    private void changeCheckButtonState(boolean state) {
        Button submit = findViewById(R.id.confirm);
        submit.setEnabled(state);
    }

    public void restart(View view) {
        setTextView(R.id.attempts_left_txt, "");
        setTextView(R.id.num_user_txt, "");

        changeCheckButtonState(true);
        Switch game_mode_switch = findViewById(R.id.gameModeSwitch);
        game_mode_switch.setChecked(false);

        initVariables(2);
    }

    public void digitButtonClick(View view) {
        Button button = (Button) view;
        String digit = button.getText().toString();

        EditText editText = findViewById(R.id.num_user_txt);
        String input = editText.getText().toString();

        setTextView(R.id.num_user_txt, input + digit);
    }

    public void clearInputButton(View view) {
        setTextView(R.id.num_user_txt, "");
    }

    public void removeInputButton(View view) {
        EditText editText = findViewById(R.id.num_user_txt);
        String input = editText.getText().toString();
        if(!input.isEmpty()) setTextView(R.id.num_user_txt, input.substring(0, input.length() - 1));
    }
}
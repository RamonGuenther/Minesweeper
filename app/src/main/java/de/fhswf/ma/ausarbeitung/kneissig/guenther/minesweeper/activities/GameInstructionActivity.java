package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;

public class GameInstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_instruction);

        View view = findViewById(R.id.instructions);

        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                view.setBackgroundResource(R.color.white);
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.grey));//status muss noch angepasst werden
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                view.setBackgroundResource(R.color.main_theme_dark_mode);
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.grey)); //Status
                break;
        }

        ImageButton backToMenue = findViewById(R.id.instructions_backButton);
        backToMenue.setOnClickListener(event -> startActivity(new Intent(this, MainActivity.class)));


    }
}

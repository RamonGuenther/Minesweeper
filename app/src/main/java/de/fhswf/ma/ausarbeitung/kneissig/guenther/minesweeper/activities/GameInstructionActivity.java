package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;

public class GameInstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_instruction);

        ImageButton backToMenue = findViewById(R.id.instructions_backButton);
        backToMenue.setOnClickListener(event -> startActivity(new Intent(this, MainActivity.class)));
    }
}

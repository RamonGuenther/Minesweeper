package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;

public class HighScoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        ImageButton imageButton = findViewById(R.id.button2);

        imageButton.setOnClickListener(e->{
            finish();
        });


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}

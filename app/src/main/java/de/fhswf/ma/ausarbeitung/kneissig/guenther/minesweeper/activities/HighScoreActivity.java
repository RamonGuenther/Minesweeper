package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;

public class HighScoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        View view = findViewById(R.id.apfel);

        Slidr.attach(this, new SlidrConfig.Builder()
                .position(SlidrPosition.RIGHT)
                .build());


        int currentNightMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                Toast.makeText(getBaseContext(), "LIGHT", Toast.LENGTH_SHORT).show();
                view.setBackgroundResource(R.color.white);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                Toast.makeText(getBaseContext(), "Dark", Toast.LENGTH_SHORT).show();
                view.setBackgroundResource(R.color.main_theme_dark_mode);
                break;
        }

        ImageButton imageButton = findViewById(R.id.button2);

        imageButton.setOnClickListener(e -> {
            finish();
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}

package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;


import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


//        setTitle("Einstellungen");  oder über XML
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.acvitity_settings);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        ImageButton button = findViewById(R.id.button2);
        button.setOnClickListener(e->{
//            finishActivity(); //bei startactivityforreason
            finish();
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
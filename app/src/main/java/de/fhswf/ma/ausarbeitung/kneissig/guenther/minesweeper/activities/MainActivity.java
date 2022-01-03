package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Set;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;

//TODO: Auf pixel passt es nicht !!!!!!!!!!!!!!!!!!!!!

//TODO AppCompatDelegate.setDefaultNightMode().

/**
 * windowBackground only affects the main window's background.
 * <p>
 * colorBackground affects not only the background of the main window but also of all components e.g. dialogs unless you override it in the component layout.
 * <p>
 * So both of them change the activity's background, but the colorBackground changes many more things as well.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(e -> {
            startActivity(new Intent(this, SettingsActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            startActivityForResult(new Intent(MainActivity.this, Pop.class)); //finishActivity() um es zu übergeben
        });


        ImageButton highScoreButton = findViewById(R.id.highScoreButton);
        highScoreButton.setOnClickListener(e->{
            startActivity(new Intent(this, HighScoreActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });


        String[] items = getResources().getStringArray(R.array.level_select);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.selectLevel);
        autoCompleteTextView.setText(items[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, items);

        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = "Spielmodus \"" + adapterView.getItemAtPosition(i).toString() + "\" wurde ausgewählt!";
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });


        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(e -> {
            startActivity(new Intent(this, GameActivity.class));
            boolean isVibrate = true ;
            if(isVibrate){
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(500);
                Toast.makeText(this, "vib started", Toast.LENGTH_LONG).show();
            }

            else{


            }
        });

        Button gameInstructionButton = findViewById(R.id.gameInstructionButton);
        gameInstructionButton.setOnClickListener(e->{
            startActivity(new Intent(this, GameInstructionActivity.class));
        });


    }

}
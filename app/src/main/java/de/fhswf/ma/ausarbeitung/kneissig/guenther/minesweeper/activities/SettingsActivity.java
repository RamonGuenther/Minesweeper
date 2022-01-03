package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;


import com.google.android.material.switchmaterial.SwitchMaterial;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvitity_settings);

        View view = findViewById(R.id.birne);

        Slidr.attach(this, new SlidrConfig.Builder()
                .position(SlidrPosition.LEFT)
                .build());


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


        SwitchMaterial switchMaterial = findViewById(R.id.darkModeSwitch);

        switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {
           if(isChecked){
               AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
           }
           else{
               AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
           }
        });


        String[] items = getResources().getStringArray(R.array.theme_select);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.selectTheme);
        autoCompleteTextView.setText(items[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, items);

        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = "Das Design \"" + adapterView.getItemAtPosition(i).toString() + "\" wurde ausgewählt!";
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });


        ImageButton button = findViewById(R.id.backButton);
        button.setOnClickListener(e -> {
//            finishActivity(); //bei startactivityforreason
            finish();
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void toggleTheme() {
        int mode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES;
        AppCompatDelegate.setDefaultNightMode(mode);
    }
}
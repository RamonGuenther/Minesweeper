package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.room.Room;


import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.transition.platform.MaterialFade;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.MinesweeperDatabase;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.HighScore;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.Settings;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Theme;

public class SettingsActivity extends AppCompatActivity {

    private Settings settings;
    private MinesweeperDatabase db;

    private List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvitity_settings);

        getWindow().setEnterTransition(new AutoTransition());
        getWindow().setExitTransition(new AutoTransition());

        db = MinesweeperDatabase.createDatabase(this);
        settings = db.settingsDao().getSettings();

        View view = findViewById(R.id.settingsLayout);

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


        SwitchMaterial darkModeSwitch = findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                settings.setDarkMode(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                settings.setDarkMode(false);
            }
        });

        SwitchMaterial vibrationSwitch = findViewById(R.id.vibrationSwitch);
        vibrationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MinesweeperGame.getInstance().getGameSettings().setVibration(isChecked);
            settings.setVibration(isChecked);
        });

        SwitchMaterial timerSwitch = findViewById(R.id.showTimerSwitch);
        timerSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MinesweeperGame.getInstance().getGameSettings().setTimerVisible(isChecked);
            settings.setShowTimer(isChecked);
        });


        SwitchMaterial mineCountSwitch = findViewById(R.id.showMineCountSwitch);
        mineCountSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MinesweeperGame.getInstance().getGameSettings().setMineCounterVisible(isChecked);
            settings.setShowMineCounter(isChecked);
        });

        SwitchMaterial modeChangeSwitch = findViewById(R.id.modeChangeShowSwitch);
        modeChangeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MinesweeperGame.getInstance().getGameSettings().setGameModeVisible(isChecked);
            settings.setShowModeSwitch(isChecked);
        });


        SwitchMaterial useFlagsSwitch = findViewById(R.id.useFlagsSwitch);
        useFlagsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MinesweeperGame.getInstance().getGameSettings().setFlagsPossible(isChecked);
            settings.setUseFlags(isChecked);
        });


        SwitchMaterial showHintsSwitch = findViewById(R.id.showHintsSwitch);
        showHintsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MinesweeperGame.getInstance().getGameSettings().setHints(isChecked);
            settings.setShowHints(isChecked);
        });


        items = new ArrayList<>();
        Arrays.asList(Theme.values()).forEach(e -> items.add(e.label));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, items);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String theme = parent.getItemAtPosition(position).toString();
                settings.setTheme(theme);
                MinesweeperGame.getInstance().getGameSettings().setTheme(theme);
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });


        ImageButton button = findViewById(R.id.backButton);
        button.setOnClickListener(e -> finish());


        spinner.setSelection(getThemeIndex(settings.getTheme()));
        darkModeSwitch.setChecked(settings.isDarkMode());
        vibrationSwitch.setChecked(settings.isVibration());
        timerSwitch.setChecked(settings.isShowTimer());
        mineCountSwitch.setChecked(settings.isShowMineCounter());
        modeChangeSwitch.setChecked(settings.isShowModeSwitch());
        useFlagsSwitch.setChecked(settings.isUseFlags());
        showHintsSwitch.setChecked(settings.isShowHints());
    }

    private int getThemeIndex(String theme) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).contains(theme)) {
                return i;
            }
        }
        return 0;
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_out_right);
    }

    //Falls man von den Einstellungen auf den Homescreen geht sollen die Daten wenigstens gespeichert sein
    @Override
    protected void onPause() {
        super.onPause();
        db.settingsDao().update(settings);
        Log.e("UPDATE SETTINGS", "Settings wurden gespeichert");
//        db.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("DESTROY SETIINGS", "Settings wurden GELÖSCHT");
        db.close();
    }

}
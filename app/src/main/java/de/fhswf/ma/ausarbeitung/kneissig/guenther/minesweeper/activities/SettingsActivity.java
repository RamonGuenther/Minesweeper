package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;


import com.google.android.material.switchmaterial.SwitchMaterial;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import java.util.ArrayList;
import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperApplication;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.Settings;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter.ThemeItemAdapter;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter.ThemeItem;


public class SettingsActivity extends AppCompatActivity {

    private List<ThemeItem> themeItemList;

    private Settings settings;

    private MinesweeperApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvitity_settings);

        application = (MinesweeperApplication) getApplication();

        settings = application.getSettings();


        Slidr.attach(this, new SlidrConfig.Builder()
                .position(SlidrPosition.LEFT)
                .build());


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
            if (!useFlagsSwitch.isChecked()) {
                modeChangeSwitch.setChecked(false);
                modeChangeSwitch.setEnabled(false);
            } else {
                modeChangeSwitch.setEnabled(true);
            }
        });

        if (!useFlagsSwitch.isChecked()) {
            modeChangeSwitch.setChecked(false);
            modeChangeSwitch.setEnabled(false);
        }

        SwitchMaterial showHintsSwitch = findViewById(R.id.showHintsSwitch);
        showHintsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MinesweeperGame.getInstance().getGameSettings().setHints(isChecked);
            settings.setShowHints(isChecked);
        });

        Spinner spinner = findViewById(R.id.spinner);
        initThemeList();
        ThemeItemAdapter adapter = new ThemeItemAdapter(this, themeItemList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ThemeItem theme = (ThemeItem) parent.getItemAtPosition(position);
                settings.setTheme(theme.getThemeName());
                MinesweeperGame.getInstance().getGameSettings().setTheme(theme.getThemeName());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ImageButton button = findViewById(R.id.backButton);
        button.setOnClickListener(e -> finish());


        ImageButton deleteStatisticsButton = findViewById(R.id.deleteStatisticsButton);

        deleteStatisticsButton.setOnClickListener(e -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.achtung));
            builder.setMessage(getString(R.string.daten_koennen_nicht_wiederhergestellt));
            builder.setPositiveButton(R.string.neues_spiel_ja, (dialog, id) -> {
                application.deleteAllGameSummaries();
                Toast.makeText(this, R.string.spieldaten_geloescht, Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton(getString(R.string.neues_spiel_nein), null);

            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            alertDialog.show();
        });

        spinner.setSelection(getThemeIndex(settings.getTheme()));
        vibrationSwitch.setChecked(settings.isVibration());
        timerSwitch.setChecked(settings.isShowTimer());
        mineCountSwitch.setChecked(settings.isShowMineCounter());
        modeChangeSwitch.setChecked(settings.isShowModeSwitch());
        useFlagsSwitch.setChecked(settings.isUseFlags());
        showHintsSwitch.setChecked(settings.isShowHints());

        View view = findViewById(R.id.settingsLayout);

        //Sonst bleibt der Hintergrund transparent wegen slidr
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                view.setBackgroundResource(R.color.white);
                spinner.setBackground(ContextCompat.getDrawable(this, R.drawable.style_spinner));
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                view.setBackgroundResource(R.color.main_theme_dark_mode);
                spinner.setBackground(ContextCompat.getDrawable(this, R.drawable.style_spinner_night));
                break;
        }
    }

    private int getThemeIndex(String theme) {
        for (int i = 0; i < themeItemList.size(); i++) {
            if (themeItemList.get(i).getThemeName().contains(theme)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_out_right);
        if(!settings.isUseFlags()){
            MinesweeperGame.getInstance().removeFlagsAndQuestionMarks();
        }
    }

    /**
     * Damit die Einstellungen gespeichert sind beim Navigieren zu einer anderen App & beim zerstören der Activity
     */
    @Override
    protected void onPause() {
        super.onPause();
        application.updateSettings(settings);
    }

    public void initThemeList() {
        themeItemList = new ArrayList<>();
        themeItemList.add(new ThemeItem(getString(R.string.bordeaux), R.drawable.theme_bordeaux_empty));
        themeItemList.add(new ThemeItem(getString(R.string.blau), R.drawable.theme_blau_empty));
        themeItemList.add(new ThemeItem(getString(R.string.gruen), R.drawable.theme_gruen_empty));
        themeItemList.add(new ThemeItem(getString(R.string.grau), R.drawable.theme_grau_empty));
        themeItemList.add(new ThemeItem(getString(R.string.classic), R.drawable.theme_classic_empty));
    }

}
package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.MinesweeperDatabase;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.HighScore;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter.HighScoreCardAdapter;

/**
 * Swipen geht nur auf dem wirklichen Hintergrund
 */
public class HighScoreActivity extends AppCompatActivity {

    //Highscore_item
    private RecyclerView recyclerView;

    //Brücke Highscore und der Recyclerview
    private RecyclerView.Adapter adapter;

    //
    private RecyclerView.LayoutManager layoutManager;
    private List<HighScore> highScoreList;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private MinesweeperDatabase db;
    private RadioButton radioButton1;
    private RadioButton radioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        View view = findViewById(R.id.apfel);

        Slidr.attach(this, new SlidrConfig.Builder()
                .position(SlidrPosition.RIGHT)
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

        ImageButton imageButton = findViewById(R.id.button2);

        imageButton.setOnClickListener(e -> {
            finish();
        });

        radioGroup = findViewById(R.id.RadioGroup);

        radioGroup.setOnClickListener(e -> {
            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
        });


        radioButton = findViewById(R.id.radioButton1);
        radioButton.setChecked(true); //Nur für das erste mal des Betreten der Activity

        radioButton.setOnClickListener(e -> {
            radioButtonEvent(Level.BEGINNER);
        });

        radioButton1 = findViewById(R.id.radioButton2);
        radioButton1.setOnClickListener(e -> {
            radioButtonEvent(Level.ADVANCED);
        });

        radioButton2 = findViewById(R.id.radioButton3);
        radioButton2.setOnClickListener(e -> {
            radioButtonEvent(Level.PROFESSIONAL);
        });



        db = MinesweeperDatabase.createDatabase(this);
        highScoreList = db.highscoreDao().getHighScoresByGameMode(GameResult.WON.label, Level.BEGINNER.label);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true); //wenn die Größe sich nicht ändern steigert performance aber nochmal googeln
        layoutManager = new LinearLayoutManager(this);
        adapter = new HighScoreCardAdapter(highScoreList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);


        TextView textView = findViewById(R.id.titleHighscore);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                List<HighScore> highScoreListtemp;

                if (tabLayout.getSelectedTabPosition() == 0) {
                    radioGroup.setVisibility(View.VISIBLE);

                    if (radioButton.isChecked()) {
                        radioButtonEvent(Level.BEGINNER);
                    } else if (radioButton1.isChecked()) {
                        radioButtonEvent(Level.ADVANCED);
                    } else if (radioButton2.isChecked()) {
                        radioButtonEvent(Level.PROFESSIONAL);
                    }
                    textView.setText("High Scores"); //TODO AUch solche String ressourcen einbinden wenn nicht schon vorhanden (getString(R.string.app_name))

                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    textView.setText("Spielverlauf");
                    radioGroup.setVisibility(View.GONE); //TODO Ivonne nach Meinung fragen
//                    radioGroup.setVisibility(View.INVISIBLE);
                    highScoreListtemp = db.highscoreDao().getMatchHistory();
                    highScoreList.clear();
                    highScoreList.addAll(highScoreListtemp);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    private void radioButtonEvent(Level level) {
        List<HighScore> test = db.highscoreDao().getHighScoresByGameMode(GameResult.WON.label, level.label);
        highScoreList.clear();
        highScoreList.addAll(test);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        try {
            super.finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } catch (Exception e) {
            Log.e("TEST", e.getMessage());
        }
    }
}

package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.MinesweeperDatabase;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.GameSummary;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.HorizontalStringPicker;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter.GameHistoryAdapter;

/**
 * Swipen geht nur auf dem wirklichen Hintergrund
 */
public class GameHistoryActivity extends AppCompatActivity {

    //Highscore_item
    private RecyclerView recyclerView;

    //Brücke Highscore und der Recyclerview
    private RecyclerView.Adapter adapter;

    //
    private RecyclerView.LayoutManager layoutManager;
    private List<GameSummary> gameSummaryList;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private MinesweeperDatabase db;
    private RadioButton radioButton1;
    private RadioButton radioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

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


        List<String> items = new ArrayList<>();
        Arrays.asList(Level.values()).forEach(e -> items.add(e.label));
        items.remove(items.size() - 1);

        HorizontalStringPicker horizontalStringPicker = findViewById(R.id.horizontalStringPicker2);
        horizontalStringPicker.setItems(items);
        horizontalStringPicker.setValue(Level.BEGINNER.label);
        horizontalStringPicker.getTextView().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String level = horizontalStringPicker.getValue();
                refreshData(level);
            }
        });


        db = MinesweeperDatabase.createDatabase(this);
        gameSummaryList = db.highscoreDao().getHighScoresByGameMode(GameResult.WON.label, Level.BEGINNER.label);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true); //wenn die Größe sich nicht ändern steigert performance aber nochmal googeln
        layoutManager = new LinearLayoutManager(this);
        adapter = new GameHistoryAdapter(gameSummaryList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);


        TextView textView = findViewById(R.id.titleHighscore);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                List<GameSummary> gameSummaryListtemp;

                if (tabLayout.getSelectedTabPosition() == 0) {
                    ImageView logo = findViewById(R.id.gameTitleHighscore);
                    if(logo != null) {
                        logo.setVisibility(View.GONE);
                    }
                    horizontalStringPicker.setVisibility(View.VISIBLE);
                    refreshData(horizontalStringPicker.getValue());
                    textView.setText("Highscores"); //TODO AUch solche String ressourcen einbinden wenn nicht schon vorhanden (getString(R.string.app_name))

                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    ImageView logo = findViewById(R.id.gameTitleHighscore);
                    if(logo != null) {
                        logo.setVisibility(View.VISIBLE);
                    }
                    textView.setText("Spielverlauf");
                    horizontalStringPicker.setVisibility(View.GONE); //TODO Ivonne nach Meinung fragen
//                    horizontalStringPicker.setVisibility(View.INVISIBLE);
                    gameSummaryListtemp = db.highscoreDao().getMatchHistory();
                    gameSummaryList.clear();
                    gameSummaryList.addAll(gameSummaryListtemp);
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


    private void refreshData(String level) {
        List<GameSummary> test = db.highscoreDao().getHighScoresByGameMode(GameResult.WON.label, level);
        gameSummaryList.clear();
        gameSummaryList.addAll(test);
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

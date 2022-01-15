package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.MinesweeperApplication;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.MinesweeperDatabase;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.GameSummary;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.HorizontalStringPicker;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.LevelHorizontalStringPicker;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter.GameHistoryAdapter;

/**
 * Swipen geht nur auf dem wirklichen Hintergrund
 */
public class GameHistoryActivity extends AppCompatActivity {

    //Brücke Highscore und der Recyclerview
    private RecyclerView.Adapter<GameHistoryAdapter.GameHistoryViewHolder> adapter;

    private List<GameSummary> gameSummaryItemList;
    private MinesweeperDatabase db;
    private TextView noGameDataTextview;
    private TextView titleHighscoreTextView;
    private MinesweeperApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        View view = findViewById(R.id.gameHistoryLayout);

        Slidr.attach(this, new SlidrConfig.Builder()
                .position(SlidrPosition.RIGHT)
                .build());


        //Sonst bleibt der Hintergrund transparent wegen slidr
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                view.setBackgroundResource(R.color.white);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                view.setBackgroundResource(R.color.main_theme_dark_mode);
                break;
        }

        ImageButton imageButton = findViewById(R.id.button2);

        imageButton.setOnClickListener(e -> {
            finish();
        });


        LevelHorizontalStringPicker horizontalStringPicker = findViewById(R.id.horizontalStringPicker2);
        horizontalStringPicker.getItemList().remove(horizontalStringPicker.getItemList().size() - 1);
        horizontalStringPicker.setValue(getString(R.string.level_anfänger));
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


        application = (MinesweeperApplication) getApplication();
        noGameDataTextview = findViewById(R.id.noGameDataTextview);

        gameSummaryItemList = application.getHighScoreListByLevel(Level.BEGINNER.label);
        noGameDataTextview.setVisibility(gameSummaryItemList.isEmpty() ? View.VISIBLE : View.GONE);

        //Highscore_item
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true); //wenn die Größe sich nicht ändern steigert performance aber nochmal googeln

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new GameHistoryAdapter(gameSummaryItemList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        titleHighscoreTextView = findViewById(R.id.titleHighscore);

        ImageView logo = findViewById(R.id.gameLogoGameHistory);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    if (logo != null) {
                        logo.setVisibility(View.GONE);
                    }
                    horizontalStringPicker.setVisibility(View.VISIBLE);
                    refreshData(horizontalStringPicker.getValue());
                    titleHighscoreTextView.setText(getString(R.string.highscores));

                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    if (logo != null) {
                        logo.setVisibility(View.VISIBLE);
                    }
                    titleHighscoreTextView.setText(getString(R.string.spielverlauf));
                    horizontalStringPicker.setVisibility(View.GONE);
                    List<GameSummary> newGameSummaryItemList = application.getMatchHistory();
                    noGameDataTextview.setVisibility(newGameSummaryItemList.isEmpty() ? View.VISIBLE : View.GONE);
                    gameSummaryItemList.clear();
                    gameSummaryItemList.addAll(newGameSummaryItemList);
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


    @SuppressLint("NotifyDataSetChanged")
    private void refreshData(String value) {
        String level = null;
        switch (value) {
            case "Anfänger":
            case "Beginner":
                level = Level.BEGINNER.label;
                break;
            case "Fortgeschritten":
            case "Advanced":
                level = Level.ADVANCED.label;
                break;
            case "Profi":
            case "Professional":
                level = Level.PROFESSIONAL.label;
                break;
        }
        List<GameSummary> newGameSummaryItemList = application.getHighScoreListByLevel(level);
        noGameDataTextview.setVisibility(newGameSummaryItemList.isEmpty() ? View.VISIBLE : View.GONE);
        gameSummaryItemList.clear();
        gameSummaryItemList.addAll(newGameSummaryItemList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}

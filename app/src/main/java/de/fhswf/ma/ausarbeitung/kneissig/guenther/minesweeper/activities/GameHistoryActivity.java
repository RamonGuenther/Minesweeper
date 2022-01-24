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
 * Die Klasse GameHistoryActivity bildet die Aktivität für die Darstellung der Spielstatistiken
 * der abgeschlossenen Spiele.
 *
 * @author Ramon Günther
 */
public class GameHistoryActivity extends AppCompatActivity {

    private RecyclerView.Adapter<GameHistoryAdapter.GameHistoryViewHolder> adapter;

    private List<GameSummary> gameSummaryItemList;
    private TextView noGameDataTextview;
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

        imageButton.setOnClickListener(e -> finish());


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


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        adapter = new GameHistoryAdapter(gameSummaryItemList);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        TextView titleHighScoreTextView = findViewById(R.id.titleHighscore);

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
                    titleHighScoreTextView.setText(getString(R.string.highscores));

                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    if (logo != null) {
                        logo.setVisibility(View.VISIBLE);
                    }
                    titleHighScoreTextView.setText(getString(R.string.spielverlauf));
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_left);
    }

    /**
     * Beim Ändern des gesetzten Items des Level-Pickers, verändert diese Methode
     * die angezeigten Daten.
     *
     * @param item String des aktuellen Items
     */
    @SuppressLint("NotifyDataSetChanged")
    private void refreshData(String item) {
        String level = null;
        switch (item) {
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
}

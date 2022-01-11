package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.MinesweeperDatabase;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.HighScore;
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


        MinesweeperDatabase db = MinesweeperDatabase.createDatabase(this);
        List<HighScore> highScoreList = db.highscoreDao().getHighscores() ;

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true); //wenn die Größe sich nicht ändern steigert performance aber nochmal googeln
        layoutManager = new LinearLayoutManager(this);
        adapter = new HighScoreCardAdapter(highScoreList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);




    }

    @Override
    public void finish() {
        try {
            super.finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }catch(Exception e){
            Log.e("TEST" , e.getMessage());
        }
    }
}

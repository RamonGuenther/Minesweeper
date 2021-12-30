package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;

import com.google.android.material.switchmaterial.SwitchMaterial;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameMode;

public class MainActivity extends AppCompatActivity {

    //R & I - Minesweeper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwitchMaterial switchMaterial = findViewById(R.id.gameMode);

        switchMaterial.setOnClickListener(e -> {
            if(switchMaterial.isChecked()){
                MinesweeperGame.getInstance().setGameMode(GameMode.FLAG_MODE);
            }
            else{
                MinesweeperGame.getInstance().setGameMode(GameMode.MINE_MODE);
            }
        });

        ImageButton resetButton = findViewById(R.id.resetGame);

        resetButton.setOnClickListener(e -> MinesweeperGame.getInstance().resetGame());

        //MinesweeperGame.getInstance().createGrid(this);
    }
}
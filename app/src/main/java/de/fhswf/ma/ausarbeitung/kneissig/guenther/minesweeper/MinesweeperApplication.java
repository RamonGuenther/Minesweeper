package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper;

import android.app.Application;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.MinesweeperDatabase;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.CustomGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.Settings;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;

public class MinesweeperApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MinesweeperDatabase db = MinesweeperDatabase.createDatabase(this);

        Settings settings = db.settingsDao().getSettings();

        //Damit immer ein Settingsobjekt in der Datenbank gespeichert ist
        if (settings == null) {
            settings = new Settings();
            db.settingsDao().insert(settings);
        }


        MinesweeperGame.getInstance().getGameSettings().setVibration(settings.isVibration());
        MinesweeperGame.getInstance().getGameSettings().setTimerVisible(settings.isShowTimer());
        MinesweeperGame.getInstance().getGameSettings().setMineCounterVisible(settings.isShowMineCounter());
        MinesweeperGame.getInstance().getGameSettings().setGameModeVisible(settings.isShowModeSwitch());
        MinesweeperGame.getInstance().getGameSettings().setFlagsPossible(settings.isUseFlags());
        MinesweeperGame.getInstance().getGameSettings().setHints(settings.isShowHints());

        if (settings.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            settings.setDarkMode(true);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            settings.setDarkMode(false);
        }

        CustomGame customGame = db.customGameDao().getCustomGame();

        if(customGame == null){
            customGame = new CustomGame();
            db.customGameDao().insert(customGame);
        }

        db.close();

        Log.i("Minesweeper Applikation", "Einstellungen wurden übernommen");
    }
}

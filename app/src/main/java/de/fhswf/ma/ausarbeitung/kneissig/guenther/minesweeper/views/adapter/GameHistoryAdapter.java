package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.GameSummary;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;

/**
 * Die Klasse GameHistoryAdapter implementiert den Adapter für die RecyclerView
 * aus der GameHistoryActivity.
 *
 * @author Ramon Günther
 */
public class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryAdapter.GameHistoryViewHolder> {

    private final List<GameSummary> gameSummaryItemList;
    private Context context;

    /**
     * Die Klasse GameHistoryViewHolder implementiert den ViewHolder für den
     * GameHistoryAdapter.
     */
    public static class GameHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView gamePlayedOn;
        public TextView playedTime;
        public TextView level;
        public TextView gameResult;
        public TextView minesFound;
        public TextView fieldSize;

        /**
         * Konstruktor der die Komponenten mithilfe der ID findet und initialisiert.
         *
         * @param itemView View
         */
        public GameHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            gamePlayedOn = itemView.findViewById(R.id.gamePlayedOn);
            playedTime = itemView.findViewById(R.id.playedTime);
            level = itemView.findViewById(R.id.level);
            gameResult = itemView.findViewById(R.id.gameResult);
            minesFound = itemView.findViewById(R.id.minesFound);
            fieldSize = itemView.findViewById(R.id.fieldSize);
        }
    }


    /**
     * Der Konstruktor inititalisiert die Liste der Items für den Adapter.
     *
     * @param gameSummaryItemList Liste mit den Objekten aus der Klasse GameSummary
     */
    public GameHistoryAdapter(List<GameSummary> gameSummaryItemList) {
        this.gameSummaryItemList = gameSummaryItemList;
    }

    /**
     * Erstellt den ViewHolder und gibt diesen zurück.
     *
     * @param parent ViewGroup
     * @param viewType int
     * @return GameHistoryViewHolder
     */
    @NonNull
    @Override
    public GameHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_summary_item, parent, false);
        context = view.getContext();
        return new GameHistoryViewHolder(view);
    }

    /**
     * Wird von der RecyclerView automatisch aufgerufen, um die Daten an der angegebenen Position
     * anzuzeigen.
     *
     * @param holder   Der ViewHolder, der aktualisiert werden soll, um den Inhalt des
     *                 Elements an der angegebenen Position im Datensatz darzustellen.
     *
     * @param position Position in der gameSummaryItemList
     */
    @Override
    public void onBindViewHolder(@NonNull GameHistoryViewHolder holder, int position) {

        GameSummary currentItem = gameSummaryItemList.get(position);

        if (currentItem.getGameResult().equals(GameResult.WON.label)) {
            holder.gameResult.setTextColor(ContextCompat.getColor(holder.gameResult.getContext(), android.R.color.holo_green_dark));
        } else {
            holder.gameResult.setTextColor(ContextCompat.getColor(holder.gameResult.getContext(), android.R.color.holo_red_dark));
        }

        holder.gameResult.setText(getGameResult(currentItem.getGameResult()));
        holder.gamePlayedOn.setText(currentItem.getGamePlayedOn());

        String playTime = currentItem.getPlayedTime() + " " + context.getString(R.string.sekunden);
        holder.playedTime.setText(playTime);

        holder.level.setText(getLevel(currentItem.getLevel()));
        holder.minesFound.setText(currentItem.getMinesFound());
        holder.fieldSize.setText(currentItem.getFieldSize());
    }


    /**
     * Gibt die Anzahl der Items in der Liste zurück.
     *
     * @return Größe der Liste
     */
    @Override
    public int getItemCount() {
        return gameSummaryItemList.size();
    }


    /**
     * Gibt das Level das gespeichert werden soll als String Resource zurück, damit
     * englische und deutsche Sprachünterstützung gewährleistet ist.
     *
     * @param level Schwierigkeitsgrad des abgeschlossenen Spiels
     * @return String des Levels
     */
    private String getLevel(String level) {
        String result = null;

        switch (level) {
            case "Anfänger":
                result = context.getString(R.string.level_anfänger);
                break;
            case "Fortgeschritten":
                result = context.getString(R.string.level_fortgeschritten);
                break;
            case "Profi":
                result = context.getString(R.string.level_profi);
                break;
            case "Benutzerdefiniert":
                result = context.getString(R.string.level_benutzedefiniert);
                break;
        }
        return result;
    }

    /**
     * Gibt das Spielergebnis das gespeichert werden soll als String Resource zurück, damit
     * englische und deutsche Sprachünterstützung gewährleistet ist.
     *
     * @param gameResult Ergebnis des abgeschlossenen Spiels
     * @return String des Spielergebnisses
     */
    private String getGameResult(String gameResult) {
        String result = null;

        switch (gameResult) {
            case "Gewonnen":
                result = context.getString(R.string.gewonnen);
                break;
            case "Verloren":
                result = context.getString(R.string.verloren);
                break;
        }
        return result;

    }

}

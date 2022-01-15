package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.GameSummary;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.MinesweeperGame;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;

public class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryAdapter.GameHistoryViewHolder> {
    private List<GameSummary> gameSummaryItemList;
    private View view;
    private Context context;

    public static class GameHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView gamePlayedOn;
        public TextView playedTime;
        public TextView level;
        public TextView gameResult;
        public TextView minesFound;
        public TextView fieldSize;
        private CardView cardView;

        public GameHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.highScoreCard);
            gamePlayedOn = itemView.findViewById(R.id.gamePlayedOn);
            playedTime = itemView.findViewById(R.id.playedTime);
            level = itemView.findViewById(R.id.level);
            gameResult = itemView.findViewById(R.id.gameResult);
            minesFound = itemView.findViewById(R.id.minesFound);
            fieldSize = itemView.findViewById(R.id.fieldSize);
        }
    }


    public GameHistoryAdapter(List<GameSummary> gameSummaryItemList) {
        this.gameSummaryItemList = gameSummaryItemList;
    }

    @NonNull
    @Override
    public GameHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_summary_item, parent, false);
        context = view.getContext();
        return new GameHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHistoryViewHolder holder, int position) {

        GameSummary currentItem = gameSummaryItemList.get(position);


//        holder.cardView.setOnClickListener(v -> {
//            //implement onClick
//            Log.e("CLICK", "ID: " + currentItem.getId());
//        });


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


    @Override
    public int getItemCount() {
        return gameSummaryItemList.size();
    }


    private String getGameResult(String gameResult){
        String result = null;

        switch (gameResult) {
            case "Gewonnen":
            case "Won":
                result = context.getString(R.string.gewonnen);
                break;
            case "Verloren":
            case "Lost":
                result = context.getString(R.string.verloren);
                break;
        }
        return result;

    }

    private String getLevel(String level) {
        String result = null;

        switch (level) {
            case "Anfänger":
            case "Beginner":
                result = context.getString(R.string.level_anfänger);
                break;
            case "Fortgeschritten":
            case "Advanced":
                result = context.getString(R.string.level_fortgeschritten);
                break;
            case "Profi":
            case "Professional":
                result = context.getString(R.string.level_profi);
                break;
            case "Benutzerdefiniert":
            case "Custom":
                result = context.getString(R.string.level_benutzedefiniert);
                break;
        }
        return result;
    }


}

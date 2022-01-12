package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.r0adkll.slidr.Slidr;

import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.HighScore;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;

public class HighScoreCardAdapter extends RecyclerView.Adapter<HighScoreCardAdapter.HighScoreViewHolder> {
    private List<HighScore> highScoreItemList;

    public static class HighScoreViewHolder extends RecyclerView.ViewHolder {
        public TextView gamePlayedOn;
        public TextView playedTime;
        public TextView level;
        public TextView gameResult;
        public TextView minesFound;
        public TextView fieldSize;
        private CardView cardView;

        public HighScoreViewHolder(@NonNull View itemView) {
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


    public HighScoreCardAdapter(List<HighScore> highScoreItemList) {
        this.highScoreItemList = highScoreItemList;
    }

    @NonNull
    @Override
    public HighScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.highscore_item, parent, false);
        return new HighScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreViewHolder holder, int position) {

        HighScore currentItem = highScoreItemList.get(position);


//        holder.cardView.setOnClickListener(v -> {
//            //implement onClick
//            Log.e("CLICK", "ID: " + currentItem.getId());
//        });


        if (currentItem.getGameResult().equals(GameResult.WON.label)) {
            holder.gameResult.setTextColor(ContextCompat.getColor(holder.gameResult.getContext(), android.R.color.holo_green_dark));
        } else {
            holder.gameResult.setTextColor(ContextCompat.getColor(holder.gameResult.getContext(), android.R.color.holo_red_dark));
        }
        holder.gameResult.setText(currentItem.getGameResult());

        holder.gamePlayedOn.setText(currentItem.getGamePlayedOn());
        holder.playedTime.setText(currentItem.getPlayedTime() + " Sekunden");
        holder.level.setText(currentItem.getLevel());
        holder.minesFound.setText(currentItem.getMinesFound());
        holder.fieldSize.setText(currentItem.getFieldSize());
    }


    @Override
    public int getItemCount() {
        return highScoreItemList.size();
    }

}

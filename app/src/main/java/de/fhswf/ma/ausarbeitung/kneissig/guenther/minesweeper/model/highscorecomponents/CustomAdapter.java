package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.highscorecomponents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import org.w3c.dom.Text;

import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.database.entities.HighScore;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.GameResult;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private List<HighScore> highScoreItemList;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView gamePlayedOn;
        public TextView playedTime;
        public TextView level;
        public TextView gameResult;
        public TextView minesLeft;
        public TextView fieldSize;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            gamePlayedOn = itemView.findViewById(R.id.gamePlayedOn);
            playedTime = itemView.findViewById(R.id.playedTime);
            level = itemView.findViewById(R.id.level);
            gameResult = itemView.findViewById(R.id.gameResult);
            minesLeft = itemView.findViewById(R.id.minesLeft);
            fieldSize = itemView.findViewById(R.id.fieldSize);
        }
    }

    public CustomAdapter(List<HighScore> highScoreItemList) {
        this.highScoreItemList = highScoreItemList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.highscore_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        HighScore currentItem = highScoreItemList.get(position);

        if(currentItem.getGameResult().equals(GameResult.WON.label)){
            holder.gameResult.setTextColor(ContextCompat.getColor(holder.gameResult.getContext(), android.R.color.holo_green_dark));
        }
        else{
            holder.gameResult.setTextColor(ContextCompat.getColor(holder.gameResult.getContext(), android.R.color.holo_red_dark));
        }
        holder.gameResult.setText(currentItem.getGameResult());

        holder.gamePlayedOn.setText(currentItem.getGamePlayedOn());
        holder.playedTime.setText(String.valueOf(currentItem.getPlayedTime()));
        holder.level.setText(currentItem.getLevel());
        holder.minesLeft.setText(currentItem.getMinesLeft());
        holder.fieldSize.setText(currentItem.getFieldSize());
    }


    @Override
    public int getItemCount() {
        return highScoreItemList.size();
    }
}

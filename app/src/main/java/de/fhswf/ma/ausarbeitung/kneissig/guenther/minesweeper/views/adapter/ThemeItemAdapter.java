package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;

/**
 * Die Klasse ThemeItemAdapter implementiert den Adapter für den Spinner
 * aus der SettingsActivity.
 *
 * @author Ramon Günther
 */
public class ThemeItemAdapter extends ArrayAdapter<ThemeItem> {

    /**
     * Der Konstruktor übergibt den Kontext und die Liste der Items an
     * die Oberklasse ArrayAdapter.
     *
     * @param context       Kontext
     * @param themeItemList Liste von ThemeItems
     */
    public ThemeItemAdapter(Context context, List<ThemeItem> themeItemList) {
        super(context, R.layout.select_list_item, themeItemList);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.select_list_item, parent, false
            );
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.themeImageView);
        TextView textViewName = convertView.findViewById(R.id.themeNameTextView);

        ThemeItem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getThemeImageId());
            textViewName.setText(currentItem.getThemeName());
        }

        return convertView;
    }
}
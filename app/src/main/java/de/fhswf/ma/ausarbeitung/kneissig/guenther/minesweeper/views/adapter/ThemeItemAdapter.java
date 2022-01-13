package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views.adapter;

import android.content.Context;
import android.hardware.lights.LightState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;

/**
 * Noch verstehen und nachvollziehen
 */
public class ThemeItemAdapter extends ArrayAdapter<ThemeItem> {

    public ThemeItemAdapter(Context context, List<ThemeItem> countryList) {
        super(context, 0, countryList);
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

        ImageView imageViewFlag = convertView.findViewById(R.id.image_view_flag);
        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        ThemeItem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getThemeImageId());
            textViewName.setText(currentItem.getThemeName());
        }

        return convertView;
    }
}
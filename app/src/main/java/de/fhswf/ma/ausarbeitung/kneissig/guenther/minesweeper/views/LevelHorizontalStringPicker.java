package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;

public class LevelHorizontalStringPicker extends HorizontalStringPicker {

    private Context context;

    public LevelHorizontalStringPicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        setItems(createLevelList());

    }

    private List<String> createLevelList() {
        List<String> items = new ArrayList<>();
//        Arrays.asList(Level.values()).forEach(e -> items.add(e.label));

        for (int i = 0; i < Level.values().length; i++) {
            switch (Level.values()[i]) {
                case BEGINNER:
                    items.add(context.getString(R.string.level_anfänger));
                    break;

                case ADVANCED:
                    items.add(context.getString(R.string.level_fortgeschritten));
                    break;

                case PROFESSIONAL:
                    items.add(context.getString(R.string.level_profi));
                    break;

                case CUSTOM:
                    items.add(context.getString(R.string.level_benutzedefiniert));
                    break;
            }
        }
        return items;
    }
}

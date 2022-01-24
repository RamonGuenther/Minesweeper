package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;
import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.model.enums.Level;

/**
 * Die Klasse LevelHorizontalStringPicker erweitert die Klasse {@link HorizontalStringPicker}
 * um die Eigenschaft sowohl mit deutscher und englischer Sprache zu funktionieren.
 *
 * @author Ramon Günther
 */
public class LevelHorizontalStringPicker extends HorizontalStringPicker {

    private final Context context;

    /**
     * Der Konstruktor erstellt die Elemente des Level-Picker
     *
     * @param context Context
     * @param attrs AttributeSet
     */
    public LevelHorizontalStringPicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        setItems(createLevelList());
    }

    /**
     * Erstellt die Elemente des Level-Pickers, je nach eingestellter Sprache (Deutsch, Englisch)
     *
     * @return Liste der Elemente des Level-Pickers
     */
    private List<String> createLevelList() {
        List<String> items = new ArrayList<>();

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

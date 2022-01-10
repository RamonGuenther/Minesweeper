package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.activities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.R;


public class HorizontalStringPicker extends ConstraintLayout {

    private final TextView textView;
    private int counter;
    private List<String> stringList;
    private final ImageButton imageButtonRight;
    private final ImageButton imageButtonLeft;

    public HorizontalStringPicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.horizontal_string_picker, this);

        imageButtonLeft = findViewById(R.id.imageButtonLeft);

        imageButtonRight = findViewById(R.id.imageButtonRight);

        textView = findViewById(R.id.textView);

        counter = 0;

        imageButtonLeft.setOnClickListener(e -> {
            if (counter <= 0 || stringList.isEmpty()) {
                return;
            }
            if (imageButtonRight.getVisibility() == INVISIBLE) {
                imageButtonRight.setVisibility(VISIBLE);
            }
            counter--;
            RunAnimationRight();
            textView.setText(stringList.get(counter));
            RunAnimationRight();
            if (counter <= 0) {
                imageButtonLeft.setVisibility(INVISIBLE);
            }
        });


        imageButtonRight.setOnClickListener(e -> {
            if (counter == stringList.size() - 1 || stringList.isEmpty()) {
                return;
            }
            if (imageButtonLeft.getVisibility() == INVISIBLE) {
                imageButtonLeft.setVisibility(VISIBLE);
            }
            counter++;
            RunAnimationLeft();
            textView.setText(stringList.get(counter));
            RunAnimationLeft();
            if (counter == stringList.size() - 1) {
                imageButtonRight.setVisibility(INVISIBLE);
            }
        });

    }

    private void RunAnimationRight() {
        Animation a = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_in_left);
        a.reset();
        textView.clearAnimation();
        textView.startAnimation(a);
    }

    private void RunAnimationLeft() {
        Animation a = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_in_right);
        a.reset();
        textView.clearAnimation();
        textView.startAnimation(a);
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setItems(List<String> stringList) {
        this.stringList = stringList;
    }

    public String getValue() {
        return textView.getText().toString();
    }

    public void setValue(String level) {
        counter = findIdByLevel(level);

        if (counter <= 0) {
            imageButtonLeft.setVisibility(INVISIBLE);
        } else {
            imageButtonLeft.setVisibility(VISIBLE);
        }
        if (counter == stringList.size() - 1) {
            imageButtonRight.setVisibility(INVISIBLE);
        } else {
            imageButtonRight.setVisibility(VISIBLE);
        }
        textView.setText(stringList.get(counter));
    }

    public int findIdByLevel(String level) {
        for (int i = 0; i < stringList.size(); i++) {
            if (stringList.get(i).contains(level)) {
                return i;
            }
        }
        return 0;
    }

    public ImageButton getImageButtonRight() {
        return imageButtonRight;
    }

    public ImageButton getImageButtonLeft() {
        return imageButtonLeft;
    }

    public TextView getTextView() {
        return textView;
    }
}

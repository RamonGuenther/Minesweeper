package de.fhswf.ma.ausarbeitung.kneissig.guenther.minesweeper.views;

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

//Statt counter , nur mit Index machen
public class HorizontalStringPicker extends ConstraintLayout {

    private final TextView textView;
    private int counter;
    private List<String> itemList;
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
            if (counter <= 0 || itemList.isEmpty()) {
                return;
            }
            if (imageButtonRight.getVisibility() == INVISIBLE) {
                imageButtonRight.setVisibility(VISIBLE);
            }
            counter--;
            RunAnimationRight();
            textView.setText(itemList.get(counter));
            RunAnimationRight();
            if (counter <= 0) {
                imageButtonLeft.setVisibility(INVISIBLE);
            }
        });


        imageButtonRight.setOnClickListener(e -> {
            if (counter == itemList.size() - 1 || itemList.isEmpty()) {
                return;
            }
            if (imageButtonLeft.getVisibility() == INVISIBLE) {
                imageButtonLeft.setVisibility(VISIBLE);
            }
            counter++;
            RunAnimationLeft();
            textView.setText(itemList.get(counter));
            RunAnimationLeft();
            if (counter == itemList.size() - 1) {
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

    public List<String> getItemList() {
        return itemList;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setItems(List<String> stringList) {
        this.itemList = stringList;
    }

    public String getValue() {
        return textView.getText().toString();
    }

    public void setValue(String item) {
        counter = findIdByLevel(item);

        if (counter <= 0) {
            imageButtonLeft.setVisibility(INVISIBLE);
        } else {
            imageButtonLeft.setVisibility(VISIBLE);
        }
        if (counter == itemList.size() - 1) {
            imageButtonRight.setVisibility(INVISIBLE);
        } else {
            imageButtonRight.setVisibility(VISIBLE);
        }
        textView.setText(itemList.get(counter));
    }

    public int findIdByLevel(String item) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).contains(item)) {
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

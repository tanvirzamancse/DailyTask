package com.codescafe.dailytask.UI;

import android.content.Context;

import com.codescafe.dailytask.Model.ColorSet;
import com.codescafe.dailytask.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSource {

    public static List<String> getAllFonts(Context context) {
        List<String> fontsList = new ArrayList<>();
        try {
            fontsList = Arrays.asList(context.getAssets().list("fonts"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontsList;
    }

    public static List<ColorSet> getAllColors() {
        ArrayList<ColorSet> colorsList = new ArrayList<>();
        colorsList.add(new ColorSet(1, R.color.color_black));
        colorsList.add(new ColorSet(2, R.color.color_blue));
        colorsList.add(new ColorSet(3, R.color.color_brown));
        colorsList.add(new ColorSet(4, R.color.color_green));
        colorsList.add(new ColorSet(5, R.color.color_white));
        colorsList.add(new ColorSet(6, R.color.color_pink));
        colorsList.add(new ColorSet(7, R.color.color_red));
        colorsList.add(new ColorSet(8, R.color.color_yellow));
        return colorsList;
    }
}

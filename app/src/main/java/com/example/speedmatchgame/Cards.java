package com.example.speedmatchgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cards {

    private static Cards instance;
    public int[] imageList;
    public ArrayList<Integer> circleShape;
    public ArrayList<Integer> squareShape;
    public ArrayList<Integer> triangleShape;
    public ArrayList<Integer> redColor;
    public ArrayList<Integer> blueColor;
    public ArrayList<Integer> purpleColor;
    public ArrayList<Integer> yellowColor;

    public static Cards setInstance(){
        if (instance == null){
            instance = new Cards();
        }
        return instance;
    }

    private Cards(){
         imageList = new int[12];
         circleShape = new ArrayList<>();
         squareShape = new ArrayList<>();
         triangleShape = new ArrayList<>();
         redColor = new ArrayList<>();
         blueColor = new ArrayList<>();
         purpleColor = new ArrayList<>();
         yellowColor = new ArrayList<>();

         imageList[0] = R.drawable.circle_red;
         imageList[1] = R.drawable.circle_blue;
         imageList[2] = R.drawable.circle_purple;
         imageList[3] = R.drawable.circle_yellow;

         imageList[4] = R.drawable.square_red;
         imageList[5] = R.drawable.square_blue;
         imageList[6] = R.drawable.square_purple;
         imageList[7] = R.drawable.square_yellow;

         imageList[8] = R.drawable.tiangle_red;
         imageList[9] = R.drawable.tiangle_blue;
         imageList[10] = R.drawable.tiangle_purple;
         imageList[11] = R.drawable.tiangle_yellow;

         circleShape.add(0);
         circleShape.add(1);
         circleShape.add(2);
         circleShape.add(3);

         squareShape.add(4);
         squareShape.add(5);
         squareShape.add(6);
         squareShape.add(7);

         triangleShape.add(8);
         triangleShape.add(9);
         triangleShape.add(10);
         triangleShape.add(11);


         redColor.add(0);
         redColor.add(4);
         redColor.add(8);

         blueColor.add(1);
         blueColor.add(5);
         blueColor.add(9);

         purpleColor.add(2);
         purpleColor.add(6);
         purpleColor.add(10);

         yellowColor.add(3);
         yellowColor.add(7);
         yellowColor.add(11);

    }



}

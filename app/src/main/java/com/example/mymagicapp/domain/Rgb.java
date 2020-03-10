package com.example.mymagicapp.domain;

public class Rgb {

    public Integer Red;
    public Integer Green;
    public Integer Blue;

    public Rgb(String hexColor){
        Red = Integer.valueOf( hexColor.substring( 1, 3 ), 16 );
        Green = Integer.valueOf( hexColor.substring( 3, 5 ), 16 );
        Blue = Integer.valueOf( hexColor.substring( 5, 7 ), 16 );
    }

}

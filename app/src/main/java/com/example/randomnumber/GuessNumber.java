package com.example.randomnumber;
import java.util.Random;
public class GuessNumber {
    static public int randomNumber(int digits)
    {
        int min, max, diff;
        Random rnd = new Random();

        switch (digits) {
            case 2:
                 min = 10;
                 max = 99;
                 diff = max - min;
                 break;
            case 3:
                min = 100;
                max = 999;
                diff = max - min;
                break;
            case 4:
                min = 1000;
                max = 9999;
                diff = max - min;
                break;
            default:
                throw new NumberFormatException("Wrong number format");
        }

        return rnd.nextInt(diff + 1) + min;
    }
}

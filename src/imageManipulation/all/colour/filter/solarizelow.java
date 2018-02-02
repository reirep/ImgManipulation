package imageManipulation.all.colour.filter;

import imageManipulation.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by the awesome pierre on 6/01/18
 * invert every colour < treshold
 */
public class solarizelow extends Transform {

    private static final String name = "solarize low";
    private static int threshold = 128;

    public solarizelow(){

    }

    public solarizelow(String arg) {
        try {
            threshold = Integer.parseInt(arg.split(Utils.SEPARATEUR_ARGS)[0]);
        } catch(IndexOutOfBoundsException | NullPointerException e){
            throw new RuntimeException("Illegal arguments !");
        }
    }

    @Override
    public Picture transform(Picture in) {
        Picture workingCopy = new Picture(in);
        workingCopy = parseAndApplyRGB(workingCopy, solarizelow::invert);
        return workingCopy;
    }

    @Override
    public String getName() {
        return name;
    }

    private static double invert(double r, double g, double b, Colors c){
        switch (c){
            case RED:
                return r < threshold ? 255 - r : r;
            case GREEN:
                return g < threshold ? 255 - g : g;
            case BLUE:
                return b < threshold ? 255 - b : b;
            default:
                throw new RuntimeException("Illegal color !");
        }
    }

    public static String help(){
        return "This filter need an argument but will take 128 as a default if none is supplied.\n" +
                "The argument will be the tresholt at witch the colours are not inverted.";
    }
}

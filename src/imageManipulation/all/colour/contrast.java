package imageManipulation.all.colour;

import imageManipulation.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class contrast extends Transform {

    private static final String name = "contrast";
    private double amount;

    public contrast(String arg) throws NoSuchMethodException {
        try {
            amount = Double.parseDouble(arg.split(Utils.SEPARATEUR_ARGS)[0]);
            amount = (259*(amount+255))/(255*(259-amount));
        } catch(IndexOutOfBoundsException | NullPointerException e){
            throw new RuntimeException();
        }
    }

    @Override
    public Picture transform(Picture in) {
        Picture workingCopy = new Picture(in);
        workingCopy = parseAndApplyRGB(workingCopy, this::changeContrast);
        return workingCopy;
    }

    @Override
    public String getName() {
        return name+" ("+amount+")";
    }

    private double changeContrast(double r, double g, double b, Colors c){
        switch (c){
            case RED:
                return amount * (r-128.0)+ 128;
            case GREEN:
                return amount * (g-128.0)+ 128;
            case BLUE:
                return amount * (b-128.0)+ 128;
            default:
                throw new RuntimeException("Illegal color");
        }
    }

    public static String help(){
        return "Cette class a besoin d'un argument: la changement de contraste a faire.\n" +
                "Les valeurs de changment possible de contraste sont entre -255 et 255.";
    }
}

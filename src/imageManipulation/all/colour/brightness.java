package imageManipulation.all.colour;

import imageManipulation.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class brightness extends Transform {

    private static final String name = "brightness";
    private int amount;

    public brightness(String arg) throws NoSuchMethodException {
        try {
            amount = Integer.parseInt(arg.split(Utils.SEPARATEUR_ARGS)[0]);
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
        return name;
    }

    private double changeContrast(double r, double g, double b, Colors c){
        switch(c){
            case RED:
                return r+amount;
            case GREEN:
                return g+amount;
            case BLUE:
                return b+amount;
            default:
                throw new RuntimeException("Illegal color !");
        }
    }

    public static String help(){
        return "Cette class a besoin d'un argument: la changement de brillance a faire.\n" +
                "Les valeurs de changment possible de contraste sont entre -255 et 255.";
    }
}

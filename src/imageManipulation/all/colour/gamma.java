package imageManipulation.all.colour;

import imageManipulation.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class gamma extends Transform {

    private static final String name = "gamma";
    private double gamma;


    public gamma(String arg) throws NoSuchMethodException {
        try {
            gamma = Double.parseDouble(arg.split(Utils.SEPARATEUR_ARGS)[0]);
            gamma = 1/gamma;
        } catch(IndexOutOfBoundsException | NullPointerException e){
            throw new RuntimeException();
        }
    }

    @Override
    public Picture transform(Picture in) {
        in = parseAndApplyRGB(in, this::changeContrast);
        return in;
    }

    @Override
    public String getName() {
        return name;
    }

    private double changeContrast(double r, double g, double b, Colors c){
        switch (c){
            case RED:
                return 255.0*Math.pow(r/255.0, gamma);
            case GREEN:
                return 255.0*Math.pow(g/255.0, gamma);
            case BLUE:
                return 255.0*Math.pow(b/255.0, gamma);
            default:
                throw new RuntimeException("Illegal color");
        }
    }

    public static String help(){
        return "Cette class a besoin d'un argument: la changement de gamma a faire.\n" +
                "Les valeurs de changment possible de contraste sont entre 0 et 10.";
    }
}

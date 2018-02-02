package imageManipulation.all.colour.filter;

import imageManipulation.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class grey extends Transform {

    private static final String name = "grey";

    @Override
    public Picture transform(Picture in) {
        Picture result = new Picture(in);
        result = parseAndApplyRGB(result, grey::getGrey);
        return result;
    }

    @Override
    public String getName() {
        return name;
    }

    private static double getGrey(double r, double g, double b, Colors c){
        return r*0.299+g*0.587+b*0.114;
    }
}

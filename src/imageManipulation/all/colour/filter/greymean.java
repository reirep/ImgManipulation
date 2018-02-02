package imageManipulation.all.colour.filter;

import imageManipulation.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class greymean extends Transform {

    private static final String name = "greymean";

    @Override
    public Picture transform(Picture in) {
        Picture result = new Picture(in);
        result = parseAndApplyRGB(result, greymean::getGrey);
        return result;
    }

    @Override
    public String getName() {
        return name;
    }

    private static double getGrey(double r,double g, double b, Colors c){
        return (r+g+b)/3;
    }
}

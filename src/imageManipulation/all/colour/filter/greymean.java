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
        in = parseAndApplyRGB(in, greymean::getGrey);
        return in;
    }

    @Override
    public String getName() {
        return name;
    }

    private static double getGrey(double r,double g, double b, Colors c){
        return (r+g+b)/3;
    }
}

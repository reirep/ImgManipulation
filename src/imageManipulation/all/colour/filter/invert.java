package imageManipulation.all.colour.filter;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class invert extends Transform {

    private static final String name = "invert colours";

    @Override
    public Picture transform(Picture in) {
        in = parseAndApplyRGB(in, invert::invert);
        return in;
    }

    @Override
    public String getName() {
        return name;
    }

    private static double invert(double r, double g, double b, Colors c){
        switch (c){
            case RED:
                return 255 - r;
            case GREEN:
                return 255 - g;
            case BLUE:
                return 255 - b;
            default:
                throw new RuntimeException("Illegal Color");
        }
    }
}

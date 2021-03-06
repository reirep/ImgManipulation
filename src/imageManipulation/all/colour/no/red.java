package imageManipulation.all.colour.no;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class red extends Transform {

    private static final String name = "no red";

    @Override
    public Picture transform(Picture in) {
        in = parseAndApplyRGB(in, red::getColor);
        return in;
    }

    @Override
    public String getName() {
        return name;
    }

    private static double getColor(double r, double g, double b, Colors c){
        switch (c){
            case RED:
                return 0.0;
            case GREEN:
                return g;
            case BLUE:
                return b;
            default:
                throw new RuntimeException("Illegal color !");
        }
    }
}

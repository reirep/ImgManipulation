package imageManipulation.all.colour.full;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class blue extends Transform {

    private static final String name = "full blue";

    @Override
    public Picture transform(Picture in) {
        in = parseAndApplyRGB(in, blue::getColor);
        return in;
    }

    @Override
    public String getName() {
        return name;
    }

    private static double getColor(double r, double g, double b, Colors c){
        switch (c){
            case RED:
                return r;
            case GREEN:
                return g;
            case BLUE:
                return 255.0;
            default:
                throw new RuntimeException("Illegal color !");
        }
    }
}

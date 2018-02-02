package imageManipulation.all.colour.full;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class red extends Transform {

    private static final String name = "full red";

    @Override
    public Picture transform(Picture in) {
        Picture result = new Picture(in);
        result = parseAndApplyRGB(result, red::getColor);
        return result;
    }

    @Override
    public String getName() {
        return name;
    }

    private static double getColor(double r, double g, double b, Colors c){
        switch (c){
            case RED:
                return 255.0;
            case GREEN:
                return g;
            case BLUE:
                return b;
            default:
                throw new RuntimeException("Illegal color !");
        }
    }
}

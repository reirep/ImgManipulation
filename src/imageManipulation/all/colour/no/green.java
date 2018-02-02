package imageManipulation.all.colour.no;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class green extends Transform {

    private static final String name = "no green";

    @Override
    public Picture transform(Picture in) {
        Picture result = new Picture(in);
        result = parseAndApplyRGB(result, green::getColor);
        return result;
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
                return 0.0;
            case BLUE:
                return b;
            default:
                throw new RuntimeException("Illegal color !");
        }
    }
}

package imageManipulation.all.colour.filter;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class sepia extends Transform {

    private static final String name = "sepia";

    @Override
    public Picture transform(Picture in) {
        Picture workingCopy = new Picture(in);
        workingCopy = parseAndApplyRGB(workingCopy,sepia::applySepia);
        return workingCopy;
    }

    //thanks to https://stackoverflow.com/questions/1061093/how-is-a-sepia-tone-created
    private static double applySepia(double r, double g, double b, Colors c){
        switch (c) {
            case RED:
            case GREEN:
            case BLUE:
                return r*0.393 + g*0.769 + b*0.189;
            default:
                throw new RuntimeException("Illegal color !");
        }
    }

    @Override
    public String getName() {
        return name;
    }
}

package imageManipulation.all.colour.filter;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class square extends Transform {

    //utilise la courbe de x^2 entre 0 et 1 comme profil

    private static final String name = "square";
    @Override
    public Picture transform(Picture in) {
        Picture result = new Picture(in);
        result = parseAndApplyRGB(result, square::getCarre);
        return result;
    }

    @Override
    public String getName() {
        return name;
    }

    private static double getCarre(double r, double g, double b, Colors c){
        switch (c){
            case RED:
                return carre(r);
            case GREEN:
                return carre(g);
            case BLUE:
                return carre(b);
            default:
                throw new RuntimeException("Illegal color !");
        }
    }

    private static double carre(double x){
        return Math.pow(x/256.0, 2)*256.0;
    }
}

package imageManipulation.all.colour.filter;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class root extends Transform {

    //utilise la courbe de sqrt(x) entre 0 et 1 comme profil

    private static final String name = "root";
    @Override
    public Picture transform(Picture in) {
        in = parseAndApplyRGB(in, root::getRacine);
        return in;
    }

    @Override
    public String getName() {
        return name;
    }

    private static double getRacine(double r, double g, double b, Colors c){
        switch (c){
            case RED:
                return racine(r);
            case GREEN:
                return racine(g);
            case BLUE:
                return racine(b);
            default:
                throw new RuntimeException("Illegal color");
        }
    }

    private static double racine(double x){
        return Math.sqrt(x/256.0)*256.0;
    }
}

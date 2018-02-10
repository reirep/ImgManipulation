package imageManipulation.all.colour.filter;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the awesome pierre on 10/02/18
 */
public class witheblackbooster extends Transform {

    private static final String NAME = "witheblackbooster";

    private static final int MAX_TRESH = 235;
    private static final int MIN_TRESH = 15;

    @Override
    public Picture transform(Picture in) {
        for(int x = 0; x < in.getWidth(); x++){
            for(int y = 0; y < in.getHeight(); y++){
                if(isAboveTreshold(MAX_TRESH, in, x,y))
                    in = setWithe(in, x, y);
                if(isUnderTreshold(MIN_TRESH, in, x, y))
                    in = setWithe(in, x, y);
            }
        }
        return in;
    }

    @Override
    public String getName() {
        return NAME;
    }

    private static boolean isAboveTreshold(int treshold, Picture p, int x, int y){
        return p.get(x,y, Colors.RED) > treshold && p.get(x,y, Colors.GREEN) > treshold && p.get(x,y, Colors.BLUE) > treshold;
    }
    private static boolean isUnderTreshold(int treshold, Picture p, int x, int y){
        return p.get(x,y, Colors.RED) < treshold && p.get(x,y, Colors.GREEN) < treshold && p.get(x,y, Colors.BLUE) < treshold;
    }
    private static Picture setBlack(Picture p, int x, int y){
        p.set(x,y,Colors.RED, 0);
        p.set(x,y,Colors.GREEN, 0);
        p.set(x,y,Colors.BLUE, 0);
        return p;
    }
    private static Picture setWithe(Picture p, int x, int y){
        p.set(x,y,Colors.RED, 255);
        p.set(x,y,Colors.GREEN, 255);
        p.set(x,y,Colors.BLUE, 255);
        return p;
    }

}

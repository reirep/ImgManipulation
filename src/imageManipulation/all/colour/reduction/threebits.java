package imageManipulation.all.colour.reduction;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the pierre on 3/02/18
 * reduce the given picture to an 8 bits version
 * algo taken from here:
 * http://www.dfstudios.co.uk/articles/programming/image-programming-algorithms/image-processing-algorithms-part-2-error-diffusion/
 */
public class threebits extends Transform {

    private static final String NAME = "3 bits reduction";

    //black, red, green, yellow, blue, magenta, cyan, wite
    private static final double []
            rR = {0,255,  0,255,  0,255,  0,255},
            gR = {0,  0,255,255,  0,  0,255,255},
            bR = {0,  0,  0,  0,255,255,255,255};

    @Override
    public Picture transform(Picture in) {
        for(int x = 0; x < in.getWidth(); x++){
            for(int y = 0; y < in.getHeight(); y++){
                double r = in.get(x,y, Colors.RED);
                double g = in.get(x,y, Colors.GREEN);
                double b = in.get(x,y, Colors.BLUE);
                int index = getNearest(r,g,b);
                in.set(x,y,Colors.RED, rR[index]);
                in.set(x,y,Colors.GREEN, gR[index]);
                in.set(x,y,Colors.BLUE, bR[index]);
            }
        }
        return in;
    }

    @Override
    public String getName() {
        return NAME;
    }

    private int getNearest(double r, double g, double b){
        double dist = Double.MAX_VALUE;
        int bestIndex = -1;
        for(int i = 0; i < rR.length; i++){
            double current = Math.pow(rR[i]-r,2)+ Math.pow(gR[i]-g,2)+ Math.pow(bR[i]-b,2);
            if(current < dist){
                dist = current;
                bestIndex = i;
            }
        }
        return bestIndex;
    }
}

package imageManipulation.all.edge;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;
import imageManipulation.Utils;
import imageManipulation.all.blur.gaussianBig;
import org.jblas.DoubleMatrix;

/**
 * Created by the awesome pierre on 2/02/18
 *
 * TODO :maybe add some variation options (gaussianbig/small/box) (sobel/scharr/prewitt)
 *
 * depends on
 *  - gaussianBig
 *  - Sobel
 */
public class x_canny extends Transform {

    private static final String NAME = "x_canny edge detection";

    /*
    * 1: gaussian blur
    * 2: sobel
    * 3: edge thining
    * */

    @Override
    public Picture transform(Picture in) {

        //blur
        gaussianBig blur = new gaussianBig();
        in = blur.transform(in);

        //application of sobel
        sobel edge = new sobel();
        in = edge.transform(in);

        DoubleMatrix GX = edge.getGX();
        DoubleMatrix GY = edge.getGY();

        //edge thinning
        for(int x = 0; x < in.getWidth(); x++){
            for(int y = 0; y < in.getHeight(); y++){
                double deg = gradToDeg(Math.atan2(GY.get(x,y), GX.get(x,y)));
                int dirX = getXVal(deg);
                int dirY = getYVal(deg);

                if(Utils.isValidCoords(x+dirX, y+dirY, in.r) && Utils.isValidCoords(x-dirX, y-dirY, in.r)){
                    if(in.get(x,y,Colors.RED) < in.get(x+dirX, y+dirY,Colors.RED) || in.get(x,y,Colors.RED) < in.get(x-dirX, y-dirY,Colors.RED)) {
                        in = deletePixel(in, x, y);
                    }
                }
                else if (Utils.isValidCoords(x+dirX, y+dirY, in.r)){
                    if(in.get(x,y,Colors.RED) < in.get(x+dirX, y+dirY,Colors.RED)) {
                        in = deletePixel(in, x, y);
                    }
                }
                else if (Utils.isValidCoords(x-dirX, y-dirY, in.r)){
                    if(in.get(x,y,Colors.RED) < in.get(x-dirX, y-dirY,Colors.RED)){
                        in = deletePixel(in, x, y);
                    }
                }
            }
        }

        //TODO Hysteresis instead of this lame threshold low filter
        final int LOWER_TRESHOLD = 50, HIGH_TRESHOLD = 200;

        for(int x = 0; x < in.getWidth(); x++)
            for(int y = 0; y < in.getHeight(); y++)
                if(in.get(x,y,Colors.RED) < LOWER_TRESHOLD)
                    in = deletePixel(in,x,y);

        return in;
    }

    @Override
    public String getName() {
        return NAME;
    }

    private int getGradDir(double g){
        if(g == 0)
            return 0;
        if(g > 0)
            return 1;
        return -1;
    }

    private Picture deletePixel(Picture p, int x, int y){
        p.set(x,y, Colors.RED, 0);
        p.set(x,y, Colors.GREEN, 0);
        p.set(x,y, Colors.BLUE, 0);
        return p;
    }

    private double gradToDeg(double val){
        val = val/Math.PI*180;
        while(val > 180)
            val -= 180;
        while (val < 0)
            val += 180;
        return val;
    }

    private int getXVal(double gradInDeg){
        if(gradInDeg >= 25.5 && gradInDeg <= 67.5) // -> tr bl
            return 1;
        if(gradInDeg >= 67.5 && gradInDeg <= 112.5) // -> vert
            return 0;
        if(gradInDeg >= 112.5 && gradInDeg <= 157.5)// -> lr br
            return -1;
        return 1;//horiz
    }

    private int getYVal(double gradInDeg){
        if(gradInDeg >= 25.5 && gradInDeg <= 67.5) // -> tr bl
            return 1;
        if(gradInDeg >= 67.5 && gradInDeg <= 112.5) // -> vert
            return 1;
        if(gradInDeg >= 112.5 && gradInDeg <= 157.5)// -> lr br
            return 1;
        return 0;//horiz
    }


    /*
    * [0; 22.5] [157.5;180] -> horizontal
    * [22.5; 67.5] -> diag top right, bot left
    * [67.5; 112.5] -> vertical
    * [112.5; 157.5] -> diag top left, bot right
    * */

    /*
    * Four points:
    * (1,0) -> horiz
    * (1,1) -> tr, bl
    * (0,1) -> vert
    * (-1,1) -> tl, br
    * */

}

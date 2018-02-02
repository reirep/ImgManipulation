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
                int dirX = getGradDir(GX.get(x,y));
                int dirY = getGradDir(GY.get(x,y));
                if(Utils.isValidCoords(x+dirX, y+dirY, in.r) && Utils.isValidCoords(x-dirX, y-dirY, in.r)){
                    if(in.get(x,y,Colors.RED) > in.get(x+dirX, y+dirY,Colors.RED) && in.get(x,y,Colors.RED) > in.get(x-dirX, y-dirY,Colors.RED)) {
                        in = deletePixel(in, x, y);
                    }
                }
                else if (Utils.isValidCoords(x+dirX, y+dirY, in.r)){
                    if(in.get(x,y,Colors.RED) > in.get(x+dirX, y+dirY,Colors.RED)) {
                        in = deletePixel(in, x, y);
                    }
                }
                else if (Utils.isValidCoords(x-dirX, y-dirY, in.r)){
                    if(in.get(x,y,Colors.RED) > in.get(x-dirX, y-dirY,Colors.RED)){
                        in = deletePixel(in, x, y);
                    }
                }
            }
        }

        //Hysteresis
        //TODO un  union-find au lieu e faire un simple filtre passe haut
        final int LOWER_TRESHOLD = 50, HIGH_TRESHOLD = 200;

        for(int x = 0; x < in.getWidth(); x++){
            for(int y = 0; y < in.getHeight(); y++){
                if(in.get(x,y,Colors.RED) < LOWER_TRESHOLD)
                    in = deletePixel(in,x,y);
            }
        }


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
}

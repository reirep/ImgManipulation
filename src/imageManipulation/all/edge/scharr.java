package imageManipulation.all.edge;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;
import imageManipulation.all.colour.filter.grey;
import org.jblas.DoubleMatrix;

import static imageManipulation.Utils.convolution;

/**
 * Created by the awesome pierre on 27/01/18
 *
 * //depends on coulour.filter.grey
 *
 */
public class scharr extends Transform {

    private static final String NAME ="edge scharr";

    private static final DoubleMatrix kernelX = new DoubleMatrix(new double[][]{
            { 3,0, -3},
            {10,0,-10},
            { 3,0, -3}
    });
    private static final DoubleMatrix kernelY = new DoubleMatrix(new double[][]{
            {  3, 10,  3},
            {  0,  0,  0},
            { -3,-10, -3},
    });

    @Override
    public Picture transform(Picture in) {

        grey g = new grey();
        in = g.transform(in);

        DoubleMatrix magX = convolution(in.r, kernelX, 1);
        DoubleMatrix magY = convolution(in.r, kernelY, 1);

        for(int x = 0; x < in.getWidth(); x++){
            for(int y = 0; y < in.getHeight(); y++){
                int c = (int)Math.sqrt(Math.pow(magX.get(x,y),2) + Math.pow(magY.get(x,y), 2) );
                in.set(x,y, Colors.RED, c);
                in.set(x,y, Colors.GREEN, c);
                in.set(x,y, Colors.BLUE, c);

            }
        }

        return in;
    }

    @Override
    public String getName() {
        return NAME;
    }
}

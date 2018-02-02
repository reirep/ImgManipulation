package imageManipulation.all.nette;

import imageManipulation.Picture;
import imageManipulation.Transform;
import org.jblas.DoubleMatrix;

import static imageManipulation.Utils.convolution;

/**
 * Created by the awesome pierre on 28/01/18
 */
public class sharpen extends Transform {

    private static final String NAME = "sharpen";

    private static final DoubleMatrix kernel = new DoubleMatrix(new double [][] {
            { 0,-1, 0},
            {-1, 5,-1},
            { 0,-1, 0}
    });

    @Override
    public Picture transform(Picture in) {
        return convolution(in,kernel, 1);
    }

    @Override
    public String getName() {
        return NAME;
    }
}

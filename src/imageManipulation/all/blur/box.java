package imageManipulation.all.blur;

import imageManipulation.Picture;
import imageManipulation.Transform;
import org.jblas.DoubleMatrix;

import static imageManipulation.Utils.convolution;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class box extends Transform {

    private final static String NAME = "box blur";

    private final static DoubleMatrix kernel = new DoubleMatrix(new double [][]{
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    });

    private static final double coef = 1/9.0;

    @Override
    public Picture transform(Picture in) {
        return convolution(in, kernel, coef);
    }

    @Override
    public String getName() {
        return NAME;
    }
}

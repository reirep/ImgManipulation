package imageManipulation.all.blur;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;
import org.jblas.DoubleMatrix;

import static imageManipulation.Utils.convolution;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class gaussianSmall extends Transform {

    private final static String NAME = "small gaussianBig blur";

    private final static DoubleMatrix kernel = new DoubleMatrix(new double[][]{
            {1,2,1},
            {2,4,2},
            {1,2,1}
    });

    private static final double coef = 1/16.0;

    @Override
    public Picture transform(Picture in) {
        return convolution(in,kernel,coef);
    }

    @Override
    public String getName() {
        return NAME;
    }
}

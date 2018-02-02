package imageManipulation.all.blur;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;
import org.jblas.DoubleMatrix;
import static imageManipulation.Utils.convolution;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class gaussianBig extends Transform {

    private final static String NAME = "big gaussian blur";

    private final static DoubleMatrix kernel = new DoubleMatrix(new double [][]{
            {1, 4, 6, 4,1},//16
            {4,16,24,16,4},//64
            {6,24,36,24,6},//96
            {4,16,24,16,4},//64
            {1, 4, 6, 4,1} //16
    });

    private static final double coef = 1/256.0;

    @Override
    public Picture transform(Picture in) {
        return convolution(in, kernel, coef);
    }

    @Override
    public String getName() {
        return NAME;
    }
}

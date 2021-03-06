package imageManipulation.all.edge;

import imageManipulation.Picture;
import imageManipulation.Transform;
import imageManipulation.all.colour.filter.grey;
import org.jblas.DoubleMatrix;

import static imageManipulation.Utils.convolution;

/**
 * Created by the awesome pierre on 28/01/18
 */
public class basic1 extends Transform {

    private static final String NAME = "edge basic 1";

    private static final DoubleMatrix kernel = new DoubleMatrix(new double [][] {
            { 1, 0,-1},
            { 0, 0, 0},
            {-1, 0, 1}
    });

    @Override
    public Picture transform(Picture in) {
        grey g = new grey();
        return convolution(g.transform(in),kernel, 1);
    }

    @Override
    public String getName() {
        return NAME;
    }
}

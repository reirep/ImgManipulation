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
 * https://en.wikipedia.org/wiki/Prewitt_operator
 *
 */
public class prewitt extends Transform {

    private static final String NAME ="edge prewitt";

    private static final DoubleMatrix kernelX = new DoubleMatrix(new double[][]{
            {-1,0,1},
            {-1,0,1},
            {-1,0,1}
    });
    private static final DoubleMatrix kernelY = new DoubleMatrix(new double[][]{
            {-1,-1,-1},
            { 0, 0, 0},
            { 1, 1, 1},
    });

    @Override
    public Picture transform(Picture in) {

        grey g = new grey();
        Picture workingCopy = g.transform(in);

        DoubleMatrix magX = convolution(workingCopy.r, kernelX, 1);
        DoubleMatrix magY = convolution(workingCopy.r, kernelY, 1);

        for(int x = 0; x < workingCopy.getWidth(); x++){
            for(int y = 0; y < workingCopy.getHeight(); y++){
                int c = (int)Math.sqrt(Math.pow(magX.get(x,y),2) + Math.pow(magY.get(x,y), 2) );
                workingCopy.set(x,y, Colors.RED, c);
                workingCopy.set(x,y, Colors.GREEN, c);
                workingCopy.set(x,y, Colors.BLUE, c);

            }
        }

        return workingCopy;
    }

    @Override
    public String getName() {
        return NAME;
    }
}

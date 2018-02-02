package imageManipulation.all.nette;

import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the awesome pierre on 28/01/18
 *
 * depends on the big gaussian blur and contrast
 *
 * TODO !
 * https://en.wikipedia.org/wiki/Unsharp_masking
 * https://stackoverflow.com/questions/2938162/how-does-an-unsharp-mask-work
 */
public class x_unsharp extends Transform {

    private static final String NAME = "x_unsharp";

    @Override
    public Picture transform(Picture in) {
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
}

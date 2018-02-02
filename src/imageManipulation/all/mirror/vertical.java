package imageManipulation.all.mirror;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class vertical extends Transform {

    private static final String name = "vertical mirroring";

    @Override
    public Picture transform(Picture in) {
        Picture workingCopy = new Picture(in);
        for(int x = 0; x < workingCopy.getWidth(); x++){
            for(int y =0; y < workingCopy.getHeight(); y++){
                workingCopy.set(x,y, Colors.RED, in.get(in.getWidth()-1-x, y,Colors.RED));
                workingCopy.set(x,y, Colors.GREEN, in.get(in.getWidth()-1-x, y,Colors.GREEN));
                workingCopy.set(x,y, Colors.BLUE, in.get(in.getWidth()-1-x, y,Colors.BLUE));
            }
        }
        return workingCopy;
    }

    @Override
    public String getName() {
        return name;
    }
}

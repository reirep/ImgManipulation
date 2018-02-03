package imageManipulation.all.mirror;

import imageManipulation.Colors;
import imageManipulation.Picture;
import imageManipulation.Transform;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class horizontal extends Transform {

    private static final String name = "horizontal mirroring";

    @Override
    public Picture transform(Picture in) {
        for(int x = 0; x < in.getWidth(); x++){
            for(int y =0; y < in.getHeight(); y++){
                in.set(x,y, Colors.RED, in.get(x,in.getHeight()-1-y,Colors.RED));
                in.set(x,y, Colors.GREEN, in.get(x,in.getHeight()-1-y,Colors.GREEN));
                in.set(x,y, Colors.BLUE, in.get(x,in.getHeight()-1-y,Colors.BLUE));
            }
        }
        return in;
    }

    @Override
    public String getName() {
        return name;
    }
}

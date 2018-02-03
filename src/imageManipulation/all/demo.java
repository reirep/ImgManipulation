package imageManipulation.all;

import imageManipulation.Picture;
import imageManipulation.Transform;
import imageManipulation.Utils;

/**
 * Created by the awesome pierre on 6/01/18
 *
 * this is an example class to create an effect
 *
 */
public class demo extends Transform {

    //when the effect takes no args
    public demo(){

    }

    //when the effect takes args, mus be in the format: transformation:arg-arg-arg
    public demo(String arg){
        String args[] = arg.split(Utils.SEPARATEUR_ARGS);
        for(String a : args)
            System.out.println(a);
    }

    private static final String name = "demo";
    @Override
    public Picture transform(Picture in) {
        return in;
    }

    @Override
    public String getName() {
        return name;
    }
}

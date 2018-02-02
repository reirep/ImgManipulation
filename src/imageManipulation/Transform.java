package imageManipulation;

/**
 * Created by the awesome pierre on 6/01/18
 */
public abstract class Transform {

    public Transform(){
        //in case of bad arguments, do that:
        //throw new RuntimeException();
    }

    public Transform(String arg){
        //in case of bad arguments, do that:
        //throw new RuntimeException();
    }

    /**
     * @param in : a valid, non-null, BufferedImage
     * @return : a copy of the original picture, modified with a filter
     * */
    public abstract Picture transform(Picture in);

    /**
     * @return : the name of the filter
     * */
    public abstract String getName();

    /**
     * @return : somme giberish to maybe help someone, one day ... maybe ...
     * */
    public static String help(){
        return "This transformation don't need any argument.";
    }


    @FunctionalInterface
    public interface Function3<A,B,C,D,R> {
        R apply(A a, B b, C c, D d);
    }

    /**
     * @param img : a valid, non null, BufferedImage.
     * @param transform : a valid, non-null, function that takes an int as an argument and return an int
     * @return : it will return the orginal image and have applid the given function on all of it's pixel,
     *           starting from the 0,0 and parsing the height first
     * */
    protected static Picture parseAndApplyRGB(Picture img, Function3<Double,Double,Double, Colors, Double> transform){
        double r,g,b;
        for(int x = 0; x < img.getWidth(); x++)
            for(int y =0; y < img.getHeight(); y++){
                r = img.get(x,y, Colors.RED);
                g = img.get(x,y, Colors.GREEN);
                b = img.get(x,y, Colors.BLUE);

                img.set(x,y,Colors.RED, transform.apply(r,g,b,Colors.RED));
                img.set(x,y,Colors.GREEN, transform.apply(r,g,b,Colors.GREEN));
                img.set(x,y,Colors.BLUE, transform.apply(r,g,b,Colors.BLUE));
            }
        return img;
    }
}

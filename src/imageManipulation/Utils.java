package imageManipulation;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import org.jblas.DoubleMatrix;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by the awesome pierre on 6/01/18
 */
public class Utils {

    static final String SEPARATEUR_FILTRES = ">";
    static final String SEPARATEUR_FILTRE_ARG = ":";
    public static final String SEPARATEUR_ARGS = ",";

    public static final String SEPARATEUR_NOM_FILTRES = "_";

    public static final String FILTER_PACKAGE = "imageManipulation.all.";

    //CLI helpers

    /**
     * Get the string of the general help, enmpty for now
     * @return the help string
     */
    public static String helpAll(){
        //TODO
        return "WIP";
    }

    /**
     * Print a list of all the currely existing filters.
     */
    public static List<String> list(){
        FastClasspathScanner scanner = new FastClasspathScanner(FILTER_PACKAGE.substring(0,FILTER_PACKAGE.length()-1));
        ScanResult result = scanner.scan();
        LinkedList<String> filters = new LinkedList<>();

        for(String s : result.getNamesOfAllClasses()) {
            if (s.contains(FILTER_PACKAGE) && !s.contains("$") && !s.contains("x_")) {
                s = s.replace(FILTER_PACKAGE, "");
                filters.add(s);
            }
        }
        return filters;
    }

    //internal utils for the photo filters

    //the luminescnce given here is this one :  ITU BT.709
    //https://stackoverflow.com/questions/596216/formula-to-determine-brightness-of-rgb-color
    public static double luminescence(double r, double g, double b){
        return 0.2126*r+0.7152*g+0.0722*b;
    }

    /**
     * Compute the average of three int
     * @param a: the first value
     * @param b: the seccond value
     * @param c: the third value
     * @return the average of the three given value
     */
    public static int avg(int a, int b, int c){
        return (a+b+c)/3;
    }

    /**
     * Compute the average of n value
     * @param values: the value to compute the average
     * @return the average of the given values
     */
    public static int avg(int ... values){
        double res = 0;
        double size = values.length;
        for(int i : values)
            res += i/size;
        return (int)Math.round(res);
    }

    /**
     * Compute the softmax of an array of value.
     * The softmax of n value is the n' values whos total is equal
     * to 1 and the proportions are aquivalent to the orginal n.
     * @param val: to values to compute the softmax on
     * @return the softmax
     */
    public static double [] softMax(double ... val){
        return softMax(val.length, val);
    }

    /**
     * Same as "softmax(double ... val)" except it take int as argument,
     * convert them and pass the to the other softmax
     * @param val: the values to use to compute the softmax
     * @return the softmax
     */
    public static double [] softMax(int ... val){
        return softMax(val.length, val);
    }

    /**
     * Compute the softmax of an array of value.
     * The softmax of n value is the n' values whos total is equal
     * to 1 and the proportions are aquivalent to the orginal n.
     * @param maxIndex: the values after or at this index will be ignored
     * @param val: to values to compute the softmax on
     * @return the softmax
     */
    public static double [] softMax(int maxIndex, double ... val){
        assert maxIndex > 0 && maxIndex <= val.length : "Illegal maxIndex in softmax.";

        double max = 0;
        for(int i = 0; i < maxIndex; i++)
            max += val[i];
        for(int i = 0; i < maxIndex; i++)
            val[i] = val[i]/max;
        return val;
    }

    /**
     * Same as "softmax(double ... val)" except it take int as argument,
     * convert them and pass the to the other softmax
     * @param maxIndex: the values after or at this index will be ignored
     * @param val: the values to use to compute the softmax
     * @return the softmax
     */
    public static double [] softMax(int maxIndex, int ... val){
        double equiv [] = new double[val.length];
        for(int i = 0; i < val.length; i++)
            equiv[i] = val[i];
        return softMax(maxIndex, equiv);
    }

    //https://en.wikipedia.org/wiki/Kernel_(image_processing)#Convolution
    //coef doit etre qqch comme 1/256 (par ex)
    public static Picture convolution(Picture p, DoubleMatrix kernel, double coef){
        p.r = convolution(p.r, kernel, coef);
        p.g = convolution(p.g, kernel, coef);
        p.b = convolution(p.b, kernel, coef);
        return p;
    }

    //exctly like the method above but won't recompute the value for ech layer
    public static Picture convolutionMono(Picture p, DoubleMatrix kernel, double coef) {
        p.r = convolution(p.r, kernel, coef);
        p.g.copy(p.r);
        p.b.copy(p.r);
        return p;
    }


    //TODO mirrorer les pixel hors images par les pixels en image au lieu de les ignorer
    //https://en.wikipedia.org/wiki/Kernel_(image_processing)#Convolution
    public static DoubleMatrix convolution(DoubleMatrix dm, DoubleMatrix kernel, double coef){
        DoubleMatrix dest = new DoubleMatrix(dm.getRows(), dm.getColumns());
        dest.copy(dm);

        double d;

        int midX = kernel.getRows()/2;
        int midY = kernel.getColumns()/2;

        for(int x = 0; x < dm.getRows(); x++){
            for(int y = 0; y < dm.getColumns(); y++){
                d = 0;

                for(int xK = 0; xK < kernel.getRows(); xK++){
                    for(int yK = 0; yK < kernel.getColumns(); yK++){
                        int xOrig = x + xK - midX;
                        int yOrig = y + yK - midY;

                        if(!isValidCoords(xOrig, yOrig, dm))
                            continue;

                        d += coef * dm.get(xOrig,yOrig)*kernel.get(xK,yK);
                    }
                }
                dest.put(x,y, d);
            }
        }
        return dest;
    }

    //TODO manipulate this to return the valid (mirrored if necessary) point
    public static boolean isValidCoords(int x, int y, DoubleMatrix p){
        return x >= 0 && y >= 0 && x < p.getRows() && y < p.getColumns();
    }

    public static class Geometry {

        //TODO test this function
        /**
         * Draw a circle in the area defined by the rectangle (x0,y0) (x0,y1) (x1,y0) (x1,y1). The background is withe.
         * The circle will be of coolour (r,g,b)
         * @param p the picture to draw the circle in
         * @param r the red channel of the circle
         * @param g the green chennel of the circle
         * @param b the blue channle of the circle
         * @param x0 the x bottom left coordinate of the rectangle
         * @param y0 the y bottom left coordinate of the rectangle
         * @param x1 the x upper right coordinate of the rectangle
         * @param y1 the y upper right coordinate of the rectangle
         * @return the image with the circle draw in it
         */
        public static Picture drawCircle(Picture p, double r, double g, double b, int x0, int y0, int x1, int y1){
            int xBase = Math.min(x0,x1);
            int yBase = Math.min(y0,y1);

            double radius = Math.abs(x1-x0)/2;
            double centerX = xBase+Math.abs(x1-x0)/2;
            double centerY = yBase+Math.abs(y1-y0)/2;

            for(int x = 0;  x < Math.abs(x1-x0); x++){
                for(int y = 0; y < Math.abs(y1-y0); y++){
                    if(isInsideTheCircle(centerX, centerY, radius, x, y)){
                        p.set(x,y,Colors.RED, r);
                        p.set(x,y,Colors.GREEN, g);
                        p.set(x,y,Colors.BLUE, b);
                    }
                    else{
                        p.set(x,y,Colors.RED, 0);
                        p.set(x,y,Colors.GREEN, 0);
                        p.set(x,y,Colors.BLUE, 0);
                    }
                }
            }
            return p;
        }

        private static boolean isInsideTheCircle(double xCenter, double yCenter, double radius, int x, int y){
            return Math.pow(x+xCenter,2)+Math.pow(y+yCenter,2) <= Math.pow(radius,2);
        }

        /**
         * Draw a uniorm rectangle inside the given coordinates
         * @param p the picture to draw the rectangle into
         * @param r the red part of the color of the rectangle
         * @param g the green part of the color of ht rectangle
         * @param b the blue part of the color of the rectangle
         * @param x0 the bottom left x coordiante
         * @param y0 the bottom left y coordinate
         * @param x1 the top right x coordinate
         * @param y1 the top right y coordinate
         * @return the picture with the rectangle draw into it
         */
        public static Picture drawRectangle(Picture p, double r, double g, double b, int x0, int y0, int x1, int y1){
            int xBase = Math.min(x0,x1);
            int yBase = Math.min(y0,y1);

            int xSize = Math.abs(x0-x1);
            int ySize = Math.abs(y0-y1);

            for(int x = xBase; x < xBase+xSize; x++){
                for(int y = yBase; y < yBase+ySize; y++){
                    p.set(x,y,Colors.RED, r);
                    p.set(x,y,Colors.GREEN, g);
                    p.set(x,y,Colors.BLUE, b);
                }
            }
            return p;
        }

        public static Picture drawLosange(Picture p, double r, double g, double b){
            //TODO
            return null;
        }

        //TODO add more forms :)
    }

    public static boolean equals(double d1, double d2, double delta){
        return Math.abs(d1-d2) < delta;
    }

}

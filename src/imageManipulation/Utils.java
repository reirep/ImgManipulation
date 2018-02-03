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
     * Print a list of all the currely existing filters, broken for now.
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

}

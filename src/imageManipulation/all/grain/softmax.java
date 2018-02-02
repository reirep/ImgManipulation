package imageManipulation.all.grain;

import imageManipulation.*;
import org.jblas.DoubleMatrix;


/**
 * Created by the awesome pierre on 6/01/18
 *
 * do this per-color
 * recorrect the color with alpha in mind
 *
 *
 * do from an orig to a copy
 *
 * do on the same instance and do soma smart parsing
 */
public class softmax extends Transform {

    private static float alpha = 0.95f;
    private static int n = 10;
    private static final int POWER_EXP_COEF = 2;

    private final static int var [] = {-1,0,1};

    private static DoubleMatrix coefs = new DoubleMatrix(var.length*var.length);
    private static DoubleMatrix oldColor =  new DoubleMatrix(var.length*var.length);

    private Picture original;

    public softmax(){}

    //when the effect takes args, mus be in the format: transformation:arg-arg-arg
    public softmax(String arg){
        String args[] = arg.split(Utils.SEPARATEUR_ARGS);
        if(args.length >= 1)
            n = Integer.parseInt(args[0]);
        if(args.length >= 2)
            alpha = Float.parseFloat(args[1]);
    }

    private static final String name = "grain softmax";
    @Override
    public Picture transform(Picture in) {
        Picture dest = new Picture(in);
        for(int $ = 0; $ < n; $++) {
            original = new Picture(dest);
            //TODO: peut etre faire couleur par couleur pour mieux utiliser les caches ? A creuser.
            for (int x = 0; x < dest.getWidth(); x++)
                for (int y = 0; y < dest.getHeight(); y++){
                    dest.set(x,y,Colors.RED, getColor(x,y,Colors.RED));
                    dest.set(x,y,Colors.GREEN, getColor(x,y,Colors.GREEN));
                    dest.set(x,y,Colors.BLUE, getColor(x,y,Colors.BLUE));
                }
        }
        return dest;
    }

    public static String help(){
        return "This filter need one or two argument. The " +
                "optionnal first one will be the number of time this filter " +
                "will be applied and the optional seccond one " +
                "will be the alpha value keeped of the original pixel.";
    }

    @Override
    public String getName() {
        return name;
    }

    private double getColor(int x, int y, Colors c){

        int cnt = 0;
        double centerColor = original.get(x,y,c);

        for (int xVar : var)
            for (int yVar : var) {

                int xV = x + xVar;
                int yV = y + yVar;

                if (xV == x && yV == y)
                    continue;
                if (!isValirCoord(xV, yV, original.getWidth(), original.getHeight()))
                    continue;

                double neibourColor = original.get(xV, yV, c);

                oldColor.put(cnt, neibourColor);
                coefs.put(cnt, getCoef(neibourColor, centerColor, getDist2(x, y, xV, yV)));

                cnt++;
            }

        coefs = softMax(coefs, cnt);

        double res = 0;
        for(int i = 0; i < cnt; i++)
            res += oldColor.get(i)*coefs.get(i);

        res = original.get(x,y,c)*alpha + (1-alpha)*res;
        return res;
    }

    private boolean isValirCoord(int x, int y, int width, int height){
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    private double getCoef(double c1, double c2, double dist){
        return (256.0- Math.abs(c1-c2)/Math.pow(dist,POWER_EXP_COEF));
    }

    private double getDist2(int x1, int y1, int x2, int y2){
        return Math.floor(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }

    private DoubleMatrix softMax(DoubleMatrix m, int cnt){
        double max = 0;
        for(int i = 0; i < cnt; i++)
            max += m.get(i);
        for(int i = 0; i < cnt; i++)
            m.put(i, m.get(i)/max);
        return m;
    }
}

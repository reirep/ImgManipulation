package imageManipulation.all.zone;


import imageManipulation.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by pierre on 18/01/18
 *
 * this zone creator parse the photo left to right and top down.
 * It takes the first unprocessed pixel it sees and regroup it with similar neibour pixel.
 * All the similir pixel will have the avg color of thoses pixels.
 */

public class first extends Transform {

    private static final String NAME = "zone first pixel ";
    private static int treshold = 60;
    private static final int var [] = {-1,0,1};

    private static boolean [][] clustered;//true quand le pixel est pris dans une zone

    public first(){}

    public first(String arg){
        String args[] = arg.split(Utils.SEPARATEUR_ARGS);
        if(args.length >= 1)
            treshold = Integer.parseInt(args[0]);
    }

    public static String help(){
        return "This transformation have one optional argument: the treshold of the zones. " +
                "It must be between 1 and 256 (50 is a big value).";
    }

    @Override
    public String getName() {
        return NAME+"("+treshold+")";
    }

    @Override
    public Picture transform(Picture in) {
        clustered = new boolean[in.getWidth()][in.getHeight()];
        LinkedList<Point> q;
        Set<Point> s;

        for(int x = 0; x < in.getWidth(); x++){
            for(int y =0; y < in.getHeight(); y++){
                if(clustered[x][y])//on skippe le pixel si il a déjà été clusteré
                    continue;

                q = new LinkedList<>();
                s  = new HashSet<>();

                q.add(new Point(x,y,
                        in.get(x,y, Colors.RED),
                        in.get(x,y,Colors.GREEN),
                        in.get(x,y,Colors.BLUE)));

                while(!q.isEmpty()){
                    Point current = q.pop();
                    s.add(current);
                    getEligibleNeibourns(
                            in.get(x,y,Colors.RED),
                            in.get(x,y,Colors.GREEN),
                            in.get(x,y,Colors.BLUE),
                            current.x,
                            current.y,
                            in,
                            q
                    );
                }

                double r = 0, g = 0, b = 0;
                double sizeSet = s.size();
                for(Point p : s){
                    r += p.r/sizeSet;
                    g += p.g/sizeSet;
                    b += p.b/sizeSet;
                }

                for(Point p : s) {
                    in.set(p.x, p.y,Colors.RED, r);
                    in.set(p.x, p.y,Colors.GREEN, g);
                    in.set(p.x, p.y,Colors.BLUE, b);
                }
            }
        }
        return in;
    }

    private static class Point{
        int x,y;
        double r,g,b;
        public Point(){}
        public Point(int x, int y, double r, double g, double b){
            this.x = x;
            this.y = y;
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    private static boolean passTreshold(double r1, double g1, double b1, double r2, double g2, double b2){
        return Math.abs(r1-r2) < treshold &&
                Math.abs(g1-g1) < treshold &&
                Math.abs(b1-b2) < treshold;
    }

    //
    private static void getEligibleNeibourns(double r, double g, double b, int x, int y, Picture src, LinkedList<Point> q){
        for(int xV : var)
            for(int yV : var){
                if(xV == yV && xV == 0)
                    continue;
                int X = x+xV;
                int Y = y+yV;
                if(X >= 0 && Y >= 0 && X < src.getWidth() && Y < src.getHeight() &&
                        passTreshold(r,g,b, src.get(X,Y,Colors.RED),src.get(X,Y,Colors.GREEN),src.get(X,Y,Colors.BLUE)) && !clustered[X][Y]){
                    clustered[X][Y] = true;
                    q.add(new Point(X, Y, src.get(X,Y,Colors.RED),src.get(X,Y,Colors.GREEN),src.get(X,Y,Colors.BLUE)));
                }
            }
    }
}

package imageManipulation;

import org.jblas.DoubleMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by the awesome pierre on 27/01/18
 */
public class Picture {
    public DoubleMatrix r, g, b;
    private StringBuilder name;

    public Picture(String name){
        this.name = new StringBuilder(name);
    }

    public Picture(String name, BufferedImage i){
        this.name = new StringBuilder(name);
        initFromBufferedImage(i);
    }

    public Picture(Picture p){
        name = new StringBuilder(p.getName());

        r = new DoubleMatrix(p.r.getRows(), p.r.getColumns());
        r.copy(p.r);

        g = new DoubleMatrix(p.g.getRows(), p.g.getColumns());
        g.copy(p.g);

        b = new DoubleMatrix(p.b.getRows(), p.b.getColumns());
        b.copy(p.b);
    }

    public Picture(String name, int width, int length){
        this.name = new StringBuilder(name);
        r = new DoubleMatrix(width,length);
        g = new DoubleMatrix(width,length);
        b = new DoubleMatrix(width,length);
    }

    private void initFromBufferedImage(BufferedImage i){
        int width = i.getWidth(), height = i.getHeight();
        r = new DoubleMatrix(width, height);
        g = new DoubleMatrix(width, height);
        b = new DoubleMatrix(width, height);

        for(int x = 0;  x < width; x++)
            for(int y = 0; y < height; y++){
                Color c = new Color(i.getRGB(x,y));
                r.put(x, y, c.getRed());
                g.put(x, y, c.getGreen());
                b.put(x, y, c.getBlue());
            }
    }

    public int getWidth(){
        return this.r.getRows();
    }

    public int getHeight(){
        return this.r.getColumns();
    }

    public double get(int x, int y, Colors c){
        switch (c){
            case RED:
                return this.r.get(x,y);
            case GREEN:
                return this.g.get(x,y);
            case BLUE:
                return this.b.get(x,y);
            default:
                throw new RuntimeException("Illegal color !");
        }
    }

    public void set(int x, int y, Colors c, double val){
        val = Math.max(Math.min(255.0,val),0.0);
        switch (c){
            case RED:
                this.r.put(x,y,val);
            break;
            case GREEN:
                this.g.put(x,y,val);
            break;
            case BLUE:
                this.b.put(x,y,val);
            break;
            default:
                throw new RuntimeException("Illegal color !");
        }
    }

    public BufferedImage getBufferedImage(){
        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x < this.getWidth(); x++){
            for(int y =0; y < this.getHeight(); y++){
                bi.setRGB(x,y, (new Color(getColorVal(r.get(x,y)), getColorVal(g.get(x,y)), getColorVal(b.get(x,y)))).getRGB());
            }
        }
        return bi;
    }

    public String getName(){
        return name.toString();
    }

    public void appendToName(String value){
        this.name.append(value);
    }

    private int getColorVal(double val){
        return (int)Math.max(Math.min(255,Math.round(val)),0.0);
    }

}

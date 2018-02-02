package imageManipulation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Created by pierre on 6/01/18
 *
 * TODO:
 *  maybe add blends and two photos effect ?
 *  https://dunnbypaul.net/blends/
 *  cadres
 *  vignette effect
 *s
 * http://www.dfstudios.co.uk/articles/programming/image-programming-algorithms/
 *  color diminution (-> 8 bits)
 *  color correction
 *
 *  play with the hue,
 *  make a color stand out ?
 *
 *  rescale (4 args)
 *
 *  UF on the similar pixel and regroup them
 *      -> play with the similarity degree
 *
 *  deflouter une photo : pixel auquel on va enlever les pixels les plus differents autour de lui (eventuellement avec la distance)
 *
 *  chercher les pixel les plus "grappés et les regrouper ensembles"
 *
 *  edge detection
 *
 *      canny edge detection
 *
 *      https://en.wikipedia.org/wiki/Feature_extraction
 *      https://en.wikipedia.org/wiki/Digital_image_processing
 *
 *  hound transofmration
 *      https://en.wikipedia.org/wiki/Hough_transform
 *
 *  fourrier transformation
 *
 */

public class main {
    private static final String FILTER_FOLDER = "imageManipulation.all.";

    public static void main(String ... args){
        if(args.length == 0)
            interactive();
        else
            commandLine(args);
    }

    private static void commandLine(String ... args){
        if(args.length == 0) {
            System.out.println("Usage : main <image> filtre1,filtre2:arg0,filtre3:arg1-arg2");
            System.exit(-1);
        }
        if(args[0].equals("help")){
            System.out.println(Utils.helpAll());
            System.exit(0);
        }
        if(args[0].equals("list")){
            System.out.println("The filters availables are :");
            Utils.list();
            System.exit(0);
        }
        try {
            applyFilters(args[0],args[1].split(Utils.SEPARATEUR_FILTRES));
        } catch (IOException e) {
            System.err.println("The picture wasn't found !");
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            System.err.println("The filter is missing !");
            System.exit(-1);
        }
    }

    private static void interactive(){
        System.out.println("What's the path to the picture ?");
        Scanner s = new Scanner(System.in);
        String path = s.nextLine();
        System.out.println("What filters have to be applied ? ("+Utils.SEPARATEUR_FILTRES+" separated)");
        String filters [] = s.nextLine().split(Utils.SEPARATEUR_FILTRES);
        try {
            applyFilters(path, filters);
        } catch (IOException e) {
            System.err.println("The picture wasn't found !\n"+e.getMessage());
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            System.err.println("The filter is missing !\n"+e.getMessage());
            System.exit(-1);
        }
    }

    private static void applyFilters(String pathPhoto, String [] filters) throws IOException, ClassNotFoundException {
        Picture original = new Picture(ImageIO.read(new File((pathPhoto))));
        StringBuilder sb = new StringBuilder();
        for(String filterRaw : filters){
            String filter = filterRaw.split(Utils.SEPARATEUR_FILTRE_ARG)[0];
            String argsFilter = filter.length() == filterRaw.length() ? "" : filterRaw.split(Utils.SEPARATEUR_FILTRE_ARG)[1];
            Class cl = Class.forName(FILTER_FOLDER+filter);
            Constructor co;
            Transform t;
            try {
                if(argsFilter.length() == 0) {
                    co = cl.getConstructor();
                    t = (Transform) co.newInstance();
                }
                else {
                    co = cl.getConstructor(String.class);
                    t = (Transform) co.newInstance(argsFilter);

                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException("Malformed filter: "+e.getMessage());
            } catch(NoSuchMethodException | RuntimeException e){
                try {
                    Class cHelp = Class.forName(FILTER_FOLDER+filter);
                    Method mHelp = cHelp.getMethod("help");
                    String help = (String)mHelp.invoke(null);
                    throw new RuntimeException("Invalid use of arguments.\nThe filter "+filter+" must be used in the following way:\n"+help);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
                    throw new RuntimeException("An error occured while trying to display the error for the wrong use of th arguments.\n"+e.getMessage());
                }
            }
            System.out.println("Now applying: \""+t.getName()+"\"");
            sb.append((sb.length() == 0 ? "_" : "&")+t.getName());
            original = t.transform(original);
        }
        File output = new File("./"+pathPhoto.substring(pathPhoto.lastIndexOf("/")+1,pathPhoto.lastIndexOf("."))+sb.toString()+".png");
        ImageIO.write(original.getBufferedImage(), "png", output);
    }
}

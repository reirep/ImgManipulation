package imageManipulation;

import imageManipulation.exceptions.BrokenFilterException;
import imageManipulation.exceptions.FilterNotFoundException;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by pierre on 6/01/18
 *
 * TODO:
 *  cadres
 *  vignette effect
 * http://www.dfstudios.co.uk/articles/programming/image-programming-algorithms/
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
 *      x_canny edge detection
 *
 *      https://en.wikipedia.org/wiki/Feature_extraction
 *      https://en.wikipedia.org/wiki/Digital_image_processing
 *
 *  hound transofmration
 *      https://en.wikipedia.org/wiki/Hough_transform
 *
 *  fourrier transformation
 *
 *  if inspiration is needed
 *  https://dsp.stackexchange.com/questions/24436/looking-for-open-source-image-processing-library-that-is-equivalent-to-hips
 *  more inspiration
 *  http://www.imagemagick.org/script/index.php
 */

public class Filter {

    public static void main(String ... args){
        if(args.length == 0)
            interactive();
        else
            commandLine(args);
    }

    private static void commandLine(String ... args){
        if(args.length == 0) {
            System.out.println("Usage : Filter <image> filtre1,filtre2:arg0,filtre3:arg1-arg2");
            System.exit(-1);
        }

        if(args[0].equals("help")){
            System.out.println(Utils.helpAll());
            System.exit(0);
        }

        if(args[0].equals("list")){
            System.out.println("The filters availables are :");
            List<String> filters = Utils.list();
            for(String filter : filters)
                System.out.println(filter);
            System.exit(0);
        }

        String filters [] = args[1].split(Utils.SEPARATEUR_FILTRES);
        try {
            applyFilters(args[0], filters);
        } catch (BrokenFilterException e) {
            System.err.println("Broken filter: "+e.getMessage());
        } catch (IllegalArgumentException | IOException e) {
            System.err.println(e.getMessage());
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
        } catch (BrokenFilterException e) {
            System.err.println("Broken filter: "+e.getMessage());
        } catch (IllegalArgumentException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Apply a filter line ona picture
     * @param pathPicture the path to the picture, can be absolute or relative
     * @param filters the list of filter of apply, correctly formated
     * @throws BrokenFilterException The requested filter is broken, please create an issue on github with a snipper and steps to reproduce
     * @throws IllegalArgumentException The wrongs arguments have been given to the filter
     * @throws IOException This one is explicit enought
     */
    public static void applyFilters(String pathPicture, String [] filters) throws BrokenFilterException, IllegalArgumentException, IOException {
        File picFile = new File(pathPicture);
        String name = picFile.getName().substring(0,picFile.getName().lastIndexOf("."));

        Picture original = new Picture(name, ImageIO.read(picFile));

        for(String filterRaw : filters){
            original = applyFilter(original,filterRaw);
        }

        File output = new File(original.getName()+".png");
        ImageIO.write(original.getBufferedImage(), "png", output);
    }

    /**
     * Apply a single filter on the picture and append the name of the filter to the picture.
     * @param p the picture to modify
     * @param filterLine the filter to apply on the picture
     * @return the picture with the filter applied
     * @throws BrokenFilterException the filter has a problem, report to the dev
     * @throws FilterNotFoundException the filter is not present
     * @throws IllegalArgumentException the arguments are wrong or the whole filter line is wrong
     */
    public static Picture applyFilter(Picture p, String filterLine) throws BrokenFilterException, FilterNotFoundException, IllegalArgumentException {
        //TODO maybe add a sout or dbg
        try {
            String [] filterDetails = filterLine.split(Utils.SEPARATEUR_FILTRE_ARG);
            String filter = filterDetails[0];
            String args = filterDetails.length >= 2 ? filterDetails[1] : null;
            return applyFilter(p,filter, args);
        } catch(IndexOutOfBoundsException e){
            throw new IllegalArgumentException("The filter is missing !");
        }catch (ClassNotFoundException e) {
            throw new FilterNotFoundException("The filter requested is nowhere to be found.");
        } catch (NoSuchMethodException e) {
            //TODO: instanciate a static version of the filter here to retrive the help message and happend it to this message.
            throw new IllegalArgumentException("The arguments are not the required ones !");
        } catch (InvocationTargetException e) {
            throw new BrokenFilterException(e.getCause().getMessage());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BrokenFilterException(e.getMessage());
        }
    }


    /**
     * Takes a picture and apply a filter to it.
     * @param p the picture to whom the filter will be applied
     * @param filter the filter that will be applied to the picture
     * @param filterArgs the arguments needed for the filter
     * @return the picture modified with the filter
     * @throws ClassNotFoundException the filter doesn't exist
     * @throws NoSuchMethodException the required contrsutor doesn't exist. Probably mean that the filter doesn't require agument if some where given and the opposite.
     * @throws IllegalAccessException the filter is broken (problems of access).
     * @throws InvocationTargetException the filter is broken (needed to unwrap thet cause that created this exception).
     * @throws InstantiationException the given filter is abstract, an interface or hasn't the correct contructor.
     */
    private static Picture applyFilter(Picture p, String filter, String filterArgs) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<Transform> filterClass = (Class<Transform>)Class.forName(Utils.FILTER_PACKAGE+filter);
        Transform t;

        if(filterArgs == null) {
            Constructor<Transform> filterConstructor = filterClass.getConstructor();
            t  = filterConstructor.newInstance();
        }
        else {
            Constructor<Transform> filterConstructor = filterClass.getConstructor(String.class);
            t  = filterConstructor.newInstance(filterArgs);
        }
        p.appendToName(Utils.SEPARATEUR_NOM_FILTRES+t.getName());
        return t.transform(p);
    }

    public static List<String> list(){
        return Utils.list();
    }
}
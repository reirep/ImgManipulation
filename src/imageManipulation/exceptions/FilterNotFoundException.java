package imageManipulation.exceptions;

import java.io.FileNotFoundException;

/**
 * Created by the awesome pierre on 3/02/18
 */
public class FilterNotFoundException extends FileNotFoundException {
    public FilterNotFoundException(){
        super();
    }
    public FilterNotFoundException(String msg){
        super(msg);
    }
}

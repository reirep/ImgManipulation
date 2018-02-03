package imageManipulation.exceptions;

/**
 * Created by the awesome pierre on 3/02/18
 */
public class BrokenFilterException extends Exception {
    public BrokenFilterException(){
        super();
    }
    public BrokenFilterException(String msg){
        super(msg);
    }
}

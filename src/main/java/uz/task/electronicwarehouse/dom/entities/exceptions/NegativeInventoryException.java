package uz.task.electronicwarehouse.dom.entities.exceptions;

/**
 * Created by Boburmirzo on 06/01/18.
 */
public class NegativeInventoryException extends IllegalArgumentException {
    public NegativeInventoryException() {}
    public NegativeInventoryException(String s) {super(s);}
    public NegativeInventoryException(String s, Throwable cause) {super(s, cause);}
    public NegativeInventoryException(Throwable cause) {super(cause);}
}

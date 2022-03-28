package Exceptions;

public class BadDataTypeException extends Exception{
    public BadDataTypeException(String errortext){
        super(errortext);
    }
}

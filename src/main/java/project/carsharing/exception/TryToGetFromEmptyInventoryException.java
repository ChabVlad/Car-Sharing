package project.carsharing.exception;

public class TryToGetFromEmptyInventoryException extends RuntimeException {
    public TryToGetFromEmptyInventoryException(String message) {
        super(message);
    }
}

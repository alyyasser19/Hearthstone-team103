package exceptions;

public class noHeroSelectedException extends HearthstoneException {
    public noHeroSelectedException()
    {
        super("");
    }
    public noHeroSelectedException(String message)
    {
        super(message);
    }
}

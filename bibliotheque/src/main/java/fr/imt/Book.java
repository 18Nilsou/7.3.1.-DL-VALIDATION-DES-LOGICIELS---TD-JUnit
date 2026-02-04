package fr.imt;

public class Book implements IBook {
    @Override
    public ISubscriber getFirstInLine() {
        return null;
    }

    @Override
    public boolean addInLine(ISubscriber subscriber) {
        return false;
    }

    @Override
    public String getType() {
        return "";
    }
}

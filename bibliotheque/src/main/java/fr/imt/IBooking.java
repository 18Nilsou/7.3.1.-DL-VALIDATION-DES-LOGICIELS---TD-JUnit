package fr.imt;

public interface IBooking {
    IBook getBook();
    ISubscriber getSubscriber();
    java.util.Date getBeginDate();
}

package fr.imt;

import java.util.Date;

public interface IBooking {
    IBook getBook();
    ISubscriber getSubscriber();
    Date getBeginDate();
    BookingState getStatus();
    void setStatus(BookingState status);
}

package fr.imt;

public class Booking implements IBooking {
    private IBook book;
    private ISubscriber subscriber;
    private java.util.Date beginDate;
    private BookingState status;

    public Booking(IBook book, ISubscriber subscriber, java.util.Date beginDate) {
        this.book = book;
        this.subscriber = subscriber;
        this.beginDate = beginDate;
        this.status = BookingState.BOOKED;
    }

    @Override
    public IBook getBook() {
        return this.book;
    }

    @Override
    public ISubscriber getSubscriber() {
        return this.subscriber;
    }

    @Override
    public java.util.Date getBeginDate() {
        return this.beginDate;
    }

    @Override
    public BookingState getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(BookingState status) {
        this.status = status;
    }
}

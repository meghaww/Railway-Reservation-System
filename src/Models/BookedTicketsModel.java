package Models;

import java.sql.Time;

public class BookedTicketsModel {
    String booking_id;
    String PNR_booked;
    String first_name, last_name, username;
    Integer no_of_tickets;
    Time booking_time;

    public BookedTicketsModel(String booking_id, String PNR_booked, String first_name, String last_name, String username, Integer no_of_tickets, Time booking_time) {
        this.booking_id = booking_id;
        this.PNR_booked = PNR_booked;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.no_of_tickets = no_of_tickets;
        this.booking_time = booking_time;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public String getPNR_booked() {
        return PNR_booked;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUsername() {
        return username;
    }

    public Integer getNo_of_tickets() {
        return no_of_tickets;
    }

    public Time getBooking_time() {
        return booking_time;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public void setPNR_booked(String PNR_booked) {
        this.PNR_booked = PNR_booked;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNo_of_tickets(Integer no_of_tickets) {
        this.no_of_tickets = no_of_tickets;
    }

    public void setBooking_time(Time booking_time) {
        this.booking_time = booking_time;
    }
}


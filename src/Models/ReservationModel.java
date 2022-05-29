package Models;

import java.sql.Time;

public class ReservationModel {
    Integer Sno;
    String pnr_id;
    String source, destination;
    String train_name;
    Time departure_time;
    Integer seats_available;

    public ReservationModel(Integer sno, String pnr_id, String source, String destination, String train_name, Time departure_time, Integer seats_available) {
        Sno = sno;
        this.pnr_id = pnr_id;
        this.source = source;
        this.destination = destination;
        this.train_name = train_name;
        this.departure_time = departure_time;
        this.seats_available = seats_available;
    }

    public Integer getSno() {
        return Sno;
    }

    public String getPnr_id() {
        return pnr_id;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getTrain_name() {
        return train_name;
    }

    public Time getDeparture_time() {
        return departure_time;
    }

    public Integer getSeats_available() {
        return seats_available;
    }

    public void setSno(Integer sno) {
        Sno = sno;
    }

    public void setPnr_id(String pnr_id) {
        this.pnr_id = pnr_id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setTrain_name(String train_name) {
        this.train_name = train_name;
    }

    public void setDeparture_time(Time departure_time) {
        this.departure_time = departure_time;
    }

    public void setSeats_available(Integer seats_available) {
        this.seats_available = seats_available;
    }

}

package com.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String vehNumber;
    private int totalSeats;
    private String destination;
    private double price;
    private String contact;
    private String name;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    private List<Seat> seats;

    public void generateSeats() {
        seats = new java.util.ArrayList<>();

        for (int i = 1; i <= totalSeats; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber(i);
            seat.setAvailable(true);
            seat.setBus(this);

            seats.add(seat);
        }
    }

    // GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public String getVehNumber() {
        return vehNumber;
    }

    public void setVehNumber(String vehNumber) {
        this.vehNumber = vehNumber;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

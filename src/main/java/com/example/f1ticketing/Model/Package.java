package com.example.f1ticketing.Model;

import javax.persistence.*;

@Entity
@Table(name = "packages")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "isFoodIncluded")
    private Boolean isFoodIncluded;

    @Column(name = "price")
    private int price;

    @Column(name = "nbOfAvailableTickets")
    private int numberOfTickets;

    @Column(name = "standard")
    private Standard standard;

    @ManyToOne
    @JoinColumn(name = "race_iD")
    private Race race;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFoodIncluded() {
        return isFoodIncluded;
    }

    public void setFoodIncluded(Boolean foodIncluded) {
        isFoodIncluded = foodIncluded;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }
}

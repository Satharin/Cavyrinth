package com.example.rafal.gra;

public class ItemShield {

    int id, defence, bonus;
    String name, immunity;
    double weight;

    public ItemShield() {}

    public ItemShield(int id, String name, int defence, String immunity, int bonus, double weight) {

        this.id = id;
        this.name = name;
        this.defence = defence;
        this.immunity = immunity;
        this.bonus = bonus;
        this.weight = weight;

    }

    public void setId(int id) {

        this.id = id;

    }

    public void setName(String name) {

        this.name = name;

    }

    public void setDefence(int defence) {

        this.defence = defence;

    }

    public void setImmunity(String immunity) {

        this.immunity = immunity;

    }

    public void setBonus(int bonus) {

        this.bonus = bonus;

    }

    public void setWeight(double weight) {

        this.weight = weight;

    }

    public int getId() {return id;}

    public String getName() {return name;}

    public int getDefence() {return defence;}

    public String getImmunity() {return immunity;}

    public int getBonus() {return bonus;}

    public double getWeight() {
        return weight;
    }

}
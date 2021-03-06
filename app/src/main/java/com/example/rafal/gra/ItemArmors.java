package com.example.rafal.gra;

public class ItemArmors {

    int id, armor, value, bonus;
    String name, attribute, immunity;
    double weight;

    public ItemArmors() {}

    public ItemArmors(int id, String name, int armor, String attribute, int value, String immunity, int bonus, double weight) {

        this.id = id;
        this.name = name;
        this.armor = armor;
        this.attribute = attribute;
        this.value = value;
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

    public void setArmor(int armor) {

        this.armor = armor;

    }

    public void setAttribute(String attribute) {

        this.attribute = attribute;

    }

    public void setValue(int value) {

        this.value = value;

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

    public int getArmor() {return armor;}

    public String getAttribute() {return attribute;}

    public int getValue() {return value;}

    public String getImmunity() {return immunity;}

    public int getBonus() {return bonus;}

    public double getWeight() {
        return weight;
    }

}
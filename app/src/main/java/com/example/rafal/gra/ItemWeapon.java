package com.example.rafal.gra;

public class ItemWeapon {

    int id, attack, defence, bonus;
    String name, elemental_attack;
    double weight;

    public ItemWeapon() {}

    public ItemWeapon(int id, String name, int attack, int defence, String elemental_attack, int bonus, double weight) {

        this.id = id;
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.elemental_attack = elemental_attack;
        this.bonus = bonus;
        this.weight = weight;

    }

    public void setId(int id) {

        this.id = id;

    }

    public void setName(String name) {

        this.name = name;

    }

    public void setAttack(int attack) {

        this.attack = attack;

    }

    public void setDefence(int defence) {

        this.defence = defence;

    }

    public void setElemental(String elemental_attack) {

        this.elemental_attack = elemental_attack;

    }

    public void setBonus(int bonus) {

        this.bonus = bonus;

    }

    public void setWeight(double weight) {

        this.weight = weight;

    }

    public int getId() {return id;}

    public String getName() {return name;}

    public int getAttack() {return attack;}

    public int getDefence() {return defence;}

    public String getElemental() {return elemental_attack;}

    public int getBonus() {return bonus;}

    public double getWeight() {
        return weight;
    }

}
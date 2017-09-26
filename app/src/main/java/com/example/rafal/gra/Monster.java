package com.example.rafal.gra;

public class Monster {

    private int id, hp, attack, defence, gold_max, low_item_chance, mid_item_chance, high_item_chance;
    private String name, immunity, sensivity;

    public Monster(int id, String name, int hp, int attack, int defence, String immunity, String sensivity,
                   int gold_max, int low_item_chance, int mid_item_chance, int high_item_chance) {

        this.id = id;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defence = defence;
        this.immunity = immunity;
        this.sensivity = sensivity;
        this.gold_max = gold_max;
        this.low_item_chance = low_item_chance;
        this.mid_item_chance = mid_item_chance;
        this.high_item_chance = high_item_chance;

    }

    public void setId(int id) {

        this.id = id;

    }

    public void setName(String name) {

        this.name = name;

    }

    public void setHp(int hp) {

        this.hp = hp;
    }

    public void setAttack(int attack) {

        this.attack = attack;

    }

    public void setDefence(int defence) {

        this.defence = defence;

    }

    public void setImmunity(String immunity) {

        this.immunity = immunity;

    }

    public void setSensivity(String sensivity) {

        this.sensivity = sensivity;

    }

    public void setGold_max(int gold_max) {

        this.gold_max = gold_max;

    }

    public void setLow_item_chance(int low_item_chance) {

        this.low_item_chance = low_item_chance;

    }

    public void setMid_item_chance(int mid_item_chance) {

        this.mid_item_chance = mid_item_chance;

    }

    public void setHigh_item_chance(int high_item_chance) {

        this.high_item_chance = high_item_chance;

    }

    public int getId() {return id;}

    public String getName() {return name;}

    public int getHp() {return hp;}

    public int getAttack() {return attack;}

    public int getDefence() {return defence;}

    public String getImmunity() {return immunity;}

    public String getSensivity() {return sensivity;}

    public int getGold_max() {return gold_max;}

    public int getLow_item_chance() {return low_item_chance;}

    public int getMid_item_chance() {
        return mid_item_chance;
    }

    public int getHigh_item_chance() {
        return high_item_chance;
    }

}
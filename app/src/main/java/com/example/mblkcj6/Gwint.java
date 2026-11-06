package com.example.mblkcj6;
import androidx.room.Entity; import androidx.room.PrimaryKey;


@Entity(tableName = "gwint_cards") public class Gwint {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int power;
    private boolean isHero;
    private String unitType;

    public Gwint(int id, int power, boolean isHero, String unitType) {
        this.id = id;
        this.power = power;
        this.isHero = isHero;
        this.unitType = unitType;
    }

    @Override
    public String toString() {
        String heroText = isHero ? " [BOHATER]" : "";
        return name + " (Sila: " + power + ")" + heroText + "\nTyp: " + unitType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isHero() {
        return isHero;
    }

    public void setHero(boolean hero) {
        isHero = hero;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
}

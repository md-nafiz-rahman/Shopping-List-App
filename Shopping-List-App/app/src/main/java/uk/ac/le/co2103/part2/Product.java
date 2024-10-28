package uk.ac.le.co2103.part2;

import java.io.Serializable;

public class Product implements Serializable {


    //Name, quantity and unit measurement for product

    private String name;
    private int quantity;
    private Unit unit;


    // Product constructor

    public Product(String name, int quantity, Unit unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }



    //Getter and setter method for product name, quantity and unit.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }


    // Enum method for unit measurements
    public enum Unit {
        UNIT("Unit"),
        KG("Kg"),
        LITRE("Litre");

        private final String displayUnit;

        Unit(String displayUnit) {
            this.displayUnit = displayUnit;
        }

        public String getDisplayUnit() {
            return displayUnit;
        }
    }
}

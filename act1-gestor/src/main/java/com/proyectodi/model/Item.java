package com.proyectodi.model;

import javafx.beans.property.*;

// clase modelo para las tareas, usamos Property de javafx para que la tabla se actualice sola
public class Item {
    // propiedades observables (lo vimos en el tema de componentes javafx)
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();

    public Item(int id, String name, String category, double price, String status) {
        setId(id);
        setName(name);
        setCategory(category);
        setPrice(price);
        setStatus(status);
    }

    // getters y setters tipicos de DAM con sus property()
    public IntegerProperty idProperty() { return id; }
    public int getId() { return id.get(); }
    public void setId(int value) { id.set(value); }

    public StringProperty nameProperty() { return name; }
    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }

    public StringProperty categoryProperty() { return category; }
    public String getCategory() { return category.get(); }
    public void setCategory(String value) { category.set(value); }

    public DoubleProperty priceProperty() { return price; }
    public double getPrice() { return price.get(); }
    public void setPrice(double value) { price.set(value); }

    public StringProperty statusProperty() { return status; }
    public String getStatus() { return status.get(); }
    public void setStatus(String value) { status.set(value); }
}

package org.miage.Entity;

public class UTxO {
    public String hashTransaction;
    public int index;
    public double value;
    public String address;

    public UTxO(String hashTransaction, int index, double value, String address) {
        this.hashTransaction = hashTransaction;
        this.index = index;
        this.value = value;
        this.address = address;
    }

    public String getHash() {
        return hashTransaction;
    }

    public int getIndex() {
        return index;
    }

    public double getValue() {
        return value;
    }

    public String getAddress() {
        return address;
    }

    public void setHash(String hashTransaction) {
        this.hashTransaction = hashTransaction;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

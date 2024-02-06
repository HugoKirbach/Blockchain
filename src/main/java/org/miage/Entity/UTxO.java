package org.miage.Entity;

import java.util.UUID;

public class UTxO {
    public UUID hashTransaction;
    public double value;
    public String address;

    public UTxO(UUID hashTransaction, double value, String address) {
        this.hashTransaction = hashTransaction;
        this.value = value;
        this.address = address;
    }

    public UUID getHash() {
        return hashTransaction;
    }


    public double getValue() {
        return value;
    }

    public String getAddress() {
        return address;
    }

    public void setHash(UUID hashTransaction) {
        this.hashTransaction = hashTransaction;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "\nUTxO{" +
                "hashTransaction='" + hashTransaction + '\'' +
                ", value=" + value +
                ", address='" + address + '\'' +
                '}';
    }
}

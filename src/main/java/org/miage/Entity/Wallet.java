package org.miage.Entity;

import java.util.List;

public class Wallet {
    public String publicKey;
    private String privateKey;
    private double balance;
    private List<UTxO> utxos;

    public Wallet(String publicKey, String privateKey, double balance) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.balance = balance;
    }
    
}

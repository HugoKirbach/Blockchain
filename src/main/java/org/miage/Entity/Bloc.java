package org.miage.Entity;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.LinkedList;
import java.util.List;

public class Bloc {

    private String hash;
    private String previousHash;
    private long timeStamp;
    private int nonce;
    private LinkedList<Transaction> transactions = new LinkedList<Transaction>();

    public Bloc(String previousHash, LinkedList<Transaction> transactions) {
        this.hash = calculateHash();
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.timeStamp = System.currentTimeMillis();

        //TODO : calculer le Hash de ce bloc a chaque modif
    }

    //il faut que hash du bloc soit < a la difficultée

    public String calculateHash(){
        String randomString = RandomStringUtils.randomAlphanumeric(20);
        String sha256hex = DigestUtils.sha256Hex(randomString);

        return sha256hex;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(LinkedList<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "\n Bloc{" +
                "hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", timeStamp=" + timeStamp +
                ", nonce=" + nonce +
                ", transactions=" + transactions +
                '}';
    }
}

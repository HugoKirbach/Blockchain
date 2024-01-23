package org.miage.Entity;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class Bloc {

    private int difficulty;
    private String hash;
    private String previousHash;
    private long timeStamp;
    private int nonce;
    private List<Transaction> transactions;

    public Bloc(int difficulty, String previousHash, List<Transaction> transactions) {
        this.hash = calculateHash();
        this.difficulty = difficulty;
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.timeStamp = System.currentTimeMillis();

        //TODO : calculer le Hash de ce bloc a chaque modif
    }

    public String calculateHash(){
        String randomString = RandomStringUtils.randomAlphanumeric(20);
        String sha256hex = DigestUtils.sha256Hex(randomString);

        return sha256hex;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "\n Bloc{" +
                "difficulty=" + difficulty +
                ", hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", timeStamp=" + timeStamp +
                ", nonce=" + nonce +
                ", transactions=" + transactions +
                '}';
    }
}

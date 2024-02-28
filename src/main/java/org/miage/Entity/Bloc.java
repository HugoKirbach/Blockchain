package org.miage.Entity;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.miage.Constante.Constante;

import java.util.LinkedList;
import java.util.List;

public class Bloc {

    private int idBlock;
    private String hash;
    private String previousHash;
    private long timeStamp;
    private int nonce;
    private LinkedList<Transaction> transactions = new LinkedList<Transaction>();

    public Bloc(String previousHash, LinkedList<Transaction> transactions) {
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.timeStamp = System.currentTimeMillis();
        calculateHash();
        //TODO : calculer le Hash de ce bloc a chaque modif
    }

    public Bloc(String previousHash) {
        this.previousHash = previousHash;
        this.transactions = new LinkedList<Transaction>();
        this.timeStamp = System.currentTimeMillis();
        
        calculateHash();
        //TODO : calculer le Hash de ce bloc a chaque modif
    }

    //il faut que debut hash du bloc commence par difficultée * des 0
    public String mineBloc(int difficulty){
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring( 0, difficulty).equals(target)){
            nonce ++;
            calculateHash();
        }
        System.out.println("Bloc miné: " + hash + " nonce: " + nonce);
        return hash; //retourne le nouveau hash pour modif dans le last bloc
        //TODO : vérifier si correct
    }

    public void calculateHash(){
        //Hash = sha256(PreviousHash + TimeStamp + Nonce + transactions)
        synchronized (this) {
            String data = previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + transactions.toString();
            hash = DigestUtils.sha256Hex(data);
        }
    }

    public String getHash() {
        return hash;
    }

    public void setHash() { //comme calculateHash
        this.hash = DigestUtils.sha256Hex(String.valueOf(this));
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
        calculateHash();
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
        calculateHash();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(LinkedList<Transaction> transactions) {
        this.transactions = transactions;
        calculateHash();
    }

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
        calculateHash();
    }

    @Override
    public String toString() {
        return "\nBloc "+idBlock+" hash'" + hash.substring(0, Constante.DIFFICULTY+6) + '\'' + //substring pour afficher que les premiers caractères en fonction de la difficulté (pour visibilité)
                ", previousHash'" + previousHash.substring(0, Constante.DIFFICULTY+6) + '\'' +
                ", nb transactions:" + transactions.size() + " nonce=" + nonce + "\n";
    }


    public int getIdBlock() {
        return idBlock;
    }

    public void setIdBlock(int idBlock) {
        this.idBlock = idBlock;
    }
}

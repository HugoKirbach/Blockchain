package org.miage.Entity;

import org.miage.Constante.Constante;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Blockchain {

    private static Blockchain INSTANCE;

    List<Wallet> listWallet = new ArrayList<>();
    LinkedList<Bloc> blockchain;

    protected int difficulty = Constante.DIFFICULTY;

    public Blockchain() {
        blockchain = new LinkedList<>();
        blockchain.add(firstBloc());
    }

    public static Blockchain getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new Blockchain();
        }
        return INSTANCE;
    }

    private Bloc firstBloc() {
        return new Bloc("1", new LinkedList<>());
    }

    public void addBlock(Bloc newBlock) {
        blockchain.add(newBlock);
    }

    public LinkedList<Bloc> getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(LinkedList<Bloc> blockchain) {
        this.blockchain = blockchain;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public static Blockchain getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(Blockchain INSTANCE) {
        Blockchain.INSTANCE = INSTANCE;
    }

    public List<Wallet> getListWallet() {
        return listWallet;
    }

    public void setListWallet(List<Wallet> listWallet) {
        this.listWallet = listWallet;
    }

    @Override
    public String toString() {
        return "Blockchain{" +
                "blockchain=" + blockchain +
                '}';
    }
}
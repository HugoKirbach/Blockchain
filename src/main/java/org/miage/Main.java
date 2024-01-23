package org.miage;

import org.miage.Entity.Bloc;
import org.miage.Entity.Wallet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Wallet> listWallets = new ArrayList<>();

        //TODO: create transactions
        /*
        * wallet1 --> wallet2
        *
        * */
        listWallets = initWallets(4);

        List<Bloc> blockchain = new ArrayList<>();
        blockchain.add(new Bloc(0, "1", null));
        blockchain.add(new Bloc(0, blockchain.get(blockchain.size()-1).getHash(), null));
        blockchain.add(new Bloc(0, blockchain.get(blockchain.size()-1).getHash(), null));
        blockchain.add(new Bloc(0, blockchain.get(blockchain.size()-1).getHash(), null));

        System.out.println(blockchain.toString());
        System.out.println("Hello world!");
    }

    public static List<Wallet> initWallets(int nb){
        List<Wallet> listWallets = new ArrayList<>();

        for (int i = 0; i<nb; i++){
            double randomAmount = Math.random() * 100;

            Wallet wallet = new Wallet(i+"_PUB", i+"_PRIV", randomAmount);
            listWallets.add(wallet);
        }
        return listWallets;
    }
}
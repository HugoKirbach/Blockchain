package org.miage;

import org.miage.DAO.BlocDao;
import org.miage.DAO.TransactionDao;
import org.miage.Entity.Bloc;
import org.miage.Entity.Blockchain;
import org.miage.Entity.Wallet;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = Blockchain.getInstance();
        BlocDao blocDao = new BlocDao();
        TransactionDao transactionDao = new TransactionDao();

        List<Wallet> listWallets = new ArrayList<>();


        listWallets = initWallets(4);
        blockchain.setListWallet(listWallets);

////
////        TransactionDao transactionDao = new TransactionDao();
////        transactionDao.createTransaction(randomWallets.get(0), randomWallets.get(1), 10);
////
////        //TODO: create transactions
////        /*
////         * wallet1 --> wallet2
////         *
////         * */
////
////
//        Blockchain blockchain = Blockchain.getInstance();
//
//        BlocDao blocDao = new BlocDao();
//        TransactionDao transactionDao = new TransactionDao();
//        Thread thread = new Thread();
//        while (true){
//            try {
//                thread.sleep(1000);
//                transactionDao.createTransaction(twoRandomWallets(blockchain.getListWallet()).get(0), twoRandomWallets(blockchain.getListWallet()).get(1), 10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            try {
//                thread.sleep(5000);
//                blocDao.createBloc();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(blockchain.toString());
//        }



        Thread transactionThread = new Thread(() -> {
            while (true) {
                try {
                    List<Wallet> randomWallets = twoRandomWallets(blockchain.getListWallet());

//                    System.out.println("anciens UTxOs receiver : "+randomWallets.get(1).getUtxos());
//                    System.out.println("ancien solde sender : "+randomWallets.get(0).getTotalAmout());
//                    System.out.println("ancien solde receiver : "+randomWallets.get(1).getTotalAmout());
                    //create a random amount
                    double randomAmount = Math.random() * 25;
                    //System.out.println("montant de la transaction : "+randomAmount);
                    transactionDao.createTransaction(randomWallets.get(0), randomWallets.get(1), randomAmount);
//                    System.out.println("nouveau solde sender : "+randomWallets.get(0).getTotalAmout());
//                    System.out.println("nouveau solde receiver : "+randomWallets.get(1).getTotalAmout());
//                    System.out.println("-----");
//                    System.out.println("UTxOs receiver : "+randomWallets.get(1).getUtxos());
                    System.out.println(blockchain.getBlockchain().getLast().toString());
                    System.out.println("-----");
                    Thread.sleep(2000);
                    //System.out.println("Objet string: "+String.valueOf(blockchain.getBlockchain().getLast()));
                    //TODO : replace 10 par un random
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println(blockchain.getBlockchain().getLast().getTransactions().toString());
            }
        });

        Thread blockThread = new Thread(() -> {
            while (true) {
                try {
                    blocDao.createBloc();
                    System.out.println("\n----- NOUVEAU BLOC CREE -----");
                    System.out.println("nb blocs : "+blockchain.getBlockchain().size());
                    lancerMinage(blockchain);
                    //TODO: revoir placement appel fonction, nouveau thread ? pour toutes les 1min lancer cette fonction sans attendre fin exec précédente execution

                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        blockThread.start();
        transactionThread.start();

    }



    public static List<Wallet> initWallets(int nb){
        List<Wallet> listWallets = new ArrayList<>();
        Blockchain blockchain = Blockchain.getInstance();
        for (int i = 0; i<nb; i++){
            double randomAmount = Math.random() * 100;

            Wallet wallet = new Wallet(randomAmount);
            listWallets.add(wallet);
            blockchain.getListWallet().add(wallet);
        }
        return listWallets;
    }

    public static List<Wallet> twoRandomWallets(List<Wallet> listWallets){
        int random1 = (int) (Math.random() * listWallets.size());
        int random2 = (int) (Math.random() * listWallets.size());
        while(random1 == random2){
            random2 = (int) (Math.random() * listWallets.size());
        }
        return Arrays.asList(listWallets.get(random1), listWallets.get(random2));
    }

    public static void lancerMinage(Blockchain blockchain){
        int blocChainSize = blockchain.getBlockchain().size();
        Bloc dernierBlocFinis = blockchain.getBlockchain().get(blocChainSize-2);
        System.out.println("DEBUT MINAGE BLOCK "+dernierBlocFinis.getIdBlock()+" hash: "+dernierBlocFinis.getHash().substring(0,8));
        Instant start = Instant.now();
        dernierBlocFinis.mineBloc(blockchain.getDifficulty());
        Instant end = Instant.now();
        System.out.println("\nFIN MINAGE BLOCK "+dernierBlocFinis.getIdBlock()+" new hash: "+dernierBlocFinis.getHash().substring(0,blockchain.getDifficulty()+10)+"...\n");
        System.out.println("Temps de minage: "+(end.toEpochMilli()-start.toEpochMilli())+" ms ("+(end.toEpochMilli()-start.toEpochMilli())/1000+"sec) avec difficulté "+blockchain.getDifficulty());
        blockchain.getBlockchain()
                .get(dernierBlocFinis.getIdBlock()+1)//on récupère le bloc suivant celui miné
                .setPreviousHash(dernierBlocFinis.getHash()); //update le previous hash du bloc récup

        System.out.println("Blockchain "+blockchain.getBlockchain().toString());
        System.out.println("\n-----\n");
    }
}
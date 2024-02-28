package org.miage;

import org.miage.Constante.Constante;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = Blockchain.getInstance();
        BlocDao blocDao = new BlocDao();
        TransactionDao transactionDao = new TransactionDao();

        List<Wallet> listWallets = new ArrayList<>();

        int dureeNvltransac = Constante.DUREE_ENTRE_TRANSAC * 1000;
        int dureeNvBloc = Constante.DUREE_ENTRE_BLOC * 1000;

        listWallets = initWallets(Constante.NB_WALLET_INIT);
        blockchain.setListWallet(listWallets);


        /*
         * Partie Création de transactions
         * */

        Thread transactionThread = new Thread(() -> {
            while (true) {
                try {
                    List<Wallet> randomWallets = twoRandomWallets(blockchain.getListWallet());
//                    System.out.println("anciens UTxOs receiver : "+randomWallets.get(1).getUtxos());
//                    System.out.println("ancien solde sender : "+randomWallets.get(0).getTotalAmout());
//                    System.out.println("ancien solde receiver : "+randomWallets.get(1).getTotalAmout());
                    //create a random amount
                    double randomAmount = Math.random() * Constante.MAX_MONTANT_TRANSAC;
                    //System.out.println("montant de la transaction : "+randomAmount);
                    transactionDao.createTransaction(randomWallets.get(0), randomWallets.get(1), randomAmount);
//                    System.out.println("nouveau solde sender : "+randomWallets.get(0).getTotalAmout());
//                    System.out.println("nouveau solde receiver : "+randomWallets.get(1).getTotalAmout());
//                    System.out.println("-----");
//                    System.out.println("UTxOs receiver : "+randomWallets.get(1).getUtxos());
//                    System.out.println(blockchain.getBlockchain().getLast().toString());
                    System.out.println("-----");
                    Thread.sleep(dureeNvltransac);
                    //System.out.println("Objet string: "+String.valueOf(blockchain.getBlockchain().getLast()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println(blockchain.getBlockchain().getLast().getTransactions().toString());
            }
        });

        /*
         * Partie Création de nouveaux blocs
         * */

        Thread blockThread = new Thread(() -> {
            while (true) {
                try {
                    blocDao.createBloc();
                    System.out.println("\n----- NOUVEAU BLOC CREE -----");
                    System.out.println("nb blocs : "+blockchain.getBlockchain().size());

                    Thread.sleep(dureeNvBloc);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        /*
         * Partie minage
         * */
        int initialDelay = 1000; //on attend 1 sec avant de lancer le premier minage pour que les blocs soient créés etc...
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Lancement minage");
                lancerMinage(blockchain);
            }
        }, initialDelay , dureeNvBloc, TimeUnit.MILLISECONDS); //thread executé toutes les "dureeNvBloc" millisecondes

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

        Bloc blocAMiner = blockchain.getBlockchain().get(blockchain.getLastBlocIdMined()+1);

        System.out.println("\nDEBUT MINAGE BLOCK "+blocAMiner.getIdBlock()+" hash: "+blocAMiner.getHash().substring(0,8)+'\n');
        Instant start = Instant.now();
        blocAMiner.mineBloc(blockchain.getDifficulty());
        Instant end = Instant.now();
        System.out.println("\nFIN MINAGE BLOCK "+blocAMiner.getIdBlock()+" new hash: "+blocAMiner.getHash().substring(0,blockchain.getDifficulty()+10)+"...\n");
        System.out.println("Temps de minage: "+(end.toEpochMilli()-start.toEpochMilli())+" ms ("+(end.toEpochMilli()-start.toEpochMilli())/1000+"sec) avec difficulté "+blockchain.getDifficulty());
        blockchain.setLastBlocIdMinedPlus1();

        blockchain.getBlockchain()
                .get(blocAMiner.getIdBlock()+1)//on récupère le bloc suivant celui miné
                .setPreviousHash(blocAMiner.getHash()); //update le previous hash du bloc récup

        System.out.println("Blockchain: "+blockchain);
        System.out.println("\n-----\n");
    }
}
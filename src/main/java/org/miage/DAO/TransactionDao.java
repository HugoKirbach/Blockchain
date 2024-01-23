package org.miage.DAO;

import org.miage.Entity.Transaction;
import org.miage.Entity.Wallet;

public class TransactionDao {

    public void createTransaction(Wallet sender, Wallet receiver, double amount){
        //Faire un thread qui creer une nouvelle transaction toutes les secondes
        Thread thread = new Thread();
        try {
            new Transaction(sender, receiver, amount);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

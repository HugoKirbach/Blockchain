package org.miage.DAO;

import org.miage.Entity.Bloc;
import org.miage.Entity.Blockchain;
import org.miage.Entity.Transaction;
import org.miage.Entity.Wallet;

public class TransactionDao {

    public void createTransaction(Wallet sender, Wallet receiver, double amount){
        Blockchain blockchain = Blockchain.getInstance();
        //Faire un thread qui creer une nouvelle transaction toutes les secondes
        if (sender.getTotalAmout() >= amount){
//                /**
//                 * Recup cumul UTxO envoyé avec fonction getUTxOsUntilAmout du sender
//                 * add au walet receiver cette liste
//                 *
//                 * */
                Transaction newTransaction = new Transaction(sender, receiver, amount);
                Bloc lastBloc = blockchain.getBlockchain().getLast();
                lastBloc.getTransactions().add(newTransaction);
        } else System.out.println("Montant insuffisant, nécessite "+amount+" possede "+sender.getTotalAmout());
    }

}

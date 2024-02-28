package org.miage.DAO;

import org.apache.commons.codec.digest.DigestUtils;
import org.miage.Entity.*;

public class TransactionDao {

    public void createTransaction(Wallet sender, Wallet receiver, double amount ) {
        Blockchain blockchain = Blockchain.getInstance();
        //Faire un thread qui creer une nouvelle transaction toutes les secondes
        if (sender.getTotalAmout() >= amount) {
//                /**
//                 * Recup cumul UTxO envoyé avec fonction getUTxOsUntilAmout du sender
//                 * add au walet receiver cette liste
//                 *
//                 * */
            Transaction newTransaction = new Transaction(sender, receiver, amount);
//            newTransaction.signature = new Signature();
            Signature signature = new Signature();
            signature.sign(String.valueOf(newTransaction), sender.getPrivateKey());
            //message+privatekey hashé

            //TODO: verify transaction --> Utiliser objet Signature, Objet Transaction
            // verifySignature (Transaction hashé, sender.privateKey, Signature.signature)
            // if true ajouter, sinon sysout refusé



            String hashPrivateKey = DigestUtils.sha256Hex(sender.getPrivateKey());
            System.out.println("Transaction de "+amount+" de "+sender.getPublicKey().substring(0,8)
                    +" vers "+receiver.getPublicKey().substring(0,8));
                    //+" signée par "+hashPrivateKey.substring(0,8)+" : "+signature.getSignature().substring(0,8));

            if (new Signature().verifySignature(String.valueOf(newTransaction), sender.getPrivateKey(), signature.getSignature())) {
                Bloc lastBloc = blockchain.getBlockchain().getLast();
                lastBloc.addTransaction(newTransaction);
                System.out.println("Transaction validée inscrite dans le bloc "+lastBloc.getIdBlock()+"\n");
            } else {
                System.out.println("Transaction REFUSEE (signature invalide)");
            }


        } else System.out.println("Montant insuffisant, nécessite " + amount + " possede " + sender.getTotalAmout());
    }

    public boolean verifySignature(String message, String privateKey, String signature){
        String hashPrivateKey = DigestUtils.sha256Hex(message+privateKey);
        return hashPrivateKey.equals(signature);
    }

}

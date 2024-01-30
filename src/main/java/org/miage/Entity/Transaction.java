package org.miage.Entity;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    protected String hash;
    protected String previousHash;
    protected Wallet sender;
    protected Wallet receiver;
    protected List<UTxO> senderUTxOs; // input
    protected List<UTxO> receiverUTxOs; // output
    protected double amount;

    public Transaction(Wallet sender, Wallet receiver, double amount) {
        this.sender = sender;
        List<UTxO> listAEnvoyer = new ArrayList<>();
        listAEnvoyer = this.sender.getUTxOsUntilAmout(amount); //list UTxO d'input
        this.receiver = receiver;
        //TODO : creer fonction qui prends la liste des UTxO a envoyer, et le amount en paramètre,
        //          Retourne un liste de 2 UTxO : Le premier pour le receiver (=amount), le second pour le sender(=cumul-amount)

        //for (UTxO utxo : listAEnvoyer) this.receiver.getUtxos().add(utxo);
        List<UTxO> listUTxORetourTransaction = splitUTxOs(listAEnvoyer, amount, sender, receiver);
        sender.getUtxos().add(listUTxORetourTransaction.get(0));
        receiver.getUtxos().add(listUTxORetourTransaction.get(1));

        this.amount = amount;
    }

    public List<UTxO> splitUTxOs(List<UTxO> UTxOsInput, double amount, Wallet sender, Wallet receiver){
        double cumul = 0;
        for (UTxO utxo : UTxOsInput){
            cumul += utxo.getValue();
        }
        double retourMonnaie = cumul - amount;
        UTxO uTxOReceiver = new UTxO(this.hash, amount, receiver.publicKey);
        UTxO uTxOMonnaieSender = new UTxO(this.hash, retourMonnaie, sender.publicKey);
        List<UTxO> listUTxOSenderReceiver = new ArrayList<>();
        listUTxOSenderReceiver.add(uTxOMonnaieSender);
        listUTxOSenderReceiver.add(uTxOReceiver);
        //get(0) ->monnaie pour le sender ; get(1) -> UTxO pour le receiver
        return listUTxOSenderReceiver;
    }

    public Wallet getSender() {
        return sender;
    }

    public void setSender(Wallet sender) {
        this.sender = sender;
    }

    public Wallet getReceiver() {
        return receiver;
    }

    public void setReceiver(Wallet receiver) {
        this.receiver = receiver;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<UTxO> getSenderUTxOs() {
        return senderUTxOs;
    }

    public void setSenderUTxOs(List<UTxO> senderUTxOs) {
        this.senderUTxOs = senderUTxOs;
    }

    public List<UTxO> getReceiverUTxOs() {
        return receiverUTxOs;
    }

    public void setReceiverUTxOs(List<UTxO> receiverUTxOs) {
        this.receiverUTxOs = receiverUTxOs;
    }

    @Override
    public String toString() {
        return "\nTransaction{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }
}

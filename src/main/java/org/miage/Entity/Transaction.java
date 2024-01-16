package org.miage.Entity;

import java.util.List;

public class Transaction {
    private String hash;
    private Wallet sender;
    private Wallet receiver;
    private List<UTxO> senderUTxOs; // input
    private List<UTxO> receiverUTxOs; // output
    private double amount;

    public Transaction(Wallet sender, Wallet receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
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
}

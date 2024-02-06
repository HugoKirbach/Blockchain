package org.miage.Entity;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wallet {
    public String publicKey;
    private String privateKey;
    protected List<UTxO> utxos = new ArrayList<>();

    public Wallet(double balance) {
        this.privateKey = RandomStringUtils.randomAlphanumeric(20);
        this.publicKey = generatePublicKey(this.privateKey);
        utxos.add(new UTxO(UUID.randomUUID(), balance, this.publicKey));
        Blockchain.getInstance().getListWallet().add(this);
    }

    public String generatePublicKey(String privateKey){
        return DigestUtils.sha256Hex(privateKey);
    }



    public double getTotalAmout(){
        double cumul = 0;
        for(UTxO utxo : this.utxos){
            cumul += utxo.getValue();
        }
        return cumul;
    }

    public List<UTxO> getUTxOsUntilAmout(double amount){
        double cumul = 0;
        List<UTxO> aRetourner = new ArrayList<>();
        for(UTxO unUtxo : this.utxos){
            cumul += unUtxo.getValue();
            aRetourner.add(unUtxo);
            this.utxos.remove(unUtxo);
            if (cumul >= amount) return aRetourner;
        }
        return null;
    }

    public List<UTxO> getUtxos() {
        return utxos;
    }

    public void setUtxos(List<UTxO> utxos) {
        this.utxos = utxos;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "\nWallet{" +
                "utxos=" + utxos +
                '}';
    }
}

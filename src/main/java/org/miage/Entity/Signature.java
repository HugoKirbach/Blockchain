package org.miage.Entity;

import org.apache.commons.codec.digest.DigestUtils;

public class Signature {
    private String signature;

    public Signature() {
        this.signature = null;
    }

    public String getSignature() {
        return signature;
    }

    public void sign(String message, String privateKey){
        //TODO : return hash du message et private key combiné
        this.signature = DigestUtils.sha256Hex(message+privateKey);
    }

    public boolean verifySignature(String message, String privateKey, String signature){
        if (DigestUtils.sha256Hex(message+privateKey).equals(signature)){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return signature.substring(0,8);
    }
}

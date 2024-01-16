package org.miage.Entity;

public class UTxO {
    
        public String hash;
        public int index;
        public double value;
        public String address;
    
        public UTxO(String hash, int index, double value, String address) {
            this.hash = hash;
            this.index = index;
            this.value = value;
            this.address = address;
        }
    
        public String getHash() {
            return hash;
        }
    
        public int getIndex() {
            return index;
        }
    
        public double getValue() {
            return value;
        }
    
        public String getAddress() {
            return address;
        }
    
        public void setHash(String hash) {
            this.hash = hash;
        }
    
        public void setIndex(int index) {
            this.index = index;
        }
    
        public void setValue(double value) {
            this.value = value;
        }
    
        public void setAddress(String address) {
            this.address = address;
        }
    
}

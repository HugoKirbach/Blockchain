package org.miage.DAO;

import org.miage.Entity.Bloc;
import org.miage.Entity.Blockchain;

import java.util.LinkedList;

public class BlocDao {

    public void createBloc() {
        Blockchain blockchain = Blockchain.getInstance();
//        Thread thread = new Thread();
//        try {
//            thread.sleep(5000);
            Bloc bloc = new Bloc(blockchain.getBlockchain().getLast().getHash());
            bloc.setIdBlock(blockchain.getBlockchain().size());
            blockchain.addBlock(bloc);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}

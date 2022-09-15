package org.jpos.testbed.txnmgr;

import java.io.Serializable;

import org.jpos.transaction.TxnSupport;

public class CheckFields extends TxnSupport{

    @Override
    public int prepare(long id, Serializable o) {
        // TODO Auto-generated method stub
        return super.prepare(id, o);
    }
    
}

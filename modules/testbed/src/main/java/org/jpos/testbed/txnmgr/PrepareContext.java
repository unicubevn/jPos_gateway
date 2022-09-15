package org.jpos.testbed.txnmgr;

import java.io.Serializable;
import java.util.Date;

import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;
import org.jpos.transaction.TransactionManager;
import org.jpos.transaction.TxnSupport;

public class PrepareContext extends TxnSupport {
    TransactionManager txnmgr;
    @Override
    public int prepare (long id, Serializable o) {
        System.out.println("PrepareContext .... is running.");
        Context ctx = (Context) o;
        ctx.getProfiler();
        if (ctx.get (ContextConstants.TIMESTAMP.toString()) == null)
            ctx.put (ContextConstants.TIMESTAMP.toString(), new Date());
        String name = ctx.getString ("TXNMGR");
        name = name == null ?
        txnmgr.getName() : ", " + txnmgr.getName(); ctx.put ("TXNMGR", name);
        return PREPARED | NO_JOIN;
    }
    public void setTransactionManager (TransactionManager txnmgr) {
        this.txnmgr = txnmgr;
    }
    @Override
    public void commit (long id, Serializable o) { }
    
    @Override
    public void abort (long id, Serializable o)  { }
}

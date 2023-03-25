package org.atom.gateway.txnmgr;

import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.ee.DB;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;
import org.jpos.transaction.TxnSupport;
import java.io.Serializable;
import java.util.UUID;

import static org.jpos.transaction.TxnConstants.DB;
import static org.jpos.transaction.TxnConstants.TX;


public class Open extends TxnSupport {
    int timeout = 0;
    public int prepare (long id, Serializable o) {
        int rc = ABORTED;
        Context ctx = (Context) o;
        ISOMsg msg = (ISOMsg) ctx.get(ContextConstants.REQUEST.toString());
        msg.dump(System.out, "---");
        System.out.print("Open starting");
        System.out.print("============");
        System.out.print(ctx);
        try {
            DB db = getDB (ctx);
            System.out.print("============" + db.toString());
            db.open ();
            ctx.put (TX, db.beginTransaction(timeout));
            checkPoint (ctx);
            Txn txn = new Txn();
            // txn.setId(uuid);
            txn.setRequestMsg(msg);
            txn.setReqMsgStr(ISOUtil.byte2hex(msg.pack()));
            System.out.print("ABC: =====" + txn);
            db.session().save(txn);
            db.commit();
            rc = PREPARED;
        } catch (Throwable t) {
            error (t);
            System.out.print("catch error: =====" );
            ctx.remove (DB); // "Close" participant checks 
                             // for DB in Context
        }
        return rc | NO_JOIN | READONLY;
    }
    public void commit (long id, Serializable o) { }
    public void abort  (long id, Serializable o) { }
    public void setConfiguration (Configuration cfg) 
        throws ConfigurationException
    {
        super.setConfiguration (cfg);
        this.timeout = cfg.getInt ("timeout", 0);
    }
    
}

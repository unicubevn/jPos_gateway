package org.jpos.testbed.txnmgr;

import java.io.Serializable;

import org.jpos.transaction.Context;
import org.jpos.transaction.GroupSelector;
import org.jpos.transaction.TxnSupport;

public class Switch extends TxnSupport implements GroupSelector {
    @Override
    public int prepare(long id, Serializable context) {
        return PREPARED | READONLY | NO_JOIN;
    }

    @Override
    public void commit(long id, Serializable ser) {
    }

    @Override
    public void abort(long id, Serializable ser) {
    }

    public String select(long id, Serializable ser) {
        Context ctx = (Context) ser;
        String type = (String) ctx.get("TXNNAME");
        String groups = null;
        if (type != null)
            groups = cfg.get(type, null);
        if (groups == null)
            groups = cfg.get("unknown", "");
        // Debug info in context
        ctx.put("SWITCH", type + " (" + groups + ")");
        return groups;
    }
}

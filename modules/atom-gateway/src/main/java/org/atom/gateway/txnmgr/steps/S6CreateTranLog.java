package org.atom.gateway.txnmgr.steps;

import java.io.Serializable;

import org.jpos.transaction.Context;
import org.jpos.transaction.TxnSupport;

public class S6CreateTranLog extends TxnSupport {
    @Override
    public int prepare(long id, Serializable o) {
        if (o instanceof Context)
            ((Context) o).checkPoint(cfg.get("message", "checkpoint"));

        return PREPARED | NO_JOIN | READONLY;
    }

    @Override
    public void commit(long id, Serializable o) {
    }

    @Override
    public void abort(long id, Serializable o) {
    }
}

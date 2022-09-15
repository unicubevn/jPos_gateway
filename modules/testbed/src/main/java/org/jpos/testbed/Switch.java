package org.jpos.testbed;

import org.jpos.core.*;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;
import org.jpos.transaction.GroupSelector;
import org.jpos.transaction.TransactionParticipant;
import java.io.Serializable;
import org.jpos.core.Configuration;

public class Switch implements GroupSelector {
    protected Configuration cfg;
    
    public int prepare(long id, Serializable context) {
        return PREPARED | READONLY | NO_JOIN;
    }

    @Override
    public void commit(long id, Serializable context) {
    }

    @Override
    public void abort(long id, Serializable context) {
    }

    public String select(long id, Serializable context) {
        try {
            ISOMsg m = (ISOMsg) ((Context) context).get("ISOMSG");
            String groups = cfg.get(m.getMTI(), null);
            return groups;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}

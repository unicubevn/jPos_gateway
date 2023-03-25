package org.atom.gateway.txnmgr;

import java.io.Serializable;
import java.util.Random;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;
import org.jpos.transaction.TxnSupport;

public class DemoParticipant extends TxnSupport {
    @Override
    protected int doPrepare(long id, Context ctx) throws Exception {
        ISOMsg message = (ISOMsg) ctx.get(ContextConstants.REQUEST.toString());
        ISOSource source = (ISOSource) ctx.get(ContextConstants.SOURCE.toString());
        assertNotNull(message, "A valid 'REQUEST' is expected in the context");
        assertNotNull(source, "A valid 'SOURCE' is expected in the context");
        assertTrue(message.hasField(4),
                "The message needs to have an amount (ISOMsg:4)");
        message.setResponseMTI();
        Random random = new Random(System.currentTimeMillis());
        message.set(37, Integer.toString(Math.abs(random.nextInt()) % 1000000));
        message.set(38, Integer.toString(Math.abs(random.nextInt()) % 1000000));
        if ("000000009999".equals(message.getString(4)))
            message.set(39, "01");
        else
            message.set(39, "00");
        source.send(message);
        return PREPARED | NO_JOIN | READONLY;
    }

    public void commit(long id, Serializable context) {
    }

    public void abort(long id, Serializable context) {
    }
}

package org.jpos.testbed.txnmgr;

import org.jpos.core.*;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.testbed.packager.AtomPackager;
import org.jpos.testbed.packager.SacomPackager;
import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants; import org.jpos.transaction.TransactionParticipant;
import java.io.Serializable;


public class StepOne implements TransactionParticipant, Configurable {
    Configuration cfg;

    @Override
    public int prepare(long id, Serializable context) {
        Context ctx = (Context) context;
        ISOMsg m = (ISOMsg) ctx.get(ContextConstants.REQUEST.toString());
        // if (m != null && (m.hasField(2) || m.hasField(35))) {
        //     try {
        //         Card card = Card.builder().isomsg(m).build();
        //         String s = cfg.get("bin." + card.getBin(), null);
        //         if (s != null) {
        //             ctx.put(ContextConstants.DESTINATION.toString(), s);
        //         }
        //     } catch (InvalidCardException ignore) { // use default destination
        //     }
        // }
        System.out.println("====== Get field 63 Data ======");
        System.out.println("Field 63.1: " + m.getString("63.1"));
        System.out.println("====== End ======");
        m.dump(System.out, "   ");
        m.unset(63);

        m.setPackager(new SacomPackager());
        try {
            m.recalcBitMap();
        } catch (ISOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        m.dump(System.out, "   ");
        return PREPARED | NO_JOIN | READONLY;
    }

    public void setConfiguration(Configuration cfg) {
        this.cfg = cfg;
    }
}
package org.atom.gateway.txnmgr;

import org.jpos.core.*;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.IFB_LLLHEX;
import org.atom.gateway.packager.AtomPackager;
import org.atom.gateway.packager.SacomPackager;
import org.jpos.transaction.Context;
import org.jpos.transaction.ContextConstants;
import org.jpos.transaction.TransactionParticipant;
import java.io.Serializable;
import org.jpos.iso.packager.GenericPackager;

public class StepOne implements TransactionParticipant, Configurable {
    Configuration cfg;

    @Override
    public int prepare(long id, Serializable context) {
        Context ctx = (Context) context;
        ISOMsg m = (ISOMsg) ctx.get(ContextConstants.REQUEST.toString());
        // m.dump(System.out, "  ");
        try {
            GenericPackager packager = new GenericPackager("cfg/atompackager.xml");
            m.setPackager(packager);
            // System.out.println("====== Get field 63 Data ======");
            // System.out.println("Field 63.1: " + m.getString("63.1"));
            // System.out.println("====== End ======");
            // m.dump(System.out, " ");
            m.unset(63);

            m.setPackager(packager);

            m.recalcBitMap();
        } catch (ISOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // m.dump(System.out, "   ");
        return PREPARED | NO_JOIN | READONLY;
    }

    public void setConfiguration(Configuration cfg) {
        this.cfg = cfg;
    }
}

// if (m != null && (m.hasField(2) || m.hasField(35))) {
// try {
// Card card = Card.builder().isomsg(m).build();
// String s = cfg.get("bin." + card.getBin(), null);
// if (s != null) {
// ctx.put(ContextConstants.DESTINATION.toString(), s);
// }
// } catch (InvalidCardException ignore) { // use default destination
// }
// }
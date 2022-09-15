package org.jpos.testbed;

import org.jpos.space.LocalSpace;
import org.jpos.space.SpaceSource;
import org.jpos.transaction.ContextConstants;
import org.jpos.util.Log;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.transaction.Context;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Rewrite everything from org.jpos.iso.IncommingListener Class
public class Dispatcher extends Log implements ISORequestListener, Configurable {

    long timeout;
    private Space<String, Context> sp;
    private String queue;
    private String source;
    private String request;
    private String timestamp;
    private Map<String, String> additionalContextEntries = null;
    private boolean remote = false;

    @SuppressWarnings("unchecked")
    public void setConfiguration(Configuration cfg)
            throws ConfigurationException {
        timeout = cfg.getLong("timeout", 15000L);
        sp = (Space<String, Context>) SpaceFactory.getSpace(cfg.get("space"));
        queue = cfg.get("queue", null);
        if (queue == null)
            throw new ConfigurationException("queue property not specified");
        source = cfg.get("source", AtomConstant.SOURCE.toString());
        request = cfg.get("request", AtomConstant.REQUEST.toString());
        timestamp = cfg.get("timestamp", AtomConstant.TIMESTAMP.toString());
        remote = cfg.getBoolean("remote") || cfg.get("space").startsWith("rspace:");
        Map<String, String> m = new HashMap<>();
        cfg.keySet()
                .stream()
                .filter(s -> s.startsWith("ctx."))
                .forEach(s -> m.put(s.substring(4), cfg.get(s)));
        if (m.size() > 0)
            additionalContextEntries = m;
    }

    public boolean process(ISOSource src, ISOMsg m) {
        // Configuration cfg;
        try {
            String mti = m.getMTI();
            // String className = cfg.get("prefix", "") + mti.substring(1); Class c =
            // Class.forName(className);
            // System.out.println("className: ------ " + className);
            System.out.println("mti: ------ " + mti);
            switch (mti) {
                case "0800":
                    // code block
                    incoming800process(src, m);
                    break;
                case "0200":
                    // code block
                    Incoming200processs(src, m);
                    break;
                default:
                    // code block
                    return false;
            }
        } catch (ISOException e) {
            warn(e);
        }
        return false;
    }

    // Message handling, in production should refactor to separated class
    private boolean incoming800process(ISOSource src, ISOMsg m) {
        try {
            m.setResponseMTI();
            m.set(39, "0000");
            src.send(m);
        } catch (Exception e) {
            warn(e);
        }
        return true;
    }

    private boolean incomingdefaultprocess(ISOSource src, ISOMsg m) {
        final Context ctx = new Context();

        String txnname = m.getString(0).substring(1);
        if (remote)
            src = new SpaceSource((LocalSpace) sp, src, timeout);
        ctx.put(timestamp, new Date(), remote);
        ctx.put(source, src, remote);
        ctx.put(request, m, remote);
        ctx.put(AtomConstant.TXNNAME.toString(), txnname);
        if (additionalContextEntries != null) {
            additionalContextEntries.entrySet().forEach(
                    e -> ctx.put(e.getKey(), e.getValue(), remote));
        }

        if (m.hasField(3)) {
            String pcode = m.getString(3);
            if (pcode.length() > 2) {
                // processing code
                txnname = txnname + "." + pcode.substring(0, 2);
            }
        }

        if (m.hasField(24)) {
            txnname = txnname + "." + m.getString(24); // function code
            if (m.hasField(101))
                txnname = txnname + "." + m.getString(101); // file name
        }

        if (m.hasField(25)) {
            txnname = txnname + "." + m.getString(25); // reason code
        }
        
        sp.out(queue, ctx, timeout);
        return true;

    }

    private boolean Incoming200processs(ISOSource source, ISOMsg m) {
        return incomingdefaultprocess(source, m);
    }

}

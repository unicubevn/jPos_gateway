package org.jpos.testbed.txnmgr;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.*;
import org.jpos.iso.channel.NCCChannel;
import org.jpos.iso.packager.ISO87BPackager;
import org.jpos.rc.CMF;
import org.jpos.rc.Result;
import org.jpos.transaction.ContextConstants;
import org.jpos.transaction.TransactionParticipant;
import org.jpos.util.Caller;
import org.jpos.util.Chronometer;
import org.jpos.util.NameRegistrar;
import org.jpos.transaction.Context;

/*
 * This module is rewritten the org.jpos.transaction.participant.QueryHost Class
 * Due to couldn't control the ChannelAdaptor too connect the partner Host, we decided bypass the ChannelAdaptor temporary.
 * We call the partner host directly and send the response back to client right inside the ATOM Query Host class
 * 
 */
public class AtomQueryHost implements TransactionParticipant, ISOResponseListener, Configurable {
    private static final long DEFAULT_TIMEOUT = 30000L;
    private static final long DEFAULT_WAIT_TIMEOUT = 1000L;

    private long timeout;
    private long waitTimeout;
    private String requestName;
    private String responseName;
    private String destination;
    private boolean continuations;
    private Configuration cfg;
    private String request;
    private boolean ignoreUnreachable;
    private boolean checkConnected = true;
    private String source;

    public AtomQueryHost() {
        super();
    }

    public int prepare(long id, Serializable ser) {
        Context ctx = (Context) ser;
        System.out.println("---------ctx-----------");
        ctx.dump(System.out, "");

        Result result = ctx.getResult();
        String ds = ctx.getString(destination);
        if (ds == null) {
            return result.fail(
                    CMF.MISCONFIGURED_ENDPOINT, Caller.info(), "'%s' not present in Context", destination).FAIL();
        }

        ISOMsg m = ctx.get(requestName);
        logISOMsg(m);
        if (m == null)
            return result.fail(CMF.INVALID_REQUEST, Caller.info(), "'%s' is null", requestName).FAIL();

        ISOSource src = (ISOSource) ctx.get("SOURCE");

        try {
            sendmsg(m, src);
        } catch (IOException | ISOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // return result.fail(CMF.ACQUIRER_NOT_SUPPORTED,e.getMessage(),"Có Lỗi xảy ra: '%s'" ).FAIL();
        }
        // String muxName = cfg.get("mux." + ds, "mux." + ds);
        // MUX mux = NameRegistrar.getIfExists(muxName);
        // if (mux == null)
        // return result.fail(CMF.MISCONFIGURED_ENDPOINT, Caller.info(), "MUX '%s' not
        // found", muxName).FAIL();

        // Chronometer chronometer = new Chronometer();
        // if (isConnected(mux)) {
        // long t = Math.max(timeout - chronometer.elapsed(), 1000L); // give at least a
        // second to catch a response
        // try {
        // if (continuations) {
        // mux.request(m, t, this, ctx);
        // return PREPARED | READONLY | PAUSE | NO_JOIN;
        // } else {
        // ISOMsg resp = mux.request(m, t);
        // if (resp != null) {
        // ctx.put(responseName, resp);
        // return PREPARED | READONLY | NO_JOIN;
        // } else if (ignoreUnreachable) {
        // ctx.log(String.format ("MUX '%s' no response", muxName));
        // } else {
        // return result.fail(CMF.HOST_UNREACHABLE, Caller.info(), "'%s' does not
        // respond", muxName).FAIL();
        // }
        // }
        // } catch (ISOException e) {
        // return result.fail(CMF.SYSTEM_ERROR, Caller.info(), e.getMessage()).FAIL();
        // }
        // } else if (ignoreUnreachable) {
        // ctx.log(String.format ("MUX '%s' not connected", muxName));
        // } else {
        // return result.fail(CMF.HOST_UNREACHABLE, Caller.info(), "'%s' is not
        // connected", muxName).FAIL();
        // }
        return PREPARED | NO_JOIN | READONLY;
    }

    @Override
    public void responseReceived(ISOMsg resp, Object handBack) {
        Context ctx = (Context) handBack;
        ctx.put(responseName, resp);
        ctx.resume();
    }

    @Override
    public void expired(Object handBack) {
        Context ctx = (Context) handBack;
        String ds = ctx.getString(destination);
        String muxName = cfg.get("mux." + ds, "mux." + ds);
        ctx.getResult().fail(CMF.HOST_UNREACHABLE, Caller.info(), "'%s' does not respond", muxName).FAIL();
        ctx.getPausedTransaction().forceAbort();
        ctx.resume();
    }

    public void setConfiguration(Configuration cfg) throws ConfigurationException {
        this.cfg = cfg;
        timeout = cfg.getLong("timeout", DEFAULT_TIMEOUT);
        waitTimeout = cfg.getLong("wait-timeout", DEFAULT_WAIT_TIMEOUT);
        requestName = cfg.get("request", ContextConstants.REQUEST.toString());
        responseName = cfg.get("response", ContextConstants.RESPONSE.toString());
        destination = cfg.get("destination", ContextConstants.DESTINATION.toString());
        continuations = cfg.getBoolean("continuations", true);
        ignoreUnreachable = cfg.getBoolean("ignore-host-unreachable", false);
        checkConnected = cfg.getBoolean("check-connected", checkConnected);
    }

    protected boolean isConnected(MUX mux) {
        if (!checkConnected || mux.isConnected())
            return true;
        long timeout = System.currentTimeMillis() + waitTimeout;
        while (System.currentTimeMillis() < timeout) {
            if (mux.isConnected())
                return true;
            ISOUtil.sleep(500);
        }
        return false;
    }

    private static void logISOMsg(ISOMsg msg) {
        System.out.println("-----  Query Host Log: ISO MESSAGE to Pack-----");
        try {
            System.out.println("  MTI : " + msg.getMTI());
            for (int i = 1; i <= msg.getMaxField(); i++) {
                if (msg.hasField(i)) {
                    System.out.println("    Field-" + i + " : " + msg.getString(i));
                }
            }
        } catch (ISOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("--------------------");
        }
    }

    private static void sendmsg(ISOMsg m, ISOSource src) throws IOException , ISOException {

        System.out.println(" ----- Send run ----");
        ISO87BPackager packager = new ISO87BPackager();
        String hostName = "113.164.14.80";
        int portNumber = 13250;
        NCCChannel channel = new NCCChannel(hostName, portNumber, packager, ISOUtil.hex2byte("6000190002"));
        channel.setTimeout(30000);
        m.recalcBitMap();
        channel.connect();
        channel.send(m);
        ISOMsg response = channel.receive();
        System.out.println(" ----- Receive  Response run ----");
        logISOMsg(response);
        System.out.println(" the Source " + src);
        src.send(response);

    }
}
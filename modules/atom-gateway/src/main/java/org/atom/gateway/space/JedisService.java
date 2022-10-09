package org.atom.gateway.space;

import org.jpos.iso.ISOUtil;
import org.jpos.q2.QBeanSupport;
import org.jpos.util.NameRegistrar;
import org.jpos.util.Profiler;

import redis.clients.jedis.Jedis;

public class JedisService extends QBeanSupport implements Runnable{
    @Override
    protected void initService() throws Exception {
        NameRegistrar.register(getName(), this);
    }

    @Override
    protected void startService() throws Exception {
        if (cfg.getLong("ping-every", 0L) > 0L)
            new Thread(this, getName()).start();
    }

    @Override
    protected void stopService() throws Exception {
        NameRegistrar.unregister(getName());
    }

    public Jedis getConnection() {
        getLog().info("getting a new jedis connection");
        Jedis jedis = new Jedis(
                cfg.get("host", "localhost"),
                cfg.getInt("port", 6379),
                cfg.getInt("timeout", 120000));

        return jedis;
    }

    public void run() {
        while (running()) {
            try {
                Profiler prof = new Profiler();
                Jedis jedis = getConnection();
                jedis.ping();
                prof.checkPoint("PING");
                jedis.quit();
                getLog().info("PING", prof);
            } catch (Exception e) {
                getLog().warn(e.getMessage());
            }
            ISOUtil.sleep(cfg.getLong("ping", 60000L));
        }
    }
}

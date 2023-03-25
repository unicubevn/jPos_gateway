package org.atom.gateway.packager;

import org.jpos.iso.ISOException;
import org.jpos.iso.packager.GenericPackager;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class EximPackager extends GenericPackager {

    public EximPackager() throws ISOException {
        super("./eximpackager.xml");
    }
}

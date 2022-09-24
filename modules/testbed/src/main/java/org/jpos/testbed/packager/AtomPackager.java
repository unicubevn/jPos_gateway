package org.jpos.testbed.packager;

import org.jpos.iso.packager.ISO93BPackager;

/**
 * ISO 8583 v1987 BINARY Packager
 * Apply ATOM's custom rule
 * 
 * @author ATOM
 * @version v1.0
 * @see ISOPackager
 * @see ISOBasePackager
 * @see ISOComponent
 * @see ISO87BPackager
 */
public class AtomPackager extends ISO93BPackager{
    public AtomPackager() {
        super();
        setFieldPackager(fld);
    }
}
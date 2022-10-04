package org.atom.gateway.packager;

import org.jpos.iso.IFB_LLLHEX;

/**
 * ISO 8583 v1987 BINARY Packager
 * Apply Sacombank's custom rule
 * 
 * @author ATOM
 * @version v1.0
 * @see ISOPackager
 * @see ISOBasePackager
 * @see ISOComponent
 * @see ISO87BPackager
 * @see 
 */
public class SacomPackager extends AtomPackager{
    public SacomPackager() {
        super();
        
        setFieldPackager(fld);
    }
}

package org.jpos.testbed.packager;

import org.jpos.iso.IFB_HEX;

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
        fld[53]  =  new IFB_HEX ( 48, "SECURITY RELATED CONTROL INFORMATION", true);
        setFieldPackager(fld);
    }
}

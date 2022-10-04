package org.jpos.testbed.packager;

import org.jpos.iso.IFB_AMOUNT;
import org.jpos.iso.IFB_BINARY;
import org.jpos.iso.IFB_BITMAP;
import org.jpos.iso.IFB_HEX;
import org.jpos.iso.IFB_LLCHAR;
import org.jpos.iso.IFB_LLLBINARY;
import org.jpos.iso.IFB_LLLCHAR;
import org.jpos.iso.IFB_LLLHEX;
import org.jpos.iso.IFB_LLNUM;
import org.jpos.iso.IFB_NUMERIC;
import org.jpos.iso.IF_CHAR;
import org.jpos.iso.ISOBasePackager;
import org.jpos.iso.ISOFieldPackager;

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
public class AtomPackager extends ISOBasePackager{
    private static final boolean pad = false;
    protected ISOFieldPackager fld[] = {
        /*000*/    new IFB_NUMERIC(  4, "MESSAGE TYPE INDICATOR", true),
            /*001*/    new IFB_BITMAP( 16, "BIT MAP"),
            /*002*/    new IFB_LLNUM( 19, "PAN - PRIMARY ACCOUNT NUMBER", pad),
            /*003*/    new IFB_NUMERIC (  6, "PROCESSING CODE", true),
            /*004*/    new IFB_NUMERIC ( 12, "AMOUNT, TRANSACTION", true),
            /*005*/    new IFB_NUMERIC ( 12, "AMOUNT, SETTLEMENT", true),
            /*006*/    new IFB_NUMERIC ( 12, "AMOUNT, CARDHOLDER BILLING", true),
            /*007*/    new IFB_NUMERIC ( 10, "TRANSMISSION DATE AND TIME", true),
            /*008*/    new IFB_NUMERIC (  8, "AMOUNT, CARDHOLDER BILLING FEE", true),
            /*009*/    new IFB_NUMERIC (  8, "CONVERSION RATE, SETTLEMENT", true),
            /*010*/    new IFB_NUMERIC (  8, "CONVERSION RATE, CARDHOLDER BILLING", true),
            /*011*/    new IFB_NUMERIC (  6, "SYSTEM TRACE AUDIT NUMBER", true),
            /*012*/    new IFB_NUMERIC (  6, "TIME, LOCAL TRANSACTION", true),
            /*013*/    new IFB_NUMERIC (  4, "DATE, LOCAL TRANSACTION", true),
            /*014*/    new IFB_NUMERIC (  4, "DATE, EXPIRATION", true),
            /*015*/    new IFB_NUMERIC (  4, "DATE, SETTLEMENT", true),
            /*016*/    new IFB_NUMERIC (  4, "DATE, CONVERSION", true),
            /*017*/    new IFB_NUMERIC (  4, "DATE, CAPTURE", true),
            /*018*/    new IFB_NUMERIC (  4, "MERCHANTS TYPE", true),
            /*019*/    new IFB_NUMERIC (  3, "ACQUIRING INSTITUTION COUNTRY CODE", true),
            /*020*/    new IFB_NUMERIC (  3, "PAN EXTENDED COUNTRY CODE", true),
            /*021*/    new IFB_NUMERIC (  3, "FORWARDING INSTITUTION COUNTRY CODE", true),
            /*022*/    new IFB_NUMERIC (  3, "POINT OF SERVICE ENTRY MODE", true),
            /*023*/    new IFB_NUMERIC (  3, "CARD SEQUENCE NUMBER", true),
            /*024*/    new IFB_NUMERIC (  3, "NETWORK INTERNATIONAL IDENTIFIEER", true),
            /*025*/    new IFB_NUMERIC (  2, "POINT OF SERVICE CONDITION CODE", true),
            /*026*/    new IFB_NUMERIC (  2, "POINT OF SERVICE PIN CAPTURE CODE", true),
            /*027*/    new IFB_NUMERIC (  1, "AUTHORIZATION IDENTIFICATION RESP LEN",true),
            /*028*/    new IFB_AMOUNT(  9, "AMOUNT, TRANSACTION FEE", true),
            /*029*/    new IFB_AMOUNT  (  9, "AMOUNT, SETTLEMENT FEE", true),
            /*030*/    new IFB_AMOUNT  (  9, "AMOUNT, TRANSACTION PROCESSING FEE", true),
            /*031*/    new IFB_AMOUNT  (  9, "AMOUNT, SETTLEMENT PROCESSING FEE", true),
            /*032*/    new IFB_LLNUM   ( 11, "ACQUIRING INSTITUTION IDENT CODE", pad),
            /*033*/    new IFB_LLNUM   ( 11, "FORWARDING INSTITUTION IDENT CODE", pad),
            /*034*/    new IFB_LLCHAR( 28, "PAN EXTENDED"),
            /*035*/    new IFB_LLNUM   ( 37, "TRACK 2 DATA", pad),
            /*036*/    new IFB_LLLCHAR(104, "TRACK 3 DATA"),
            /*037*/    new IF_CHAR( 12, "RETRIEVAL REFERENCE NUMBER"),
            /*038*/    new IF_CHAR     (  6, "AUTHORIZATION IDENTIFICATION RESPONSE"),
            /*039*/    new IF_CHAR     (  2, "RESPONSE CODE"),
            /*040*/    new IF_CHAR     (  3, "SERVICE RESTRICTION CODE"),
            /*041*/    new IF_CHAR     (  8, "CARD ACCEPTOR TERMINAL ID"),
            /*042*/    new IF_CHAR     ( 15, "CARD ACCEPTOR MERCHANT ID" ),
            /*043*/    new IF_CHAR     ( 40, "CARD ACCEPTOR NAME/LOCATION"),
            /*044*/    new IFB_LLCHAR  ( 25, "ADITIONAL RESPONSE DATA"),
            /*045*/    new IFB_LLCHAR  ( 76, "TRACK 1 DATA"),
            /*046*/    new IFB_LLLCHAR (204, "AMOUNT - FEE"),
            /*047*/    new IFB_LLLCHAR (999, "ADITIONAL DATA - NATIONAL"),
            /*048*/    new IFB_LLLCHAR (999, "ADITIONAL DATA - PRIVATE"),
            /*049*/    new IF_CHAR     (  3, "CURRENCY CODE, TRANSACTION"),
            /*050*/    new IF_CHAR     (  3, "CURRENCY CODE, SETTLEMENT"),
            /*051*/    new IF_CHAR     (  3, "CURRENCY CODE, CARDHOLDER BILLING"   ),
            /*052*/    new IFB_BINARY(  8, "PIN - PERSONAL IDENTIFICATION NUMBER DATA"   ),
            /*053*/    new IFB_HEX ( 48, "SECURITY RELATED CONTROL INFORMATION",true),
            /*054*/    new IFB_LLLCHAR (120, "ADDITIONAL AMOUNTS"),
            /*055*/    new IFB_LLLCHAR (999, "IC CARD SYSTEM RELATED DATA"),
            /*056*/    new IFB_LLNUM   ( 35, "ORIGINAL DATA ELEMENTS", pad),
            /*057*/    new IFB_NUMERIC (  3, "Authorization life cycle code", pad),
            /*058*/    new IFB_LLNUM   ( 11, "Authorizing agent institution Id Code", pad),
            /*059*/    new IFB_LLLCHAR (999, "Transport data"),
            /*060*/    new IFB_LLLCHAR (999, "RESERVED PRIVATE - BATCH NUMBER, SOFTWARE ID"),
            /*061*/    new IFB_LLLCHAR (999, "RESERVED PRIVATE - PRODUCT CODES"),
            /*062*/    new IFB_LLLCHAR (999, "RESERVED PRIVATE - INVOICE NUMBER"),
            /*063*/    new IFB_LLLCHAR (999, "RESERVED PRIVATE - ADDITIONAL DATA"),
            /*064*/    new IFB_BINARY  (  8, "MESSAGE AUTHENTICATION CODE FIELD"),
            /*----------------- */
            /*064*/ new IFB_BINARY  (  8, "Message authentication code field"),
            /*065*/ new IFB_BINARY  (  8, "Reserved for ISO use"),
            /*066*/ new IFB_LLLCHAR (204, "Amounts, original fees"),
            /*067*/ new IFB_NUMERIC (  2, "Extended payment data", pad),
            /*068*/ new IFB_NUMERIC (  3, "Country code, receiving institution", pad),
            /*069*/ new IFB_NUMERIC (  3, "Country code, settlement institution", pad),
            /*070*/ new IFB_NUMERIC (  3, "Country code, authorizing agent Inst.", pad),
            /*071*/ new IFB_NUMERIC (  8, "Message number", pad),
            /*072*/ new IFB_LLLCHAR (999, "Data record"),
            /*073*/ new IFB_NUMERIC (  6, "Date, action", pad),
            /*074*/ new IFB_NUMERIC ( 10, "Credits, number", pad),
            /*075*/ new IFB_NUMERIC ( 10, "Credits, reversal number", pad),
            /*076*/ new IFB_NUMERIC ( 10, "Debits, number", pad),
            /*077*/ new IFB_NUMERIC ( 10, "Debits, reversal number", pad),
            /*078*/ new IFB_NUMERIC ( 10, "Transfer, number", pad),
            /*079*/ new IFB_NUMERIC ( 10, "Transfer, reversal number", pad),
            /*080*/ new IFB_NUMERIC ( 10, "Inquiries, number", pad),
            /*081*/ new IFB_NUMERIC ( 10, "Authorizations, number", pad),
            /*082*/ new IFB_NUMERIC ( 10, "Inquiries, reversal number", pad),
            /*083*/ new IFB_NUMERIC ( 10, "Payments, number", pad),
            /*084*/ new IFB_NUMERIC ( 10, "Payments, reversal number", pad),
            /*085*/ new IFB_NUMERIC ( 10, "Fee collections, number", pad),
            /*086*/ new IFB_NUMERIC ( 16, "Credits, amount", pad),
            /*087*/ new IFB_NUMERIC ( 16, "Credits, reversal amount", pad),
            /*088*/ new IFB_NUMERIC ( 16, "Debits, amount", pad),
            /*089*/ new IFB_NUMERIC ( 16, "Debits, reversal amount", pad),
            /*090*/ new IFB_NUMERIC ( 10, "Authorizations, reversal number", pad),
            /*091*/ new IFB_NUMERIC (  3, "Country code, transaction Dest. Inst.", pad),
            /*092*/ new IFB_NUMERIC (  3, "Country code, transaction Orig. Inst.", pad),
            /*093*/ new IFB_LLNUM   ( 11, "Transaction Dest. Inst. Id code", pad),
            /*094*/ new IFB_LLNUM   ( 11, "Transaction Orig. Inst. Id code", pad),
            /*095*/ new IFB_LLCHAR  ( 99, "Card issuer reference data"),
            /*096*/ new IFB_LLLBINARY(999,"Key management data"),
            /*097*/ new IFB_AMOUNT  (1+16,"Amount, Net reconciliation", pad),
            /*098*/ new IF_CHAR     ( 25, "Payee"),
            /*099*/ new IFB_LLCHAR  ( 11, "Settlement institution Id code"),
            /*100*/ new IFB_LLNUM   ( 11, "Receiving institution Id code", pad),
            /*101*/ new IFB_LLCHAR  ( 17, "File name"),
            /*102*/ new IFB_LLCHAR  ( 28, "Account identification 1"),
            /*103*/ new IFB_LLCHAR  ( 28, "Account identification 2"),
            /*104*/ new IFB_LLLCHAR (100, "Transaction description"),
            /*105*/ new IFB_NUMERIC ( 16, "Credits, Chargeback amount", pad),
            /*106*/ new IFB_NUMERIC ( 16, "Debits, Chargeback amount", pad),
            /*107*/ new IFB_NUMERIC ( 10, "Credits, Chargeback number", pad),
            /*108*/ new IFB_NUMERIC ( 10, "Debits, Chargeback number", pad),
            /*109*/ new IFB_LLCHAR  ( 84, "Credits, Fee amounts"),
            /*110*/ new IFB_LLCHAR  ( 84, "Debits, Fee amounts"),
            /*111*/ new IFB_LLLCHAR (999, "Reserved for ISO use"),
            /*112*/ new IFB_LLLCHAR (999, "Reserved for ISO use"),
            /*113*/ new IFB_LLLCHAR (999, "Reserved for ISO use"),
            /*114*/ new IFB_LLLCHAR (999, "Reserved for ISO use"),
            /*115*/ new IFB_LLLCHAR (999, "Reserved for ISO use"),
            /*116*/ new IFB_LLLCHAR (999, "Reserved for national use"),
            /*117*/ new IFB_LLLCHAR (999, "Reserved for national use"),
            /*118*/ new IFB_LLLCHAR (999, "Reserved for national use"),
            /*119*/ new IFB_LLLCHAR (999, "Reserved for national use"),
            /*120*/ new IFB_LLLCHAR (999, "Reserved for national use"),
            /*121*/ new IFB_LLLCHAR (999, "Reserved for national use"),
            /*122*/ new IFB_LLLCHAR (999, "Reserved for national use"),
            /*123*/ new IFB_LLLCHAR (999, "Reserved for private use"),
            /*124*/ new IFB_LLLCHAR (999, "Reserved for private use"),
            /*125*/ new IFB_LLLCHAR (999, "Reserved for private use"),
            /*126*/ new IFB_LLLCHAR (999, "Reserved for private use"),
            /*127*/ new IFB_LLLCHAR (999, "Reserved for private use"),
            /*128*/ new IFB_BINARY  (  8, "Message authentication code field")
        };
    public AtomPackager() {
        super();
        setFieldPackager(fld);
    }
}

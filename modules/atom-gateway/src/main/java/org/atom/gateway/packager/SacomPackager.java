package org.atom.gateway.packager;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.jpos.iso.IFB_LLLHEX;
import org.jpos.iso.ISOException;
import org.jpos.iso.packager.GenericPackager;

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
public class SacomPackager extends GenericPackager{
    private static String str = String.join("\n"
    ,"<?xml version='1.0' encoding='UTF-8' standalone='no'?>"
    ,"<!DOCTYPE isopackager ["
    ,"    <!ELEMENT isopackager (isofield+,isofieldpackager*)*>"
    ,"    <!ATTLIST isopackager maxValidField     CDATA        #IMPLIED>"
    ,"    <!ATTLIST isopackager bitmapField       CDATA        #IMPLIED>"
    ,"    <!ATTLIST isopackager thirdBitmapField  CDATA        #IMPLIED>"
    ,"    <!ATTLIST isopackager firstField        CDATA        #IMPLIED>"
    ,"    <!ATTLIST isopackager emitBitmap        (true|false) #IMPLIED>"
    ,"    <!ATTLIST isopackager headerLength      CDATA        #IMPLIED>"
    ,"    <!-- isofield -->"
    ,"   <!ELEMENT isofield (#PCDATA)>"
    ,"    <!ATTLIST isofield id     CDATA        #REQUIRED>"
    ,"    <!ATTLIST isofield length CDATA        #REQUIRED>"
    ,"    <!ATTLIST isofield name   CDATA        #REQUIRED>"
    ,"    <!ATTLIST isofield class  NMTOKEN      #REQUIRED>"
    ,"    <!ATTLIST isofield params CDATA        #IMPLIED>"
    ,"    <!ATTLIST isofield encoding CDATA      #IMPLIED>"
    ,"    <!ATTLIST isofield token  CDATA        #IMPLIED>"
    ,"    <!ATTLIST isofield pad    (true|false) #IMPLIED>"
    ,"    <!ATTLIST isofield trim (true|false) #IMPLIED>"
    ,"    <!-- isofieldpackager -->"
    ,"    <!ELEMENT isofieldpackager (isofield+,isofieldpackager*)*>"
    ,"    <!ATTLIST isofieldpackager id       CDATA        #REQUIRED>"
    ,"    <!ATTLIST isofieldpackager name     CDATA        #REQUIRED>"
    ,"    <!ATTLIST isofieldpackager length   CDATA        #REQUIRED>"
    ,"    <!ATTLIST isofieldpackager class    NMTOKEN      #REQUIRED>"
    ,"    <!ATTLIST isofieldpackager params   CDATA        #IMPLIED>"
    ,"    <!ATTLIST isofieldpackager token    CDATA        #IMPLIED>"
    ,"    <!ATTLIST isofieldpackager pad      (true|false) #IMPLIED>"
    ,"    <!ATTLIST isofieldpackager packager NMTOKEN      #REQUIRED>"
    ,"    <!ATTLIST isofieldpackager emitBitmap (true|false) #IMPLIED>"
    ,"    <!ATTLIST isofieldpackager maxValidField CDATA        #IMPLIED>"
    ,"    <!ATTLIST isofieldpackager bitmapField CDATA        #IMPLIED>"
    ,"    <!ATTLIST isofieldpackager firstField  CDATA        #IMPLIED>"
    ,"    <!ATTLIST isofieldpackager headerLength  CDATA        #IMPLIED>"
    ,"    ]>"
    ,"<!-- ISO 8583:1987 (BINARY) field descriptions for GenericPackager -->"
    ,"<isopackager>"
    ,"        <isofield id='0' length='4' name='MESSAGE TYPE INDICATOR' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"       <isofield id='1' length='16' name='BIT MAP' class='org.jpos.iso.IFB_BITMAP' />"
    ,"    <isofield id='2' length='19' name='PAN - PRIMARY ACCOUNT NUMBER' pad='false' class='org.jpos.iso.IFB_LLNUM' />"
    ,"    <isofield id='3' length='6' name='PROCESSING CODE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='4' length='12' name='AMOUNT, TRANSACTION' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='5' length='12' name='AMOUNT, SETTLEMENT' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='6' length='12' name='AMOUNT, CARDHOLDER BILLING' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='7' length='10' name='TRANSMISSION DATE AND TIME' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='8' length='8' name='AMOUNT, CARDHOLDER BILLING FEE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='9' length='8' name='CONVERSION RATE, SETTLEMENT' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='10' length='8' name='CONVERSION RATE, CARDHOLDER BILLING' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='11' length='6' name='SYSTEM TRACE AUDIT NUMBER' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='12' length='6' name='TIME, LOCAL TRANSACTION' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='13' length='4' name='DATE, LOCAL TRANSACTION' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='14' length='4' name='DATE, EXPIRATION' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='15' length='4' name='DATE, SETTLEMENT' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='16' length='4' name='DATE, CONVERSION' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='17' length='4' name='DATE, CAPTURE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='18' length='4' name='MERCHANTS TYPE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='19' length='3' name='ACQUIRING INSTITUTION COUNTRY CODE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='20' length='3' name='PAN EXTENDED COUNTRY CODE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='21' length='3' name='FORWARDING INSTITUTION COUNTRY CODE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='22' length='3' name='POINT OF SERVICE ENTRY MODE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='23' length='3' name='CARD SEQUENCE NUMBER' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='24' length='3' name='NETWORK INTERNATIONAL IDENTIFIEER' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='25' length='2' name='POINT OF SERVICE CONDITION CODE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='26' length='2' name='POINT OF SERVICE PIN CAPTURE CODE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='27' length='1' name='AUTHORIZATION IDENTIFICATION RESP LEN' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='28' length='9' name='AMOUNT, TRANSACTION FEE' pad='true' class='org.jpos.iso.IFB_AMOUNT' />"
    ,"    <isofield id='29' length='9' name='AMOUNT, SETTLEMENT FEE' pad='true' class='org.jpos.iso.IFB_AMOUNT' />"
    ,"    <isofield id='30' length='9' name='AMOUNT, TRANSACTION PROCESSING FEE' pad='true' class='org.jpos.iso.IFB_AMOUNT' />"
    ,"    <isofield id='31' length='9' name='AMOUNT, SETTLEMENT PROCESSING FEE' pad='true' class='org.jpos.iso.IFB_AMOUNT' />"
    ,"    <isofield id='32' length='11' name='ACQUIRING INSTITUTION IDENT CODE' pad='false' class='org.jpos.iso.IFB_LLNUM' />"
    ,"    <isofield id='33' length='11' name='FORWARDING INSTITUTION IDENT CODE' pad='false' class='org.jpos.iso.IFB_LLNUM' />"
    ,"    <isofield id='34' length='28' name='PAN EXTENDED' class='org.jpos.iso.IFB_LLCHAR' />"
    ,"    <isofield id='35' length='37' name='TRACK 2 DATA' pad='false' class='org.jpos.iso.IFB_LLNUM' />"
    ,"    <isofield id='36' length='104' name='TRACK 3 DATA' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='37' length='12' name='RETRIEVAL REFERENCE NUMBER' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='38' length='6' name='AUTHORIZATION IDENTIFICATION RESPONSE' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='39' length='2' name='RESPONSE CODE' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='40' length='3' name='SERVICE RESTRICTION CODE' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='41' length='8' name='CARD ACCEPTOR TERMINAL IDENTIFICACION' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='42' length='15' name='CARD ACCEPTOR IDENTIFICATION CODE' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='43' length='40' name='CARD ACCEPTOR NAME/LOCATION' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='44' length='25' name='ADITIONAL RESPONSE DATA' class='org.jpos.iso.IFB_LLCHAR' />"
    ,"    <isofield id='45' length='76' name='TRACK 1 DATA' class='org.jpos.iso.IFB_LLCHAR' />"
    ,"    <isofield id='46' length='999' name='ADITIONAL DATA - ISO' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='47' length='999' name='ADITIONAL DATA - NATIONAL' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='48' length='999' name='ADITIONAL DATA - PRIVATE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='49' length='3' name='CURRENCY CODE, TRANSACTION' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='50' length='3' name='CURRENCY CODE, SETTLEMENT' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='51' length='3' name='CURRENCY CODE, CARDHOLDER BILLING' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='52' length='8' name='PIN DATA' class='org.jpos.iso.IFB_BINARY' />"
    ,"    <isofield id='53' length='48' name='SECURITY RELATED CONTROL INFORMATION' pad='true' class='org.jpos.iso.IFB_HEX' />"
    ,"    <isofield id='54' length='120' name='ADDITIONAL AMOUNTS' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='55' length='999' name='RESERVED ISO' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='56' length='999' name='RESERVED ISO' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='57' length='999' name='RESERVED NATIONAL' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='58' length='999' name='RESERVED NATIONAL' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='59' length='999' name='RESERVED NATIONAL' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='60' length='999' name='RESERVED PRIVATE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='61' length='999' name='RESERVED PRIVATE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='62' length='999' name='RESERVED PRIVATE' class='org.jpos.iso.IFB_LLLCHAR' />"
    
    ,"    <isofieldpackager id='63' length='9999' name='ADDITIONAL DATA' class='org.jpos.iso.IFB_LLLLBINARY' emitBitmap='true' bitmapField='0' packager='org.jpos.iso.packager.GenericSubFieldPackager'>"
    ,"        <isofield id='0' length='0' name='Bitmap Placeholder' class='org.jpos.iso.IF_NOP' />"
    ,"        <isofield id='1' length='4' name='Network Identification Code' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"        <isofield id='2' length='4' name='Time (Preauth Time Limit)' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"        <isofield id='3' length='4' name='Time (Preauth Time Limit)' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"        <isofield id='4' length='4' name='STIP/Switch Reason Code' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"        <isofield id='5' length='6' name='Batch number' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"        <isofield id='6' length='12' name='Tip Amount' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"        <isofield id='7' length='12' name='Cashback Amount' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"        <isofield id='8' length='12' name='Surchagre Amount' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"        <isofield id='9' length='255' name='Fraud Data' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"        <isofield id='10' length='8' pad='true' name='CustomerID/OdooID' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"        <isofield id='11' length='16' name='OrderID' class='org.jpos.iso.IFB_LLCHAR' />"
    ,"        <isofield id='12' length='12' name='National/CitizenID' class='org.jpos.iso.IFB_LLCHAR' />"
    ,"        <isofield id='13' length='14' pad='true' name='Payment Plan' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"        <isofield id='14' length='900' pad='true' name='Signature Data' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"        <isofield id='15' length='1' pad='true' name='N/A' class='org.jpos.iso.IFB_LLNUM' />"
    ,"        <isofield id='16' length='1' pad='true' name='N/A' class='org.jpos.iso.IFB_LLNUM' />"
    ,"        <isofield id='17' length='1' pad='true' name='N/A' class='org.jpos.iso.IFB_LLNUM' />"
    ,"        <isofield id='18' length='1' pad='true' name='N/A' class='org.jpos.iso.IFB_LLNUM' />"
    ,"        <isofield id='19' length='1' pad='true' name='N/A' class='org.jpos.iso.IFB_LLNUM' />"
    ,"        <isofield id='20' length='24' name='Serial Number' class='org.jpos.iso.IFB_LLCHAR' />"
    ,"        <isofield id='21' length='24' pad='true' name='ATOM Transaction ID' class='org.jpos.iso.IFB_HEX' />"
    ,"        <isofield id='22' length='48' pad='true' name='ATOM Authen Token' class='org.jpos.iso.IFB_HEX' />"
    ,"        <isofield id='23' length='48' pad='true' name='PIN Encryption Key' class='org.jpos.iso.IFB_HEX' />"
    ,"        <isofield id='24' length='48' pad='true' name='DUK/PT KEY SERIAL NUMBER' class='org.jpos.iso.IFB_HEX' />"
    ,"        <isofield id='25' length='48' pad='true' name='MAC Key' class='org.jpos.iso.IFB_HEX' />"
    ,"    </isofieldpackager>"
    ,"    <isofield id='64' length='8' name='MESSAGE AUTHENTICATION CODE FIELD' class='org.jpos.iso.IFB_BINARY' />"
    ,"    <isofield id='65' length='1' name='BITMAP, EXTENDED' class='org.jpos.iso.IFB_BINARY' />"
    ,"    <isofield id='66' length='1' name='SETTLEMENT CODE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='67' length='2' name='EXTENDED PAYMENT CODE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='68' length='3' name='RECEIVING INSTITUTION COUNTRY CODE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='69' length='3' name='SETTLEMENT INSTITUTION COUNTRY CODE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='70' length='3' name='NETWORK MANAGEMENT INFORMATION CODE' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='71' length='4' name='MESSAGE NUMBER' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='72' length='4' name='MESSAGE NUMBER LAST' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='73' length='6' name='DATE ACTION' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='74' length='10' name='CREDITS NUMBER' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='75' length='10' name='CREDITS REVERSAL NUMBER' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='76' length='10' name='DEBITS NUMBER' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='77' length='10' name='DEBITS REVERSAL NUMBER' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='78' length='10' name='TRANSFER NUMBER' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='79' length='10' name='TRANSFER REVERSAL NUMBER' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='80' length='10' name='INQUIRIES NUMBER' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='81' length='10' name='AUTHORIZATION NUMBER' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='82' length='12' name='CREDITS, PROCESSING FEE AMOUNT' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='83' length='12' name='CREDITS, TRANSACTION FEE AMOUNT' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='84' length='12' name='DEBITS, PROCESSING FEE AMOUNT' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='85' length='12' name='DEBITS, TRANSACTION FEE AMOUNT' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='86' length='16' name='CREDITS, AMOUNT' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='87' length='16' name='CREDITS, REVERSAL AMOUNT' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='88' length='16' name='DEBITS, AMOUNT' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='89' length='16' name='DEBITS, REVERSAL AMOUNT' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='90' length='42' name='ORIGINAL DATA ELEMENTS' pad='true' class='org.jpos.iso.IFB_NUMERIC' />"
    ,"    <isofield id='91' length='1' name='FILE UPDATE CODE' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='92' length='2' name='FILE SECURITY CODE' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='93' length='6' name='RESPONSE INDICATOR' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='94' length='7' name='SERVICE INDICATOR' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='95' length='42' name='REPLACEMENT AMOUNTS' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='96' length='16' name='MESSAGE SECURITY CODE' class='org.jpos.iso.IFB_BINARY' />"
    ,"    <isofield id='97' length='17' name='AMOUNT, NET SETTLEMENT' pad='false' class='org.jpos.iso.IFB_AMOUNT' />"
    ,"    <isofield id='98' length='25' name='PAYEE' class='org.jpos.iso.IF_CHAR' />"
    ,"    <isofield id='99' length='11' name='SETTLEMENT INSTITUTION IDENT CODE' pad='false' class='org.jpos.iso.IFB_LLNUM' />"
    ,"    <isofield id='100' length='11' name='RECEIVING INSTITUTION IDENT CODE' pad='false' class='org.jpos.iso.IFB_LLNUM' />"
    ,"    <isofield id='101' length='17' name='FILE NAME' class='org.jpos.iso.IFB_LLCHAR' />"
    ,"    <isofield id='102' length='28' name='ACCOUNT IDENTIFICATION 1' class='org.jpos.iso.IFB_LLCHAR' />"
    ,"    <isofield id='103' length='28' name='ACCOUNT IDENTIFICATION 2' class='org.jpos.iso.IFB_LLCHAR' />"
    ,"    <isofield id='104' length='100' name='TRANSACTION DESCRIPTION' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='105' length='999' name='RESERVED ISO USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='106' length='999' name='RESERVED ISO USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='107' length='999' name='RESERVED ISO USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='108' length='999' name='RESERVED ISO USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='109' length='999' name='RESERVED ISO USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='110' length='999' name='RESERVED ISO USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='111' length='999' name='RESERVED ISO USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='112' length='999' name='RESERVED NATIONAL USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='113' length='999' name='RESERVED NATIONAL USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='114' length='999' name='RESERVED NATIONAL USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='115' length='999' name='RESERVED NATIONAL USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='116' length='999' name='RESERVED NATIONAL USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='117' length='999' name='RESERVED NATIONAL USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='118' length='999' name='RESERVED NATIONAL USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='119' length='999' name='RESERVED NATIONAL USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='120' length='999' name='RESERVED PRIVATE USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='121' length='999' name='RESERVED PRIVATE USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='122' length='999' name='RESERVED PRIVATE USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='123' length='999' name='RESERVED PRIVATE USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='124' length='999' name='RESERVED PRIVATE USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='125' length='999' name='RESERVED PRIVATE USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='126' length='999' name='RESERVED PRIVATE USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='127' length='999' name='RESERVED PRIVATE USE' class='org.jpos.iso.IFB_LLLCHAR' />"
    ,"    <isofield id='128' length='8' name='MAC 2' class='org.jpos.iso.IFB_BINARY' />"
    ,"</isopackager>"
    );
    public SacomPackager() throws ISOException {
        super(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));
        
        // setFieldPackager(fld);
    }
}

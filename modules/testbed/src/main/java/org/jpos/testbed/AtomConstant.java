package org.jpos.testbed;

public enum AtomConstant {
    PROFILER, TIMESTAMP,
    SOURCE, REQUEST, RESPONSE,
    LOGEVT,
    DB, TX, 
    IRC,
    TXNNAME,QUEUE,
    RESULT,
    MID,
    TID,
    PCODE,
    CARD,
    TRANSMISSION_TIMESTAMP,
    TRANSACTION_TIMESTAMP,
    CAPTURE_DATE,
    POS_DATA_CODE,
    AMOUNT,
    LOCAL_AMOUNT,
    ORIGINAL_MTI,
    ORIGINAL_STAN,
    ORIGINAL_TIMESTAMP,
    ORIGINAL_DATA_ELEMENTS,
    DESTINATION,
    PANIC,
    PAUSED_TRANSACTION(":paused_transaction");

    private final String name;

    AtomConstant() {
        this.name = name();
    }
    AtomConstant(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

package org.atom.gateway.txnmgr;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jpos.iso.ISOMsg;

@Entity
@Table(name = "tbl_transaction")
public class Txn implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private ISOMsg requestMsg;
    private String reqMsgStr;
    private ISOMsg responseMsg;
    private String resMsgStr;

    // Getter , Setter
    public Integer getId() {
        return this.id;
    }

    public ISOMsg getRequest() {
        return this.requestMsg;
    }

    public ISOMsg getResponse() {
        return this.responseMsg;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setRequestMsg(ISOMsg message) {
        this.requestMsg = message;
    }

    public void setResponseMsg(ISOMsg message) {
        this.responseMsg = message;
    }

    public ISOMsg getRequestMsg() {
        return requestMsg;
    }

    public String getReqMsgStr() {
        return reqMsgStr;
    }

    public void setReqMsgStr(String reqMsgStr) {
        this.reqMsgStr = reqMsgStr;
    }

    public ISOMsg getResponseMsg() {
        return responseMsg;
    }

    public String getResMsgStr() {
        return resMsgStr;
    }

    public void setResMsgStr(String resMsgStr) {
        this.resMsgStr = resMsgStr;
    }
}

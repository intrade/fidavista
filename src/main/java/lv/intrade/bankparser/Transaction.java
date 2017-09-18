package lv.intrade.bankparser;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class Transaction {
    private String code;
    private String type;
    private Date regDate;
    private Date bookDate;
    private Date valueDate;
    private long bankRef;
    private String docNo;
    private String cord;
    private double accAmt;
    private String description;
    private Agent agent;
    private String trxCurrency;

    public Transaction() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public long getBankRef() {
        return bankRef;
    }

    public void setBankRef(long bankRef) {
        this.bankRef = bankRef;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getCord() {
        return cord;
    }

    public void setCord(String cord) {
        this.cord = cord;
    }

    public double getAccAmt() {
        return accAmt;
    }

    public void setAccAmt(double accAmt) {
        this.accAmt = accAmt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getTrxCurrency() {
        return trxCurrency;
    }

    public void setTrxCurrency(String trxCurrency) {
        this.trxCurrency = trxCurrency;
    }

    public String toString(){ return ToStringBuilder.reflectionToString(this); }
}

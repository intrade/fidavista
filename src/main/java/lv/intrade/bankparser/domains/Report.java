package lv.intrade.bankparser.domains;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report {
    private String account;
    private String currency;
    private AccountOwner owner;
    private AccountHolder holder;
    private Date reportStartDate;
    private Date reportEndDate;
    private List<Transaction> transactionList;
    private double openBalance;
    private double closeBalance;

    public Report() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public AccountOwner getOwner() {
        return owner;
    }

    public void setOwner(AccountOwner owner) {
        this.owner = owner;
    }

    public AccountHolder getHolder() {
        return holder;
    }

    public void setHolder(AccountHolder holder) {
        this.holder = holder;
    }

    public Date getReportStartDate() {
        return reportStartDate;
    }

    public void setReportStartDate(Date reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public Date getReportEndDate() {
        return reportEndDate;
    }

    public void setReportEndDate(Date reportEndDate) {
        this.reportEndDate = reportEndDate;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public double getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(double openBalance) {
        this.openBalance = openBalance;
    }

    public double getCloseBalance() {
        return closeBalance;
    }

    public void setCloseBalance(double closeBalance) {
        this.closeBalance = closeBalance;
    }

    protected void addTransaction(Transaction trx){
        if (this.transactionList == null) this.transactionList = new ArrayList<>();
        this.transactionList.add(trx);
    }

    public String toString(){ return ToStringBuilder.reflectionToString(this); }

}

package lv.intrade.bankparser;

import java.sql.Date;

public class ReportFactory {
    private static ReportFactory ourInstance = new ReportFactory();
    private Report report;
    private AccountHolder accountHolder;
    private AccountOwner accountOwner;

    public static ReportFactory getInstance() {
        return ourInstance;
    }

    private ReportFactory() {
    }

    public void setReport(Report r){ this.report = r; }
    public void createReport(){
        this.report = new Report();
    }

    public void setReportAccount(String reportAccount) {
        this.report.setAccount(reportAccount);
    }

    public void setReportCurrency(String reportCurrency) {
        this.report.setCurrency(reportCurrency);
    }

    public void setReportOpenBalance(String reportOpenBalance) {
        this.report.setOpenBalance(Double.parseDouble(reportOpenBalance));
    }

    public void setReportCloseBalance(String reportCloseBalance) {
        this.report.setCloseBalance(Double.parseDouble(reportCloseBalance));
    }

    public void setReportAccHolder(String reportAccHolderName, String reportAccHolderLID) {
        this.accountHolder = new AccountHolder(reportAccHolderName, reportAccHolderLID);
        this.report.setHolder(accountHolder);
    }

    public void setReportAccOwner(String name, String legalId) {
        this.accountOwner = new AccountOwner(name, legalId);
        this.report.setOwner(accountOwner);
    }

    public void setReportStartDate(Date reportStartDate) {
        this.report.setReportStartDate(reportStartDate);
    }

    public void setReportEndDate(Date reportEndDate) {
        this.report.setReportEndDate(reportEndDate);
    }

    public void setReportTrx(Transaction reportTrx) {
        this.report.addTransaction(reportTrx);
    }

    public Report getReport() {
        return report;
    }
}

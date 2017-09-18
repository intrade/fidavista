package lv.intrade.bankparser;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AccountHolder {
    private String bankName;
    private String legalId;

    public AccountHolder(String bankName, String legalId) {
        this.bankName = bankName;
        this.legalId = legalId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLegalId() {
        return legalId;
    }

    public void setLegalId(String legalId) {
        this.legalId = legalId;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }

}

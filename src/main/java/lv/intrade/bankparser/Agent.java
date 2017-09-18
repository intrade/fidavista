package lv.intrade.bankparser;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Agent {
    private String name;
    private String account;
    private String legalId;

    public Agent() {
    }

    public Agent(String name, String account, String legalId) {
        this.name = name;
        this.account = account;
        this.legalId = legalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

package lv.intrade.bankparser;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AccountOwner {
    private String name;
    private String legalId;

    public AccountOwner(String name, String legalId) {
        this.name = name;
        this.legalId = legalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

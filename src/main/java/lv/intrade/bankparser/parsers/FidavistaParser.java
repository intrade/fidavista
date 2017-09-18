package lv.intrade.bankparser.parsers;

import lv.intrade.bankparser.domains.Agent;
import lv.intrade.bankparser.domains.Report;
import lv.intrade.bankparser.domains.ReportBuilder;
import lv.intrade.bankparser.domains.Transaction;
import lv.intrade.bankparser.exceptions.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FidaVista file parser
 * Available for version 1.01
 */

public class FidavistaParser implements BankParser {
    private File file;
    private ReportBuilder builder;
    private Document document;
    private NodeList nodeList;
    private Node node;
    private Element element;
    private final String TIME_FINDER_PATTERN = "\\s\\d+:\\d+";

    @Override
    public Report getReport(File file) {
        this.file = file;
        builder = ReportBuilder.getInstance();
        builder.createReport();
        try {
            parseFile();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return builder.getReport();
    }

    private void parseFile() throws ParserException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new ParserException("Error in Fidavista Parser class." + e.getMessage());
            //e.printStackTrace();
        }
        try {
            document = builder.parse(file);
        } catch (SAXException e) {
            throw new ParserException("Error in Fidavista Parser class." + e.getMessage());
        } catch (IOException e) {
            throw new ParserException("Error in Fidavista Parser class." + e.getMessage());
        }
        nodeList = document.getElementsByTagName("Statement");
        node = nodeList.item(0);
        element = (Element) node;

        setReportBaseFields(document);

        setReportPeriod(document);

        setAccountHolder(document);

        setAccountOwner(document);

        collectTransactions(document);
    }

    private void collectTransactions(Document document) {
        NodeList nList = document.getElementsByTagName("TrxSet");
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Transaction trx = new Transaction();
                Element eElement = (Element) node;
                trx.setCode(getElementContent(eElement,"TypeCode"));
                trx.setType(getElementContent(eElement,"TypeName"));
                trx.setRegDate(
                        Date.valueOf(
                                (
                                        getElementContent(eElement,"RegDate").isEmpty() ?
                                                getElementContent(eElement,"BookDate") :
                                                getElementContent(eElement,"RegDate")
                                )
                        )
                );
                trx.setBookDate(Date.valueOf(getElementContent(eElement,"BookDate")));
                String fullDate;
                Pattern p = Pattern.compile(TIME_FINDER_PATTERN);
                Matcher m = p.matcher(getElementContent(eElement,"ValueDate"));
                if (m.find()){
                    fullDate = getElementContent(eElement,"ValueDate").substring(0,10);
                } else {
                    fullDate = getElementContent(eElement,"ValueDate");
                }
                trx.setValueDate(Date.valueOf(fullDate));
                trx.setBankRef(Long.parseLong(getElementContent(eElement,"BankRef")));
                trx.setDocNo(getElementContent(eElement,"DocNo"));
                trx.setCord(getElementContent(eElement,"CorD"));
                trx.setAccAmt(Double.parseDouble(getElementContent(eElement, "AccAmt")));
                trx.setDescription(getElementContent(eElement,"PmtInfo"));
                Agent agent = new Agent();
                agent.setAccount(getElementContent(eElement,"AccNo"));
                agent.setName(getElementContent(eElement,"Name"));
                agent.setLegalId(getElementContent(eElement,"LegalId"));
                trx.setAgent(agent);
                trx.setTrxCurrency(getElementContent(eElement,"Ccy"));
                builder.appendTransactionToList(trx);
            }
        }
    }

    private String getElementContent(Element element, String tagName) {
        Node node = element.getElementsByTagName(tagName).item(0);
        return Objects.nonNull(node) ? node.getTextContent() : "";
    }

    private void setAccountOwner(Document document) {
        nodeList = document.getElementsByTagName("ClientSet");
        node = nodeList.item(0);
        element = (Element) node;
        builder.setReportAccOwner(
                getElementContent(element,"Name"),
                getElementContent(element,"LegalId")
        );
    }

    private void setAccountHolder(Document document) {
        nodeList = document.getElementsByTagName("BankSet");
        node = nodeList.item(0);
        element = (Element) node;
        builder.setReportAccHolder(
                getElementContent(element,"Name"),
                getElementContent(element,"LegalId")
        );
    }

    private void setReportPeriod(Document document) {
        nodeList = document.getElementsByTagName("Period");
        node = nodeList.item(0);
        element = (Element) node;
        builder.setReportStartDate(Date.valueOf(getElementContent(element,"StartDate")));
        builder.setReportEndDate(Date.valueOf(getElementContent(element,"EndDate")));
    }

    private void setReportBaseFields(Document document) {
        nodeList = document.getElementsByTagName("AccountSet");
        node = nodeList.item(0);
        element = (Element) node;
        builder.setReportAccount(getElementContent(element,"AccNo"));
        builder.setReportCurrency(getElementContent(element,"Ccy"));
        builder.setReportOpenBalance(getElementContent(element,"OpenBal"));
        builder.setReportCloseBalance(getElementContent(element,"CloseBal"));
    }

}

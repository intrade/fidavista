package lv.intrade.bankparser.parsers;

import lv.intrade.bankparser.Agent;
import lv.intrade.bankparser.Report;
import lv.intrade.bankparser.ReportFactory;
import lv.intrade.bankparser.Transaction;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FidaVista file parser
 * Available for version 1.01
 */

public class FidavistaParser implements BankParser {
    private File file;
    private ReportFactory factory;
    private Document document;
    private NodeList nodeList;
    private Node rNode;
    private Element aElement;

    @Override
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public Report getReport() throws IOException, SAXException, ParserConfigurationException {
        factory = ReportFactory.getInstance();
        factory.createReport();
        parseFile();
        return factory.getReport();
    }

    private void parseFile() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        document = builder.parse(file);
        nodeList = document.getElementsByTagName("Statement");
        rNode = nodeList.item(0);
        aElement = (Element) rNode;

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
                trx.setCode(eElement.getElementsByTagName("TypeCode").item(0).getTextContent());
                trx.setType(eElement.getElementsByTagName("TypeName").item(0).getTextContent());
                trx.setRegDate(
                        Date.valueOf(
                                (
                                        eElement.getElementsByTagName("RegDate").item(0).getTextContent().isEmpty() ?
                                                eElement.getElementsByTagName("BookDate").item(0).getTextContent() :
                                                eElement.getElementsByTagName("RegDate").item(0).getTextContent()
                                )
                        )
                );
                trx.setBookDate(Date.valueOf(eElement.getElementsByTagName("BookDate").item(0).getTextContent()));
                String fullDate;
                Pattern p = Pattern.compile("\\s\\d+:\\d+");
                Matcher m = p.matcher(eElement.getElementsByTagName("ValueDate").item(0).getTextContent());
                if (m.find()){
                    fullDate = eElement.getElementsByTagName("ValueDate").item(0).getTextContent().substring(0,10);
                } else {
                    fullDate = eElement.getElementsByTagName("ValueDate").item(0).getTextContent();
                }
                trx.setValueDate(Date.valueOf(fullDate));
                trx.setBankRef(Long.parseLong(eElement.getElementsByTagName("BankRef").item(0).getTextContent()));
                trx.setDocNo(
                        (
                                eElement.getElementsByTagName("DocNo").getLength()>0 ?
                                        eElement.getElementsByTagName("DocNo").item(0).getTextContent() :
                                        null
                        )
                );
                trx.setCord(eElement.getElementsByTagName("CorD").item(0).getTextContent());
                trx.setAccAmt(Double.parseDouble(eElement.getElementsByTagName("AccAmt").item(0).getTextContent()));
                trx.setDescription(eElement.getElementsByTagName("PmtInfo").item(0).getTextContent());
                Agent agent = new Agent();
                agent.setAccount(eElement.getElementsByTagName("AccNo").item(0).getTextContent());
                agent.setName((eElement.getElementsByTagName("Name").getLength()>0 ? eElement.getElementsByTagName("Name").item(0).getTextContent() : null));
                agent.setLegalId(
                        (
                                eElement.getElementsByTagName("LegalId").getLength()>0 ?
                                        eElement.getElementsByTagName("LegalId").item(0).getTextContent() :
                                        null
                        )
                );
                trx.setAgent(agent);
                trx.setTrxCurrency(eElement.getElementsByTagName("Ccy").item(0).getTextContent());
                factory.setReportTrx(trx);
            }
        }
    }

    private void setAccountOwner(Document document) {
        nodeList = document.getElementsByTagName("ClientSet");
        rNode = nodeList.item(0);
        aElement = (Element) rNode;
        factory.setReportAccOwner(
                aElement.getElementsByTagName("Name").item(0).getTextContent(),
                aElement.getElementsByTagName("LegalId").item(0).getTextContent()
        );
    }

    private void setAccountHolder(Document document) {
        nodeList = document.getElementsByTagName("BankSet");
        rNode = nodeList.item(0);
        aElement = (Element) rNode;
        factory.setReportAccHolder(
                aElement.getElementsByTagName("Name").item(0).getTextContent(),
                aElement.getElementsByTagName("LegalId").item(0).getTextContent()
        );
    }

    private void setReportPeriod(Document document) {
        nodeList = document.getElementsByTagName("Period");
        rNode = nodeList.item(0);
        aElement = (Element) rNode;
        factory.setReportStartDate(Date.valueOf(aElement.getElementsByTagName("StartDate").item(0).getTextContent()));
        factory.setReportEndDate(Date.valueOf(aElement.getElementsByTagName("EndDate").item(0).getTextContent()));
    }

    private void setReportBaseFields(Document document) {
        nodeList = document.getElementsByTagName("AccountSet");
        rNode = nodeList.item(0);
        aElement = (Element) rNode;
        factory.setReportAccount(aElement.getElementsByTagName("AccNo").item(0).getTextContent());
        factory.setReportCurrency(aElement.getElementsByTagName("Ccy").item(0).getTextContent());
        factory.setReportOpenBalance(aElement.getElementsByTagName("OpenBal").item(0).getTextContent());
        factory.setReportCloseBalance(aElement.getElementsByTagName("CloseBal").item(0).getTextContent());
    }

}

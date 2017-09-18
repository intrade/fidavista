package lv.intrade.bankparser;

import lv.intrade.bankparser.parsers.BankParser;
import lv.intrade.bankparser.parsers.FidavistaParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Main {
    private static File xmlFile = new File("example.xml");

    public static void main(String[] args) {
            BankParser parser = new FidavistaParser();
            parser.setFile(xmlFile);
            try {
                Report report = parser.getReport();
                System.out.println(report);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
    }
}

package lv.intrade.bankparser.parsers;

import lv.intrade.bankparser.domains.Report;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public interface BankParser {
    Report getReport(File file) throws IOException, SAXException, ParserConfigurationException;
}

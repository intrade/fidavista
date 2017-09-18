package lv.intrade.bankparser.parsers;

import lv.intrade.bankparser.Report;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public interface BankParser {
    void setFile(File file);
    Report getReport() throws IOException, SAXException, ParserConfigurationException;
}

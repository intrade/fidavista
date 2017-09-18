package lv.intrade.bankparser.parsers;

import lv.intrade.bankparser.Report;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public interface BankParser {
    // В чем смысл создавать отдельно метод? Файл можно напрямую подать в метод getReport()
    void setFile(File file);

    // Хорошей практикой считается кидать одно глобальное исключение, которое будет содержать
    // в себе конкретну причину, т.е. будет BankParserException, а в нем уже конктреное
    // исключение IOException...
    Report getReport() throws IOException, SAXException, ParserConfigurationException;
}

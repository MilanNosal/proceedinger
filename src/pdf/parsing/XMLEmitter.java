package pdf.parsing;

import jlibs.xml.Namespaces;
import jlibs.xml.sax.XMLDocument;
import org.xml.sax.SAXException;
import pdf.Constants;
import pdf.model.*;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by Milan on 15.5.2014.
 */
public class XMLEmitter {
    private static final String URI_PAPERS = "http://kpi.fei.tuke.sk/scyr/nosal/papers";

    public void emitXML(List<Item> submissions, OutputStreamWriter outputStream) throws TransformerConfigurationException, SAXException {
        XMLDocument xml = new XMLDocument(new StreamResult(outputStream), false, 4, Constants.encoding);

        xml.startDocument();
        xml.startElement("papers");
        xml.addAttribute("xmlns", URI_PAPERS);
        xml.addAttribute(Namespaces.URI_XSI, "schemaLocation", URI_PAPERS + " papers.xsd");

        for (Item item : submissions) {
            if (item instanceof Paper) {
                emitPaper(xml, (Paper) item);
            } else if (item instanceof Section) {
                emitSection(xml, (Section) item);
            }
        }
        xml.endElement();
        xml.endDocument();
    }

    private void emitSection(XMLDocument xml, Section section) throws SAXException {
        xml.startElement("section")
                .addAttribute("id", section.getSection().toString());
        if(!section.getPresentation().equals(PresentationEnum.NONE)) {
            xml.addAttribute("form", section.getPresentation().toString());
        }

        if(section.getSection().hasChangedPriority()) {
            xml.addAttribute("priority", Integer.toString(section.getSection().getPriority()));
        }
        if(section.getPresentation().hasChangedPriority()) {
            xml.addAttribute("presentationPriority", Integer.toString(section.getPresentation().getPriority()));
        }

        if(section.getSection().hasChangedName()) {
            xml.addElement("fullTitle", section.getSection().getName());
        }
        if(section.getPresentation().hasChangedName()) {
            xml.addElement("fullForm", section.getPresentation().getName());
        }
        xml.endElement();
    }

    private void emitPaper(XMLDocument xml, Paper paper) throws SAXException {
        xml.startElement("paper")
           .addAttribute("filename", paper.getFilename());
        if(paper.getPages()!=null) {
            xml.addAttribute("pages", paper.getPages().toString());
        }
        {
            xml.startElement("authors");
            for(String author : paper.getAuthorsRev()) {
                xml.startElement("author")
                        .addElement("firstName", author.substring(author.lastIndexOf(' ')).trim())
                        .addElement("surname", author.substring(0, author.lastIndexOf(' ')).trim())
                        .endElement();

            }
            xml.endElement();

            xml.addElement("title", paper.getTitle());
            xml.addElement("presentationForm", paper.getPresentation().toString());
            xml.addElement("section", paper.getSection().toString());
            xml.addElement("department", paper.getDepartment());
        }

        xml.endElement();
    }

//    xml.startElement("company");{
//        xml.addAttribute("name", company.name);
//        for(Employee emp: company.employees){
//            xml.startElement("employee");{
//                xml.addAttribute("id", emp.id);
//                xml.addAttribute("age", ""+emp.age);
//                xml.addElement("name", emp.name);
//                xml.addElement("email", emp.email);
//            }
//            xml.endElement("employee");
//        }
//    }
//    xml.endElement("company");
}

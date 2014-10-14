package pdf.parsing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import pdf.Constants;
import pdf.model.Item;
import pdf.model.Paper;
import pdf.model.Section;
import pdf.model.PresentationEnum;
import pdf.model.SectionEnum;

public class XMLStaxParser {

    private XMLStreamReader streamReader;

    public List<Item> parseXML(Reader xmlDocument) throws ParsingException, IOException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();

        List<Item> itemsRead = new ArrayList<>();

        streamReader = factory.createXMLStreamReader(xmlDocument);

        while (streamReader.hasNext()) {
            streamReader.next();
            if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                switch (streamReader.getLocalName()) {
                    case "paper": {
                        itemsRead.add(readPaper());
                        break;
                    }
                    case "section": {
                        itemsRead.add(readSection());
                        break;
                    }
                    default: {
                        System.out.println(">>> Read: " + streamReader.getLocalName());
//                            throw new ParsingException
//                                    ("An element that should not be here accured in the document ("+streamReader.getLocalName()+")");
                    }
                }
            }
        }

        return itemsRead;
    }

    private Section readSection() throws XMLStreamException {
        SectionEnum sectionEnum = SectionEnum.valueOfString(streamReader.getAttributeValue(null, "id"));

        String presentationForm = streamReader.getAttributeValue(null, "form");
        PresentationEnum presentationEnum = presentationForm != null ? PresentationEnum.valueOf(presentationForm) : PresentationEnum.NONE;

        String priority = streamReader.getAttributeValue(null, "priority");
        if (priority != null) {
            sectionEnum.setPriority(Integer.parseInt(priority));
        }

        String presentationPriority = streamReader.getAttributeValue(null, "presentationPriority");
        if (presentationPriority != null) {
            presentationEnum.setPriority(Integer.parseInt(presentationPriority));
        }

        streamReader.next();

        while (streamReader.getEventType() != XMLStreamReader.START_ELEMENT
                && !(streamReader.getEventType() == XMLStreamReader.END_ELEMENT && streamReader.getLocalName().equals("section"))) {
            streamReader.next();
        }
        if (streamReader.getLocalName().equals("fullTitle")) {
            sectionEnum.setName(streamReader.getElementText().trim());
            while (streamReader.getEventType() != XMLStreamReader.START_ELEMENT
                    && !(streamReader.getEventType() == XMLStreamReader.END_ELEMENT && streamReader.getLocalName().equals("section"))) {
                streamReader.next();
            }
        }
        if (presentationEnum != PresentationEnum.NONE && streamReader.getLocalName().equals("fullForm")) {
            presentationEnum.setName(streamReader.getElementText().trim());
        }

        return new Section(sectionEnum, presentationEnum);
    }

    private Paper readPaper() throws XMLStreamException {
        String temp = streamReader.getAttributeValue(null, "pages");
        Integer pages = temp == null ? null : Integer.parseInt(temp);

        String path = streamReader.getAttributeValue(null, "filename");

        List<String> authors = new ArrayList<>();
        String name;
        // move on authors
        while (!(streamReader.getEventType() == XMLStreamReader.START_ELEMENT
                && streamReader.getLocalName().equals("authors"))) {
            streamReader.next();
        }
        // move on author
        while (!(streamReader.getEventType() == XMLStreamReader.START_ELEMENT
                && streamReader.getLocalName().equals("author"))) {
            streamReader.next();
        }
        while (streamReader.getEventType() == XMLStreamReader.START_ELEMENT && streamReader.getLocalName().equals("author")) {
            while (!(streamReader.getEventType() == XMLStreamReader.START_ELEMENT
                    && streamReader.getLocalName().equals("firstName"))) {
                streamReader.next();
            }
            name = streamReader.getElementText().trim();

            while (!(streamReader.getEventType() == XMLStreamReader.START_ELEMENT
                    && (streamReader.getLocalName().equals("middleName") || streamReader.getLocalName().equals("surname")))) {
                streamReader.next();
            }

            if (streamReader.getLocalName().equals("middleName")) {
                name += " " + streamReader.getElementText().trim();
                while (!(streamReader.getEventType() == XMLStreamReader.START_ELEMENT
                        && streamReader.getLocalName().equals("surname"))) {
                    streamReader.next();
                }
            }
            name += " " + streamReader.getElementText().trim();
            authors.add(name.trim());

            while (streamReader.getEventType() != XMLStreamReader.START_ELEMENT) {
                streamReader.next();
            }
        }

        while (!(streamReader.getEventType() == XMLStreamReader.START_ELEMENT
                && streamReader.getLocalName().equals("title"))) {
            streamReader.next();
        }

        String title = streamReader.getElementText().trim();

        while (!(streamReader.getEventType() == XMLStreamReader.START_ELEMENT
                && streamReader.getLocalName().equals("presentationForm"))) {
            streamReader.next();
        }
        PresentationEnum presentationEnum = PresentationEnum.valueOf(streamReader.getElementText().trim());

        while (!(streamReader.getEventType() == XMLStreamReader.START_ELEMENT
                && streamReader.getLocalName().equals("section"))) {
            streamReader.next();
        }
        SectionEnum sectionEnum = SectionEnum.valueOfString(streamReader.getElementText().trim());

        while (!(streamReader.getEventType() == XMLStreamReader.START_ELEMENT
                && streamReader.getLocalName().equals("department"))) {
            streamReader.next();
        }
        String department = streamReader.getElementText().trim();

        // I should have everything I need
        return new Paper(authors, title, pages, presentationEnum, sectionEnum, department, path);
    }
}

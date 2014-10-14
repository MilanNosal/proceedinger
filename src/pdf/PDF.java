package pdf;

import java.io.*;
import java.util.Collections;
import java.util.List;

import org.xml.sax.SAXException;
import pdf.latexgen.LatexCreator;
import pdf.latexgen.LatexParser;
import pdf.model.Author;
import pdf.model.Item;
import pdf.papers.AuthorsPopulation;
import pdf.papers.PageNumbersPrepare;
import pdf.papers.PaperSorter;
import pdf.parsing.*;
import pdf.comparators.AuthorsComparator;
import pdf.comparators.ItemComparator;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;

public class PDF {

    public static void main(String[] args) throws IOException, ParsingException, XMLStreamException, TransformerConfigurationException, SAXException {
        System.out.println(">> Let's go for it, Luke!");
        // inicializacia konfiguracie
        Constants.initialize(args);
        mainLatexGenerator();
        System.out.println(">> Well done my young padawan. Well done. May the force be with you!");
    }

    public static void mainLatexGenerator() throws IOException, ParsingException, XMLStreamException, TransformerConfigurationException, SAXException {

        try (CharArrayWriter characterWriter = new CharArrayWriter();
             BufferedWriter inmemoryBuffer = new BufferedWriter(characterWriter);
             Reader reader = new InputStreamReader(new FileInputStream(Constants.inputXML), Constants.encoding);
             FileOutputStream latexOutputWriter = new FileOutputStream(Constants.outputTex);
             BufferedWriter bufferedOutput = new BufferedWriter(new OutputStreamWriter(latexOutputWriter, Constants.encoding));) {

            // precita vstup nakonfigurovany cez parametre

            List<Item> papers = (new XMLStaxParser()).parseXML(reader);

            // zoradi ak je to pozadovane
            if (Constants.sort) {
                Collections.sort(papers, new ItemComparator());
            }

            new XMLEmitter()
                    .emitXML(
                            papers,
                            new OutputStreamWriter(
                                    new FileOutputStream(Constants.outputXML),
                                    Constants.encoding));

            new PaperSorter().preparePapers(papers);

            new PageNumbersPrepare().prepareNumbers(papers);

            List<Author> authors = new AuthorsPopulation().process(papers);
            // zoradit podla abecedy
            Collections.sort(authors, new AuthorsComparator());

            new LatexCreator().prepareLatex(papers, inmemoryBuffer, authors);

            // preraba diakritiku do latexovskych znaciek
            new LatexParser().parseLatex(bufferedOutput, characterWriter.toCharArray());
        }
    }
}

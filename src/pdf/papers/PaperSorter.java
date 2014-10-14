package pdf.papers;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.util.*;

import pdf.Constants;
import pdf.model.Item;

/**
 * Usporiada zbornik, najprv Titulnu stranu, potom intro, obsah, clanky, indexLength a tiraz.
 * Cele to spoji do jedneho dokumentu. Obsah a indexLength su vyplnene prazdnymi stranami.
 */
public class PaperSorter {

    public void preparePapers(List<Item> papers) throws IOException {
        Rectangle pageSize;

        List<PdfReader> pdfReaders = new LinkedList<>();
        OutputStream outputStream = new FileOutputStream(Constants.combinedOutputPdf);
        File blank = new File(Constants.pathToBlank);
        pageSize = (new PdfReader(new FileInputStream(blank))).getPageSize(1);

        int index = 0;
        PdfReader reader;

        File source = new File(Constants.titlePdf);

        PdfReader pdfTempReader = new PdfReader(new FileInputStream(source));
        Constants.titleLength = pdfTempReader.getNumberOfPages();
        pdfReaders.add(pdfTempReader);

        source = new File(Constants.introductionPdf);

        pdfTempReader = new PdfReader(new FileInputStream(source));
        Constants.introductionLength = pdfTempReader.getNumberOfPages();
        pdfReaders.add(pdfTempReader);

        int prefix = index + Constants.contentsLength;
        for (; index < prefix; index++) {
            pdfReaders.add(new PdfReader(new FileInputStream(blank)));
        }

        for (; index < papers.size() + prefix; index++) {
            source = new File(papers.get(index - prefix).getPDF());

            reader = new PdfReader(new FileInputStream(source));
            if (papers.get(index - prefix).getPages() == null) {
                papers.get(index - prefix).setPages(reader.getNumberOfPages());
            }
            if (reader.getNumberOfPages() != papers.get(index - prefix).getPages()) {
                System.err.println("Inconsistent number of pages in \n" + papers.get(index - prefix));
            }
            pdfReaders.add(reader);
        }

        prefix = index + Constants.indexLength;
        for (; index < prefix; index++) {
            pdfReaders.add(new PdfReader(new FileInputStream(blank)));
        }

        source = new File(Constants.endingPdf);
        pdfTempReader = new PdfReader(new FileInputStream(source));
        Constants.endingLength = pdfTempReader.getNumberOfPages();
        pdfReaders.add(pdfTempReader);

        concatPDFs(pdfReaders, pageSize, outputStream);
    }

    public static void concatPDFs(List<PdfReader> readers, Rectangle pageSize, OutputStream outputStream) {
        Document document = new Document();
        document.setPageSize(pageSize);
        try {
            // Create a writer for the outputstream
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            document.open();

            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF data

            PdfImportedPage page;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();

            // Loop through the PDF files and add to the outputTex.
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();

                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);
                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}

package pdf.latexgen;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import pdf.Constants;
import pdf.model.Author;
import pdf.model.Item;
import pdf.model.Section;

/**
 * Vygeneruje latex, ktory ma obsah a indexLength, zvysok su prazdne strany. Ten je potom prekryty pomocou pdftk
 * s tym pdfkom, co sa spajalo v
 */
public class LatexCreator {
    public void prepareLatex(List<Item> papers, BufferedWriter bw, List<Author> authors ) throws IOException {
        bw.write("\\documentclass[a4paper,12pt]{article}\n\n\\usepackage{amsmath}\n\\usepackage{hyperref}\n\\usepackage{authorindex}\n"
                + "\\usepackage{multicol}\n\\usepackage[top=1.9cm, bottom=1.8cm, left=1.44cm, right=1.44cm]{geometry}\n"
                + "\\usepackage{lmodern}\n\n\\usepackage[IL2]{fontenc}\n\\usepackage[slovak]{babel}\n\\usepackage[utf8x]{inputenc}\n\n"
                + "\n\\usepackage{fancyhdr}\n\\fancyhead[L]{\\fontsize{8}{10} \\selectfont " + Constants.proceedingsHeader +
                "}\n\n\\begin{document}\n\n\n\\pagestyle{empty}\n");

        
        for(int i = 0; i < Constants.titleLength; i++) {
            bw.write("\\newpage\\mbox{}\n");
        }
        
        for(int i = 0; i < Constants.introductionLength; i++) {
            bw.write("\\newpage\\mbox{}\n");
            if(i<1)
                bw.write("\\setcounter{page}{1}\n");
        }
        
        bw.write("\n\\newpage\n\\pagestyle{plain}");
        
        int offset = Constants.introductionLength + Constants.titleLength + Constants.contentsLength; // prefix
        
        bw.write("\n\\noindent\\LARGE\\textbf{Contents}\\normalsize\n\\vspace{1cm}\n\n\\noindent");

        boolean first = true;
        for(Item p : papers) {
            if(first) {
                first = false;
            } else if(p instanceof Section){
                bw.write("\n\\newpage\n\\noindent");
            }
            bw.write(p.toTOC());
            offset += p.getPages();
        }
        bw.write("\\\\\n\\textbf{Author's Index} \\dotfill~\\hyperlink{page."+offset+"}{"+offset+"}");
        bw.flush();
        bw.write("\n\n");
        for(Item p : papers) {
            bw.write(p.toLatex());
        }
        bw.flush();
        bw.write("\\newpage\\noindent\\LARGE\\textbf{Author's index}\\footnotesize\\vspace{.4cm}\n\n\\begin{multicols}{3}\n\\begin{theauthorindex}\n\\setlength{\\columnsep}{115pt}\n");
        
        for(Author a : authors) {
            bw.write(a.toIndex());
        }
        bw.write("\n\\end{theauthorindex}\n\\end{multicols}\n");
        
        
        for(int i = 0; i < Constants.endingLength; i++) {
            bw.write("\n\\newpage"+(i>0?"":"\n\\pagestyle{empty}")+"\\mbox{}");
        }
        
        bw.write("\n\\end{document}\n");
        bw.flush();
    }
}

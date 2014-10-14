package pdf.model;

import java.util.List;

import pdf.Constants;

public class Paper extends Item {

    private String[] authors;
    private String[] authorsRev;
    private String title;
    private Integer pages;
    private PresentationEnum presentation;
    private SectionEnum section;
    private String department;
    private String filename;

    public Paper(List<String> authors, String title, Integer pages, PresentationEnum presentation, SectionEnum section, String department, String filename) {
        this.authors = authors.toArray(new String[]{});
        this.title = title;
        this.pages = pages;
        this.presentation = presentation;
        this.section = section;
        authorsRev = new String[authors.size()];
        for (int i = 0; i < authors.size(); i++) {
            authorsRev[i] = reverseAuthor(this.authors[i]);
            this.authors[i] = this.authors[i].trim();
        }
        this.department = department;
        this.filename = filename;
    }

    private String reverseAuthor(String author) {
        String temp = (author.substring(author.lastIndexOf(' ')).trim());
        temp = temp + " " + (author.substring(0, author.lastIndexOf(' ')).trim());
        return temp;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public SectionEnum getSection() {
        return section;
    }

    public String[] getAuthors() {
        return authors;
    }

    @Override
    public String[] getAuthorsRev() {
        return authorsRev;
    }

    @Override
    public PresentationEnum getPresentation() {
        return presentation;
    }

    @Override
    public Integer getPages() {
        return pages;
    }

    @Override
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String toLatex() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (int i = 0; i < pages; i++) {
            sb.append("\\newpage\n");
            if (first) {
                first = false;
            } else {
                sb.append("\\thispagestyle{fancy}\n");
            }
            sb.append("\\mbox{}\n");
        }
        return sb.toString();
    }

    @Override
    public String toTOC() {
        StringBuilder sb = new StringBuilder("\\textbf{");
        boolean first = true;
        for (String author : authors) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(author);
        }
        sb.append("}\\\\\\emph{").append(this.title).append("} \\dotfill~\\hyperlink{page.").append(this.getPageNum()).append("}{").append(this.getPageNum()).append("}\\vspace{.3cm}\\\\\n");
        return sb.toString();
    }

    @Override
    public String getPDF() {
        return Constants.papersFolder + "/" + this.filename;
    }

    public String getFilename() {
        return this.filename;
    }

    @Override
    public String department() {
        return this.department;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.section.toString());
        sb.append("\n");
        for (int i = 0; i < authors.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(authors[i]);
        }
        sb.append("\n").append(title).append("\n").append(department).append("\n")
                .append(pages).append("\n").append(presentation.toString()).append("\n\n");
        return sb.toString();
    }
}

package pdf.model;

public class AuthorHeader extends Author {
    public AuthorHeader(String author) {
        super(author, null);
    }
    
    @Override
    public String toIndex() {
        StringBuilder sb = new StringBuilder("\n\\vspace{.2cm}\\normalsize\\textbf{");
        sb.append(author).append("}\\footnotesize\n\n");
        return sb.toString();
    }
}

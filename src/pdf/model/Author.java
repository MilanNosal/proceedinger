package pdf.model;

import java.util.List;

public class Author {
    List<Integer> pages;
    
    String author;
    
    public Author(String author, List<Integer> pages) {
        this.author = author;
        this.pages = pages;
    }
    
    public final void addPage(int page) {
        pages.add(page);
    }

    public String getAuthor() {
        return author;
    }

    public List<Integer> getPages() {
        return pages;
    }
    
    @Override
    public String toString() {
        String s = author;
        for(int i : pages)
            s+=" "+i;
        return s;
    }
    
    public String toIndex() {
        StringBuilder sb = new StringBuilder("\\item[");
        sb.append(author).append("] \\aipages{");
        boolean first = true;
        for(int page : pages) {
            if(first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append("\\hyperlink{page.").append(page).append("}{").append(page).append("}");
        }
        sb.append("}\n");
        return sb.toString();
    }
}

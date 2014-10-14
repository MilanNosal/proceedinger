package pdf.comparators;

import java.util.Comparator;
import pdf.model.Author;

public class AuthorsComparator implements Comparator<Author> {

    UTFStringComparator utf = new UTFStringComparator();
    
    @Override
    public int compare(Author o1, Author o2) {
        return utf.compare(o1.getAuthor(), o2.getAuthor());
    }
    
}

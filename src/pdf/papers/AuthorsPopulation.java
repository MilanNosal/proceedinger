package pdf.papers;

import java.util.*;
import pdf.model.Author;
import pdf.model.AuthorHeader;
import pdf.model.Item;
import pdf.model.Paper;

/**
 * Prejde zoznam prispevkov a vytvori z neho zoznam autorov aj s cislami stran kde maju
 * prispevky. Prispevky musia predtym prejst ocislovanim.
 */
public class AuthorsPopulation {
    
    HashMap<String, Set<Integer>> map = new LinkedHashMap<>();
    
    public List<Author> process(List<Item> sortedItems) {
        String initials;
        for (Item item : sortedItems) {
            if (item instanceof Paper) {
                Paper paper = (Paper) item;
                for (String author : paper.getAuthorsRev()) {
                    if (!map.containsKey(author)) {
                        map.put(author, new HashSet());
                    }
                    map.get(author).add(paper.getPageNum());
                    if (author.length() > 1) {
                        initials = author.substring(0, 2);
                        if(!"ch".equalsIgnoreCase(initials)) {
                            initials = author.substring(0, 1);
                        }
                        if (!map.containsKey(initials)) {
                            map.put(initials, null);
                        }
                    }
                }
            }
        }
        List<Author> authors = new LinkedList<>();
        for (String a : map.keySet()) {
            if (map.get(a) == null) {
                authors.add(new AuthorHeader(a));
            } else {
                List<Integer> pages = new ArrayList<>(map.get(a));
                Collections.sort(pages);
                authors.add(new Author(a, pages));
            }
        }
        return authors;
    }
}

package pdf.comparators;

import java.util.Comparator;
import pdf.model.Item;

public class ItemComparator implements Comparator<Item> {

    private UTFStringComparator utf = new UTFStringComparator();

    @Override
    public int compare(Item o1, Item o2) {
        // najprv podla sekcie
        if ((o1.getSection().getPriority() - o2.getSection().getPriority()) != 0) {
            return o1.getSection().getPriority() - o2.getSection().getPriority();
        }
        // potom ci je oral alebo nie
        if ((o1.getPresentation().getPriority() - o2.getPresentation().getPriority()) != 0) {
            return o1.getPresentation().getPriority() - o2.getPresentation().getPriority();
        }
        // A napokon podla nazvu
        return utf.compare(o1.getTitle(), o2.getTitle());
    }
}

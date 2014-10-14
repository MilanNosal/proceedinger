package pdf.comparators;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import pdf.model.Item;
import pdf.model.Paper;
import pdf.model.Section;

public class DepartmentComparator implements Comparator<Item> {
    
    private Set<String> departments = new HashSet<>();

    private UTFStringComparator utf = new UTFStringComparator();

    @Override
    public int compare(Item o1, Item o2) {
        if(o1 instanceof Section && o2 instanceof Section) {
            return 0;
        } else if(o1 instanceof Section) {
            return -1;
        } else if(o2 instanceof Section) {
            return 1;
        }
        Paper p1 = (Paper)o1;
        Paper p2 = (Paper)o2;
        departments.add(p1.getDepartment());
        departments.add(p2.getDepartment());
        if (p1.getDepartment().compareTo(p2.getDepartment()) != 0) {
            return p1.getDepartment().compareTo(p2.getDepartment());
        }
        // A napokon podla nazvu
        return utf.compare(o1.getTitle(), o2.getTitle());
    }

    public Set<String> getDepartments() {
        return departments;
    }
}
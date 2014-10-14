package pdf.papers;

import java.util.List;
import pdf.Constants;
import pdf.model.Item;

public class PageNumbersPrepare {
    public void prepareNumbers(List<Item> sortedPapers) {
        int prefix = Constants.titleLength + Constants.introductionLength + Constants.contentsLength;
        for(Item i : sortedPapers) {
            i.setPageNum(prefix);
            prefix+=i.getPages();
        }
    }
}

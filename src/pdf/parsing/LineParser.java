package pdf.parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import pdf.model.Item;
import pdf.model.Paper;
import pdf.model.Section;
import pdf.model.PresentationEnum;
import pdf.model.SectionEnum;

@Deprecated
public class LineParser {

    public List<Item> parse(Reader is) throws IOException {
        List<Item> papers = new LinkedList<>();
        BufferedReader br = new BufferedReader(is);
        Item p;
        while ((p = parsePaper(br)) != null) {
            papers.add(p);
        }
        return papers;
    }

    private Item parsePaper(BufferedReader br) throws IOException {
        Item item;
        String line = br.readLine();
        while ("".equals(line)) {
            line = br.readLine();
        }
        if (line == null) {
            return null;
        } else if (line.startsWith("section")) {
            SectionEnum section = SectionEnum.valueOf(line.replace("section", "").trim());
            line = br.readLine();
            PresentationEnum pres = PresentationEnum.NONE;
            if(!"".equals(line) && line!=null) {
                pres = PresentationEnum.valueOf(line.trim());
            }            
            item = new Section(section, pres);
        } else {
            SectionEnum section = SectionEnum.valueOf(line.trim());
            line = br.readLine();
            if (line == null) {
                return null;
            }
            StringTokenizer st = new StringTokenizer(line.trim(), ",");
            List<String> authors = new LinkedList<>();
            while (st.hasMoreTokens()) {
                authors.add(st.nextToken());
            }
            String title = br.readLine().trim();
            String department = br.readLine();
            int pages = Integer.parseInt(br.readLine());
            line = br.readLine();
            if (line == null) {
                return null;
            }

            item = new Paper(authors, title, pages, PresentationEnum.valueOf(line.trim()), section, department, "");
        }
        return item;
    }
}

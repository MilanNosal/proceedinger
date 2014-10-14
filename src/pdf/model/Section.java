package pdf.model;

import pdf.Constants;

public class Section extends Item {
    private SectionEnum section;
    private PresentationEnum presentation;

    public Section(SectionEnum section, PresentationEnum presentation) {
        this.section = section;
        this.presentation = presentation;
    }

    @Override
    public String toTOC() {
        StringBuilder sb = new StringBuilder("\n\\large\\textbf{")
                .append(section.getName());
        if(!this.presentation.equals(PresentationEnum.NONE)) {
            sb.append(" -- ").append(this.presentation.getName());
        }
        sb.append("}\\vspace{.5cm}\n\n\\noindent\n\\normalsize");
        return sb.toString();
    }

    @Override
    public String toLatex() {
        StringBuilder sb = new StringBuilder("\n\\newpage\n\\thispagestyle{empty}\n\\vspace*{\\fill}\n\\huge\\textbf{Section:}\\\\");
        sb.append("\n\\indent\\textbf{").append(this.section.getName()).append("}");
        
        if(!PresentationEnum.NONE.equals(this.presentation)) {
            sb.append("\\\\\n\\indent\\LARGE\\textbf{").append(this.presentation.getName()).append("}");
        }
        
        sb.append("\\vspace{3cm}\n\\vspace*{\\fill}\n");
        
        return sb.toString();
    }

    @Override
    public SectionEnum getSection() {
        return section;
    }

    @Override
    public String[] getAuthorsRev() {
        return new String[]{};
    }

    @Override
    public PresentationEnum getPresentation() {
        return this.presentation;
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public Integer getPages() {
        return 1;
    }

    @Override
    public void setPages(Integer pages) {
        // do nothing
    }

    @Override
    public String getPDF() {
        return Constants.pathToBlank;
    }

    @Override
    public String department() {
        return "";
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("section ");
        sb.append(section.toString()).append(section.getName()).append("\n");
        if(!PresentationEnum.NONE.equals(presentation)) {
            sb.append(presentation.toString()).append(presentation.getName()).append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}

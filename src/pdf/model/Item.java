package pdf.model;

public abstract class Item {
    
    private int pageNum;
    
    public abstract String toTOC();
    public abstract String toLatex();
    public abstract SectionEnum getSection();
    public abstract String[] getAuthorsRev();
    public abstract PresentationEnum getPresentation();
    public abstract String getTitle();
    public abstract Integer getPages();
    public abstract void setPages(Integer pages);
    public abstract String getPDF();
    public abstract String department();

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}

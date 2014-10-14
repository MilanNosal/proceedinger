package pdf.model;

public enum PresentationEnum {
    ORAL("Oral form",1),
    POSTER("Poster form",2),
    NONE("",0);
    private String name;
    private int priority;
    private boolean changedName = false;
    private boolean changedPriority = false;

    private PresentationEnum(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.changedName = true;
    }

    public void setPriority(int priority) {
        this.priority = priority;
        this.changedPriority = true;
    }

    public boolean hasChangedName() {
        return this.changedName;
    }

    public boolean hasChangedPriority() {
        return changedPriority;
    }
}

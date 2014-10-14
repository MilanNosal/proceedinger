package pdf.model;

public enum SectionEnum {
    IT ("Informatics & Telecommunications", 1),
    EEE ("Electrical & Electronics Engineering", 2);
    private String name;
    private int priority;
    private boolean changedName = false;
    private boolean changedPriority = false;

    private SectionEnum(String name, int priority) {
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

    public static SectionEnum valueOfString(String value) {
        switch(value) {
            case "EEE":
                return SectionEnum.EEE;
            case "IT":
                return SectionEnum.IT;
            default:
                throw new RuntimeException("Unknown SectionEnum constant! ("+value+")");
        }
    }
}

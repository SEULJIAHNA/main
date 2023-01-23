package shareYourFashion.main.constant;

public enum Sex {
    MAN("MAN") , WOMAN("WOMAN");

    private final String displayValue;

    private Sex(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}

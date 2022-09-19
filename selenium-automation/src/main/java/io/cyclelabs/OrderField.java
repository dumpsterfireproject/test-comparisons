package io.cyclelabs;

public class OrderField {
    private String field;
    private String value;

    public OrderField(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return this.field;
    }

    public String getValue() {
        return this.value;
    }
}

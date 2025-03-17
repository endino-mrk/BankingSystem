package main;

import org.junit.Test;

public class TestField {
    @Test
    public void TestNumericString() {
        Field<String, Integer> pin = new Field("Pin", String.class, 6, new Field.StringFieldLengthValidator());
    }
}

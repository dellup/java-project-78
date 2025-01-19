package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public class StringSchema {
    private boolean required = false;
    private int minimLength;
    List<String> containsList = new ArrayList<>();

    public StringSchema required() {
        required = true;
        return this;
    }
    public StringSchema minLength(int length) {
        minimLength = length;
        return this;
    }
    public StringSchema contains(String str) {
        containsList.add(str);
        return this;
    }
    public boolean isValid(String str) {
        if (required && (str == null || str.isEmpty())) {
            return false;
        } else if (!(required) && (str == null || str.isEmpty())) {
            return true;
        }
        if (str.length() < minimLength) {
            return false;
        }
        for (String string : containsList) {
            if (!(str.contains(string))) {
                return false;
            }
        }
        return true;
    }
}

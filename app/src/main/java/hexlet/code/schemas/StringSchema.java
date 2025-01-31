package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public class StringSchema extends BaseSchema<String> {
    private int minimLength;
    private List<String> containsList = new ArrayList<>();
    public StringSchema minLength(int length) {
        minimLength = length;
        return this;
    }
    public StringSchema contains(String str) {
        containsList.add(str);
        return this;
    }
    public boolean isValid(String str) {
        if (!super.isValid(str)) {
            return false;
        } else {
            if (str == null) {
                return true;
            }
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

package graph;

import utils.FormatUtils;
import utils.MathUtils;

public class CustomDouble implements Cloneable {

    public double _value;

    public CustomDouble(double value) {
        this._value = value;
    }


    @Override
    public CustomDouble clone() {
        return new CustomDouble(this._value);
    }

    @Override
    public String toString() {
        return ""+FormatUtils.round(_value, 2);
    }
}

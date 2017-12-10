package banbanmu.github.io.cleanpotty.view;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by min on 2017. 9. 17..
 */

public class DateAxisValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        String date = "";

        date += (int)value / 10000 + "-";
        value %= 10000;

        date += (int)value / 100 + "-";
        value %= 100;

        date += (int)value;

        return date;
    }
}

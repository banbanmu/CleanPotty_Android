package banbanmu.github.io.cleanpotty.util;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import banbanmu.github.io.cleanpotty.R;

/**
 * Created by min on 2017. 9. 17..
 */

public class ToolbarUtil {

    public void setToolbar(Toolbar toolbar, Context context, String title, @MenuRes int menuResId) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(ContextCompat.getColor(context, R.color.white));
        toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.primary_color));
        if(menuResId != 0) toolbar.inflateMenu(menuResId);
        toolbar.setNavigationIcon(R.drawable.img_empty_thumbs);
    }
}

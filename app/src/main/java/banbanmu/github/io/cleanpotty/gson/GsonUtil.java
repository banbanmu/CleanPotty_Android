package banbanmu.github.io.cleanpotty.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.Date;

/**
 * Created by min on 2017. 9. 16..
 */

public class GsonUtil {

    public static Gson gson = new GsonBuilder()
            // TODO Date format 짜기
            .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSSZ")
            .serializeNulls()
            .create();

    public static Date getDate(JsonElement json) {
        if (isEmpty(json)) return null;
        return gson.fromJson(json, Date.class);
    }

    public static boolean isEmpty(JsonElement json) {
        return json == null || json.isJsonNull();
    }
}

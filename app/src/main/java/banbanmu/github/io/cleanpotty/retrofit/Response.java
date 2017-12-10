package banbanmu.github.io.cleanpotty.retrofit;

import com.google.gson.JsonElement;

/**
 * Created by min on 2017. 9. 16..
 */

public class Response {

    private String code;

    private JsonElement data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public String toString() {
        return code + "\n" + data;
    }
}

package banbanmu.github.io.cleanpotty.retrofit.helper;

import banbanmu.github.io.cleanpotty.retrofit.RetrofitClient;
import banbanmu.github.io.cleanpotty.retrofit.api.PoopAPI;

/**
 * Created by min on 2017. 9. 17..
 */

public class PoopHelper {

    private PoopAPI api;

    public PoopHelper() {
        api = RetrofitClient.getClient().create(PoopAPI.class);
    }

    public PoopAPI getApi() {
        return api;
    }
}

package banbanmu.github.io.cleanpotty.retrofit.helper;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import banbanmu.github.io.cleanpotty.gson.GsonUtil;
import banbanmu.github.io.cleanpotty.resource.Potty;
import banbanmu.github.io.cleanpotty.retrofit.ResponseCode;
import banbanmu.github.io.cleanpotty.retrofit.RetrofitClient;
import banbanmu.github.io.cleanpotty.retrofit.api.PottyAPI;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by min on 2017. 9. 17..
 */

public class PottyHelper {

    private PottyAPI api;

    public PottyHelper() {
        api = RetrofitClient.getClient().create(PottyAPI.class);
    }

    public Observable<List<Potty>> list(String token) {
        return api.list(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> {

                    if(response.getCode().equals(ResponseCode.CODE_SUCCESS)) {
                        JsonElement json = response.getData();
                        Type collectionType = new TypeToken<List<Potty>>() {
                        }.getType();

                        List<Potty> potties = GsonUtil.gson.fromJson(json, collectionType);

                        return new ArrayList<Potty>(potties);
                    } else {
                        throw new RuntimeException();
                    }
                });
    }
}

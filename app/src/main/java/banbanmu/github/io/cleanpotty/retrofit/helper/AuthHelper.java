package banbanmu.github.io.cleanpotty.retrofit.helper;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonElement;

import banbanmu.github.io.cleanpotty.retrofit.ResponseCode;
import banbanmu.github.io.cleanpotty.retrofit.RetrofitClient;
import banbanmu.github.io.cleanpotty.retrofit.api.UserAPI;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by min on 2017. 9. 16..
 */

public class AuthHelper {

    private UserAPI api;

    public AuthHelper() {
        api = RetrofitClient.getClient().create(UserAPI.class);
    }

    public Observable<String> signIn(String email, String password) {
        Log.d("sign in", email + " " + password);
        return api.signin(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> {
                    if(response.getCode().equals(ResponseCode.CODE_SUCCESS)){
                        String token = response.getData().getAsString();
                        return token;
                    }
                    else return "";
                });
    }
}

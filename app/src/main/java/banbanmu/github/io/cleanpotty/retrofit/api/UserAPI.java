package banbanmu.github.io.cleanpotty.retrofit.api;

import banbanmu.github.io.cleanpotty.retrofit.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by min on 2017. 9. 16..
 */

public interface UserAPI {

    @FormUrlEncoded
    @POST("user/signin")
    Observable<Response> signin(
            @Field("email") String email,
            @Field("password") String password
    );
}

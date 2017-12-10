package banbanmu.github.io.cleanpotty.retrofit.api;

import banbanmu.github.io.cleanpotty.retrofit.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by min on 2017. 9. 16..
 */

public interface PottyAPI {

    @GET("potties")
    Observable<Response> list(
            @Header("authorization") String token
    );
}

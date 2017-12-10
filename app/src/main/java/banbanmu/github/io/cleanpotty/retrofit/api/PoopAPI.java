package banbanmu.github.io.cleanpotty.retrofit.api;

import banbanmu.github.io.cleanpotty.retrofit.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by min on 2017. 9. 17..
 */

public interface PoopAPI {

    @GET("potties/story")
    Observable<Response> data(
            @Header("authorization") String token
    );
}

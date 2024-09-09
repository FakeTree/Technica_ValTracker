import okhttp3.*;
import java.io.IOException;

/**
 * Interceptor for a test method which changes the request header.
 */
public class ApiRequestTestInterceptor implements Interceptor {

    public static final MediaType APPLICATION_JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .header("X-Riot-Token", "Some-API-key")
                .build();

        return chain.proceed(request);
    }
}
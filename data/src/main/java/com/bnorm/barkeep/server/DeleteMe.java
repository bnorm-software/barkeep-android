package com.bnorm.barkeep.server;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class DeleteMe {
    public static void main(String[] args) throws IOException {

        HttpUrl.Builder urlBuilder = new HttpUrl.Builder();
        urlBuilder.scheme("https");
        urlBuilder.host("barkeep.beefyhost.com");
        //        urlBuilder.addPathSegment("rest");
        //        urlBuilder.addPathSegment("api");
        //        urlBuilder.addPathSegment("2");
        // This last, empty segment adds a trailing '/' which is required for relative paths in the annotations
        urlBuilder.addPathSegment("");

        HttpUrl url = urlBuilder.build();

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new SessionInterceptor());
        clientBuilder.addInterceptor(new TokenInterceptor());
        clientBuilder.addInterceptor(new WireTraceInterceptor());

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.validateEagerly(true);
        retrofitBuilder.baseUrl(url);
        retrofitBuilder.client(clientBuilder.build());
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());

        BarkeepTestService service = retrofitBuilder.build().create(BarkeepTestService.class);

        Login authentication = new Login();
        authentication.setUsername("bnorm");
        authentication.setPassword("nohomohug");

        Response<AuthenticationResponse> execute1 = service.login(authentication).execute();
        System.out.println(execute1.body());

        Response<Void> execute2 = service.getIngredient().execute();
        System.out.println(execute2.body());

        Response<Void> execute3 = service.logout().execute();
        System.out.println(execute3.body());

        Response<Void> execute4 = service.getIngredient().execute();
        System.out.println(execute4.body());
    }

    private static class SessionInterceptor implements Interceptor {

        private String sessionId = null;

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            if (sessionId != null) {
                builder.addHeader("Cookie", sessionId);
            }

            okhttp3.Response response = chain.proceed(builder.build());

            String setCookie = response.header("Set-Cookie");
            if (setCookie != null) {
                sessionId = setCookie.split(";")[0];
            }

            return response;
        }
    }

    private static class TokenInterceptor implements Interceptor {

        private String token = null;

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            if (token != null) {
                builder.addHeader("Authorization", token);
            }

            okhttp3.Response response = chain.proceed(builder.build());

            String setToken = response.header("Set-Authorization");
            if (setToken != null) {
                token = setToken;
            }

            String clearToken = response.header("Clear-Authorization");
            if (clearToken != null) {
                token = null;
            }

            return response;
        }
    }

    public static class WireTraceInterceptor implements Interceptor {


        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            RequestBody requestBody = request.body();
            if (requestBody != null) {
                if (requestBody.contentType() != null && requestBody.contentType().charset() != null) {
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);
                    String rawBody = buffer.readByteString().utf8();
                    System.out.println(String.format("Sending [%s %s] with body [%s]",
                                                     request.method(),
                                                     request.url(),
                                                     rawBody));
                } else {
                    System.out.println(String.format("Sending [%s %s] with non-text body",
                                                     request.method(),
                                                     request.url()));
                }
            } else {
                System.out.println(String.format("Sending [%s %s]", request.method(), request.url()));
            }

            Instant start = Instant.now();
            okhttp3.Response response = chain.proceed(request);

            Duration duration = Duration.between(start, Instant.now());
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                if (responseBody.contentType() != null && responseBody.contentType().charset() != null) {
                    String rawBody = responseBody.string();
                    System.out.println(String.format("Received response for [%s %s] in %sms with body [%s]",
                                                     response.request().method(),
                                                     response.request().url(),
                                                     duration.toMillis(),
                                                     rawBody));

                    // Reading the body as a string closes the stream so create a new response body
                    response = response.newBuilder()
                                       .body(ResponseBody.create(responseBody.contentType(), rawBody))
                                       .build();
                } else {
                    System.out.println(String.format("Received response for [%s %s] in %sms with non-text body",
                                                     response.request().method(),
                                                     response.request().url(),
                                                     duration.toMillis()));
                }
            } else {
                System.out.println(String.format("Received response for [%s %s] in %sms",
                                                 response.request().method(),
                                                 response.request().url(),
                                                 duration.toMillis()));
            }

            return response;
        }
    }

    public interface BarkeepTestService {

        @POST("Login")
        Call<AuthenticationResponse> login(@Body Login authentication);

        @POST("Logout")
        Call<Void> logout();

        @GET("ingredients")
        Call<Void> getIngredient();
    }

    public static class Login {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


    public static class BaseResponse {

        public boolean success;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }


    public static class AuthenticationResponse extends BaseResponse {

        public String message;
        public String Authorization;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAuthorization() {
            return Authorization;
        }

        public void setAuthorization(String authorization) {
            Authorization = authorization;
        }
    }
}

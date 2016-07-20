package com.bnorm.barkeep.data.api;

import java.io.IOException;

import android.util.Log;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;

public class WireTraceInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        RequestBody requestBody = request.body();
        if (requestBody != null) {
            if (requestBody.contentType() != null && requestBody.contentType().charset() != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                String rawBody = buffer.readByteString().utf8();
                Log.v("BARKEEP.WIRE",
                      String.format("Sending [%s %s] with body [%s]", request.method(), request.url(), rawBody));
            } else {
                Log.v("BARKEEP.WIRE",
                      String.format("Sending [%s %s] with non-text body", request.method(), request.url()));
            }
        } else {
            Log.v("BARKEEP.WIRE", String.format("Sending [%s %s]", request.method(), request.url()));
        }

        long instant = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(request);

        long duration = System.currentTimeMillis() - instant;
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            if (responseBody.contentType() != null && responseBody.contentType().charset() != null) {
                String rawBody = responseBody.string();
                Log.v("BARKEEP.WIRE",
                      String.format("Received [%d] response for [%s %s] in %sms with body [%s]",
                                    response.code(),
                                    response.request().method(),
                                    response.request().url(),
                                    duration,
                                    rawBody));

                // Reading the body as a string closes the stream so create a new response body
                response = response.newBuilder().body(ResponseBody.create(responseBody.contentType(), rawBody)).build();
            } else {
                Log.v("BARKEEP.WIRE",
                      String.format("Received [%d] response for [%s %s] in %sms with non-text body",
                                    response.code(),
                                    response.request().method(),
                                    response.request().url(),
                                    duration));
            }
        } else {
            Log.v("BARKEEP.WIRE",
                  String.format("Received [%d] response for [%s %s] in %sms",
                                response.code(),
                                response.request().method(),
                                response.request().url(),
                                duration));
        }

        return response;
    }
}

package com.testalarstudios;

import android.content.Context;

import com.testalarstudios.exception.NoConnectivityException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.testalarstudios.App.isOnline;

public class ConnectivityInterceptor implements Interceptor {
    private final Context context;

    public ConnectivityInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        if (!isOnline(context)) {
            throw new NoConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}

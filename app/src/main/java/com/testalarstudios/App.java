package com.testalarstudios;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.pixplicity.easyprefs.library.Prefs;
import com.testalarstudios.api.ApiService;

import org.osmdroid.config.Configuration;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class App extends Application {
    private static App instance;
    private ApiService api;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        new Prefs.Builder()
                .setContext(this).setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getClient())
                .build();
        api = retrofit.create(ApiService.class);

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        Configuration.getInstance().setTileDownloadThreads((short) 12);
        Configuration.getInstance().setDebugMode(false);
    }

    private final OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityInterceptor(this));

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        return builder.build();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    public ApiService getApiService() {
        return api;
    }

    public static App getInstance() {
        return instance;
    }
}

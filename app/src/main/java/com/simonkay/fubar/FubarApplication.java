package com.simonkay.fubar;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

/**
 * Created by Simon Kay on 12/13/2014.
 */
public class FubarApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        //noinspection SpellCheckingInspection
        Parse.initialize(this, "ttgRrIbay1WNBVQ4iLaFYJaTc08Gx8ZvO6D0BoNQ", "DuneYcQRZboOqLrwMaevjsFIuevqebGwUj4qVQZx");


        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

        ParseTwitterUtils.initialize("o0ktHjpy170SJcfy9Pm0ByoFO", "22JjHDrgHtdpOb8yxSdHx2LWG7CmdWEyZDP2s0ziFlZHpfLPP4");
    }
}

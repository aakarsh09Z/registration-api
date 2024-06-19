package com.aakarsh09z.registrationapi.Configuration;
/*
    The AppConstants class contains static final variables, which means these values are constants. These constants are used throughout the application to avoid hardcoding values directly in the codebase.
*/
public class AppConstants {
    public static final String secret="6bc61f8260b0ee59efdd01c68ee1c6bf7434d5ae22e3ece9c3da16e9ce01e3906bc61f8260b0ee59efdd01c68ee1c6bf7434d5ae22e3ece9c3da16e9ce01e3906bc61f8260b0ee59efdd01c68ee1c6bf7434d5ae22e3ece9c3da16e9ce01e3906bc61f8260b0ee59efdd01c68ee1c6bf7434d5ae22e3ece9c3da16e9ce01e390";
    public static final long JWT_ACCESS_TOKEN_VALIDITY = 24 * 60 *60; //30 sec
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 100 * 24 * 60 *60; //2 min
    public static final int OTP_EXPIRATION_MINUTE=10; //10 min
}

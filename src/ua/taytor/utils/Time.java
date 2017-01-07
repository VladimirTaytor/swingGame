package ua.taytor.utils;


public class Time {

    public static final long SECOND = 1000000000L;

    public static long getCurrentTime(){
        return System.nanoTime();
    }
}

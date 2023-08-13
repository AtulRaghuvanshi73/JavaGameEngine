package util;

public class Time {
    public static float timeStarted = System.nanoTime();

    //to find the time elapsed getting the time since the game started in nanoseconds
    public static float getTime(){return (float)((System.nanoTime() - timeStarted) * 1E-9);}

}

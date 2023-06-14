package com.mygdx.game;

public class PlatformCounter {

    public int count(float y, int distanceBetweenPlatforms) {
        return (int) y / distanceBetweenPlatforms;
    }
}

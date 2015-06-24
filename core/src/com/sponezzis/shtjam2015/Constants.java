package com.sponezzis.shtjam2015;

/**
 * Created by sponaas on 6/23/15.
 */
public class Constants {

    public static final float PIXELS_TO_METERS = 0.01f;
    public static final float METERS_TO_PIXELS = 100f;

    public static final int TARGET_FPS = 60;
    public static final String LOG_TAG = "ShtJam2015";

    public static final float GROUND_HEIGHT_IN_PIXELS = 40f;
    public static final float AIR_FRICTION = 0.05f;

    public static final short   BITMASK_PLAYER = 0x0001;
    public static final short   BITMASK_ENEMY = 0x0002;
    public static final short   BITMASK_LEVEL_BOUNDS = 0x0004;
    public static final short   BITMASK_PLAYER_BULLET = 0x0008;
    public static final short   BITMASK_ENEMY_BULLET = 0x0010;

    public static final float PLAYER_ACCEL_VERT = 0.5f;
    public static final float PLAYER_ACCEL_HORIZ = 0.5f;
    public static final float PLAYER_MAXSPEED_VERT = 3f;
    public static final float PLAYER_MAXSPEED_HORIZ = 4f;

}

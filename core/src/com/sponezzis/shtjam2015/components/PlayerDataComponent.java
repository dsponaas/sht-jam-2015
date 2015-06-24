package com.sponezzis.shtjam2015.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by sponaas on 6/23/15.
 */
public class PlayerDataComponent extends Component {

    public boolean alive;
    public float invincibilityTime;
    public float playerDeathTime;

    public float points2xTime;
    public float rapidShotTime;
    public float spreadShotTime;

    public PlayerDataComponent() {
        alive = true;
        invincibilityTime = -1f;
        playerDeathTime = -1f;

        points2xTime = -1f;
        rapidShotTime = -1f;
        spreadShotTime = -1f;
    }

}

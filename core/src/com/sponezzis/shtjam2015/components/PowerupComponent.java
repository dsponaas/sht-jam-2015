package com.sponezzis.shtjam2015.components;

import com.badlogic.ashley.core.Component;
import com.sponezzis.shtjam2015.Constants;

/**
 * Created by sponaas on 6/24/15.
 */
public class PowerupComponent extends Component {

    public int type;
    public float timer;
    public boolean pickedUp;

    public PowerupComponent(Constants.PowerupType typeInit) {
        type = typeInit.ordinal();
        pickedUp = false;
        timer = Constants.POWERUP_TIMER;
    }

}

package com.sponezzis.shtjam2015.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by sponaas on 6/23/15.
 */
public class PositionComponent extends Component {

    public float x, y;

    public PositionComponent(float xInit, float yInit) {
        x= xInit;
        y = yInit;
    }

}

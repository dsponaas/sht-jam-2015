package com.sponezzis.shtjam2015.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.sponezzis.shtjam2015.Constants;

/**
 * Created by sponaas on 6/23/15.
 */
public class BodyComponent extends Component {

    public Body body;

    public BodyComponent(PositionComponent positionComponent, Body bodyInit) {
        body = bodyInit;
        body.setTransform( positionComponent.x * Constants.PIXELS_TO_METERS, positionComponent.y * Constants.PIXELS_TO_METERS, 0);
    }

}

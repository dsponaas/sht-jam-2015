package com.sponezzis.shtjam2015.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by sponaas on 6/24/15.
 */
public class EnemyDataComponent extends Component {

    public int points;

    public EnemyDataComponent(int pointsInit) {
        points = pointsInit;
    }

}

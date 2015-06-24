package com.sponezzis.shtjam2015;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by sponaas on 6/23/15.
 */
public class InputManager implements InputProcessor {

    public static boolean moveUpActive = false;
    public static boolean moveDownActive = false;
    public static boolean moveLeftActive = false;
    public static boolean moveRightActive = false;

    private static final int MOVE_UP_KEY = Input.Keys.UP;
    private static final int MOVE_DOWN_KEY = Input.Keys.DOWN;
    private static final int MOVE_LEFT_KEY = Input.Keys.LEFT;
    private static final int MOVE_RIGHT_KEY = Input.Keys.RIGHT;

    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case MOVE_UP_KEY:
                moveUpActive = true;
                break;
            case MOVE_DOWN_KEY:
                moveDownActive = true;
                break;
            case MOVE_LEFT_KEY:
                moveLeftActive = true;
                break;
            case MOVE_RIGHT_KEY:
                moveRightActive = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch (keycode)
        {
            case MOVE_UP_KEY:
                moveUpActive = false;
                break;
            case MOVE_DOWN_KEY:
                moveDownActive = false;
                break;
            case MOVE_LEFT_KEY:
                moveLeftActive = false;
                break;
            case MOVE_RIGHT_KEY:
                moveRightActive = false;
                break;
        }
        return true;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

package com.sponezzis.shtjam2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by sponaas on 6/23/15.
 */
public class Utils {
    public static JsonValue readJsonFromFile(String path) {
        FileHandle file = Gdx.files.internal(path);
        JsonReader jsonReader = new JsonReader();
        return jsonReader.parse(file.readString());
    }
}

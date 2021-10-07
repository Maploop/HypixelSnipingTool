package me.mappy.cache;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    private final static Map<String, String> gameMode = new HashMap<>();
    private final static Map<String, String> gameType = new HashMap<>();

    private final String key;

    public Cache(String key) {
        this.key = key;
    }

    public String getGameMode() {
        return gameMode.get(key);
    }

    public String getGameType() {
        return gameType.get(key);
    }

    public void setGameMode(String value) {
        gameMode.put(key, value);
    }

    public void setGameType(String value) {
        gameType.put(key, value);
    }

    public boolean isCached() {
        return gameMode.containsKey(key) && gameType.containsKey(key);
    }
}

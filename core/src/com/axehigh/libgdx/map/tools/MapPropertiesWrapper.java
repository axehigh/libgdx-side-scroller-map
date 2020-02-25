package com.axehigh.libgdx.map.tools;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;


public class MapPropertiesWrapper {

    private final int mapWidth;
    private final int mapHeight;
    private final int tilePixelWidth;
    private final int tilePixelHeight;
    private final int totalMapWidthInPixels;
    private final int totalMapHeightInPixels;

    public MapPropertiesWrapper(TiledMap map) {
        MapProperties prop = map.getProperties();
        mapWidth = prop.get("width", Integer.class);
        mapHeight = prop.get("height", Integer.class);
        tilePixelWidth = prop.get("tilewidth", Integer.class);
        tilePixelHeight = prop.get("tileheight", Integer.class);
        totalMapWidthInPixels = mapWidth * tilePixelWidth;
        totalMapHeightInPixels = mapHeight * tilePixelHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getTilePixelWidth() {
        return tilePixelWidth;
    }

    public int getTilePixelHeight() {
        return tilePixelHeight;
    }

    public int getTotalMapWidthInPixels() {
        return totalMapWidthInPixels;
    }

    public int getTotalMapHeightInPixels() {
        return totalMapHeightInPixels;
    }

    @Override
    public String toString() {
        return "MapPropertiesWrapper{" +
                "mapWidth=" + mapWidth +
                ", mapHeight=" + mapHeight +
                ", tilePixelWidth=" + tilePixelWidth +
                ", tilePixelHeight=" + tilePixelHeight +
                ", totalMapWidthInPixels=" + totalMapWidthInPixels +
                ", totalMapHeightInPixels=" + totalMapHeightInPixels +
                '}';
    }
}

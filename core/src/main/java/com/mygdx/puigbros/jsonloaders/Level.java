package com.mygdx.puigbros.jsonloaders;

import java.util.ArrayList;

public class Level
{
    private int mapWidth;
    private int mapHeight;

    private byte tileMap[][];

    private ArrayList<Enemy> enemies;

    public Level() {
    }

    public Level(int mapWidth, int mapHeight, byte[][] tileMap)
    {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileMap = tileMap;

        enemies = new ArrayList<>();
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public byte[][] getTileMap() {
        return tileMap;
    }

    public void setTileMap(byte[][] tileMap) {
        this.tileMap = tileMap;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void addEnemy(Enemy e)
    {
        enemies.add(e);
    }

}

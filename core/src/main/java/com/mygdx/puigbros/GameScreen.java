package com.mygdx.puigbros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class GameScreen implements Screen {

    PuigBros game;
    Joypad joypad;

    Stage stage;
    TileMap tileMap;

    Player player;
    ArrayList<Actor> enemies;


    public GameScreen(PuigBros game)
    {
        this.game = game;

        // Create joypad
        joypad = new Joypad(game.camera);
        joypad.addButton(40,340, 60, 60, "Left");
        joypad.addButton(160,340, 60, 60, "Right");
        joypad.addButton(100,400, 60, 60, "Down");
        joypad.addButton(100,280, 60, 60, "Up");
        joypad.addButton(700,340, 60, 60, "Jump");

        tileMap = new TileMap(game.batch);
        stage = new Stage();
        player = new Player();
        enemies = new ArrayList<>();
        player.setMap(tileMap);
        player.setJoypad(joypad);
        stage.addActor(player);

        Viewport viewport = new Viewport() {
        };
        viewport.setCamera(game.camera);
        stage.setViewport(viewport);

        Json json = new Json();

        FileHandle file = Gdx.files.internal("Level.json");
        String scores = file.readString();
        Level l = json.fromJson(Level.class, scores);
        tileMap.loadFromLevel(l);

        for(int i = 0; i < l.getEnemies().size(); i++)
        {
            Enemy e = l.getEnemies().get(i);
            if(e.getType().equals("Dino"))
            {
                Dino t = new Dino(e.getX() * tileMap.TILE_SIZE, e.getY() * tileMap.TILE_SIZE);
                t.setMap(tileMap);
                enemies.add(t);
                stage.addActor(t);
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Render step =============================================
        game.camera.update();

        ScreenUtils.clear(Color.SKY);

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        //game.batch.draw(game.img, 0, 0);
        game.batch.end();

        game.shapeRenderer.setProjectionMatrix(game.camera.combined);
        /*game.shapeRenderer.begin();
        game.shapeRenderer.setColor(Color.YELLOW);
        game.shapeRenderer.line(0,0,800,480);
        game.shapeRenderer.end();*/
        tileMap.render(/*game.shapeRenderer*/);
        player.drawDebug(game.shapeRenderer);
        for (int i = 0; i < enemies.size(); i++)
            enemies.get(i).drawDebug(game.shapeRenderer);
        joypad.render(game.shapeRenderer);
        stage.draw();

        // Update step =============================================
        stage.act(delta);
        tileMap.scrollX = (int)(player.getX() - 400);
        if(tileMap.scrollX < 0)
            tileMap.scrollX = 0;
        if(tileMap.scrollX >= tileMap.width * tileMap.TILE_SIZE - 800)
            tileMap.scrollX = tileMap.width * tileMap.TILE_SIZE - 800 - 1;

        Rectangle rect_player = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());

        for (int i = 0; i < enemies.size(); i++)
        {
            Actor enemy = enemies.get(i);
            Rectangle rect_enemy = new Rectangle(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());

            WalkingCharacter wc = (WalkingCharacter) enemy;
            if(!wc.isDead())
            {
                if(rect_enemy.overlaps(rect_player))
                {
                    if(player.getY() < wc.getY() && player.isFalling() && player.getSpeed().y > 0.f)
                    {
                        player.jump();
                        wc.kill();
                    }
                    else
                    {
                        player.kill();
                    }

                }
            }
        }


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

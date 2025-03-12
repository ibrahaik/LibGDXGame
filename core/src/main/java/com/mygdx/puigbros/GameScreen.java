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
import com.mygdx.puigbros.jsonloaders.CollectableJson;
import com.mygdx.puigbros.jsonloaders.EnemyJson;
import com.mygdx.puigbros.jsonloaders.LevelJson;

import java.util.ArrayList;

public class GameScreen implements Screen {

    PuigBros game;
    ButtonLayout joypad, pauseMenu;

    Stage stage;
    TileMap tileMap;

    Player player;
    ArrayList<Actor> enemies;
    ArrayList<Actor> collectables;
    boolean paused;


    public GameScreen(PuigBros game)
    {
        this.game = game;

        pauseMenu = new ButtonLayout(game.camera, game.manager, game.mediumFont);
        pauseMenu.loadFromJson("pausemenu.json");
        // Create joypad
        joypad = new ButtonLayout(game.camera, game.manager, null);
        joypad.loadFromJson("joypad.json");
        /*joypad.addButton(40,340, 60, 60, "Left");
        joypad.addButton(160,340, 60, 60, "Right");
        joypad.addButton(100,400, 60, 60, "Down");
        joypad.addButton(100,280, 60, 60, "Up");
        joypad.addButton(700,340, 60, 60, "Jump");*/

        tileMap = new TileMap(game.manager, game.batch);
        stage = new Stage();
        player = new Player(game.manager);
        enemies = new ArrayList<>();
        collectables = new ArrayList<>();
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
        LevelJson l = json.fromJson(LevelJson.class, scores);
        tileMap.loadFromLevel(l);

        for(int i = 0; i < l.getEnemies().size(); i++)
        {
            EnemyJson e = l.getEnemies().get(i);
            if(e.getType().equals("Dino"))
            {
                Dino d = new Dino(e.getX() * tileMap.TILE_SIZE, e.getY() * tileMap.TILE_SIZE, game.manager, player);
                d.setMap(tileMap);
                enemies.add(d);
                stage.addActor(d);
            }
        }

        for(int i = 0; i < l.getCollectables().size(); i++)
        {
            CollectableJson c = l.getCollectables().get(i);
            if(c.getType().equals("PowerUp"))
            {
                PowerUp p = new PowerUp(c.getX() * tileMap.TILE_SIZE, c.getY() * tileMap.TILE_SIZE, game.manager);
                p.setMap(tileMap);
                collectables.add(p);
                stage.addActor(p);
            }
        }

        paused = false;
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
        //game.batch.begin();
        //game.batch.draw(game.img, 0, 0);
        //game.batch.end();

        game.shapeRenderer.setProjectionMatrix(game.camera.combined);
        /*game.shapeRenderer.begin();
        game.shapeRenderer.setColor(Color.YELLOW);
        game.shapeRenderer.line(0,0,800,480);
        game.shapeRenderer.end();*/
        tileMap.render(/*game.shapeRenderer*/);
        //player.drawDebug(game.shapeRenderer);
        //for (int i = 0; i < enemies.size(); i++)
        //    enemies.get(i).drawDebug(game.shapeRenderer);
        stage.draw();

        if(paused)
        {
            pauseMenu.render(game.batch, game.textBatch);
        }
        else
        {
            joypad.render(game.batch, game.textBatch);
            game.textBatch.begin();
            game.mediumFont.draw(game.textBatch, "Lifes: " + game.lifes, 40,460);
            game.textBatch.end();
        }


        // Update step =============================================
        if(paused)
        {
            if(pauseMenu.consumeRelease("Resume"))
            {
                joypad.setAsActiveInputProcessor();
                paused = false;
            }
            if(pauseMenu.consumeRelease("Quit"))
            {
                game.setScreen(new MainMenuScreen(game));
                this.dispose();
            }
        }
        else
        {
            updateGameLogic(delta);

            // Pause game
            if(joypad.consumePush("Pause"))
            {
                paused = true;
                pauseMenu.setAsActiveInputProcessor();
            }
        }
    }

    void updateGameLogic(float delta)
    {
        stage.act(delta);

        // Scroll update
        tileMap.scrollX = (int) (player.getX() - 400);
        if (tileMap.scrollX < 0)
            tileMap.scrollX = 0;
        if (tileMap.scrollX >= tileMap.width * tileMap.TILE_SIZE - 800)
            tileMap.scrollX = tileMap.width * tileMap.TILE_SIZE - 800 - 1;

        // Collision player - enemies
        Rectangle rect_player = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());

        for (int i = 0; i < enemies.size(); i++) {
            Actor enemy = enemies.get(i);
            Rectangle rect_enemy = new Rectangle(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());

            WalkingCharacter wc = (WalkingCharacter) enemy;
            if (!player.isDead() && !wc.isDead()) {
                if (rect_enemy.overlaps(rect_player)) {
                    if(player.hasInvulnerability()) {
                      wc.kill();
                    } else if (player.getY() < wc.getY() && player.isFalling() && player.getSpeed().y > 0.f) {
                        player.jump();
                        wc.kill();
                    } else {
                        player.kill();
                    }

                }
            }
        }

        for (int i = 0; i < collectables.size(); i++) {
            Actor collectable = collectables.get(i);
            Rectangle rect_coll = new Rectangle(collectable.getX(), collectable.getY(), collectable.getWidth(), collectable.getHeight());
            if (rect_coll.overlaps(rect_player)) {
                player.getInvulnerability();
                collectable.remove();
                collectables.remove(collectable);
            }
        }

            // Lose life
        if (player.isDead() && player.getAnimationFrame() >= 25.f) {
            game.lifes--;
            if (game.lifes <= 0)
                game.setScreen(new MainMenuScreen(game));
            else
                game.setScreen(new GameScreen(game));
            this.dispose();
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

package com.mygdx.swordbound;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class LevelCompleteScreen implements Screen {

    SwordBound game;
    ButtonLayout endMenu;
    String currentLevel;
    public LevelCompleteScreen(SwordBound game, String currentLevel)
    {
        this.game = game;
        this.currentLevel = currentLevel;

        endMenu = new ButtonLayout(game.camera, game.manager, game.mediumFont);
        endMenu.loadFromJson("endmenu.json");

        game.manager.get("sound/levelcomplete.wav", Sound.class).play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.textBatch.setProjectionMatrix(game.textCamera.combined);

        game.batch.begin();
        game.batch.draw(game.manager.get("guiV2/Winner/winner.png", Texture.class), 0, 0, 1300, 840, 0,0, 1000, 750, false, true);
        game.batch.end();

        game.textBatch.begin();
        game.bigFont.draw(game.textBatch,"ENHORABONA!", 500, 1440 - 60);
        game.textBatch.end();

        endMenu.render(game.batch, game.textBatch);


        if(endMenu.consumeRelease("Menu"))
        {
            this.dispose();
            String nextLevel = getNextLevel(currentLevel);
            game.setScreen(new GameScreen(game, nextLevel));
        }

        if(endMenu.consumeRelease("Next"))
        {
            this.dispose();
            String nextLevel = getNextLevel(currentLevel);
            game.setScreen(new GameScreen(game, nextLevel));
        }
    }

    private String getNextLevel(String current) {
        if (current == null) return "Level2.json"; // fallback de seguridad
        int levelNumber = Integer.parseInt(current.replaceAll("\\D+", ""));
        return "Level" + (levelNumber + 1) + ".json";
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

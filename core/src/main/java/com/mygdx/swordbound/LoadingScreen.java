package com.mygdx.swordbound;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;


public class LoadingScreen implements Screen {

    SwordBound game;
    float loadProgress;
    Texture currentFrame;
    float animationTime = 0f;

    boolean isRunning = true;
    float animationSwitchTime = 0f;
    float animationSwitchInterval = 1.5f; // cambiar entre running y slashing cada 3 segundos

    float positionX = 100f;
    float movementSpeed = 120f; // píxeles por segundo
    boolean movingForward = true;

    boolean animationReady = false;

    LoadingScreen(SwordBound game)
    {
        this.game = game;
        AssetManager  manager = game.manager;

        // Add assets for loading

        // Tiles
        for(int i = 1; i < 19; i++)
            manager.load("tiles/"+i+".png", Texture.class);

        // Background image
        manager.load("BG.png", Texture.class);

        //GUI

        manager.load("guiV2/izquierdaon.png", Texture.class);
        manager.load("guiV2/izquierdaoff.png", Texture.class);
        manager.load("guiV2/derechaon.png", Texture.class);
        manager.load("guiV2/derechaoff.png", Texture.class);
        manager.load("guiV2/pausaoff.png", Texture.class);
        manager.load("guiV2/pausaon.png", Texture.class);
        manager.load("guiV2/xboxoff.png", Texture.class);
        manager.load("guiV2/xboxon.png", Texture.class);
        manager.load("guiV2/button_xbox_digital_a_4.png", Texture.class);
        manager.load("guiV2/button_xbox_digital_a_6.png", Texture.class);
        manager.load("guiV2/mainmenu/backgroundPC.png", Texture.class);
        manager.load("guiV2/Bottons/Start/startoff.png", Texture.class);
        manager.load("guiV2/Bottons/Start/starton.png", Texture.class);
        manager.load("guiV2/Bottons/Rect/bottonoff.png", Texture.class);
        manager.load("guiV2/Bottons/Rect/bottonon.png", Texture.class);
        manager.load("guiV2/Winner/winner.png", Texture.class);


        // Player
        for (int i = 0; i < 10; i++)
        {
            manager.load("player/Idle (" +(i+1)+").png", Texture.class);
        }
        for (int i = 0; i < 12; i++) {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("player/Valkyria/running/0_Valkyrie_Running_" + numberStr + ".png", Texture.class);
        }

        for (int i = 0; i < 6; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("player/Valkyria/jumpingLoop/0_Valkyrie_Jump Loop_" + numberStr + ".png", Texture.class);
        }

        for (int i = 0; i < 6; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("player/Valkyria/jumpingStart/0_Valkyrie_Jump Start_" + numberStr + ".png", Texture.class);
        }

        for (int i = 0; i < 15; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("player/Valkyria/Dying/0_Valkyrie_Dying_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 12; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("player/Valkyria/slashing/0_Valkyrie_Slashing_" + numberStr + ".png", Texture.class);        }

        // Goblin

        for (int i = 0; i < 24; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/goblin/walking/0_Goblin_Walking_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 15; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/goblin/dying/0_Goblin_Dying_" + numberStr + ".png", Texture.class);        }

       // Ogre

        for (int i = 0; i < 15; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/ogre/dying/0_Ogre_Dying_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 12; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/ogre/slashing/0_Ogre_Slashing_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 24; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/ogre/walking/0_Ogre_Walking_" + numberStr + ".png", Texture.class);        }

        // Orco

        for (int i = 0; i < 15; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/orco/dying/0_Orc_Dying_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 12; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/orco/runSlashing/0_Orc_Run Slashing_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 12; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/orco/slashing/0_Orc_Slashing_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 24; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/orco/walking/0_Orc_Walking_" + numberStr + ".png", Texture.class);        }

        //coin
        for (int i = 0; i < 4; i++)
        {
            manager.load("powerup/frame000" +i+".png", Texture.class);
        }



        // Sounds
        manager.load("sound/music.mp3", Music.class);
        manager.load("sound/loselife.wav", Sound.class);
        manager.load("sound/kill.wav", Sound.class);
        manager.load("sound/jump.wav", Sound.class);
        manager.load("sound/powerup.wav", Sound.class);
        manager.load("sound/levelcomplete.wav", Sound.class);
        manager.load("sound/hit-flesh-01-266311.wav", Sound.class);
        manager.load("sound/sword-hit-7160.wav", Sound.class);
        manager.load("sound/hurt.wav", Sound.class);
        manager.load("sound/death.wav", Sound.class);
        manager.load("sound/gameover.wav", Sound.class);

        loadProgress = 0f;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Actualización de progreso
        loadProgress = game.manager.getProgress();

        // Comprobación de animación
        if (!animationReady && areValkyriaFramesLoaded()) {
            animationReady = true;
            animationTime = 0f;
            positionX = 100f;
        }

        // Limpia pantalla
        ScreenUtils.clear(new Color(0.02f, 0.05f, 0.08f, 1));

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.textBatch.setProjectionMatrix(game.textCamera.combined);
        game.shapeRenderer.setProjectionMatrix(game.camera.combined);

        // Dibuja la animación de Valkyria si está lista
        if (animationReady) {
            animationTime += delta;
            animationSwitchTime += delta;

            if (animationSwitchTime > animationSwitchInterval) {
                isRunning = !isRunning;
                animationSwitchTime = 0f;
                animationTime = 0f;
            }

            if (movingForward) {
                positionX += movementSpeed * delta;
                if (positionX > 500) movingForward = false;
            } else {
                positionX -= movementSpeed * delta;
                if (positionX < 100) movingForward = true;
            }

            String animationFolder = isRunning ? "running" : "slashing";
            int totalFrames = 12;

            int frameIndex = (int)(animationTime * 10) % totalFrames;
            String frameStr = String.format("%03d", frameIndex);
            String framePath = "player/Valkyria/" + animationFolder + "/0_Valkyrie_" +
                (isRunning ? "Running" : "Slashing") + "_" + frameStr + ".png";

            if (game.manager.isLoaded(framePath)) {
                currentFrame = game.manager.get(framePath, Texture.class);

                game.batch.begin();
                game.batch.draw(
                    currentFrame,
                    positionX, 150,
                    128, 128,
                    0, 0,
                    currentFrame.getWidth(),
                    currentFrame.getHeight(),
                    false, true
                );
                game.batch.end();
            }
        }

        // Dibuja barra de progreso (esto siempre)
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(new Color(0.0f, 0.2f, 0.0f, 1)); // borde verde oscuro
        game.shapeRenderer.rect(90, 290, 620, 100);
        game.shapeRenderer.setColor(new Color(0.05f, 0.1f, 0.1f, 1)); // fondo barra
        game.shapeRenderer.rect(100, 300, 600, 80);
        game.shapeRenderer.setColor(new Color(0.0f, 0.6f, 0.4f, 1)); // barra verde brillante
        game.shapeRenderer.rect(110, 310, 580 * loadProgress, 60);
        game.shapeRenderer.end();


        // Cambio de pantalla
        if (game.manager.update()) {
            game.setScreen(new MainMenuScreen(game));
            this.dispose();
        }
    }



    private boolean areValkyriaFramesLoaded() {
        for (int i = 0; i < 12; i++) {
            String frame = String.format("player/Valkyria/running/0_Valkyrie_Running_%03d.png", i);
            if (!game.manager.isLoaded(frame)) return false;
        }
        for (int i = 0; i < 12; i++) {
            String frame = String.format("player/Valkyria/slashing/0_Valkyrie_Slashing_%03d.png", i);
            if (!game.manager.isLoaded(frame)) return false;
        }
        return true;
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

package com.mygdx.swordbound;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ogre extends WalkingCharacter {
    static final float RUN_SPEED = 80f;
    static final float RUN_ACCELERATION = 120f;
    static final float DETECTION_DISTANCE = 400f;

    AssetManager manager;
    Texture currentFrame;
    Player player;
    boolean detected;
    float animationFrame = 0;

    boolean attacking = false;
    float attackFrame = 0;

    public Ogre(int x, int y, AssetManager manager, Player player) {
        setBounds(x, y, 64, 116);
        this.manager = manager;
        this.player = player;
        currentFrame = manager.get("enemies/ogre/walking/0_Ogre_Walking_000.png", Texture.class);
        lookLeft = true;
        detected = false;
    }

    @Override
    public void act(float delta) {
        if (dead) {
            animationFrame += delta * 30.f;
            speed.x = 0f;

            if (animationFrame >= 15) {
                this.remove();
            } else {
                int frame = (int) animationFrame;
                String frameStr = String.format("%03d", frame);
                currentFrame = manager.get("enemies/ogre/dying/0_Ogre_Dying_" + frameStr + ".png", Texture.class);
            }
            return;
        }

        float distanceToPlayer = Math.abs(player.getX() - getX());
        detected = distanceToPlayer < DETECTION_DISTANCE;

        if (detected) {
            // Perseguir al jugador
            lookLeft = player.getX() < getX();
            float dir = lookLeft ? -1 : 1;
            speed.x += dir * RUN_ACCELERATION * delta;
            if (Math.abs(speed.x) > RUN_SPEED) speed.x = dir * RUN_SPEED;

            // Iniciar ataque si está muy cerca
            if (distanceToPlayer < 60 && !attacking) {
                attacking = true;
                attackFrame = 0;
                speed.x = 0;
                manager.get("sound/sword-hit-7160.wav", Sound.class).play(1.0f);
            }
        } else {
            // Patrullar
            if (!lookLeft) {
                speed.x += RUN_ACCELERATION * delta;
                if (speed.x > RUN_SPEED) speed.x = RUN_SPEED;
                if (map.isSolid((int)(getX() + getWidth()/2 + delta*speed.x), (int)getY())) lookLeft = true;
            } else {
                speed.x -= RUN_ACCELERATION * delta;
                if (speed.x < -RUN_SPEED) speed.x = -RUN_SPEED;
                if (map.isSolid((int)(getX() - getWidth()/2 + delta*speed.x), (int)getY())) lookLeft = false;
            }
        }

        super.act(delta);

        // Animaciones
        if (attacking) {
            attackFrame += delta * 15;
            if (attackFrame >= 12) {
                attacking = false;
                attackFrame = 0;
            }
            int frame = (int) attackFrame;
            String frameStr = String.format("%03d", frame);
            currentFrame = manager.get("enemies/ogre/slashing/0_Ogre_Slashing_" + frameStr + ".png", Texture.class);
        } else if (!falling) {
            animationFrame += delta * 10.f;
            if (animationFrame >= 24.f) animationFrame = 0;
            int frame = (int) animationFrame;
            String frameStr = String.format("%03d", frame);
            currentFrame = manager.get("enemies/ogre/walking/0_Ogre_Walking_" + frameStr + ".png", Texture.class);
        }
    }

    @Override
    public void kill() {
        super.kill();
        animationFrame = 0f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, getX() - getWidth()*0.5f - map.scrollX - (lookLeft ? 44 : 12),
            getY() - getHeight()*0.5f, 128, 128, 0, 0, 680, 472, lookLeft, true);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.setColor(com.badlogic.gdx.graphics.Color.ORANGE);
        shapes.rect(getX() - getWidth() * 0.5f - map.scrollX, getY() - getHeight() * 0.5f, getWidth(), getHeight());
    }

    public boolean isAttacking() {
        return attacking && attackFrame > 5 && attackFrame < 10;
    }
}

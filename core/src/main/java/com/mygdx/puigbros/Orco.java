package com.mygdx.puigbros;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Orco extends WalkingCharacter {
    static final float RUN_SPEED = 120f;
    static final float RUN_ACCELERATION = 150f;
    static final float DETECTION_DISTANCE = 400f;

    AssetManager manager;
    Texture currentFrame;
    Player player;

    float animationFrame = 0;
    float attackFrame = 0;

    int health = 2;
    boolean attacking = false;
    boolean hasRevived = false;
    boolean reviving = false;
    float reviveTimer = 0;

    public Orco(float x, float y, AssetManager manager, Player player) {
        setBounds(x, y, 60, 110);
        this.manager = manager;
        this.player = player;
        currentFrame = manager.get("enemies/orco/walking/0_Orc_Walking_000.png", Texture.class);
        lookLeft = true;
    }

    @Override
    public void act(float delta) {
        if (dead) {
            animationFrame += delta * 15f;
            int frame = (int) animationFrame;
            if (frame >= 15) {
                if (!hasRevived) {
                    hasRevived = true;
                    dead = false;
                    reviving = true;
                    reviveTimer = 1f; // 1 segundo de invulnerabilidad
                    animationFrame = 0;
                } else {
                    this.remove();
                    return;
                }
            } else {
                String frameStr = String.format("%03d", frame);
                currentFrame = manager.get("enemies/orco/dying/0_Orc_Dying_" + frameStr + ".png", Texture.class);
            }
            return;
        }

        if (reviving) {
            reviveTimer -= delta;
            if (reviveTimer <= 0f) {
                reviving = false;
            }
        }

        float distanceToPlayer = Math.abs(player.getX() - getX());
        boolean detected = distanceToPlayer < DETECTION_DISTANCE;

        if (detected) {
            // Corre atacando
            lookLeft = player.getX() < getX();
            float dir = lookLeft ? -1 : 1;
            speed.x += dir * RUN_ACCELERATION * delta;
            if (Math.abs(speed.x) > RUN_SPEED) speed.x = dir * RUN_SPEED;
            attacking = true;
        } else {
            // Patrulla
            attacking = false;
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
            attackFrame += delta * 12f;
            if (attackFrame >= 12) attackFrame = 0;
            String frameStr = String.format("%03d", (int) attackFrame);
            currentFrame = manager.get("enemies/orco/runSlashing/0_Orc_Run Slashing_" + frameStr + ".png", Texture.class);
        } else {
            animationFrame += delta * 10f;
            if (animationFrame >= 24f) animationFrame = 0;
            String frameStr = String.format("%03d", (int) animationFrame);
            currentFrame = manager.get("enemies/orco/walking/0_Orc_Walking_" + frameStr + ".png", Texture.class);
        }
    }

    public boolean isAttacking() {
        return attacking && !reviving;
    }

    @Override
    public void kill() {
        if (reviving || dead) return;
        health--;
        if (health <= 0) {
            super.kill();
            animationFrame = 0;
            manager.get("sound/death.wav", Sound.class).play(1.0f);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (reviving) {
            float blink = (reviveTimer * 10) % 2;
            if (blink >= 1) return; // parpadeo
        }
        batch.draw(currentFrame, getX() - getWidth()/2 - map.scrollX - (lookLeft ? 44 : 12),
            getY() - getHeight()/2, 128, 128, 0, 0, 680, 472, lookLeft, true);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.setColor(reviving ? com.badlogic.gdx.graphics.Color.YELLOW : com.badlogic.gdx.graphics.Color.RED);
        shapes.rect(getX() - getWidth()/2 - map.scrollX, getY() - getHeight()/2, getWidth(), getHeight());
    }
}

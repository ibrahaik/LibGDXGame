package com.mygdx.puigbros;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Player extends WalkingCharacter
{
    boolean canDoubleJump = true;
    boolean groundedLastFrame = false;
    static final float JUMP_IMPULSE = -600f;
    static final float RUN_SPEED = 300f;
    static final float BRAKE_SPEED = 600F;
    static final float STOP_SPEED = 30f;
    static final float RUN_ACCELERATION = 1000f;
    static final float INVULNERABILITY_DURATION = 20f;

    AssetManager manager;
    ButtonLayout joypad;

    Texture currentFrame;

    float animationFrame = 0;
    float invulnerability = 0f;

    boolean slashing = false;
    float slashingFrame = 0;
    float slashingCooldown = 0;
    static final float SLASH_COOLDOWN_TIME = 0.5f;

    int health = 4;
    boolean recentlyHit = false;
    float hitCooldown = 0f;
    static final float HIT_COOLDOWN_TIME = 1f;

    ShapeRenderer shapeRenderer;
    public Player(AssetManager manager)
    {
        setBounds(400,40,20, 100);
        this.manager = manager;
        currentFrame = manager.get("player/Idle (1).png", Texture.class);
        invulnerability = 0.f;
    }

    public void setJoypad(ButtonLayout joypad) {
        this.joypad = joypad;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        int tileX = (int) (getX() / TileMap.TILE_SIZE);
        int tileY = (int) (getY() / TileMap.TILE_SIZE);
        int tileId = map.getTile(tileX, tileY);
        if (tileId == 17 || tileId == 18) kill();

        if (!falling && groundedLastFrame == false) canDoubleJump = true;
        groundedLastFrame = !falling;

        if(getX() < getWidth() / 2) setX(getWidth() / 2);

        if (slashingCooldown > 0) slashingCooldown -= delta;

        if (joypad.consumePush("Slash") && !slashing && !falling && slashingCooldown <= 0) {
            slashing = true;
            slashingFrame = 0;
            slashingCooldown = SLASH_COOLDOWN_TIME;
            manager.get("sound/sword-hit-7160.wav", Sound.class).play(1.0f);
        }

        if (recentlyHit) {
            hitCooldown -= delta;
            if (hitCooldown <= 0) recentlyHit = false;
        }

        if(dead) {
            animationFrame += 10.f * delta;
            int frameTexture = (int)animationFrame;
            if(frameTexture >= 15) frameTexture = 14;
            String frameStr = String.format("%03d", frameTexture);
            currentFrame = manager.get("player/Valkyria/Dying/0_Valkyrie_Dying_" + frameStr + ".png", Texture.class);
            speed.x = 0f;
        } else if(slashing) {
            slashingFrame += 45 * delta;
            if(slashingFrame >= 12) {
                slashingFrame = 0;
                slashing = false;
            }
            int frame = (int) slashingFrame;
            String frameStr = String.format("%03d", frame);
            currentFrame = manager.get("player/Valkyria/slashing/0_Valkyrie_Slashing_" + frameStr + ".png", Texture.class);
        } else {
            if(invulnerability > 0.f) invulnerability -= delta;

            if(falling) {
                int frameNum;
                String frameStr;

                if(speed.y < 0) {
                    float base_impulse = -JUMP_IMPULSE;
                    float current_impulse = -speed.y;
                    animationFrame = 0 + ((base_impulse - current_impulse) / 32);
                    if(animationFrame > 5) animationFrame = 5;
                    if(animationFrame < 0) animationFrame = 0;

                    frameNum = (int) animationFrame;
                    frameStr = String.format("%03d", frameNum);
                    currentFrame = manager.get("player/Valkyria/jumpingStart/0_Valkyrie_Jump Start_" + frameStr + ".png", Texture.class);
                } else {
                    animationFrame += 10 * delta;
                    if(animationFrame > 5) animationFrame = 0;
                    frameNum = (int) animationFrame;
                    frameStr = String.format("%03d", frameNum);
                    currentFrame = manager.get("player/Valkyria/jumpingLoop/0_Valkyrie_Jump Loop_" + frameStr + ".png", Texture.class);
                }
            } else if((speed.x < 0.1f && speed.x > -0.1f)) {
                animationFrame += 10 * delta;
                if (animationFrame >= 10.f) animationFrame -= 10.f;
                currentFrame = manager.get("player/Idle ("+(int)(animationFrame+1)+").png", Texture.class);
            } else {
                animationFrame += 10 * delta;
                if (animationFrame >= 12.f) animationFrame -= 12.f;
                int frameNum = (int) animationFrame;
                String frameStr = String.format("%03d", frameNum);
                currentFrame = manager.get("player/Valkyria/running/0_Valkyrie_Running_" + frameStr + ".png", Texture.class);
            }

            if (joypad.consumePush("Jump")) {
                if (!falling) {
                    jump(0.5f);
                    manager.get("sound/jump.wav", Sound.class).play();
                } else if (canDoubleJump) {
                    jump(1.f);
                    canDoubleJump = false;
                    manager.get("sound/jump.wav", Sound.class).play();
                }
            }

            if(!falling) {
                if (joypad.isPressed("Right")) {
                    lookLeft = false;
                    speed.x += RUN_ACCELERATION * delta;
                    if (speed.x > RUN_SPEED) speed.x = RUN_SPEED;
                } else if (joypad.isPressed("Left")) {
                    lookLeft = true;
                    speed.x -= RUN_ACCELERATION * delta;
                    if (speed.x < -RUN_SPEED) speed.x = -RUN_SPEED;
                } else {
                    if(speed.x < 0f) {
                        if(speed.x < -STOP_SPEED) speed.x += delta * BRAKE_SPEED;
                        else speed.x = 0f;
                    } else if (speed.x > 0f) {
                        if(speed.x > STOP_SPEED) speed.x -= delta * BRAKE_SPEED;
                        else speed.x = 0f;
                    }
                }
            }
        }
    }

    public boolean isSlashing() {
        return slashing;
    }

    public int getHealth() {
        return health;
    }

    public Rectangle getSlashHitbox() {
        if (!slashing) return null;
        float slashWidth = 60;
        float slashHeight = 80;
        float xOffset = lookLeft ? -getWidth() / 2 - slashWidth : getWidth() / 2;
        return new Rectangle(getX() + xOffset, getY() - slashHeight / 2, slashWidth, slashHeight);
    }

    public boolean isInAttackFrame() {
        return slashing && slashingFrame >= 4 && slashingFrame <= 8;
    }

    public void takeDamage() {
        takeDamage(1);
    }

    public void takeDamage(int amount) {
        if (!recentlyHit && !dead && invulnerability <= 0.f) {
            health -= amount;
            hitCooldown = HIT_COOLDOWN_TIME;
            recentlyHit = true;

            manager.get("sound/hurt.wav", Sound.class).play();

            jumpSilently(0.4f);
            // Impulso hacia atrás
            speed.x = lookLeft ? 250f : -250f;

            if (health <= 0) kill();
        }
    }

    public void jumpSilently(float scale) {
        speed.y = JUMP_IMPULSE * scale;
    }

    public void jump(float strength) {
        speed.y = JUMP_IMPULSE * strength;
    }

    @Override
    public void kill() {
        if(!dead) {
            super.kill();
            animationFrame = 0;
            manager.get("sound/death.wav", Sound.class).play();
        }
    }

    public void getInvulnerability() {
        invulnerability = INVULNERABILITY_DURATION;
    }

    public boolean hasInvulnerability() {
        return invulnerability > 0.f;
    }

    float getAnimationFrame() {
        return animationFrame;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (recentlyHit) {
            float t = (hitCooldown * 10) % 2;
            if (t >= 1) return;
        }

        batch.draw(currentFrame, getX() - getWidth()*0.5f - map.scrollX - (lookLeft ? 28 : 50),
            getY() - getHeight()*0.5f,
            128, 128, 0, 0, 669, 569, lookLeft, true);

        // Dibujar barra de vida encima del jugador
        // Dibujar barra de vida estilo HUD
        float barWidth = 200;
        float barHeight = 12;
        float x = 40;
        float y = 50;


        batch.end();
        if (shapeRenderer != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.rect(x, y, barWidth, barHeight);
            // Color adaptativo según la vida
            Color healthColor;
            if (health >= 3) {
                healthColor = Color.GREEN;
            } else if (health == 2) {
                healthColor = Color.YELLOW;
            } else {
                healthColor = Color.RED;
            }

            shapeRenderer.setColor(healthColor);
            shapeRenderer.rect(x, y, (barWidth * health) / 4f, barHeight);

            shapeRenderer.end();
        }

        batch.begin();
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.setColor(Color.NAVY);
        shapes.rect(getX() - getWidth() * 0.5f - map.scrollX, getY() - getHeight() * 0.5f, getWidth(), getHeight());
        if (slashing) {
            Rectangle hitbox = getSlashHitbox();
            if (hitbox != null) {
                shapes.setColor(Color.RED);
                shapes.rect(hitbox.x - map.scrollX, hitbox.y, hitbox.width, hitbox.height);
            }
        }
    }
}

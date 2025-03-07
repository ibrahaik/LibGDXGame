package com.mygdx.puigbros;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Dino extends WalkingCharacter
{

    static final float RUN_SPEED = 120f;
    static final float RUN_ACCELERATION = 200f;
    boolean lookRight = false;

    AssetManager manager;
    Texture currentFrame;
    Player player;

    float animationFrame = 0;

    public Dino(int x, int y, AssetManager manager, Player player)
    {
        setBounds(x,y,64, 116);
        this.manager = manager;
        this.player = player;
        currentFrame = manager.get("dino/Walk (1).png", Texture.class);
    }

    @Override
    public void act(float delta) {

        if(Math.abs(player.getX() - getX()) > 500) return;

        super.act(delta);

        if(dead)
        {
            animationFrame += delta * 6.f;
            int textureFrame = (int) animationFrame+1;
            if(textureFrame >= 9)
                textureFrame = 8;
            currentFrame = manager.get("dino/Dead ("+textureFrame+").png", Texture.class);

            speed.x = 0f;

            if(animationFrame >= 15)
            {
                this.remove();
            }

        }
        else if(!falling)
        {
            animationFrame += delta * 6.f;
            if(animationFrame >= 10.f)
                animationFrame -= 10.f;
            currentFrame = manager.get("dino/Walk ("+(int)(animationFrame+1)+").png", Texture.class);

            if(lookRight)
            {

                speed.x += RUN_ACCELERATION * delta;
                if(speed.x > RUN_SPEED)
                {
                    speed.x = RUN_SPEED;
                }

                if(map.isSolid((int)(getX() + getWidth()/2 + delta * speed.x), (int)(getY() + getHeight()*0.25f)))
                {
                    lookRight = false;
                }
            }
            else
            {
                speed.x -= RUN_ACCELERATION * delta;
                if(speed.x < -RUN_SPEED)
                {
                    speed.x = -RUN_SPEED;
                }

                if(map.isSolid((int)(getX() - getWidth()/2 + delta * speed.x), (int)(getY() + getHeight()*0.25f)))
                {
                    lookRight = true;
                }
            }
        }
        else
        {
            currentFrame = manager.get("dino/Walk (1).png", Texture.class);
        }
    }

    @Override
    public void kill() {
        super.kill();
        speed.y = -100;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame, getX() - getWidth()*0.5f - map.scrollX - (lookRight ? 12 : 44), getY() - getHeight()*0.5f, 128, 128, 0, 0, 680, 472, !lookRight, true);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        //super.drawDebug(shapes);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.GREEN);
        shapes.rect(getX() - getWidth()*0.5f - map.scrollX, getY() - getHeight()*0.5f, getWidth(), getHeight());
        shapes.end();
    }

}

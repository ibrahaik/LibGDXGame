package com.mygdx.puigbros;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Dino extends WalkingCharacter
{

    static final float RUN_SPEED = 120f;
    static final float RUN_ACCELERATION = 200f;
    boolean lookRight = false;

    Texture[] walkTextures;
    Texture[] deadTextures;
    Texture currentFrame;

    float animationFrame = 0;

    public Dino(int x, int y)
    {
        setBounds(x,y,64, 64);
        loadTextures();
    }

    void loadTextures()
    {
        walkTextures = new Texture[10];

        for (int i = 0; i < 10; i++)
        {
            walkTextures[i] = new Texture("dino/Walk (" +(i+1)+").png");
        }

        deadTextures = new Texture[8];

        for (int i = 0; i < 8; i++)
        {
            deadTextures[i] = new Texture("dino/Dead (" +(i+1)+").png");
        }

        currentFrame = walkTextures[0];
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(dead)
        {

        }
        else if(!falling)
        {
            animationFrame += delta * 4.f;
            if(animationFrame >= 10.f)
                animationFrame = 9.f;
            currentFrame = walkTextures[(int)animationFrame];

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
            currentFrame = walkTextures[0];
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

        batch.draw(currentFrame, getX() - getWidth()*0.5f - map.scrollX - (lookRight ? 28 : 50), getY() - getHeight()*0.5f, 128, 128, 0, 0, 669, 569, !lookRight, true);
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

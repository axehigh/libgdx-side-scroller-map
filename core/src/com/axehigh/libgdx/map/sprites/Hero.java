package com.axehigh.libgdx.map.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;


import static com.axehigh.libgdx.map.MapGame.*;

public class Hero extends Sprite {
    public Body b2body;
    private World world;

    public Hero(World world, float x, float y) {
        this.world = world;
        this.setTexture(new Texture("hero.png"));
        defineHeroInBox2d();
        setBounds(x, y, getTexture().getWidth() / PPM, getTexture().getHeight() / PPM);
    }


    public void defineHeroInBox2d() {
        BodyDef bdef = new BodyDef();
        bdef.position.set((32) / PPM, (32) / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = MARIO_BIT;
        fdef.filter.maskBits = GROUND_BIT |
                COIN_BIT |
                BRICK_BIT |
                ENEMY_BIT |
                OBJECT_BIT |
                ENEMY_HEAD_BIT |
                ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 6 / PPM), new Vector2(2 / PPM, 6 / PPM));
//        fdef.filter.categoryBits = MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        setRegion(getTexture());
    }

    public void draw(Batch batch) {
        super.draw(batch);
//        batch.draw(this.getTexture(), getX(), getY());
//        batch.draw(texture2, this.getX(), this.getY());
    }

    public void dispose() {
        this.getTexture().dispose();
    }

    public void update(float dt) {
//        this.setX(0);
//        this.setY(0);

        Gdx.app.log("Hero", "Coordinates:: " + this.getX() + ", " + this.getY());
//        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

    }
}

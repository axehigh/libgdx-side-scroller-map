//package com.axehigh.libgdx.map.tools;
//
//import com.axehigh.libgdx.map.GameScreen;
//import com.axehigh.libgdx.map.MapGame;
//import com.badlogic.gdx.maps.MapObject;
//import com.badlogic.gdx.maps.objects.RectangleMapObject;
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.FixtureDef;
//import com.badlogic.gdx.physics.box2d.PolygonShape;
//import com.badlogic.gdx.physics.box2d.World;
//
//public class WorldCreator {
//
//
//    public WorldCreator (GameScreen screen) {
//        World world = screen.getWorld();
//        TiledMap map = screen.getMap();
//        //create body and fixture variables
//        BodyDef bdef = new BodyDef();
//        PolygonShape shape = new PolygonShape();
//        FixtureDef fdef = new FixtureDef();
//        Body body;
//
//        //create ground bodies/fixtures
//        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MapGame.PPM, (rect.getY() + rect.getHeight() / 2) / MapGame.PPM);
//
//            body = world.createBody(bdef);
//
//            shape.setAsBox(rect.getWidth() / 2 / MapGame.PPM, rect.getHeight() / 2 / MapGame.PPM);
//            fdef.shape = shape;
//            body.createFixture(fdef);
//        }
//
////        //create pipe bodies/fixtures
////        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
////            Rectangle rect = ((RectangleMapObject) object).getRectangle();
////
////            bdef.type = BodyDef.BodyType.StaticBody;
////            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MapGame.PPM, (rect.getY() + rect.getHeight() / 2) / MapGame.PPM);
////
////            body = world.createBody(bdef);
////
////            shape.setAsBox(rect.getWidth() / 2 / MapGame.PPM, rect.getHeight() / 2 / MapGame.PPM);
////            fdef.shape = shape;
////            fdef.filter.categoryBits = MapGame.OBJECT_BIT;
////            body.createFixture(fdef);
////        }
////
////        //create brick bodies/fixtures
////        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
////            //new Brick(screen, object);
////        }
////
////        //create coin bodies/fixtures
////        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
////
////            //new Coin(screen, object);
////        }
//
//
//    }
//
//
//}

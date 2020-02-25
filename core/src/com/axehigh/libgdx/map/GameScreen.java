package com.axehigh.libgdx.map;

import com.axehigh.libgdx.map.tools.MapPropertiesWrapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    private final int screenHeight;
    private final int screenWidth;
    MapGame game;

    SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewPort;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    float viewPortWidth;
    float viewPortHeight;
    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
//    private WorldCreator creator;

    // For test
    private Texture texture;


    float PPM = 128;
    float scrollSpeed = 800 / PPM;

    MapPropertiesWrapper mapPropertiesWrapper;

    public GameScreen(MapGame mapGame) {
        this.game = mapGame;

        viewPortWidth = 1600;
        viewPortHeight = 1200;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
//        viewPort = new FitViewport(viewPortWidth / PPM, viewPortHeight / PPM, camera);
        viewPort = new StretchViewport(viewPortWidth / PPM, viewPortHeight / PPM, camera);

        batch = new SpriteBatch();
        texture = new Texture("badlogic.jpg");

        //Load our map and setup our map mapRenderer
        maploader = new TmxMapLoader();
        map = maploader.load("map02.tmx");
//        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / MapGame.PPM);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        mapPropertiesWrapper = new MapPropertiesWrapper(map);

        //initially set our gamcam to be centered correctly at the start of of map
//        camera.setToOrtho(false, viewPortWidth, viewPortHeight);
//        camera.position.set(viewPortWidth / 2, viewPortWidth / 2, 0);
        camera.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

//        creator = new WorldCreator(this);

        camera.update();

        // Debug setup
        Gdx.app.log("Setup", "MapSize: " + mapPropertiesWrapper.toString());
        Gdx.app.log("Setup", "ViewPort screen width: " + viewPort.getScreenWidth());
        Gdx.app.log("Setup", "ViewPort world width: " + viewPort.getWorldWidth());
        Gdx.app.log("Setup", "screen width: " + screenWidth);
    }


    public void handleInput(float dt) {
//        if (Gdx.input.isTouched()) {
//            Gdx.app.log("handleinput", "positionX: " + gamecam.position.x);
//            gamecam.position.x += 100 * dt;
//        }

        if (Gdx.input.isTouched()) {

            Gdx.app.log("handleinput", "Touch: " + Gdx.input.getX());
            Gdx.app.log("handleinput", "positionX: " + camera.position.x);

            float newCameraPositionX = camera.position.x;
            if (inputLeftSide()) {
                moveCameraRight(dt, newCameraPositionX);
            }

            if (inputRightSide(Gdx.input.getX())) {
                moveCameraLeft(dt, newCameraPositionX);
            }

        }

    }

    private void moveCameraLeft(float dt, float newCameraPositionX) {
        newCameraPositionX -= scrollSpeed * dt;
        Gdx.app.log("Move", "Left: " + newCameraPositionX);
        if (newCameraPositionX > (viewPort.getWorldWidth() / 2)) {
            camera.position.x = newCameraPositionX;
        }
    }

    private void moveCameraRight(float dt, float newCameraPositionX) {
        newCameraPositionX += scrollSpeed * dt;
        Gdx.app.log("Move", "Right: " + newCameraPositionX);
//        if (newCameraPositionX < mapPropertiesWrapper.getTotalMapWidthInPixels() - viewPort.getWorldWidth() / 2) {
        if (newCameraPositionX < mapPropertiesWrapper.getMapWidth() - viewPort.getWorldWidth() / 2) {
            camera.position.x = newCameraPositionX;
        }

    }

    private boolean inputLeftSide() {
        return Gdx.input.getX() <= Gdx.graphics.getWidth() / 2;
    }

    private boolean inputRightSide(float x) {
        return x > Gdx.graphics.getWidth() / 2;
    }

    public void update(float dt) {
        handleInput(dt);
        //Gdx.app.log(GameGlobal.tagMethod, "maplayers: " + map.getLayers().size());
        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        //update our camera with correct coordinates after changes
        camera.update();
//        tell our renderer to draw only what our camera can see in our game world.
        mapRenderer.setView(camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        Gdx.app.log(GameGlobal.tagMethod, "delta: " + delta);

        update(delta);
        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        mapRenderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, camera.combined);

//        batch.setProjectionMatrix(camera.combined);

//        batch.begin();
        //Set our batch to now draw what the Hud camera sees.
//        batch.draw(texture, 0, 0);
//        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
//        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
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
        map.dispose();
        mapRenderer.dispose();
//        world.dispose();
//        b2dr.dispose();
    }

    public World getWorld() {
//        return world;
        return null;
    }

    public TiledMap getMap() {
        return map;
    }
}

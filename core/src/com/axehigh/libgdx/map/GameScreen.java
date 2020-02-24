package com.axehigh.libgdx.map;

import com.axehigh.libgdx.map.tools.WorldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    MapGame game;

    SpriteBatch batch;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private WorldCreator creator;

    // For test
    private Texture texture;


    public GameScreen(MapGame mapGame) {
        this.game = mapGame;
        gamecam = new OrthographicCamera();
        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(MapGame.V_WIDTH / MapGame.PPM, MapGame.V_HEIGHT / MapGame.PPM, gamecam);

        batch = new SpriteBatch();
        texture = new Texture("badlogic.jpg");

        //Load our map and setup our map mapRenderer
        maploader = new TmxMapLoader();
        map = maploader.load("map01.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / MapGame.PPM);

        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        gamecam.position.set(0, 200, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
//        world = new World(new Vector2(0, -10), true);
        //allows for debug lines of our box2d world.
//        b2dr = new Box2DDebugRenderer();

//        creator = new WorldCreator(this);


    }

    public void handleInput(float dt) {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
//            Gdx.app.log("handleinput", "positionX: " + gamecam.position.x);
//            gamecam.position.x = gamecam.position.x + 100 * dt;
//        }

        if (Gdx.input.isTouched()) {
            Gdx.app.log("handleinput", "positionX: " + gamecam.position.x);
            gamecam.position.x += 100 * dt;
        }
    }

    public void update(float dt) {
        handleInput(dt);
        //Gdx.app.log(GameGlobal.tagMethod, "maplayers: " + map.getLayers().size());
        //takes 1 step in the physics simulation(60 times per second)
//        world.step(1 / 60f, 6, 2);


        //update our gamecam with correct coordinates after changes
        gamecam.update();
//        tell our renderer to draw only what our camera can see in our game world.
        mapRenderer.setView(gamecam);
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
//        b2dr.render(world, gamecam.combined);

        batch.setProjectionMatrix(gamecam.combined);

        batch.begin();
        //Set our batch to now draw what the Hud camera sees.
        batch.draw(texture, 0, 0);
//        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        world.dispose();
        b2dr.dispose();
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }
}

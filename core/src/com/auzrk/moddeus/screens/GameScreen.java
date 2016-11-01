/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.screens;

import com.auzrk.moddeus.CollisionHandler;
import com.auzrk.moddeus.Moddeus;
import com.auzrk.moddeus.entities.DrawableEntity;
import com.auzrk.moddeus.entities.Entity;
import com.auzrk.moddeus.entities.Player;
import com.auzrk.moddeus.entities.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;

/**
 *
 * @author Aurzrk
 */
public class GameScreen extends AbstractScreen{
    
    //Phys vars
    public World world; //Physics context
    float worldTime;    //Accumulator for how many physics steps are due
    final float worldStep; //Fixed time step for physics
    
    //Entities cannot be removed mid-frame due to nullpointer exceptions and physics bodies,
    //so pre and dead lists are needed
    public ArrayList<Entity> preEntList; //Entities to be added to entList
    public ArrayList<Entity> entList; //List containing all current entities in the game
    public ArrayList<Entity> deadEntList; //Entities to be removed from entList
    public ArrayList<DrawableEntity> drawList; //Entities to be drawn
    
    public String levelname; //Level to be loaded
    TiledMap map; //Stores the loaded level
    TiledMapTileLayer background, ground, foreground, spawns; //Seperate layers
    OrthogonalTiledMapRenderer renderer; //Renderer for tile map and entities
    
    float timeScale; //To slow down time
    
    Batch batch; //Render batch
    
    OrthographicCamera cam;
    
    public Player player;
    
    //DEBUG
    Box2DDebugRenderer debug;
    boolean debugOn;
    float framerateCounter;
    float framerate;
    
    public GameScreen(Moddeus game, String levelname) {
        super(game);
        this.levelname = levelname;
        
        world = new World(new Vector2(0, -325), true); //Init physics with gravity down
        worldTime = 0;
        worldStep = 1/300f; //Step forward physics sim at increments of 1/300 of a second
        world.setContactListener(new CollisionHandler());
        
        
        preEntList = new ArrayList<Entity>(); //Init ent lists
        entList = new ArrayList<Entity>();
        deadEntList = new ArrayList<Entity>();
        drawList = new ArrayList<DrawableEntity>();
        
        map = new TmxMapLoader().load("level/" + levelname + ".tmx"); //Load in map
        
        background = (TiledMapTileLayer) map.getLayers().get("background"); //Render before anything else
        ground = (TiledMapTileLayer) map.getLayers().get("ground"); //Physical (with box2d bodies) layer rendered before entities
        foreground = (TiledMapTileLayer) map.getLayers().get("foreground"); //Rendered on top of entities
        spawns = (TiledMapTileLayer) map.getLayers().get("spawns"); //The spawn locations for any entities on the map
        
        for(int x = 0; x <= ground.getWidth(); x++){            //Create a tile entity for each cell of the ground layer
            for(int y = 0; y <= ground.getHeight(); y++){       
                Cell cell = ground.getCell(x, y);
                if(cell != null){
                   addEnt(new Tile(x, y, cell, this, ((String) cell.getTile().getProperties().get("type"))));      //Tiles will be added to entList in the next frame
                }
            }
        }
        
        //I could put this in the above loop but I think clarity is more important than a tiny performance bonus
        for(int x = 0; x <= spawns.getWidth(); x++){    //spawn entities from the map into the game
            for(int y = 0; y <= spawns.getWidth(); y++){
                Cell cell = spawns.getCell(x, y);
                if(cell != null){
                    spawnEnt((String) cell.getTile().getProperties().get("type"), x, y);
                }
            }
        }
        
        renderer = new OrthogonalTiledMapRenderer(map, 1/16f); //Set to render map and set units to 1 unit = 16px
      
        timeScale = 1; //We just want normal time to start.
        
        batch = renderer.getBatch(); //Init drawing batch
        
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 24, 13.5f);
        
        for(Entity e : preEntList){ //Find the player on this level
            if(e instanceof Player){
                player = (Player) e;
            }
        }
        
        
        //DEBUG
        debug = new Box2DDebugRenderer();
        debugOn = false;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        
        framerateCounter += delta;
        if(framerateCounter > 0.5){
            framerate = 1/delta;
            framerateCounter = 0;
        }
        
        delta *= timeScale; //Scale time (if we want slow motion or to pause)
        
        Gdx.gl.glClearColor(0.8f, 0.8f, 1f, 1); //Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //
        
        //UPDATING ENTITIES
        for(Entity e : entList){            //Cycle through all active ents and update
            e.update(delta);
        }
        
        worldTime += delta;
        while(worldTime > worldStep){       //Step physics how ever many times it needs to be
            world.step(worldStep, 6, 2);    //6 and 2 are recommended by documentation as position and velocity iterations 
            worldTime -= delta;             // I haven't read into what they do properly or why those vals are default
        }
        
        
        renderer.setView(cam); 
        cam.position.x = player.pos.x;
        cam.position.y = player.pos.y;
        cam.update();                       //Update cam pos
        
        //BEGIN DRAWING
        batch.begin();
        
        renderer.renderTileLayer(background); //Render background
        renderer.renderTileLayer(ground); //Render ground
        
        renderer.renderTileLayer(spawns); //TESTING PURPOSES
        
        for(DrawableEntity e : drawList){ //Draw every active drawable entity
            batch.draw(e.getFrame(), e.pos.x, e.pos.y, 0, 0, e.width, e.height, 1, 1, e.rot);
           // System.out.println("Drawing " + e.toString());//TESTING
        }
        
        renderer.renderTileLayer(foreground); //render overlay
        
        
        batch.end();
        //FINISH DRAWING
        
        //DRAW UI
        game.batch.begin();
        
        game.font.draw(game.batch, "Health: " + String.valueOf(player.health), 0, Gdx.graphics.getHeight()-50);
        
        if(debugOn){
            game.font.draw(game.batch, "FPS: " + String.valueOf(framerate), 200, 200);
            Vector2 vel = player.body.getLinearVelocity();
            game.font.draw(game.batch, "Vel : " + String.valueOf(vel.x) + "," + String.valueOf(vel.y), 200, 300);
        }
        game.batch.end();
        
        // ADDING/REMOVING ENTITIES
        for(Entity e : preEntList){
            entList.add(e);
            if(e instanceof DrawableEntity){
                drawList.add( (DrawableEntity) e);
            }
        }
        preEntList.clear();
        
        for(Entity e : deadEntList){
            entList.remove(e);
            if(e instanceof DrawableEntity){
                drawList.remove(e);
            }
        }
        deadEntList.clear();
        //CHANGING ENTS FINISHED
        
        //DEBUG
        if(Gdx.input.isKeyJustPressed(Keys.B)){
            debugOn = !debugOn;
        }
        if(debugOn){
            debug.render(world, cam.combined);
        }
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
        super.dispose();
    }
    
   //END SCREEN FUNCTIONS
    
    public void addEnt(Entity e){
        preEntList.add(e);
    }
    
    public void delEnt(Entity e){
        deadEntList.add(e);
    }
    
    public void spawnEnt(String s, float x, float y){
        //Im forced to use a massive condition chain here as this java version doesnt support strings in switches... seriously java!?
        if(s.equalsIgnoreCase("player")){
            addEnt(new Player(new Vector2(x,y), this));
        }else{
            System.out.println( "'" + s + "'" + " doesn't exist as an entity!");
        }
    }
    
}

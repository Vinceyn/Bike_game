package ch.epfl.cs107.play.game.actor.bike;
 
import java.awt.Color;
import java.util.ArrayList;

import com.sun.glass.events.KeyEvent;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.CoinScore;
import ch.epfl.cs107.play.game.actor.general.NoFrictionTerrain;
import ch.epfl.cs107.play.game.actor.general.Pendulum;
import ch.epfl.cs107.play.game.actor.general.PickUp;
import ch.epfl.cs107.play.game.actor.general.Scale;
import ch.epfl.cs107.play.game.actor.general.SpeedCounter;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.actor.general.TrapSpikes;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;
 
public class BikeGame extends ActorGame {
	
	//We have every gameEntity of our game as argument
    private Terrain terrain;
    private Terrain terrain2;
    private Crate crate1;
    private Crate crate2;
    private Crate crate3;
    private Crate crate4;
    private Crate crate5;
    
    private Crate crate7;
    private Crate crate8;
    private Bike bike;
    private NoFrictionTerrain noFrictionTerrain1;
    private NoFrictionTerrain noFrictionTerrain2;
    private NoFrictionTerrain noFrictionTerrain3;
    private NoFrictionTerrain noFrictionTerrain4;
    
    private PickUp pickUp1;
    private PickUp pickUp2;
    private PickUp pickUp3;
    private FinishLine finishLine;
    private Pendulum pendulum1;
    private Pendulum pendulum2;
    private CoinScore coinScore;
    private TrapSpikes trapSpikes1;
    private TrapSpikes trapSpikes2;
    private Scale scale1;
    private Scale scale2;
    private SpeedCounter speedCounter;
    
    //Used to run the program
    private Window window;
    private FileSystem fileSystem;
    
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
    	//We initialise our window and our fileSystem
       this.window = window;
       this.fileSystem = fileSystem;
    	
    	//We initialise the world and the window
        super.begin(window, fileSystem);
       
        //We create the ArrayList of our terrain's points and add them to it
        ArrayList<Vector> points1 = new ArrayList<Vector>();
        points1.add(new Vector(-1000f, 1000f));
        points1.add(new Vector(-5f, 1500.0f));
        points1.add(new Vector(-5f, 1500.0f));
        points1.add(new Vector(-5.0f, 0.0f));
        points1.add(new Vector(11f, 0f));
        points1.add(new Vector(11f, -7f));
        points1.add(new Vector(-12f, -7f));
        points1.add(new Vector(-12f, -22.0f));
        points1.add(new Vector(16.0f, -22.0f));
        points1.add(new Vector(39.0f, -22.0f));
        points1.add(new Vector(52.0f, -14.0f));
        points1.add(new Vector(80.0f, -14.0f));
        points1.add(new Vector(80.0f, -18.0f));
        points1.add(new Vector(72.0f, -18.0f));
        points1.add(new Vector(72.0f, -22.0f));
        points1.add(new Vector(105.0f, -22.0f));
        points1.add(new Vector(112.0f, -18.0f));
        points1.add(new Vector(86.0f, -40.0f));
        points1.add(new Vector(70.0f, -40.0f));
        points1.add(new Vector(17f, -40.0f));
        points1.add(new Vector(17f, -87.0f));
        points1.add(new Vector(17f, -100.0f));
        points1.add(new Vector(35f, -100.0f));
        points1.add(new Vector(77f, -98.0f));
        points1.add(new Vector(95f, -120.0f));
        points1.add(new Vector(200f, -120.0f));
        points1.add(new Vector(200f, -1000.0f));
        points1.add(new Vector(-1000f, -1000f));
        
        ArrayList<Vector> points2 = new ArrayList<Vector>();
        points2.add(new Vector(1000f, 1000.0f));
        points2.add(new Vector(39f, 1000.0f));
        points2.add(new Vector(39f, -7f));
        points2.add(new Vector(39f, -14f)); 
        points2.add(new Vector(56, -3f));
        points2.add(new Vector(145f, -3f));
        points2.add(new Vector(145f, -22f));
        points2.add(new Vector(120f, -22f));
        points2.add(new Vector(90f, -50f));
        points2.add(new Vector(70f, -50f));
        points2.add(new Vector(70f, -60f));
        points2.add(new Vector(100f, -60f));
        points2.add(new Vector(100f, -64f));
        points2.add(new Vector(40f, -64f));
        points2.add(new Vector(35f, -92f));
        points2.add(new Vector(77f, -92f));
        points2.add(new Vector(77f, -80f));
        points2.add(new Vector(110f, -80f));
        points2.add(new Vector(110f, -105f));
        points2.add(new Vector(88f, -105f));
        points2.add(new Vector(95f, -110f));
        points2.add(new Vector(1000f, -110f));
        points2.add(new Vector(1000f,-40f));
        
        ArrayList<Vector> pointsNoFriction1 = new ArrayList<Vector>();
        pointsNoFriction1.add(new Vector(11f, 0f));
        pointsNoFriction1.add(new Vector(26f, -7f)); //to remove for nofriction
        pointsNoFriction1.add(new Vector(11f, -7f));
       
        ArrayList<Vector> pointsNoFriction2 = new ArrayList<Vector>();
        pointsNoFriction2.add(new Vector(39f, -7f));
        pointsNoFriction2.add(new Vector(14f, -14f));
        pointsNoFriction2.add(new Vector(39f, -14f));
        
        ArrayList<Vector> pointsNoFriction3 = new ArrayList<Vector>();
        pointsNoFriction3.add(new Vector(17f, -87.0f));
        pointsNoFriction3.add(new Vector(17f, -100.0f));
        pointsNoFriction3.add(new Vector(35f, -100.0f));
        
        ArrayList<Vector> pointsNoFriction4 = new ArrayList<Vector>();
        pointsNoFriction4.add(new Vector(40f, -64f));
        pointsNoFriction4.add(new Vector(25f, -68f));
        pointsNoFriction4.add(new Vector(35f, -92f));
        
        
        //We initialise every gameEntity of the game with desired values
        bike = new Bike(this, false, 0.0f, 0.0f, Color.WHITE, Color.WHITE, 0.10f , 0.17f ,1.0f, 0.0f, 10.f);
        speedCounter = new SpeedCounter(this, 0f, 0f, bike);
        
        
        //We create the terrain thanks to the polyline we previously made
        terrain = new Terrain(this,true, 0.f,0.f, true, points1, Color.GRAY, Color.BLACK, 0.15f,1.0f,0.0f);
        terrain2 = new Terrain(this,true, 0.f,0.f, true, points2, Color.GRAY, Color.BLACK, 0.15f,1.0f,0.0f);
        
        noFrictionTerrain1 = new NoFrictionTerrain(this,true, 0.f,0.f, true, pointsNoFriction1, Color.CYAN, Color.BLACK, 0.15f,1.0f,0.0f);
        noFrictionTerrain2 = new NoFrictionTerrain(this,true, 0.f,0.f, true, pointsNoFriction2, Color.CYAN, Color.BLACK, 0.15f,1.0f,0.0f);
        noFrictionTerrain3 = new NoFrictionTerrain(this,true, 0.f,0.f, true, pointsNoFriction3, Color.CYAN, Color.BLACK, 0.15f,1.0f,0.0f);
        noFrictionTerrain4 = new NoFrictionTerrain(this,true, 0.f,0.f, true, pointsNoFriction4, Color.CYAN, Color.BLACK, 0.15f,1.0f,0.0f);
        
        
        pickUp1 = new PickUp(this, -10f, -21f);
        pickUp2 = new PickUp(this, 74f, -21f);
        pickUp3 = new PickUp(this, 98f, -63f);
        coinScore = new CoinScore(this, 0f, 0f, pickUp1, pickUp2 ,pickUp3);        
        
        scale1 = new Scale(this , 0f, -20f, 2.f, 0.5f, Color.RED, Color.RED, 0.1f, 1.0f, 0.0f);
        scale2 = new Scale(this , 56f, -62f, 2.f, 0.5f, Color.RED, Color.RED, 0.1f, 1.0f, 0.0f);
        
        crate1 = new Crate(this, true, 18f , -22f, 0.8f, 0.8f, 10.f);
        crate2 = new Crate(this, true, 23f , -22f, 0.8f, 0.8f, 10.f);
        crate3 = new Crate(this, true, 28f , -22f, 0.8f, 0.8f, 10f);
        crate4 = new Crate(this, true, 55.5f , -64f, 1.95f, 1.95f, 0.0f);
        crate5 = new Crate(this, true, 80f , -50f, 3f, 1f, 10.f);
       
        crate7 = new Crate(this, true, 45f , -99.9f, 1f, 1f, 10.f);
        crate8 = new Crate(this, true, 57f , -99.3f, 1f, 1f, 10.f);
        
        
        trapSpikes1 = new TrapSpikes(this,124f,-22.f,bike);
        trapSpikes2 = new TrapSpikes(this, 90f ,-105.f,bike);
        
        pendulum1 = new Pendulum(this, true, 60f, -9.f, 1f);
        pendulum2 = new Pendulum(this, true, 82f, -9.f, 1f);
        
        finishLine = new FinishLine(this, true , 120f, -120f );
        
        coinScore = new CoinScore(this, 0f, 0f, pickUp1, pickUp2 ,pickUp3);
        
        //Finally, we add every element of the game to our actor's game actor arraylist
        add(terrain);
        add(terrain2);
        add(bike);
        add(finishLine);
        add(pickUp1);
        add(pickUp2);
        add(pickUp3);
        add(scale1);
        add(scale2);
        add(crate1);
        add(crate2);
        add(crate3);
        add(crate4);
        add(crate5);
        
        add(crate7);
        add(crate8);
        add(speedCounter);
        add(coinScore);
        add(trapSpikes1);
        add(trapSpikes2);
        add(noFrictionTerrain1);
        add(noFrictionTerrain2);
        add(noFrictionTerrain3);
        add(noFrictionTerrain4);
        add(pendulum1);
        add(pendulum2);
        
        //We set our view candidate to the bike
        setViewCandidate(bike);

        return true;
    }
   
    public void update(float deltaTime) {
    	//We firstly use the update methode of actor game
    	super.update(deltaTime);
    	
    	//If r is pressed, we remove every actor of the game then begin a new one
    	if(getKeyboard().get(KeyEvent.VK_R).isPressed()) {
    		while(getActors().size()>0) {
    			getActors().remove(get(0));
    		}
    	begin(window, fileSystem);
    	}
    }
  
   
}
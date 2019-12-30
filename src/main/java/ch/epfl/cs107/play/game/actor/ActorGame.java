package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public abstract class ActorGame implements Game{
	
	//We create the actor's ArrayList
	private ArrayList<Actor> actors;
	
	//We create to supplements actor's ArrayList, which will be used later to add actors or remove it 
	private ArrayList<Actor> removePool = new ArrayList<Actor>();
	private ArrayList<Actor> addPool = new ArrayList<Actor>();
	
	//An actorGame is in a physical world, and need external data
	private World world;
	private Window window;
	private FileSystem fileSystem;	
	
	//For the camera
	private Vector viewCenter ;
	private Vector viewTarget ;
	private Positionable viewCandidate ;
	private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.2f ;
	private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.1f ;
	private static final float VIEW_SCALE = 20.0f ;
	
	
	
	//Begin the program: 
	//Associate the window, the actors's ArrayList and the fileSystem
	//Create the world
	//Associate the gravity and the initial viewpoint
    public boolean begin(Window window, FileSystem fileSystem) {
    	
    	this.window = window;
    	world = new World();
    	actors = new ArrayList<Actor>();
    	this.fileSystem = fileSystem;
    	world.setGravity(new Vector(0.0f, -9.81f));
    	viewCenter = Vector.ZERO;
    	viewTarget = Vector.ZERO;
    	return true;
    }
    
    //update the actorGame each delta time
    //update the physical world
    //remove and add the actors desired, then clear the removePool and the addPool
    //Update everyActors via an iteration
    //update the camera
    //draw every actor with their draw method
    public void update(float deltaTime) {
    	world.update(deltaTime);

    	for (Actor toRemove : removePool) {
    		actors.remove(toRemove);
    	}
    	
    	for (Actor toAdd: addPool) {
    		actors.add(toAdd);	
    	}
    	
    	
    	removePool.clear();
    	addPool.clear();
    	for(Actor actor: actors) {
    		actor.update(deltaTime);
    	}
     	
    	
    	cameraUpdate(deltaTime);
    	

    	for(Actor actor: actors) {
    		actor.draw(window);
    	}
    }

    
    //update the camera, which is following the viewCandidate
    private void cameraUpdate(float deltaTime) {
    	if (viewCandidate != null) {
        	viewTarget = viewCandidate.getPosition().add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION)) ;
        	}
        	// Interpolate with previous location
        	float ratio = (float)Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND, deltaTime) ;
        	viewCenter = viewCenter.mixed(viewTarget , 1.0f - ratio) ;
        	// Compute new viewport
        	Transform viewTransform =Transform.I.scaled(VIEW_SCALE).translated(viewCenter) ;
        	window.setRelativeTransform(viewTransform) ;
    }
    
    
	//Entity's constructor, who initialise an entity at a given position and set it fixed or not
	public EntityBuilder createEntityBuilder(boolean fixed, Vector position) {
    	EntityBuilder entityBuilder = world.createEntityBuilder();
    	entityBuilder.setFixed(fixed);
    	entityBuilder.setPosition(position);
    	return entityBuilder;
	}
	
	//Set the view candidate to the desired positionable
	public void setViewCandidate(Positionable viewCandidate) {
    	this.viewCandidate = viewCandidate;
    }
    
    //Give the viewCandidate
    public Positionable getViewCandidate() {
    	return viewCandidate;
    }
    
    
    //Get all of the actors of the actorGame, useful to iterate through it in bike game
    protected ArrayList<Actor> getActors() {
		return actors;
	}



	//Getter to get the actor at the desirate index
	protected Actor get(int index) {
		return actors.get(index);
	}
	

	
	
	//remove an actor from the arrayList
	public void remove(Actor actor) {
		
		removePool.add(actor);
	}
	
	//add an actor to the arrayList
	public void add(Actor actor) {
		addPool.add(actor);
	}

	
	//End the game
	public void end() {
    };


	//Give the keyboard from the actorGame
	public Keyboard getKeyboard (){
		return window.getKeyboard () ;
		}
	
	//Utilitarian method used in Wheel to create the constraint builder
	//The 2 next methods are also utilitarian methods
	//We need the world to create a constraint builder but we don't wanna make a getWorld so we use it instead
	public WheelConstraintBuilder createWheelConstraintBuilder() {
		return world.createWheelConstraintBuilder();
	}
	
	public RevoluteConstraintBuilder createRevoluteConstraintBuilder() {
		return world.createRevoluteConstraintBuilder();
	}
	
	public RopeConstraintBuilder createRopeConstraintBuilder() {
		return world.createRopeConstraintBuilder();
	}
	
	//Give the window
	public Canvas getCanvas (){
		return window ;
		}
}

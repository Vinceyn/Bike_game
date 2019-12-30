package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

/**
 * Simple game, to show basic the basic architecture
 */
public class HelloWorldGame implements Game {

    // Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity body;
    
    //graphical representation of the body
    private ImageGraphics graphics;
    private ImageGraphics graphics2;
    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        
        //Create physics engine
        world = new World();
        
        //Gravitational constant used
    	world.setGravity(new Vector(0.0f, -9.81f));
    	 //Builder used to create the "EntityBuilder"
    	EntityBuilder entityBuilder = world.createEntityBuilder();
    	
    	//It will not move
    	entityBuilder.setFixed(true);
    	
    	//Set the position
    	entityBuilder.setPosition(new Vector (0f, 0f));
    	body = entityBuilder.build();
    	
    	//Graphic
    	graphics = new ImageGraphics("stone.broken.4.png", 1, 1);
    	graphics.setAlpha(1.0f);
    	graphics.setDepth(0.0f);
    	graphics.setParent(body);

       
    	
    	//Second Object
    	graphics2 = new ImageGraphics("bow.png", 1, 1);
    	graphics2.setAlpha(0.0f);
    	graphics2.setDepth(1.0f);
    	graphics2.setParent(body);
        
        return true;
    
    }
    // This event is called at each frame
    @Override
    public void update(float deltaTime) {
        //Game logic come here
    	//Nothing to do, yet
    	
    	//SimulatePhysics
    	world.update(deltaTime);
    	graphics.draw(window);
    	graphics2.draw(window);
    	window.setRelativeTransform(Transform.I.scaled(2.0f));

        // The actual rendering will be done now, by the program loop
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}

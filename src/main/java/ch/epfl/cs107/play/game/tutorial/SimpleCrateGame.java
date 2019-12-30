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
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.PartBuilder;

public class SimpleCrateGame implements Game{
	
	private Entity block;
	private Entity crate;
	private Window window;
	private World world;
	private ImageGraphics graphicsCrate;
	private ImageGraphics graphicsBloc;
	
	
    public boolean begin(Window window, FileSystem fileSystem) {
    	// Store context
    	this.window = window;
    	//Create physics engine
    	world = new World();
    	//Gravitational constant used
    	world.setGravity(new Vector(0.0f, -9.81f));
    	//Initialise the entity block with an entityBuilder
    	EntityBuilder entityBuilderBlock = world.createEntityBuilder();
    	entityBuilderBlock.setFixed(true);
    	entityBuilderBlock.setPosition(new Vector(1.0f, 0.5f));
    	block = entityBuilderBlock.build();
    	
    	//Initialise the hitbox of ball, use the previous partBuilder
    	PartBuilder partBuilderb = block.createPartBuilder();
    	Polygon polygonb = new Polygon(
    			new Vector(0.0f, 0.0f),
    			new Vector(1.0f, 0.0f),
    			new Vector (1.0f, 1.0f),
    			new Vector (0.0f, 1.0f)
    			);
    	partBuilderb.setShape(polygonb);
    	partBuilderb.build();
    	partBuilderb.setFriction(0.5f);
    	//Initialise the entity block with an entityBuilder
    	EntityBuilder entityBuilderCrate = world.createEntityBuilder();
    	entityBuilderCrate.setFixed(false);
    	entityBuilderCrate.setPosition(new Vector(0.2f, 4.0f));
    	crate = entityBuilderCrate.build();
    	
    	//Initialise the hitbox of ball, use the previous partBuilder
    	PartBuilder partBuilderc = crate.createPartBuilder();
    	Polygon polygonc = new Polygon(
    			new Vector(0.0f, 0.0f),
    			new Vector(1.0f, 0.0f),
    			new Vector (1.0f, 1.0f),
    			new Vector (0.0f, 1.0f)
    			);
    	partBuilderc.setShape(polygonc);
    	partBuilderc.build();
    	
    	//Associate the graphic to the bloc
    	graphicsCrate = new ImageGraphics("box.4.png", 1, 1);
    	graphicsCrate.setAlpha(1.0f);
    	graphicsCrate.setDepth(100.0f);
    	graphicsCrate.setParent(crate);
    	//Associate the graphic to the bloc
    	graphicsBloc = new ImageGraphics("stone.broken.4.png", 1, 1);
    	graphicsBloc.setAlpha(1.0f);
    	graphicsBloc.setDepth(0.0f);
    	graphicsBloc.setParent(block);
    	
    	
   
    	return true;
    }
    
    @Override
    public void update(float deltaTime) {
    	//update the world and the camera, draw the graphics
    	world.update(deltaTime);
    	graphicsCrate.draw(window);
    	graphicsBloc.draw(window);
    	window.setRelativeTransform(Transform.I.scaled(10.0f));
    	
    }
    
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }

	
}

package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;
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
import ch.epfl.cs107.play.math.RopeConstraint;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.WeldConstraint;
import ch.epfl.cs107.play.math.RevoluteConstraint;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;


public class ContactGame implements Game{
	//The two different entities
	private Entity block;
	private Entity ball;
	
	//We need these to create the interface
	private Window window;
	private World world;
	
	//Image graphics associated to the Entity
	private ShapeGraphics graphicsBall;
	private ImageGraphics graphicsBloc;
	//Contact listener
	private BasicContactListener contactListener;
	
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
    	entityBuilderBlock.setPosition(new Vector(-5.0f, -1.0f));
    	block = entityBuilderBlock.build();
    	
    	//Initialise the hitbox of the block
    	PartBuilder partBuilder = block.createPartBuilder();
    	Polygon polygonb = new Polygon(
    			new Vector(0.0f, 0.0f),
    			new Vector(10.0f, 0.0f),
    			new Vector (10.0f, 1.0f),
    			new Vector (0.0f, 1.0f)
    			);
    	partBuilder.setShape(polygonb);
    	partBuilder.setFriction(0.5f);
    	partBuilder.build();
    	
    	//Associate the graphic to the bloc
    	graphicsBloc = new ImageGraphics("stone.broken.4.png", 10f, 1f);
    	graphicsBloc.setAlpha(1.0f);
    	graphicsBloc.setDepth(0.0f);
    	graphicsBloc.setParent(block);
    	
    	//Initialise the entity with an entityBuilder
    	EntityBuilder entityBuilderBall = world.createEntityBuilder();
    	entityBuilderBall.setFixed(false);
    	entityBuilderBall.setPosition(new Vector(0.0f, 2.0f));
    	ball = entityBuilderBall.build();
    	
    	//Initialise the hitbox of the ball, use of the previous partBuilder
    	partBuilder = ball.createPartBuilder();
    	Circle circle = new Circle(0.5f, new Vector(0.0f, 0.0f));
    	partBuilder.setShape(circle);
    	partBuilder.setFriction(5.0f);
    	partBuilder.build();
    	
    	//Associate the graphic to the ball
    	graphicsBall = new ShapeGraphics(circle, Color.BLUE, Color.BLUE, .1f, 1.f, 0);
    	graphicsBall.setParent(ball);
    	
    	//We add the contactListener
    	contactListener = new BasicContactListener();
    	ball.addContactListener(contactListener);
    	
    	return true;
    }
    
    @Override
    public void update(float deltaTime) {
    	//We update the world
    	world.update(deltaTime);
    	
    	//We get if there is a collision
    	int numberOfCollisions = contactListener.getEntities().size();
    	
    	//if there is a collision
    	if (numberOfCollisions > 0) {
    		graphicsBall.setFillColor(Color.RED);
    	}
    	//draw the graphic
    	graphicsBall.draw(window);
    	graphicsBloc.draw(window);
    	
    	//Camera
    	window.setRelativeTransform(Transform.I.scaled(10.0f));
    	
    }
    
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
}

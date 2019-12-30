package ch.epfl.cs107.play.game.tutorial;
import java.awt.Color;
import java.awt.event.KeyEvent;

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
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;

public class ScaleGame implements Game{
	//Create the entity
	private Entity block;
	private Entity ball;
	private Entity plank;
	//We need these for our world
	private Window window;
	private World world;
	//graphics associated to the plank
	private ImageGraphics graphicsPlank;
	private ImageGraphics graphicsBall;
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
    	entityBuilderBlock.setPosition(new Vector(-5.0f, -1.f));
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
    	
    	//Initialise the graphic of the bloc
    	graphicsBloc = new ImageGraphics("stone.broken.4.png", 10f, 1f);
    	graphicsBloc.setAlpha(1.0f);
    	graphicsBloc.setDepth(0.0f);
    	graphicsBloc.setParent(block);
    	
    	//Initialise the entity plank with an entityBuilder
    	EntityBuilder entityBuilderPlank = world.createEntityBuilder();
    	entityBuilderPlank.setFixed(false);
    	entityBuilderPlank.setPosition(new Vector(-2.5f, 2.f));
    	plank = entityBuilderPlank.build();
    	
    	//Initialise the hitbox of the plank, use of the previous partBuilder
    	partBuilder = plank.createPartBuilder();
    	Polygon polygonp = new Polygon(
    			new Vector(0.0f, 0.0f),
    			new Vector(5.0f, 0.0f),
    			new Vector(5.0f, 0.2f),
    			new Vector(0.0f, 0.2f)
    			);
    	partBuilder.setShape(polygonp);
    	partBuilder.setFriction(0.5f);
    	partBuilder.build();
    	
    	//Associate the graphic to the plank
    	graphicsPlank = new ImageGraphics("wood.3.png", 5.0f, 0.2f);
    	graphicsPlank.setAlpha(1.0f);
    	graphicsPlank.setDepth(0.0f);
    	graphicsPlank.setParent(plank);
    	
    	
    	//Initialise the entity ball with an entityBuilder
    	EntityBuilder entityBuilderBall = world.createEntityBuilder();
    	entityBuilderBall.setFixed(false);
    	entityBuilderBall.setPosition(new Vector(0.5f, 4.0f));
    	ball = entityBuilderBall.build();
    	
    	//Initialise the hitbox of ball, use of the previous partbuilder
    	partBuilder = ball.createPartBuilder();
    	Circle circle = new Circle(0.5f, new Vector(0.0f, 0.0f));
    	partBuilder.setShape(circle);
    	partBuilder.setFriction(10.0f);
    	partBuilder.build();

    	//Associate the graphic to the ball
    	graphicsBall = new ImageGraphics("box.4.png", 1.0f, 1.0f , new Vector (0.5f, 0.50f)) ;
    	graphicsBall.setAlpha(1.0f);
    	graphicsBall.setDepth(0.0f);
    	graphicsBall.setParent(ball);
    	
    	
    	//Create the revolute constraint builder
    	RevoluteConstraintBuilder revoluteConstraintBuilder =
    			world.createRevoluteConstraintBuilder () ;
    	revoluteConstraintBuilder.setFirstEntity(block) ;
    	revoluteConstraintBuilder.setFirstAnchor(new Vector( 5.0f , (1.f *7.f) /4.f)) ;
    	revoluteConstraintBuilder.setSecondEntity(plank) ;
    	revoluteConstraintBuilder.setSecondAnchor(new Vector(2.5f, 0.1f)) ;
    	revoluteConstraintBuilder.setInternalCollision(true) ;
    	revoluteConstraintBuilder.build () ;
    	return true;
    }
    
    @Override
    public void update(float deltaTime) {
    	//Command: if we press right/left button we apply angular force to the ball
    	if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown ()) {
    		ball.applyAngularForce (10.0f) ;
    		} else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown ()) {
    		ball.applyAngularForce ( -10.0f) ;
    		}
    	//update the world, the camera and draw the graphics
    	world.update(deltaTime);
    	graphicsBall.draw(window);
    	graphicsBloc.draw(window);
    	graphicsPlank.draw(window);
    	window.setRelativeTransform(Transform.I.scaled(10.0f));
    	
    }
    
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
}
//
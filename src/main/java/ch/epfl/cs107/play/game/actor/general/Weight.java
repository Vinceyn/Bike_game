package ch.epfl.cs107.play.game.actor.general;
 
import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

//Weight of the pendulum
public class Weight extends GameEntity implements Actor {
	
	//Graphic 
    private ShapeGraphics weightGraphics;
   
    //Constructor
    //Call to the superConstructor, then initialize the hitbox and the graphic
    public Weight(ActorGame actorGame, boolean fixed, float positionX, float positionY, float radius){
        super(actorGame, fixed, new Vector(positionX, positionY));
        if (radius <0) {
        	throw new IllegalArgumentException("Création d'un weight avec des arguments invalides");
        }
        setPartBuilder(getEntity(), radius, new Vector(0.0f,0.0f), 0.0f, false);
        Circle circle = new Circle(radius, new Vector(0.0f,0.0f));
        weightGraphics = new ShapeGraphics(circle, Color.GRAY, Color.GRAY, 0.0f, 1.0f, 0.0f);
        weightGraphics.setParent(getEntity());
        }
    
    //Attach the block and the weight
    public void attach(Entity block , float length, float blockHeight) {
        RopeConstraintBuilder ropeConstraintBuilder = getOwner().createRopeConstraintBuilder();
        ropeConstraintBuilder.setFirstEntity(block);
        ropeConstraintBuilder.setFirstAnchor(new Vector(blockHeight/2, blockHeight/2));
        ropeConstraintBuilder.setSecondEntity(getEntity());
        ropeConstraintBuilder.setSecondAnchor(Vector.ZERO);
        ropeConstraintBuilder.setMaxLength(length);
        ropeConstraintBuilder.setInternalCollision(false);
        ropeConstraintBuilder.build();
    }
   
    //Destroy the entity then set the graphic at null
    @Override 
    public void destroy() {
    	super.destroy(getEntity());
    	weightGraphics = null;
    }
    
    //draw the graphic
    //Check if the graphic isn't null
    @Override
    public void draw(Canvas canvas) {
    	if (weightGraphics != null) {
        weightGraphics.draw(canvas);
    	}
       
    }
 
}
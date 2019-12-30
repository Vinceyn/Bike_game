package ch.epfl.cs107.play.game.actor.general;
 
import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
 
public class Pendulum extends GameEntity implements Actor {
    //graphic of the Pendulum
    private ShapeGraphics baseGraphics;
    
    //Weight used to create the graphic
    private Weight weight;
   
    //Constructor
    //Call to the part builder and creation of the hitbox and the graphic
    //creation of a weight
    //We attach the weight and the pendulum
    public Pendulum(ActorGame actorGame, boolean fixed, float positionX, float positionY, float blockHeight) {
        super(actorGame, fixed, new Vector(positionX, positionY));
        if (blockHeight < 0) {
        	throw new IllegalArgumentException("Création d'un pendulum avec des arguments invalides");
        }
        setPartBuilder(getEntity(), blockHeight, blockHeight, 0.0f, false);
        Polygon polygon = new Polygon(
                new Vector(0.0f, 0.0f),
                new Vector(blockHeight, 0.0f),
                new Vector (blockHeight, blockHeight),
                new Vector (0.0f, blockHeight)
                );
        baseGraphics = new ShapeGraphics(polygon, Color.GRAY, Color.WHITE, 0.15f);
        baseGraphics.setParent(getEntity());
        weight = new Weight(actorGame, !fixed , positionX + 4f  , positionY + 1.0f  , 1f );
        weight.attach(getEntity(), 3f , blockHeight);
       
    }
    
    //destroy method
    //we destroy the right, the entity and set the graphic to null
    @Override
    public void destroy() {
    	weight.destroy();
    	super.destroy(getEntity());
    	baseGraphics = null;
    }
 
    @Override
    public void draw(Canvas canvas) {
    	if (baseGraphics != null) {
        baseGraphics.draw(canvas);
        weight.draw(canvas);
    	}
       
    }
   
}
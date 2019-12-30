package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

//The scale class
public class Scale extends GameEntity implements Actor{
	//Graphic associated to the scale
	ImageGraphics graphicScale;
	
	//We need another entity to create a scale
	//We called it scale help and it has an equiteral triangle shape
	//So we decided to make the scale with this condition
	ScaleHelp scaleHelp;
	
	//Scale constructor
	public Scale(ActorGame actorGame, float positionX, float positionY, float helpHeight, float friction, Color fillColor, Color outlineColor, float thickness, float alpha, float depth){
		super(actorGame, false, new Vector(positionX, positionY));
		//We called x the length of a side in scaleHelp
		if (outlineColor == null || fillColor == null) {
			throw new NullPointerException("Création d'un Scale avec des objets null");
		}
		if (friction < 0 || thickness < 0 || alpha < 0 || alpha > 1 || helpHeight < 0) {
			throw new IllegalArgumentException("Création d'un Scale avec des valeurs illégales");
		}
		float x = (float) Math.sqrt(4/3) * helpHeight;
		
		//Scale's value are hard to find
		//Indeed we must find the good friction so the scale will not be stuck to the terrain
		//We also need to find the good height and the good width for the wight
		//We foundd good values for these 3 attributes so it's not in the parameter
		//We crate the scaleX and set the hitbox and the graphic to the scale
		scaleHelp = new ScaleHelp(actorGame, positionX + ((float)5) - (x/2), positionY - 0.2f, helpHeight - 0.2f, friction, fillColor, outlineColor, thickness, alpha, depth);
    	setPartBuilder(getEntity(), 10f, 0.2f,friction, false);
    	graphicScale = new ImageGraphics("metal.hollow.3.png", 10f, 0.2f);
    	graphicScale.setParent(getEntity());
    
    	
    	//We create the constraint builder between the two entites
    	//We use the method we created in actorGame to create the constraint Builder
    	RevoluteConstraintBuilder revoluteConstraintBuilder = getOwner().createRevoluteConstraintBuilder() ;
    	revoluteConstraintBuilder.setFirstEntity(scaleHelp.scaleGetEntity());
    	revoluteConstraintBuilder.setFirstAnchor(new Vector( (float) x/2 , helpHeight));
    	revoluteConstraintBuilder.setSecondEntity(getEntity()) ;
    	revoluteConstraintBuilder.setSecondAnchor(new Vector( 5f, 0.1f)) ;
    	revoluteConstraintBuilder.setInternalCollision(false) ;
    	revoluteConstraintBuilder.build (); 
	}
	
	@Override
	//destroy the entity
	//associate the graphic to the null value
	public void destroy() {
		super.destroy(getEntity());
		graphicScale = null;
	}	
	
	//We draw the scale graphic and the scaleHelp graphic
	//check if the graphic isn't null
	@Override
	public void draw(Canvas canvas) {
		if (graphicScale != null) {
		graphicScale.draw(canvas);
		scaleHelp.draw(canvas);
		}
		
	}
}

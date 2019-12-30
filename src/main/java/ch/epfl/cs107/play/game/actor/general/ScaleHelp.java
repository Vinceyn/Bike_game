package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;


public class ScaleHelp extends GameEntity implements Actor {
	//ScaleHelp is the second entity of the scale which has an equilateral triange shape
	
	//Graphic
	private ShapeGraphics scaleHelpGraphics;
	ScaleHelp(ActorGame actorGame, float positionX, float positionY, float height, float friction, Color fillColor, Color outlineColor, float thickness, float alpha, float depth ){
		super(actorGame, true, new Vector(positionX , positionY - height));
		//x is the value of a length
		if (outlineColor == null || fillColor == null) {
			throw new NullPointerException("Création d'un ScaleHelp avec des objets null");
		}
		if (friction < 0 || thickness < 0 || alpha < 0 || alpha > 1 || height < 0) {
			throw new IllegalArgumentException("Création d'un ScaleHelp avec des arguments invalides");
		}
		float x = (float) Math.sqrt(4/3) * height;
		//We create a triangle hitbox
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Polygon triangle = new Polygon(
				new Vector(0.0f, 0.0f),
				new Vector((x/2), height),
				new Vector(x , 0f)
				);
		partBuilder.setShape(triangle);
		partBuilder.setFriction(friction);
		partBuilder.build();
		
		//We associate the graphic to the entity
		scaleHelpGraphics = new ShapeGraphics(triangle, outlineColor, fillColor, thickness, alpha, depth);
		scaleHelpGraphics.setParent(this);
	}
	
	//draw the graphic
	//check if the graphic isn't null
	@Override
	public void draw(Canvas canvas) {
		if (scaleHelpGraphics != null) {
		scaleHelpGraphics.draw(canvas);
		}
	}
	 
	@Override
	//destroy the entity
	//associate the graphic to the null value
	public void destroy() {
		super.destroy(getEntity());
		scaleHelpGraphics = null;
	}	
	//method to keep getEntity() protected
	protected Entity scaleGetEntity() {
		return getEntity();
	}
}
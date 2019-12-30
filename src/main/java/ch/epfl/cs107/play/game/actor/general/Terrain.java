package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;
import java.util.List;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Terrain extends GameEntity implements Actor {

	private ShapeGraphics terrainGraphic;
	
	public Terrain(ActorGame game, boolean fixed, float positionX, float positionY, boolean closed, List<Vector> points, Color fillColor, Color outlineColor, float thickness, float alpha, float depth) {
		//ListVector must be superior or equal to 2
		
		//We initialise our entity
		super(game, fixed, new Vector(positionX, positionY));
		if (points == null || fillColor == null || outlineColor == null) {
			throw new NullPointerException("Création d'un Terrain avec des objets null");
		}
		
		if (thickness < 0 || alpha < 0 || alpha > 1) {
			throw new IllegalArgumentException("Création d'un Terrain avec des arguments invalides");
		}
		//We create our graphic
		Polyline polyline = new Polyline(closed, points);
		terrainGraphic = new ShapeGraphics(polyline, fillColor, outlineColor, thickness, alpha, depth);
		terrainGraphic.setParent(getEntity());
		
		//We create the terrain's hitbox
		PartBuilder partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(polyline);
		partBuilder.setFriction(10.0f);
		partBuilder.build();
		// TODO Auto-generated constructor stub
	}

	
	@Override
	//destroy the entity and the graphic of the terrain
	public void destroy() {
		super.destroy(getEntity());
		terrainGraphic = null;
	}
	
	
	@Override
	public void draw(Canvas canvas) {
		if (terrainGraphic != null) { //Check if the terrainGraphic isn't null
		terrainGraphic.draw(canvas); //we display the graphical representation of the terrain on the canvas
		} 
		// TODO Auto-generated method stub
		
	}
	

}

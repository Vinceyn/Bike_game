package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;
import java.util.List;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class NoFrictionTerrain extends GameEntity implements Actor {
	
	//We create a List of vector
	private final List<Vector> points;
	//graphic
	private ShapeGraphics noFrictionTerrainGraphic;
	
	public NoFrictionTerrain(ActorGame game, boolean fixed, float positionX, float positionY, boolean closed, List<Vector> points, Color fillColor, Color outlineColor, float thickness, float alpha, float depth) {
		//listvector must be superior or equals to 2
		super(game, fixed, new Vector(positionX, positionY));
		if (points == null || fillColor == null || outlineColor == null) {
			throw new NullPointerException("Création d'un NoFrictionTerrain avec des objets null");
		}
		
		if (thickness < 0 || alpha < 0 || alpha > 1) {
			throw new IllegalArgumentException("Création d'un NoFrictionTerrain avec des arguments invalides");
		}
		//We initialise our entity
		this.points = points;
		
		//We create our graphic
		Polyline polyline = new Polyline(closed, points);
		noFrictionTerrainGraphic = new ShapeGraphics(polyline, fillColor, outlineColor, thickness, alpha, depth);
		noFrictionTerrainGraphic.setParent(getEntity());
		
		//We create the terrain's hitbox
		PartBuilder partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(polyline);
		partBuilder.setFriction(10.0f);
		partBuilder.build();
		// TODO Auto-generated constructor stub
	}
	
	//destroy the entity
	//set the graphic to null
	@Override
	public void destroy() {
		super.destroy(getEntity());
		noFrictionTerrainGraphic = null;
	}
	
	
	//draw the graphic
	//check if the graphic isn't null
	@Override
	public void draw(Canvas canvas) {
		if (noFrictionTerrainGraphic != null) {
		noFrictionTerrainGraphic.draw(canvas);
		}
		// TODO Auto-generated method stub
		
	}
	

	
}
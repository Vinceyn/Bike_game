package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

public abstract class GameEntity {
	
	private Entity entity;
	private ActorGame game;
	
	
	//Constructor of gameEntity
	//Call the createEntityBuilder() method, so it creates an entity when we call it
	protected GameEntity(ActorGame game, boolean fixed, Vector position) {
		if (game == null || position == null) {
			throw new NullPointerException("Création d'une GameEntity avec un argument nul");
		}
		entity = game.createEntityBuilder(fixed, position).build();
		this.game = game;
	}
	
	//Constructor of GameEntity, use the overcharge
	protected GameEntity(ActorGame game, boolean fixed){
		if (game == null) {
			throw new NullPointerException("Création d'une GameEntity avec un argument nul");
		}
		entity = game.createEntityBuilder(fixed, Vector.ZERO).build();
		this.game = game;
	}

	//destroy the entity
	public void destroy(Entity entity) {
		if (entity == null) {
			throw new NullPointerException("destruction d'une entité avec un argument nul");
		}
		entity.destroy();
	}
	
	//Getter of the entity
	protected Entity getEntity() {
		return entity;
	}
	
	//Getter of the actorGame
	protected ActorGame getOwner() {
		return game;
	}
	

	//Utilitary method used with overcharge to modularize the program
	//set the partBuilder of a rectangle or a circle
	public void setPartBuilder(Entity entity, float radius, Vector center, float friction, boolean ghost) {
		PartBuilder partBuilder = entity.createPartBuilder();
    	Circle circle = new Circle(radius, center);
    	partBuilder.setShape(circle);
    	partBuilder.setFriction(friction);
    	partBuilder.setGhost(ghost);
    	partBuilder.build();
	}
	
	public void setPartBuilder(Entity entity, float width, float height, float friction, boolean ghost) {
		PartBuilder partBuilder = entity.createPartBuilder();
    	Polygon polygon = new Polygon(
    			new Vector(0.0f, 0.0f),
    			new Vector(width, 0.0f),
    			new Vector(width, height),
    			new Vector(0.0f, height)
    			);
    	partBuilder.setShape(polygon);
    	partBuilder.setFriction(friction);
    	partBuilder.setGhost(ghost);
    	partBuilder.build();
	}
	
	
	public Transform getTransform() {
	     return entity.getTransform(); // assure que le référentiel de la GameEntity est le même que celui de son //Entity
	    }
	
	public Vector getVelocity() {
      return entity.getVelocity(); // assure que la vitesse  de la GameEntity est la même que celle de son Entity
}
	
}

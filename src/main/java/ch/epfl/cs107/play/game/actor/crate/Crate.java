package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Crate extends GameEntity implements Actor {
	
	//Graphic associated to the crate
	private ImageGraphics crateGraphic;
	
	
	//Constructor with all of the informations needed
	//Firstly we call the constructor of gameEntity with the good information
	//We create the crategraphic with the box.4.png which is a good picture for the crate
	//We set the graphic's parent to this entity
	//Then we put the hitbox of the crate
	public Crate(ActorGame actorGame, boolean fixed, float positionX, float positionY, float width, float height, float friction){
		super(actorGame, fixed, new Vector(positionX, positionY));
		if (width < 0 || height < 0 || friction < 0) {
			throw new IllegalArgumentException("Création d'un crate avec des arguments invalides");
		}
		crateGraphic = new ImageGraphics("box.4.png", width, height);
		crateGraphic.setParent(getEntity());
		setPartBuilder(getEntity(), width, height, friction, false);
		}
	
	//destroy the entity
	//use the method super.destroy, then associate the crateGraphic to null
	@Override
	public void destroy() {
		super.destroy(getEntity());
		crateGraphic = null;
	}
	
	//check first if the crate isn't destroy
	//if no, draw the crateGraphic
	@Override
	public void draw(Canvas canvas) {
		if (crateGraphic != null) {
		crateGraphic.draw(canvas);
		}
		// TODO Auto-generated method stub
	}
	
	
}
	


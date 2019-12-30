package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

//blocker class who block the crates from falling down
public class Blocker extends GameEntity implements Actor{
	public Blocker(ActorGame actorGame, float positionX, float positionY, float width) {
		super(actorGame, true, new Vector(positionX, positionY));
		if (width < 0) {
			throw new IllegalArgumentException("Création d'un blocker avec un width < 0");
		}
		setPartBuilder(getEntity(), width, 0.1f, 0.0f, false);

    	}
	
	//method used to have the entity without passing getEntity() to public
	protected Entity getBlockerEntity() {
		return getEntity();
	}
	
	@Override
	//destroy the entity
	public void destroy() {
		super.destroy(getEntity());
	}
	//we must overite the draw method, even if there is no graphic
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

}

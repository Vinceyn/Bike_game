package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

//class of pickable object
public class PickUp extends Trigger implements Actor{
	//graphic
	private ImageGraphics coinGraphic; 
	
	//Constructor
	//call to the super constructor, initialisation of the graphic and set of the contact listener
	public PickUp(ActorGame actorGame,float positionX,float positionY) {
		super(actorGame, true, positionX, positionY, 0.5f, Vector.ZERO);
		coinGraphic = new ImageGraphics("coin.gold.png", 1f, 1f);
		coinGraphic.setParent(getEntity());
		
		getEntity().addContactListener(getContactListener());
	}
	
	@Override
	//destroy method
	public void destroy() {
		super.destroy();
		coinGraphic = null;
		super.destroy(getEntity());
	}
	
	//draw the coin while it has not been picked up
	//We check that coinGraphic isn't null
	public void draw(Canvas canvas) {
		if (!getContact() && (coinGraphic != null)) {
			coinGraphic.draw(canvas);
		}
	
		
		
	} 
}

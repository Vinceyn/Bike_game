package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.window.Canvas;

public class TrapSpikes extends Trigger implements Actor{
	//two imageGraphics to see wether or not the trap has been activated
	private ImageGraphics graphicTrapBefore;
	private ImageGraphics graphicTrapAfter;
	
	//the trap need spikes, crates and a blocker
	private Spike spike;
	private Crate crate1;
	private Crate crate2;
	private Crate crate3;
	private Blocker blocker;
	//this boolean allow to destroy the blocker only one time
	private boolean needRemove;
	
	//constructor
	public TrapSpikes(ActorGame actorGame, float positionX, float positionY, Bike bike) {
		//call to the super constructor and creation of the graphic
		super(actorGame, true, positionX, positionY, 1.5f, 1.5f);
		if (bike == null) {
			throw new NullPointerException("Création d'un trapSpikes avec des objets null");
		}
		graphicTrapBefore = new ImageGraphics("lever.green.left.png", 1.5f, 1.5f);
		graphicTrapBefore.setParent(getEntity());
		graphicTrapAfter = new ImageGraphics("lever.green.right.png", 1.5f, 1.5f);
		graphicTrapAfter.setParent(getEntity());
		
		 //Create the entity 
		crate1 = new Crate(getOwner(), false, positionX + 4f, positionY + 11f, 3f, 3f, 5.0f);
		crate2 = new Crate(actorGame, false, positionX + 7.2f, positionY + 11f, 3f, 3f, 5.0f);
		crate3 = new Crate(actorGame, false, positionX + 10.4f, positionY + 11f, 3f, 3f, 5.0f);
		spike = new Spike(actorGame, positionX + 4f, positionY, bike);
		blocker = new Blocker(actorGame, positionX + 4f, positionY + 10.9f, 9f);
		
		//We add the contact listener to this entity
        getEntity().addContactListener(getContactListener());
        
        //initialise the boolean
        needRemove = true;

	}
	
	@Override
	//draw everye entity of the trap
	//check if the trap hasn't been destroyed
	public void draw(Canvas canvas) {
		if (graphicTrapBefore != null) {
		//draw every entity
		spike.draw(canvas);
		crate1.draw(canvas);
		crate2.draw(canvas);
		crate3.draw(canvas);
		//draw the desired graphic following if the bike hit the trigger or not
		if (!getContact()) {
		graphicTrapBefore.draw(canvas);
		}
		else {
			graphicTrapAfter.draw(canvas);
		}
		}
	}
	
	@Override
	//destroy method
	public void destroy(){
		crate1.destroy();
		crate2.destroy();
		crate3.destroy();
		spike.destroy();
		if(blocker != null) {
			blocker.destroy(blocker.getBlockerEntity());
		}
		graphicTrapBefore = null;
		graphicTrapAfter = null;
		super.destroy();
		super.destroy(getEntity());
	}
	
	@Override
	public void update(float deltaTime) {
		//update the spike
		spike.update(deltaTime);
		//if there is a contact, we destroy the blocker so the crates will fall
		if(needRemove && getContact()) {
			blocker.destroy(blocker.getBlockerEntity());
			needRemove = false;
		}
	}
	
}

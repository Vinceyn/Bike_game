package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.window.Canvas;

//spike immobilize the bike
//it detects if the bike is in the hitbox so it is a trigger
public class Spike extends Trigger implements Actor{
	//graphic for the spike
	ImageGraphics spikeGraphic;
	//this class need a bike so it can immobilize the bike's wheel
	Bike bike;
	
	//constructor
	//call the super contructor, initialise the bike and create a graphic
	//add the contact listener to the entity
	public Spike(ActorGame actorGame, float positionX, float positionY, Bike bike) {
		super(actorGame, true, positionX, positionY, 7f, 1f);
		if (bike == null) {
			throw new NullPointerException("Création d'un Spike avec des objets null");
		}
		this.bike = bike;
		spikeGraphic = new ImageGraphics("spikes.png", 7f, 1f);
		spikeGraphic.setParent(getEntity());
		
		getEntity().addContactListener(getContactListener());

	}
	
	@Override
	//draw the graphic
	//check if the graphic isn't null
	public void draw(Canvas canvas) {
		if (spikeGraphic != null) {
		spikeGraphic.draw(canvas);
		}
	}
	
	@Override
	//destroy the entity
	//associate the graphic to the null value
	public void destroy() {
		super.destroy(getEntity());
		spikeGraphic = null;
		super.destroy();
	}	
	//immobilize the bike by putting his wheel's power to 0 if there is a contact
	private void stuck() {
		if (getContact()) {
			bike.getMotorWheel().power(0.0f);
			bike.getOtherWheel().power(0.0f);
		}
	}
	
	@Override
	//at each delta time we call the stuck method
	public void update(float deltaTime) {
		stuck();
	}
}

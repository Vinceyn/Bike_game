package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Vector;

public abstract class Trigger extends GameEntity implements Actor{
	
	//contact Listener
	private ContactListener contactListener;
	
	//see if the contactListener hit the bike
	private boolean contacted;
	
	//constructor
	//Call to the super-constructor and set the hitbox
	protected Trigger(ActorGame actorGame, boolean fixed, float positionX, float positionY, float width, float height) {
		super(actorGame, fixed, new Vector(positionX, positionY));
		if(width < 0 || height < 0) {
			throw new IllegalArgumentException("Création d'un trigger avec des arguments invalides");
		}
		setPartBuilder(getEntity(), width, height, 0.0f, true);
		//Anonymous clas so we define the methods
		//If the contactListener touch a ghost hitbox the boolean become true
		//the bike is the only ghost hitbox which is moving
		contactListener = new ContactListener() {
	        @Override
	        public void beginContact(Contact contact) {
	        if (contact.getOther().isGhost()) {
	            contacted = true ;
	        }
	        }
	       
	        @Override
	        public void endContact(Contact contact) {}
	        } ;
	       
		}
	//Overcharge of trigger
	//previous was for rectangle, this one is for circle
	protected Trigger(ActorGame actorGame, boolean fixed, float positionX, float positionY, float radius, Vector center) {
		super(actorGame, fixed, new Vector(positionX, positionY));
		if (center == null) {
			throw new NullPointerException("Création d'un trigger avec des objets null");
		}
		if (radius < 0) {
			throw new IllegalArgumentException("Création d'un trigger avec des arguments invalides");
		}
		setPartBuilder(getEntity(), radius, center, 0.0f, true);
		contactListener = new ContactListener() {
	        @Override
	        public void beginContact(Contact contact) {
	        if (contact.getOther().isGhost()) {
	            contacted = true ;
	        }
	        }
	       
	        @Override
	        public void endContact(Contact contact) {}
	        } ;
	       
	}
	
	//destroy 
	@Override
	public void destroy() {
		super.destroy(getEntity());
	}
	
	//get the contactListener 
	protected ContactListener getContactListener() {
		return contactListener;
	}
	
	//get the boolean contacted value
	protected boolean getContact() {
		return contacted;
	}
	
	
}

package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

public class CrateGame extends ActorGame{
	//The 3 crates of our crateGame
	Crate crate1;
	Crate crate2;
	Crate crate3;

	@Override 
	public boolean begin(Window window, FileSystem fileSystem) {
		//We firstly initiate our game
		super.begin(window, fileSystem);
		
		//Then we create our crate
		crate1 = new Crate(this, false, 0.0f, 5.0f, 1f, 1f, 0f);
		crate2 = new Crate(this, false, 0.2f, 7.0f, 1f, 1f, 0f);
		crate3 = new Crate(this, false, 2.0f, 6.0f, 1f, 1f, 0f);
		
		//We add our crate to our arrayList of actor
		add(crate1);
		add(crate2);
		add(crate3);
		

		return true;
	}
	
	
}

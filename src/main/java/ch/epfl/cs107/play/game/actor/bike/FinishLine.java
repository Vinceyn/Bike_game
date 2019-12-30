package ch.epfl.cs107.play.game.actor.bike;
 
 
 
import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.general.Trigger;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
 
//The finishLine extends the trigger because it's a contactListener
public class FinishLine extends Trigger implements Actor {
   
	//The two graphics, one for the finishLine, the other for the win message
    private ImageGraphics finishLineGraphics;
    private TextGraphics messageWin;
    
    //Constructor: call the super-constructor
    //Initialise the two graphics
    //add the contact Listener
    public FinishLine(ActorGame actorGame, boolean fixed, float positionX, float positionY){
        super(actorGame, fixed, positionX , positionY, 2.f, new Vector(0.5f, 0.5f));
        finishLineGraphics = new ImageGraphics("flag.yellow.png", 1.5f, 2.f);
        finishLineGraphics.setParent(getEntity());
        
        messageWin = new TextGraphics("You win!!",0.2f, Color.BLACK, Color.WHITE , 0.02f, true , false , new Vector(0.5f, 0.5f), 1.0f, 100.0f) ;
        messageWin.setParent(getOwner().getCanvas()) ;
        messageWin.setRelativeTransform(Transform.I.translated(0.0f, -1.0f)) ;

       
        getEntity().addContactListener(getContactListener());
       
   
    }
    
    @Override
    //destroy method
   public void destroy(){
	   super.destroy(getEntity());
	   finishLineGraphics = null;
   }
    //The finishLine graphic is always draw
    //If there is a contact, we win so we draw the win message
    //if we win we set the viewCandidate to the finishLine
    //it's amusing and we can have a condition to see if we have win or not
   	//check if the finishLine hasn't been destroyed
    @Override
    public void draw(Canvas canvas) {
    	if (finishLineGraphics != null) {
        finishLineGraphics.draw(canvas);
        if (getContact()) {
            messageWin.draw(canvas);
            getOwner().setViewCandidate(this);
        }
    	}
       
        }  
   
}
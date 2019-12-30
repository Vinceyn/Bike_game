package ch.epfl.cs107.play.game.actor.general;
 
import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
 
//Speed counter give the speed of the motor wheel
//it extends info display
public class SpeedCounter extends InfoDisplay {
   
	//bike from which we get the motorSpeed
    Bike bike;
    
    //The two graphics associated
    TextGraphics speedDisplay;
    ShapeGraphics box;
   
   //firstly we initialize the entity and the bike
    public SpeedCounter(ActorGame actorGame, float positionX, float positionY ,Bike bike) {
        super(actorGame, positionX ,positionY);
        if(bike == null) {
        	throw new NullPointerException("Création d'un speedCounter avec des objets null");
        }
        this.bike = bike;
        
        //Then we initialize the graphics
        speedDisplay = new TextGraphics("Speed : " + Float.toString(Math.round(Math.abs(bike.getMotorWheel().getSpeed()))),0.08f, Color.BLACK, Color.WHITE , 0.02f, true , false , new Vector(2.3f, 4.8f), 1.0f, 100.0f) ;
        Polygon polygon = new Polygon(new Vector(-1f, 0.32f),new Vector(-1f, 0.52f),new Vector(-0.5f, 0.52f), new Vector(-0.5f, 0.32f) ) ;
        box = new ShapeGraphics( polygon , Color.GREEN, Color.GRAY, 0.15f, 0.7f, 5.0f );
        
        //We add the graphics to our graphic arraylist in infoDisplay
        super.addArrayGraphics(speedDisplay);
        super.addArrayGraphics(box);
        
        //We set our graphic's parent to the canvas
        //so the graphic will be fixed on the screen
        box.setParent(getOwner().getCanvas());
        speedDisplay.setParent(getOwner().getCanvas());
        
        //We translate the text graphic
        speedDisplay.setRelativeTransform(Transform.I.translated(0.0f, -1.0f)) ;
    }
   
    //Draw method
    //While the bike hasn't got hitten we draw all of the graphic
    //We use our super class draw method
    @Override
    public void draw(Canvas canvas) {
        if (bike.getHit() == false) {
            super.draw(canvas);
        }
            }
    @Override
	//destroy the entity
    //We already override destroy() on info display so just call it
	public void destroy() {
		super.destroy();
		super.destroy(getEntity());
	}	
	
   
    @Override
    public void update(float deltaTime) {
    	//At each update we update the value of the speed
        speedDisplay.setText("Speed : " + Float.toString(Math.round(Math.abs(bike.getMotorWheel().getSpeed()))));
        //We change the color of the box depending on the speed value 
        if (Math.round(Math.abs(bike.getMotorWheel().getSpeed())) <= 7) {
            box.setFillColor(Color.GREEN);
        }
        else if ((Math.round(Math.abs(bike.getMotorWheel().getSpeed())) > 7) && (Math.round(Math.abs(bike.getMotorWheel().getSpeed())) <= 15)) {
            box.setFillColor(Color.YELLOW);
        }
        else if ((Math.round(Math.abs(bike.getMotorWheel().getSpeed())) > 15) && (Math.round(Math.abs(bike.getMotorWheel().getSpeed())) <= 20))  {
            box.setFillColor(Color.ORANGE);
        }
        else {
        	box.setFillColor(Color.RED);
        }
        //We change the anchor of the text depending on if it has 2 digits or 1
        if (Math.round(Math.abs(bike.getMotorWheel().getSpeed())) >= 10) {
            speedDisplay.setAnchor(new Vector(2.1f, 4.8f));
        }
        else {
            speedDisplay.setAnchor(new Vector(2.3f, 4.8f));
        }
    }  
   
       
}
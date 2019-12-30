package ch.epfl.cs107.play.game.actor.general;
 
import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
 
//Score of the player, modelized by the coins
public class CoinScore extends InfoDisplay{
   
	//The 3 coins and the textGraphic for the "score" word
    private PickUp coin1;
    private PickUp coin2;
    private PickUp coin3;
    private TextGraphics scoreDisplay;
   
    //The 3 graphics associate to the 3 coins
    private ImageGraphics coin1Yup;
    private ImageGraphics coin2Yup;
    private ImageGraphics coin3Yup;
   
    //constructor
    //Firstly we made a constructor call then initialize our 3 coins
    public CoinScore(ActorGame actorGame, float positionX, float positionY ,PickUp coin1, PickUp coin2, PickUp coin3) {
         super(actorGame, positionX ,positionY);
         if (coin1 == null || coin2 == null || coin3 == null) {
        	 throw new NullPointerException("Création d'un coinScore avec des objets null");
         }
         this.coin1 = coin1;
         this.coin2 = coin2;
         this.coin3 = coin3;
         
         //Initalization of the 3 image Graphics
         coin1Yup = new ImageGraphics("coin.silver.png", 0.075f, 0.075f, new Vector(-9.8f,-5.2f), 1.0f , 10.f) ;
         coin2Yup = new ImageGraphics("coin.silver.png", 0.075f, 0.075f, new Vector(-10.9f,-5.2f), 1.0f , 10.f) ;
         coin3Yup = new ImageGraphics("coin.silver.png", 0.075f, 0.075f, new Vector(-12f,-5.2f), 1.0f , 10.f) ;
         
         //initization of our text grapihcs
         scoreDisplay = new TextGraphics("Score : ",0.08f, Color.BLACK, Color.WHITE , 0.02f, true , false , new Vector(-1.4f, 4.8f), 1.0f, 100.0f);
         
         //We add our graphic to the graphic array
         super.addArrayGraphics(coin1Yup);
         super.addArrayGraphics(coin2Yup);
         super.addArrayGraphics(coin3Yup);
         super.addArrayGraphics(scoreDisplay);

         //We set our graphic's parent to the canvas
         //so the graphic will be fixed on the screen
         coin1Yup.setParent(getOwner().getCanvas());
         coin2Yup.setParent(getOwner().getCanvas());
         coin3Yup.setParent(getOwner().getCanvas());
         scoreDisplay.setParent(getOwner().getCanvas());
         
         //We set relative transform for scoreDisplay
         scoreDisplay.setRelativeTransform(Transform.I.translated(0.0f, -1.0f)) ;
    }
    
    @Override
	//destroy the entity
    //We already override destroy() on info display so just call it
	public void destroy() {
		super.destroy();
		super.destroy(getEntity());
	}	
	
	//draw all of the graphic by calling the InfoDisplay Method
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        }
    
   
    //If there is a contact with a coin, its associated image will change
     @Override
        public void update(float deltaTime) {
            if (coin1.getContact()) {
                coin1Yup.setName("coin.gold.png");
            }
            if (coin2.getContact()) {
                coin2Yup.setName("coin.gold.png");
            }
            if (coin3.getContact()) {
                coin3Yup.setName("coin.gold.png");
            }
           
           
        }  
 
}
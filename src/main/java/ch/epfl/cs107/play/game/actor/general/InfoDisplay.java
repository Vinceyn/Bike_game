package ch.epfl.cs107.play.game.actor.general;
 
import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
 
public abstract class InfoDisplay extends GameEntity implements Actor {
 
	//We create an arrayList of graphics
    private ArrayList<Graphics> graphicArray;
    
    //We create 3 differents graphics from the 3 different subClasses of graphics
    //It will help us to check they object Type
    //it will also help us to manipulate via reference our objects
    private TextGraphics textGraphic;
    private ImageGraphics imageGraphic;
    private ShapeGraphics shapeGraphic;
   
    //We initialise our infoDisplay 
    //call to the supper constructor, then creation of our graphic Array
    //We initialize our 3 graphics to random value
    //Indeed, we don't care about the initial value we will never use them
    protected InfoDisplay(ActorGame actorGame, float positionX, float positionY) {
        super(actorGame,true, new Vector(positionX, positionY));
        graphicArray = new ArrayList<Graphics>();
        textGraphic = new TextGraphics("hello", positionY, null);
        imageGraphic = new ImageGraphics(null, positionY, positionY);
        shapeGraphic = new ShapeGraphics(null, null, null, positionY);
    }
   
    //This method add a graphic to our arrayList. It has a Graphic in parameter
    protected void addArrayGraphics(Graphics graphic) {
    	//Firstly we check the type via the getClass() function
    	   if (graphic.getClass().equals(textGraphic.getClass()) ) {
    		   //then, if we know it's a text graphic, we add it to the array by transtyping it into a textgraphic
               graphicArray.add((TextGraphics) graphic);
               //Then, we use our textGraphic variable to get the reference of the graphic
               //so we set our reference's graphic to the entity
               textGraphic = (TextGraphics) graphic;
               textGraphic.setParent(getEntity());
           }
    	   //We repeat it again for the imageGraphic and the shapeGraphic
           if (graphic.getClass().equals(imageGraphic.getClass()) ) {
               graphicArray.add((ImageGraphics) graphic);
               imageGraphic = (ImageGraphics) graphic;
               imageGraphic.setParent(getEntity());
           }
          
           if (graphic.getClass().equals(shapeGraphic.getClass()) ) {
               graphicArray.add((ShapeGraphics) graphic);
               shapeGraphic = (ShapeGraphics) graphic;
               shapeGraphic.setParent(getEntity());
           }
    }
   
    
   @Override
   public void destroy() {
	   super.destroy(getEntity());
	   for (Graphics graphicToDestroy: graphicArray) {
		   graphicToDestroy = null;
	   }
   }
   
   @Override
    public void draw(Canvas canvas) {
        for (Graphics graphicToShow : graphicArray) {
        	if (graphicToShow != null) {
            graphicToShow.draw(canvas);
            }
        }
        // TODO Auto-generated method stub
    }
   
   
}
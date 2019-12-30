package ch.epfl.cs107.play.game.actor.bike;
 
import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.general.Wheel;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
 
public class Bike extends GameEntity implements Actor {
	
	//The bike has the graphic of all of our body's part
    private ShapeGraphics headShape;
    private ShapeGraphics armShape;
    private ShapeGraphics kneeShape;
    private ShapeGraphics hipShape;
    private ShapeGraphics leg1Shape;
    private ShapeGraphics leg2Shape;
    private ShapeGraphics arm1upShape;
    private ShapeGraphics arm2upShape;
    
    //The bike has 2 wheels
    private Wheel wheelLeft;
    private Wheel wheelRight;
    
    //A boolean to have the direction
    private  boolean right = true;
    
    //Attribute of the bike, used afterwards when we change the direction
    private Color fillColor;
    private Color outlineColor;
    private float thicknessBike;
    private float alpha;
    private float depth;
    
    //boolean to check if the bike is destroyed to display the death message 
    private boolean touched;
    
    //The maximum speed of the bike
    private final float MAX_WHEEL_SPEED = 20.0f;
    
    //Used for the commands
    private Keyboard keyboard;
    
    //Boolean to check if the bike was hitten
    private boolean hit;
    
    //Message of death
    private TextGraphics messageGO;
    
    //Hitbox of the biker
    PartBuilder partBuilder;
    
    //Bike constructor
    public Bike(ActorGame actorGame, boolean fixed, float positionX, float positionY ,Color fillColor,Color outlineColor, float thicknessBike, float thicknessWheel, float alpha, float depth, float friction) {
    	//We create an entity at the position desired 
        super(actorGame, fixed, new Vector(positionX, positionY));
        
        if(fillColor == null || outlineColor == null) {
        	throw new NullPointerException("création d'un bike avec un objet nul");
        }
        if (thicknessBike < 0 || thicknessWheel <0 || friction < 0 || alpha < 0 || alpha > 1) {
        	throw new IllegalArgumentException("création d'un bike avec des valeurs non valides");
        }
        //We initialise our class attribut with what we need
        //We need theses attributs to create the symetric shape when we change the direction
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
        this.thicknessBike = thicknessBike;
        this.alpha = alpha;
        this.depth = depth;
        
        //We initialise the booleans touched and hit at false
        touched = false;
        hit = false;
        
        //We initialise our keyboard with the one from actorGame
        keyboard = actorGame.getKeyboard();
        
        
        //We create the hitbox
        //The hitbox has a diamond form so we cannot use the gameEntityMethod
        //We put the partBuilder in ghost
        Polygon polygon = new Polygon(
                new Vector(0.0f, 0.5f),
                new Vector(0.5f, 1.0f),
                new Vector(0.0f, 2.0f),
                new Vector(-0.5f, 1.0f)
                ) ;
        partBuilder = getEntity().createPartBuilder();
        partBuilder.setShape(polygon);
        partBuilder.setFriction(friction);
        partBuilder.setGhost(true);
        partBuilder.build();
        
        //We initialise the lose message
        messageGO = new TextGraphics("Game over",0.2f, Color.BLACK, Color.WHITE , 0.02f, true , false , new Vector(0.5f, 0.5f), 1.0f, 100.0f) ;
        messageGO.setParent(getOwner().getCanvas()) ;
        messageGO.setRelativeTransform(Transform.I.translated(0.0f, -1.0f)) ;
   
        //We create every graphic part of the biker
        //We have all of the coordinate in our method getLocation()
        Circle head = new Circle(0.2f, getHeadLocation()) ;
        headShape = new ShapeGraphics(head, fillColor, outlineColor, thicknessBike, alpha, depth);
        headShape.setParent(getEntity());
        
        Polyline arm = new Polyline(getShoulderLocation(),getHandLocation()) ;
        armShape = new ShapeGraphics(arm, fillColor, outlineColor, thicknessBike, alpha, depth);
        armShape.setParent(getEntity());
        
        Polyline hip = new Polyline(getShoulderLocation(),getHipLocation()) ;
        hipShape = new ShapeGraphics(hip, fillColor, outlineColor, thicknessBike, alpha, depth);
        hipShape.setParent(getEntity());
        
        Polyline knee = new Polyline(getKneeLocation(),getHipLocation()) ;
        kneeShape = new ShapeGraphics(knee, fillColor, outlineColor, thicknessBike, alpha, depth);
        kneeShape.setParent(getEntity());
        
        Polyline leg1 = new Polyline(getLeg1Location(),getKneeLocation()) ;
        leg1Shape = new ShapeGraphics(leg1, fillColor, outlineColor, thicknessBike, alpha, depth);
        leg1Shape.setParent(getEntity());
        
        Polyline leg2 = new Polyline(getLeg2Location(),getKneeLocation()) ;
        leg2Shape = new ShapeGraphics(leg2, fillColor, outlineColor, thicknessBike, alpha, depth);
        leg2Shape.setParent(getEntity());
        
        //These shapes are for when we get to the finish line
        Polyline arm1Up = new Polyline(getShoulderLocation(),getHand1UpLocation()) ;
        arm1upShape = new ShapeGraphics(arm1Up, fillColor, outlineColor, thicknessBike, alpha, depth);
        arm1upShape.setParent(getEntity());
        
        Polyline arm2Up = new Polyline(getShoulderLocation(),getHand2UpLocation()) ;
        arm2upShape = new ShapeGraphics(arm2Up, fillColor, outlineColor, thicknessBike, alpha, depth);
        arm2upShape.setParent(getEntity());
        
        //We initialise our wheels with the constructor of wheel
        //We also attach the wheel to the bike
        wheelLeft = new Wheel(actorGame, positionX - 1.0f, positionY , friction, thicknessWheel, alpha, depth );       
        wheelLeft.attach(getEntity() , new Vector(-1.0f, 0.0f), new Vector(-0.5f, -1.0f)) ;
        
        wheelRight = new Wheel(actorGame, positionX + 1.0f, positionY , friction, thicknessWheel, alpha, depth );
        wheelRight.attach(getEntity() , new Vector(1.0f, 0.0f), new Vector(0.5f, -1.0f)) ;
        
        //Finally, we define the initial direction of the biker
        right = true;
        
        
        //We create the contactListener
        //We must redefine the contactListener when we instanciate it because it's an anonymous class
        ContactListener listener = new ContactListener() {
            //if there is a contact with an hitbox which isn't ghosted or his wheels, the hit boolean becomes true
            @Override
            public void beginContact(Contact contact) {
            if (contact.getOther().isGhost()) {
                hit = false ;
            }
            else if (contact.getOther().getEntity() == wheelLeft.getWheelEntity() || contact.getOther().getEntity() == wheelRight.getWheelEntity()) {
                hit = false;
            }
            else {
                hit = true ;
            }
            }
           
            @Override
            public void endContact(Contact contact) {}
            } ;
           //finally we add the contact listener to the entity
            getEntity().addContactListener(listener);
    }
    
    @Override
    //when the entity is destroy, we remove it from the actor arrayList and detach his wheels
    public void destroy() {
    	super.destroy(getEntity());
    	wheelLeft.detach();
    	wheelRight.detach();
    	
    	
    }
   
    //All of the following getLocation methods are used to create the shape of the biker
    //Depending on the direction boolean, it will returns different abscissa, except for the head whose location is the same
    private Vector getHeadLocation() {
        return new Vector(0.0f, 1.75f) ;
        }
   
    private Vector getShoulderLocation() {
    	if (right) {
        return new Vector(-0.1f, 1.5f);
    	}
    	else {
    		return new Vector(0.1f, 1.5f);
    	}
    }
   
    private Vector getHandLocation(){
    	if (right) {
        return new Vector(0.5f, 1.0f);
    	}
    	else {
    		return new Vector(-0.5f, 1.0f);
    	}
    }
   
    private Vector getHipLocation(){
    	if (right) {
        return new Vector(-0.5f, 0.8f);
    	}
    	else {
    		return new Vector(0.5f, 0.8f);
    	}
    }
   
    private Vector getLeg1Location(){
    	if (right) {
        return new Vector(-0.20f, -0.0f);
    	}
    	else {
    		return new Vector(0.20f, 0.0f);
    	}
    }
   
    private Vector getLeg2Location(){
    	if (right) {
        return new Vector(0.20f, 0.0f);
    	}
    	else {
    		return new Vector(-0.20f, 0.0f);
    	}
    }
   
    private Vector getKneeLocation(){
    	if (right) {
        return new Vector(-0.05f, 0.5f);
    	}
    	else {
    		return new Vector(0.05f, 0.5f);
    	}
    }
    
    private Vector getHand1UpLocation() {
        if (right == true) {
        return new Vector(-0.5f, 2.3f) ;
        }
        else {
            return new Vector(0.5f, 2.3f) ;
            }
        }
    
    private Vector getHand2UpLocation() {
        if (right == true) {
        return new Vector(0.5f, 2.3f) ;
        }
        else {
            return new Vector(-0.5f, 2.3f) ;
            }
        }
   
    //Draw the bike and the wheels
    @Override
    public void draw(Canvas canvas) {
    	//if the boolean touched is true, there are 2 possibility
    	//either we win and the viewCandidate is on the finishLine, either we lose and the view candidate is on the biker
    	//we use these conditions to determine what to draw
    	if (touched) {
    		//If we lose we will just draw the lose message and the wheels
    		if (getOwner().getViewCandidate() == this) {
            messageGO.draw(canvas);
    		}
            wheelLeft.draw(canvas);
            wheelRight.draw(canvas);
        }
    	//if we win we will draw our victory position
    	else if (getOwner().getViewCandidate() != this) {
        	
        	arm2upShape.draw(canvas);
        	arm1upShape.draw(canvas);
        	headShape.draw(canvas);
        	kneeShape.draw(canvas);
        	hipShape.draw(canvas);
        	leg1Shape.draw(canvas);
            leg2Shape.draw(canvas);
            wheelLeft.draw(canvas);
            wheelRight.draw(canvas);
        }
    	//If it is not touched we will just draw the normal biker's shapes
    	else {
        headShape.draw(canvas);
        armShape.draw(canvas);
        kneeShape.draw(canvas);
        hipShape.draw(canvas);
        leg1Shape.draw(canvas);
        leg2Shape.draw(canvas);
        wheelLeft.draw(canvas);
        wheelRight.draw(canvas);
    	}
       
    }
    

    //return the hit value
    public boolean getHit() {
    	return hit;
    }
    //get the motor wheel depending on the boolean right
    public Wheel getMotorWheel() {
    	if (right) {
    		return wheelLeft;
    	}
    	else {
    		return wheelRight;
    	}
    }
    
    //get the no-motor wheel depending on the boolean right
    public Wheel getOtherWheel() {
    	if (right) {
    		return wheelRight;
    	}
    	else {
    		return wheelLeft;
    	}
    }
    
    
    //Update method
    @Override
    public void update(float deltaTime) {
    	
    	//We want to change the direction of the biker
    	if (keyboard.get(KeyEvent.VK_SPACE).isPressed()) {
    		//Change the value of the boolean
    		right = !right;
    		
    		//We create again each polyline and associate it with his shape
    		//All of our polyline change direction following the boolean "right" value
    		Polyline arm = new Polyline(getShoulderLocation(),getHandLocation()) ;
            armShape = new ShapeGraphics(arm, fillColor, outlineColor, thicknessBike, alpha, depth);
            armShape.setParent(getEntity());
            
            Polyline hip = new Polyline(getShoulderLocation(),getHipLocation()) ;
            hipShape = new ShapeGraphics(hip, fillColor, outlineColor, thicknessBike, alpha, depth);
            hipShape.setParent(getEntity());
            
            Polyline knee = new Polyline(getKneeLocation(),getHipLocation()) ;
            kneeShape = new ShapeGraphics(knee, fillColor, outlineColor, thicknessBike, alpha, depth);
            kneeShape.setParent(getEntity());
            
            Polyline leg1 = new Polyline(getLeg1Location(),getKneeLocation()) ;
            leg1Shape = new ShapeGraphics(leg1, fillColor, outlineColor, thicknessBike, alpha, depth);
            leg1Shape.setParent(getEntity());
            
            Polyline leg2 = new Polyline(getLeg2Location(),getKneeLocation()) ;
            leg2Shape = new ShapeGraphics(leg2, fillColor, outlineColor, thicknessBike, alpha, depth);
            leg2Shape.setParent(getEntity());
            
            Polyline arm1Up = new Polyline(getShoulderLocation(),getHand1UpLocation()) ;
            arm1upShape = new ShapeGraphics(arm1Up, fillColor, outlineColor, thicknessBike, alpha, depth);
            arm1upShape.setParent(getEntity());
            
            Polyline arm2Up = new Polyline(getShoulderLocation(),getHand2UpLocation()) ;
            arm2upShape = new ShapeGraphics(arm2Up, fillColor, outlineColor, thicknessBike, alpha, depth);
            arm2upShape.setParent(getEntity());
    	}
    	
    	//We stop the motor's movement
    	wheelRight.relax();
    	wheelLeft.relax();
    	
    	
    	//When the Down key is pressed, we put the motor's speed of the two wheels at 0
    	if (keyboard.get(KeyEvent.VK_DOWN).isDown()) {
    		wheelRight.power(0.0f);
    		wheelLeft.power(0.0f);
    	}
    	
    	
    	//When the up key is pressed, we put the motor's speed at 20
    	//It changes depending of the value of the boolean 'right'
    	//We must take care of the sign of the speed, because it's in radian following the direction of the trigonometrical circle
    	if (keyboard.get(KeyEvent.VK_UP).isDown()) {
    				if(right && (getMotorWheel().getSpeed() > -MAX_WHEEL_SPEED)) {
    				wheelLeft.power(-20.0f);
    		}
    				if (!right && (getMotorWheel().getSpeed() < MAX_WHEEL_SPEED)){
    				wheelRight.power(20.0f);
    			}
    		}
    		
    	
    	//We apply angular force to our biker if we press the left or right button
    	if (keyboard.get(KeyEvent.VK_LEFT).isDown()) {
    			getEntity().applyAngularForce(25.0f);
    		
    	}
    		
    		if (keyboard.get(KeyEvent.VK_RIGHT).isDown()) {
        			getEntity().applyAngularForce(-25.0f);
    	}
    	
    	//If the bike hit something, we change the value of the boolean touched (who will change permanently)
    	//we also call the destroy method
    	if (hit == true) {
    		touched = true;
    		destroy();
    	
    	}
    	
    }



	
}



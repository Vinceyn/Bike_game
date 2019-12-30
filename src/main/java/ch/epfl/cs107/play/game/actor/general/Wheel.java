package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.window.Canvas;

public class Wheel extends GameEntity implements Actor {
	
	//Attribute of the wheel: his graphic, his constraint, and the vehicle he's attached to
	private ShapeGraphics wheelGraphic;
	private WheelConstraint wheelConstraint;
	private Entity vehicle;
	
	//Wheel's builder
	public Wheel(ActorGame game, float positionX, float positionY, float friction, float thickness, float alpha, float depth) {
		//Creation of the entity
		super(game, false, new Vector(positionX, positionY));
		
		if(friction < 0 || thickness <0 || alpha < 0 || alpha > 1) {
			throw new IllegalArgumentException("Création d'un wheel avec des arguments invalides");
		}
		//Creation of the hitbox
		setPartBuilder(getEntity(), 0.5f, Vector.ZERO, friction, false);
		
		//Creation of the graphic
    	Circle circle = new Circle(0.5f, Vector.ZERO);
		wheelGraphic = new ShapeGraphics(circle, Color.BLACK, Color.RED, thickness, alpha, depth);
    	wheelGraphic.setParent(getEntity());
    	
	}
	
	//attach method
	//we attach the wheel to the given vehicle
	public void attach(Entity vehicle , Vector anchor , Vector axis) {
		if (vehicle == null) {
			throw new NullPointerException("Attach d'un Wheel avec un vehicle inexistant");
		}
		this.vehicle = vehicle;
		WheelConstraintBuilder constraintBuilder = getOwner().createWheelConstraintBuilder();
		constraintBuilder.setFirstEntity(vehicle) ;
		constraintBuilder.setFirstAnchor(anchor) ;
		constraintBuilder.setSecondEntity(getEntity()) ;
		constraintBuilder.setSecondAnchor(Vector.ZERO) ;
		constraintBuilder.setAxis(axis) ;
		constraintBuilder.setFrequency (3.0f) ;
		constraintBuilder.setDamping (0.5f) ;
		constraintBuilder.setMotorMaxTorque (10.0f) ;
		wheelConstraint = constraintBuilder.build () ;
	}
	
	//We firstly enable the motor, then put the speed at the desired value
	public void power(float speed) {
		wheelConstraint.setMotorEnabled(true);
		wheelConstraint.setMotorSpeed(speed);
	}
	
	//We disable the motor
	public void relax() {
		wheelConstraint.setMotorEnabled(false);
	}
	
	//We detach the wheel from the bike by destroying the constraint builder
	public void detach() {
		wheelConstraint.destroy();
	}
	
	
	/**
	@return relative rotation speed , in radians per second
	*/
	public float getSpeed () {
		float speed = getEntity().getAngularVelocity();
		if (vehicle == null) {
			return speed;
		}
		else {
			return speed - vehicle.getAngularVelocity();
		}
	}
	
	@Override
	//when the wheel are destroyed, they are detach from the vehicle and destroyed
	public void destroy() {
		this.detach();
		getEntity().destroy();
	}

	
	//we draw the wheelGraphic
	@Override
	public void draw(Canvas canvas) {
		if (wheelGraphic != null) {
		wheelGraphic.draw(canvas);
		}
}
	
	//We get the wheel Entity
	//we put this in public, otherwise there would be too much visibility problems
	public Entity getWheelEntity() {
		return getEntity();
	}
	
	
}
	
package front;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Windmill {

	// Our object is two boxes and one joint
	// Consider making the fixed box much smaller and not drawing it
	RevoluteJoint joint;
	Form box1;
	Form box2;
	Form block;
	Box2DProcessing box2d;

	public Windmill(PApplet app, Box2DProcessing box2d, int x, int y) {
		this.box2d = box2d;
		// Initialize locations of two boxes
		// Initialize locations of two boxes
		box1 = new Form(app, box2d, x, y, 100, 10, false);
		box2 = new Form(app, box2d, x, y, 20, 20, true);
		block = new Form(app, box2d, x, y + 15, 200, 2, true);
		// Define joint as between two bodies
		RevoluteJointDef rjd = new RevoluteJointDef();
		Vec2 offset = box2d.vectorPixelsToWorld(new Vec2(0, 60));
		rjd.initialize(box1.getBody(), box2.getBody(), box1.getBody().getWorldCenter());
		// Turning on a motor (optional)
		rjd.motorSpeed = (app.PI * 2) * -1; // how fast?
		rjd.maxMotorTorque = 10; // how powerful?
		rjd.enableMotor = false; // is it on?
		// There are many other properties you can set for a Revolute joint
		// For example, you can limit its angle between a minimum and a maximum
		// See box2d manual for more
		// Create the joint
		joint = (RevoluteJoint) box2d.world.createJoint(rjd);
	}

	// Turn the motor on or off
	public void toggleMotor() {
		joint.enableMotor(!joint.isMotorEnabled());
	}

	public boolean motorOn() {
		return joint.isMotorEnabled();
	}

	public void show(PApplet app) {
		box2.display(app);
		box1.display(app);
		if (block != null) {
			block.display(app);
		}
		// Draw anchor just for debug}
		Vec2 anchor = box2d.coordWorldToPixels(box1.getBody().getWorldCenter());
		app.fill(255, 0, 0);
		app.stroke(0);
		app.ellipse(anchor.x, anchor.y, 4, 4);
	}

	public void blockOut() {
		if (block != null) {
			block.killBody();
			block = null;
		}
		toggleMotor();
	}

	public void restartPosition(Vec2 start) {
		box2.restartPosition(start);
		box1.restartPosition(start);
		if (block != null) {
			block.restartPosition(start);
		}

	}
}
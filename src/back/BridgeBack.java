package back;

import java.util.ArrayList;

import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import front.Point;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import tuio.Reactivision;

public class BridgeBack {

	// Bridge properties
	float totalLength; // How long
	int numPoints; // How many points

	// Our chain is a list of particles
	protected ArrayList<Point> particles;
	protected PApplet app;
	Box2DProcessing box2d;

	// Chain constructor
	protected BridgeBack(PApplet app, Box2DProcessing box2d,Reactivision react, float l, int n) {
		this.app = app;
		this.box2d = box2d;
		totalLength = l;
		numPoints = n;

		particles = new ArrayList<Point>();

		float len = totalLength / numPoints;

		// Here is the real work, go through and add particles to the chain
		// itself
		for (int i = 0; i < numPoints + 1; i++) {
			// Make a new particle
			Point p = null;

			// First and last particles are made with density of zero
			if (i == 0 || i == numPoints)
				p = new Point(app,box2d,(550)+i * len, app.height / 2, 4, true);
			else
				p = new Point(app,box2d,(550)+i * len, app.height / 2, 4, false);
			particles.add(p);

			// Connect the particles with a distance joint
			if (i > 0) {
				DistanceJointDef djd = new DistanceJointDef();
				Point previous = particles.get(i - 1);
				// Connection between previous particle and this one
				djd.bodyA = previous.body;
				djd.bodyB = p.body;
				// Equilibrium length
				djd.length = box2d.scalarPixelsToWorld(len);
				// These properties affect how springy the joint is
				djd.frequencyHz = 0;
				djd.dampingRatio = 0;

				// Make the joint. Note we aren't storing a reference to the
				// joint ourselves anywhere!
				// We might need to someday, but for now it's ok
				DistanceJoint dj = (DistanceJoint) box2d.world.createJoint(djd);
			}
		}
	}
	

	

}

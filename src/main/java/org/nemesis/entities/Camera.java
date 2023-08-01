package org.nemesis.entities;

import org.joml.Vector3f;
import org.nemesis.renderEngine.Keyboard;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

	private Vector3f position = new Vector3f(0,0,0);
	private float pitch;
	private float yaw;
	private float roll;

	public Camera () {
	}

	public void move() {
		if (Keyboard.isKeyDown(GLFW_KEY_W) || Keyboard.isKeyDown(GLFW_KEY_UP)) {
			position.z -= 0.02f;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_S) || Keyboard.isKeyDown(GLFW_KEY_DOWN)) {
			position.z += 0.02f;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_A) || Keyboard.isKeyDown(GLFW_KEY_LEFT)) {
			position.x -= 0.02f;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_D) || Keyboard.isKeyDown(GLFW_KEY_RIGHT)) {
			position.x += 0.02f;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_SPACE)) {   // Go up
			position.y += 0.02f;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {  // Go down
			position.y -= 0.02f;
		}
	}

	public Vector3f getPosition () {
		return position;
	}

	public float getPitch () {
		return pitch;
	}

	public float getYaw () {
		return yaw;
	}

	public float getRoll () {
		return roll;
	}
}

package org.nemesis.entities;

import org.nemesis.components.MeshComponent;
import org.nemesis.components.TransformComponent;
import org.nemesis.renderEngine.DisplayManager;
import org.nemesis.renderEngine.Keyboard;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {

	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;

	private static final float TERRAIN_HEIGHT = 0;

	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;

	public Player ( int id, TransformComponent transformComponent, MeshComponent meshComponent ) {
		super( id );
		this.addComponent( transformComponent );
		this.addComponent( meshComponent );
	}

	public void move() {
		checkInputs();
		this.getComponent( TransformComponent.class ).rotation.y += currentTurnSpeed * DisplayManager.getFrameTimeSeconds();
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians( this.getComponent( TransformComponent.class ).rotation.y() )));
		float dz = (float) (distance * Math.cos(Math.toRadians( this.getComponent( TransformComponent.class ).rotation.y() )));
		this.getComponent( TransformComponent.class ).position.x += dx;
		this.getComponent( TransformComponent.class ).position.z += dz;
		this.upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		this.getComponent( TransformComponent.class ).position.y += upwardsSpeed * DisplayManager.getFrameTimeSeconds();
		if(this.getComponent( TransformComponent.class ).position.y <= TERRAIN_HEIGHT) {
			upwardsSpeed = 0;
			this.getComponent( TransformComponent.class ).position.y = TERRAIN_HEIGHT;
		}
	}

	public void jump() {
		if ( upwardsSpeed == 0 ) {
			this.upwardsSpeed = JUMP_POWER;
		}
	}

	private void checkInputs() {
		if ( Keyboard.isKeyDown( GLFW_KEY_W ) ) {
			this.currentSpeed = RUN_SPEED;
		} else if ( Keyboard.isKeyDown( GLFW_KEY_S ) ) {
			this.currentSpeed = -RUN_SPEED;
		} else {
			this.currentSpeed = 0;
		}

		if ( Keyboard.isKeyDown( GLFW_KEY_D ) ) {
			this.currentTurnSpeed = -TURN_SPEED;
		} else if ( Keyboard.isKeyDown( GLFW_KEY_A ) ) {
			this.currentTurnSpeed = TURN_SPEED;
		} else {
			this.currentTurnSpeed = 0;
		}

		if ( Keyboard.isKeyDown( GLFW_KEY_SPACE ) ) {
			this.jump();
		}
	}
}

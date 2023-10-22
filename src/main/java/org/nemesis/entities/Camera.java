package org.nemesis.entities;

import org.joml.Vector3f;
import org.nemesis.components.TransformComponent;
import org.nemesis.renderEngine.Mouse;

public class Camera {

	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(0,0,0);
	private float pitch = 20;
	private float yaw = 0;
	private float roll;

	private Player player;

	public Camera (Player player) {
		this.player = player;
	}

	public void move() {
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition( horizontalDistance, verticalDistance );
		this.yaw = 180 - (player.getComponent( TransformComponent.class )).rotation.y + angleAroundPlayer;
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

	private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
		float theta = player.getComponent( TransformComponent.class ).rotation.y + angleAroundPlayer;
		float offsetX = (float) (horizontalDistance * Math.sin( Math.toRadians( theta ) ));
		float offsetZ = (float) (horizontalDistance * Math.cos( Math.toRadians( theta ) ));
		position.x = player.getComponent( TransformComponent.class ).position.x - offsetX;
		position.z = player.getComponent( TransformComponent.class ).position.z - offsetZ;
		position.y = player.getComponent( TransformComponent.class ).position.y + verticalDistance;
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians( pitch )));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians( pitch )));
	}

	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
	}

	private void calculatePitch() {
		if ( Mouse.isLeftButtonPressed()) {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
	}

	private void calculateAngleAroundPlayer() {
		if (Mouse.isLeftButtonPressed()) {
			float angleChange = Mouse.getDX() * 0.1f;
			angleAroundPlayer -= angleChange;
		}
	}
}

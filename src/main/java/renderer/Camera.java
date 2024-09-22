package renderer;
import core.Input;
import org.joml.*;

import java.lang.Math;

import static org.joml.Math.lerp;
import static org.lwjgl.glfw.GLFW.*;

public class Camera {

	private Matrix4f m_Projection;
	private Matrix4f m_View;

	private Vector3f m_Position;

	private float m_FOV;
	private float m_AspectRatio;
	private float m_NearClip;
	private float m_FarClip;

	private float m_Yaw;
	private float m_Pitch;

	private Vector2f m_InitialMousePosition;
	private float m_ViewportWidth;
	private float m_ViewportHeight;
	private float m_MoveSpeed;

	public Camera(float fov, float aspectRatio, float nearClip, float farClip) {
		m_Projection = new Matrix4f();
		m_View = new Matrix4f();

		m_Position = new Vector3f();

		m_FOV = fov;
		m_AspectRatio = aspectRatio;
		m_NearClip = nearClip;
		m_FarClip = farClip;

		m_Yaw = 0.0f;
		m_Pitch = 0.0f;

		m_InitialMousePosition = new Vector2f();
		m_ViewportWidth = 1280.0f;
		m_ViewportHeight = 720.0f;
		m_MoveSpeed = 5.0f;

		updateProjection();
		updateView();
	}

	public void updateProjection() {
		m_AspectRatio = m_ViewportWidth / m_ViewportHeight;
		m_Projection.setPerspective((float) Math.toRadians(m_FOV), m_AspectRatio, m_NearClip, m_FarClip);
	}

	public void resize(float width, float height) {
		m_ViewportWidth = width;
		m_ViewportHeight = height;
		updateProjection();

		Renderer.setViewport((int)width, (int)height);
	}

	public void updateView() {
		Quaternionf orientation = getOrientation();
		Matrix4f translation = new Matrix4f().setTranslation(m_Position);
		Matrix4f rotation = orientation.get(new Matrix4f());

		m_View.set(rotation.mul(translation.invert()));
	}

	public Vector2f panSpeed() {
		float xFactor = 0.0f;
		float yFactor = 0.0f;
		float x = Math.min(m_ViewportWidth / 1000.0f, 2.4f);
		float y = Math.min(m_ViewportHeight / 1000.0f, 2.4f);

		xFactor = 0.0366f * (x * x) - 0.1778f * x + 0.75f;
		yFactor = 0.0366f * (y * y) - 0.1778f * y + 0.75f;

		return new Vector2f(xFactor, yFactor);
	}

	public Matrix4f getViewProjection(){
		Matrix4f viewProjectionMatrix = new Matrix4f();
		return viewProjectionMatrix.set(m_Projection).mul(m_View);
	}

	public float rotationSpeed() {
		return 0.8f;
	}

	public void onUpdate(float deltaTime) {
		mouseInput();

		Vector3f velocity = new Vector3f();

		if (Input.isKeyPressed(GLFW_KEY_A))
			velocity.sub(getRightDirection());
		else if (Input.isKeyPressed(GLFW_KEY_D))
			velocity.add(getRightDirection());
		if(!Input.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
			if (Input.isKeyPressed(GLFW_KEY_W))
				velocity.sub(getForwardDirection());
			else if (Input.isKeyPressed(GLFW_KEY_S))
				velocity.add(getForwardDirection());
		}
		else{
			if (Input.isKeyPressed(GLFW_KEY_W))
				velocity.add(getUpDirection());
			else if (Input.isKeyPressed(GLFW_KEY_S))
				velocity.sub(getUpDirection());
		}

		float speed = 5.2f;
		m_Position.x += velocity.x * speed * deltaTime;
		m_Position.y += velocity.y * speed * deltaTime;
		m_Position.z += velocity.z * speed * deltaTime;

		updateView();
	}

	public void mouseInput() {

		Vector2f mouse = new Vector2f(Input.getMouseX(), Input.getMouseY());
		Vector2f delta = mouse.sub(m_InitialMousePosition, new Vector2f()).mul(0.003f);
		m_InitialMousePosition = mouse;

		if(Input.isMouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT))
			mouseRotate(delta.mul(0.8f));

		if(Input.isMouseButtonPressed(GLFW_MOUSE_BUTTON_MIDDLE))
			mousePan(delta);

		updateView();
	}

	public void setPitch(float pitch){
		m_Pitch =  (float)Math.toRadians(pitch);
	}

	public void setYaw(float yaw){
		m_Yaw = (float)Math.toRadians(yaw);
	}

	public void setPosition(Vector3f position){
		m_Position = position;
	}

	public void mousePan(Vector2f delta) {
		Vector2f speeds = panSpeed();
		m_Position.add(getRightDirection().mul(-delta.x * speeds.x));
		m_Position.add(getUpDirection().mul(delta.y * speeds.y));
	}

	public void mouseRotate(Vector2f delta) {
		float yawSign = getUpDirection().y < 0 ? -1.0f : 1.0f;
		m_Yaw -= yawSign * delta.x * rotationSpeed();
		m_Pitch -= delta.y * rotationSpeed();
	}

	public Vector3f getUpDirection() {
		return getOrientation().positiveY(new Vector3f());
	}

	public Vector3f getRightDirection() {
		return getOrientation().positiveX(new Vector3f());
	}

	public Vector3f getForwardDirection() {
		return getOrientation().positiveZ(new Vector3f());
	}

	public Quaternionf getOrientation() {
		return new Quaternionf().rotateXYZ(-m_Pitch, -m_Yaw, 0.0f);
	}

}

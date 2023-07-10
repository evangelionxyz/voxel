package renderer;
import core.Input;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.*;

public class Camera {
	private Matrix4f mProjectionMatrix;
	private Matrix4f mViewMatrix;
	private Vector3f mPosition;
	private float mAspectRatio, mFov, mNear, mFar;
	private float mMoveSpeed = 3.4f;
	
	public Camera(float fov, float aspectRatio, float near, float far) {
		mFov = fov;
		mAspectRatio = aspectRatio;
		mNear = near;
		mFar = far;

		mPosition = new Vector3f(0.0f, 0.0f, -1.0f);
		mProjectionMatrix = new Matrix4f();
		mViewMatrix = new Matrix4f();

		updateProjection(fov, aspectRatio, near, far);
	}
	
	public void resize(float width, float height) {
		Renderer.setViewport((int)width, (int)height);
		mAspectRatio = width / height;
		mProjectionMatrix.setPerspective(mFov, mAspectRatio, mNear, mFar);
	}
	
	public void updateProjection(float fov, float aspectRatio, float near, float far) {
		mProjectionMatrix.setPerspective(fov, aspectRatio, near, far);
	}

	public void updateViewProjection(){
		mViewMatrix.setTranslation(mPosition.x, mPosition.y, mPosition.z);
	}
	
	public Matrix4f getViewProjection() {
		Matrix4f viewProjectionMatrix = new Matrix4f();
		return viewProjectionMatrix.set(mProjectionMatrix).mul(mViewMatrix);
	}

	public void onUpdate(float deltaTime){
		kyControl(deltaTime);
		updateViewProjection();
	}

	private void kyControl(float deltaTime){
		Vector3f velocity = new Vector3f(0.0f, 0.0f, 0.0f);

		if(Input.isKeyPressed(GLFW_KEY_A))
			velocity.x -= mMoveSpeed * deltaTime;
		else if(Input.isKeyPressed(GLFW_KEY_D))
			velocity.x += mMoveSpeed * deltaTime;
		if(Input.isKeyPressed(GLFW_KEY_W))
			velocity.y += mMoveSpeed * deltaTime;
		else if(Input.isKeyPressed(GLFW_KEY_S))
			velocity.y -= mMoveSpeed * deltaTime;
		if(Input.isKeyPressed(GLFW_KEY_Q))
			velocity.z += 2.0f * deltaTime;
		else if(Input.isKeyPressed(GLFW_KEY_E))
			velocity.z -= 2.0f * deltaTime;

		mPosition.lerp(mPosition, deltaTime, mPosition.add(velocity));
	}
}

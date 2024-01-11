package engine;

import engine.component.CollisionComponent;
import engine.component.Component;
import engine.component.TransformComponent;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class GameObject {

	protected int priority = 0;
	protected boolean isPressed = false;
	protected boolean isDragged = false;
	protected boolean mouseControlEnabled = false;

	private String tag;
	private TransformComponent transformComponent;
	private Map<String, Component> componentMap;

	public GameObject(String tag, Vec2d pos, Vec2d size) {
		this.tag = tag;
		this.transformComponent = new TransformComponent(pos, size);
		this.componentMap = new HashMap<>();
	}

	public void addComponent(Component component) {
		componentMap.put(component.getTag(), component);
	}

	public void removeComponent(Component component) {
		componentMap.remove(component.getTag(), component);
	}

	public Component getComponent(String tag) {
		return componentMap.get(tag);
	}

	public TransformComponent getTransformComponent() {
		return transformComponent;
	}

	public int getPriority() {
		return this.priority;
	}

	public boolean isPressed() {
		return isPressed;
	}

	public boolean isDragged() {
		return isDragged;
	}

	public boolean isMouseControlEnabled() {
		return mouseControlEnabled;
	}

	public Map<String, Component> getComponentMap() {
		return componentMap;
	}

	public String getTag() {
		return tag;
	}

	public void onTick(long nanosSinceLastTick) {

	}

	public void onLateTick() {

	}

	public void onDraw(GraphicsContext g) {

	}

	public void onResize(double zoomFactor) {
		transformComponent.onResize(new Vec2d(zoomFactor, zoomFactor));
	}

	public void onMouseClicked(MouseEvent e) {

	}

	public void onMouseDragged(MouseEvent e) {
		if (!mouseControlEnabled) {
			return;
		}

		isDragged = true;

		Vec2d prevPos = getTransformComponent().getPos();
		Vec2d delta = new Vec2d(e.getX() - prevPos.x, e.getY() - prevPos.y);

		getTransformComponent().setPos(new Vec2d(e.getX(), e.getY()));

		CollisionComponent collisionComponent = (CollisionComponent) getComponent("collision");
		if (collisionComponent != null) {
			collisionComponent.updatePos(delta);
		}
	}

	public void onMouseReleased(MouseEvent e) {
		isPressed = false;
	}

	public void onMousePressed(MouseEvent e) {
		isPressed = true;
		isDragged = false;
	}

	public void onHpBelowZero() {

	}
}

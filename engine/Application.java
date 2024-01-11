package engine;

import engine.support.FXFrontEnd;
import engine.support.Vec2d;
import engine.ui.Screen;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is your main Application class that you will contain your
 * 'draws' and 'ticks'. This class is also used for controlling
 * user input.
 */
public class Application extends FXFrontEnd {
	public Map<String, Screen> screenMap = new HashMap<>();

	protected String activeScreenTag = "default";
	protected Vec2d prevWindowSize = currentStageSize;

	public Application(String title) {
		super(title);
	}

	public Application(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
		super(title, windowSize, debugMode, fullscreen);
	}

	/**
	 * Called periodically and used to update the state of your game.
	 *
	 * @param nanosSincePreviousTick approximate number of nanoseconds since the previous call
	 */
	@Override
	protected void onTick(long nanosSincePreviousTick) {
		Screen activeScreen = screenMap.get(activeScreenTag);

		if (activeScreen != null) {
			activeScreen.onTick(nanosSincePreviousTick);

			GameWorld gameWorld = activeScreen.getGameWorld();

			if (gameWorld != null) {
				gameWorld.onTick(nanosSincePreviousTick);
			}
		}
	}

	/**
	 * Called after onTick().
	 */
	@Override
	protected void onLateTick() {
		// Don't worry about this method until you need it. (It'll be covered in class.)
	}

	/**
	 * Called periodically and meant to draw graphical components.
	 *
	 * @param g a {@link GraphicsContext} object used for drawing.
	 */
	@Override
	protected void onDraw(GraphicsContext g) {
		Screen activeScreen = screenMap.get(activeScreenTag);

		if (activeScreen != null) {
			activeScreen.onDraw(g);
		}
	}

	/**
	 * Called when a key is typed.
	 *
	 * @param e an FX {@link KeyEvent} representing the input event.
	 */
	@Override
	protected void onKeyTyped(KeyEvent e) {

	}

	/**
	 * Called when a key is pressed.
	 *
	 * @param e an FX {@link KeyEvent} representing the input event.
	 */
	@Override
	protected void onKeyPressed(KeyEvent e) {
		Screen activeScreen = screenMap.get(activeScreenTag);

		if (activeScreen != null) {
			GameWorld gameWorld = activeScreen.getGameWorld();

			if (gameWorld != null) {
				gameWorld.onKeyPressed(e);
			}
		}
	}

	/**
	 * Called when a key is released.
	 *
	 * @param e an FX {@link KeyEvent} representing the input event.
	 */
	@Override
	protected void onKeyReleased(KeyEvent e) {
		// quit game if on escape
		if (e.getCode() == KeyCode.ESCAPE) {
			Platform.exit();
		}
	}

	/**
	 * Called when the mouse is clicked.
	 *
	 * @param e an FX {@link MouseEvent} representing the input event.
	 */
	@Override
	protected void onMouseClicked(MouseEvent e){
		Screen activeScreen = screenMap.get(activeScreenTag);

		if (activeScreen != null) {
			activeScreen.onMouseClicked(e);

			GameWorld gameWorld = activeScreen.getGameWorld();

			if (gameWorld != null) {
				gameWorld.onMouseClicked(e);
			}
		}
	}

	/**
	 * Called when the mouse is pressed.
	 *
	 * @param e an FX {@link MouseEvent} representing the input event.
	 */
	@Override
	protected void onMousePressed(MouseEvent e) {
		Screen activeScreen = screenMap.get(activeScreenTag);

		if (activeScreen != null) {
			activeScreen.onMousePressed(e);

			GameWorld gameWorld = activeScreen.getGameWorld();

			if (gameWorld != null) {
				gameWorld.onMousePressed(e);
			}
		}
	}

	/**
	 * Called when the mouse is released.
	 *
	 * @param e an FX {@link MouseEvent} representing the input event.
	 */
	@Override
	protected void onMouseReleased(MouseEvent e) {
		Screen activeScreen = screenMap.get(activeScreenTag);

		if (activeScreen != null) {
			activeScreen.onMouseReleased(e);

			GameWorld gameWorld = activeScreen.getGameWorld();

			if (gameWorld != null) {
				gameWorld.onMouseReleased(e);
			}
		}
	}

	/**
	 * Called when the mouse is dragged.
	 *
	 * @param e an FX {@link MouseEvent} representing the input event.
	 */
	@Override
	protected void onMouseDragged(MouseEvent e) {
		Screen activeScreen = screenMap.get(activeScreenTag);

		if (activeScreen != null) {
			GameWorld gameWorld = activeScreen.getGameWorld();

			if (gameWorld != null) {
				gameWorld.onMouseDragged(e);
			}
		}
	}

	/**
	 * Called when the mouse is moved.
	 *
	 * @param e an FX {@link MouseEvent} representing the input event.
	 */
	@Override
	protected void onMouseMoved(MouseEvent e) {
		Screen activeScreen = screenMap.get(activeScreenTag);

		if (activeScreen != null) {
			activeScreen.onMouseMoved(e);
		}
	}

	/**
	 * Called when the mouse wheel is moved.
	 *
	 * @param e an FX {@link ScrollEvent} representing the input event.
	 */
	@Override
	protected void onMouseWheelMoved(ScrollEvent e) {
		Screen activeScreen = screenMap.get(activeScreenTag);

		if (activeScreen != null) {
			GameWorld gameWorld = activeScreen.getGameWorld();

			if (gameWorld != null) {
				gameWorld.onMouseWheelMoved(e);
			}
		}
	}

	/**
	 * Called when the window's focus is changed.
	 *
	 * @param newVal a boolean representing the new focus state
	 */
	@Override
	protected void onFocusChanged(boolean newVal) {

	}

	/**
	 * Called when the window is resized.
	 *
	 * @param newSize the new size of the drawing area.
	 */
	@Override
	protected void onResize(Vec2d newSize) {
		for (Screen each : screenMap.values()) {
			each.setScreenSize(newSize);
		}

		Screen activeScreen = screenMap.get(activeScreenTag);

		if (activeScreen != null) {
			activeScreen.onResize(newSize.pdiv(prevWindowSize));
			prevWindowSize = newSize;
		}
	}

	/**
	 * Called when the app is shutdown.
	 */
	@Override
	protected void onShutdown() {

	}

	/**
	 * Called when the app is starting up.
	 */
	@Override
	protected void onStartup() {

	}

	public void setActiveScreenTag(String tag) {
		activeScreenTag = tag;
	}

	public Vec2d getWindowSize() {
		return currentStageSize;
	}
}

package engine.ui.button;

public class SwitchController {
	SwitchButton onButton;

	public SwitchController() { }

	public void setOnButton(SwitchButton switchButton) {
		if (onButton != null) {
			onButton.setOn(false);
		}

		onButton = switchButton;
		onButton.setOn(true);
	}
}

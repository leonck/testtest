package com.hubsan.joystick.widgets;


public interface JoystickMovedListener {
    public void OnMoved(float pan, float tilt);
    public void OnReleased();
    public void OnReturnedToCenter();
}


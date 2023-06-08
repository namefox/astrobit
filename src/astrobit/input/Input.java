package astrobit.input;

import astrobit.other.UnitConverter;
import astrobit.other.Vector2;

import java.awt.event.*;

public final class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private static final boolean[] keys = new boolean[Code.CUT.value + 1];
    private static final boolean[] mouse = new boolean[5];

    public static double mouseWheel;
    public static boolean mouseIn, mouseOut, mouseDragging;
    public static Vector2 mousePosition = new Vector2(0, 0);

    @Override public void keyTyped(KeyEvent e){}
    @Override public void mouseClicked(MouseEvent e){}

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouse[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouse[e.getButton()] = false;
        mouseDragging = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseIn = true;
        mouseOut = false;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseIn = false;
        mouseOut = true;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseDragging = true;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = UnitConverter.pixelToUnit(new Vector2(e.getX(), e.getY()), Vector2.one)[0];
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheel = e.getPreciseWheelRotation();
    }

    public static boolean keyPressed(Code keyCode) {
        return keys[keyCode.value];
    }

    public static boolean keyReleased(Code keyCode) {
        return !keys[keyCode.value];
    }

    public static boolean mousePressed(Code mouseCode) {
        return mouse[mouseCode.value];
    }

    public static boolean mouseReleased(Code mouseCode) {
        return !mouse[mouseCode.value];
    }
}
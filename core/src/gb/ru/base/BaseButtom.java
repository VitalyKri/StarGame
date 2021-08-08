package gb.ru.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseButtom extends Sprite {

    private static final float PRESS_SCALE = 0.9f;

    private int pointer;
    private boolean pressed;


    public BaseButtom(TextureRegion region) {
        super(region);
    }

    public abstract void action();

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {

        if (pressed || !isMe(touch)) {
            return false;
        }

        this.pointer = pointer;
        scale = PRESS_SCALE;
        pressed = true;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || !pressed) {
            return false;
        }
        // если отпускает на кнопке, только тогда работает.
        if (isMe(touch)) {
            action();
        }
        scale = 1f;
        pressed = false;
        return false;

    }
}

package gb.ru.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gb.ru.base.Sprite;
import gb.ru.math.Rect;

public class FriendShip extends Sprite {

    // измеряется в процентах от размера корабля
    private float speed;

    private Vector2 vDeraction, touch;

    public FriendShip(Texture texture) {
        super(new TextureRegion(texture));
        touch = new Vector2();
        vDeraction = new Vector2();

    }

    @Override
    public void draw(SpriteBatch batch) {
        if (touch.dst(pos) > speed) {
            pos.add(vDeraction);
        } else {
            pos.set(touch);
        }
        super.draw(batch);
    }


    @Override
    public void resize(Rect worldBounds) {

        setHeightProportion(worldBounds.getHeight() / 20);
        pos.set(worldBounds.pos);
        vDeraction.set(pos);

    }

    public void setSpeed(float percent) {
        this.speed = (float) getWidth() * percent / 100;
    }

    public void updateManeuver(Vector2 touch) {

        this.touch.set(touch.x, touch.y);
        vDeraction.set(this.touch.cpy().sub(pos)).setLength(speed);
    }
}

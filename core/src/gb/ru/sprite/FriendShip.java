package gb.ru.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gb.ru.base.Sprite;
import gb.ru.math.Rect;

public class FriendShip extends Sprite {

    // измеряется в процентах от размера корабля
    private float speed;

    private Vector2 vDeraction, touch;

    public FriendShip(TextureAtlas atlas) {
        super(new TextureRegion(atlas.findRegion("")));
        touch = new Vector2();
        vDeraction = new Vector2();

    }

    @Override
    public void update(float delta) {
        if (touch.dst(pos) > speed) {
            pos.add(vDeraction);
        } else {
            pos.set(touch);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }


    @Override
    public void resize(Rect worldBounds) {

        setHeightProportion(0.05f * scale);

    }

    public void setSpeed(float percent) {
        this.speed = (float) getWidth() * percent / 100;
    }

    public void updateManeuver(Vector2 touch) {

        this.touch.set(touch.x, touch.y);
        vDeraction.set(this.touch.cpy().sub(pos)).setLength(speed);
    }
}

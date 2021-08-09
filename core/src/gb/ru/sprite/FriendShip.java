package gb.ru.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import gb.ru.base.Sprite;
import gb.ru.math.Rect;


public class FriendShip extends Sprite {


    // измеряется в процентах от размера корабля
    private float speed;
    private HashSet<Direction> directions = new HashSet<>();
    private Iterator<Direction> iterator;
    private Vector2 vDeraction, touch;
    private Rect worldBounds;


    public FriendShip(TextureAtlas atlas) {
        super(new TextureRegion(atlas.findRegion("main_ship_add")));
        touch = new Vector2();
        vDeraction = new Vector2();
    }

    @Override
    public void update(float delta) {
        if (touch.dst(pos) > speed) {
            pos.add(vDeraction);
            checkAndHandelBounds();
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
        this.worldBounds = worldBounds;
        setHeightProportion(0.07f);

    }

    public void checkAndHandelBounds() {
        if (getLeft() < worldBounds.getLeft()) {
            pos.sub(vDeraction);
        }
        if (getRight() > worldBounds.getRight()) {
            pos.sub(vDeraction);
        }
        if (getBottom() < worldBounds.getBottom()) {
            pos.sub(vDeraction);
        }
        if (getTop() > worldBounds.getTop()) {
            pos.sub(vDeraction);
        }

    }

    public void setSpeed(float percent) {
        this.speed = (float) getWidth() * percent / 100;
        vDeraction.setLength(this.speed);
    }

    public void updateManeuver(Vector2 touch) {
        // если нажаты кнопки, мышь не работает
        if (directions.size() == 0) {
            this.touch.set(touch.x, touch.y);
            vDeraction.set(this.touch.cpy().sub(pos)).setLength(speed);
        }

    }

    public void deleteManeuver() {
        updateManeuver(pos);
    }

    public void deleteManeuver(Direction direction) {
        directions.remove(direction);
        iterator = directions.iterator();
        vDeraction.set(0, 0);
        while (iterator.hasNext()) {
            vDeraction.add(iterator.next().getVector());
        }
        vDeraction.setLength(speed);
        this.touch.set(pos.cpy().add(vDeraction)).setLength(2);
    }

    public void updateManeuver(Direction direction) {
        directions.add(direction);
        iterator = directions.iterator();
        vDeraction.set(0, 0);
        while (iterator.hasNext()) {
            vDeraction.add(iterator.next().getVector());
        }
        vDeraction.setLength(speed);
        this.touch.set(pos.cpy().add(vDeraction)).setLength(2);
    }
}

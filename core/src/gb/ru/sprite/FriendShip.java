package gb.ru.sprite;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

import gb.ru.base.Ship;
import gb.ru.math.Rect;
import gb.ru.pool.BulletPool;
import gb.ru.pool.ExplosionPool;


public class FriendShip extends Ship {

    private static final int HP = 100;

    // измеряется в процентах от размера корабля
    private HashSet<Direction> directions = new HashSet<>();
    private LinkedHashMap<Integer, Vector2> touchDeration = new LinkedHashMap<>();
    private Iterator<Direction> iterator;


    public FriendShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(new TextureRegion(atlas.findRegion("main_ship")), 1, 2, 2, 1);
        touch = new Vector2();
        vDeraction = new Vector2();
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletHeight = 0.01f;
        bulletDamage = 1;
        collisionDamage = 5;
        hp = HP;
        reloadInterval = 0.25f;
        this.explosionPool = explosionPool;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        checkAndHandelBounds();
    }

    public void checkAndHandelBounds() {
        if (getBottom() < worldBounds.getBottom()) {
            setBottom(worldBounds.getBottom());
        }
        if (getTop() > worldBounds.getTop()) {
            setTop(worldBounds.getTop());
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

    public void setSpeed(float percent) {
        this.speed = (float) percent / 100;
        vDeraction.setLength(this.speed);
    }

    public void updateManeuver() {
        // если нажаты кнопки, мышь не работает
        if (touchDeration.isEmpty()) {
            this.touch.set(pos.x, pos.y);
            vDeraction.set(this.touch.cpy().sub(pos)).setLength(speed);
        } else {
            for (Integer key : touchDeration.keySet()
            ) {
                // т.к. в touchDown всегда работает с 1 ссылкой, пришлось в первый элемент мапы записывать свой вектор,
                // и если первая кнопка отжимается, всегда управление переходит к самому первому.
                this.touch.set(touchDeration.get(key));
                touchDeration.put(key, this.touch);
                vDeraction.set(this.touch.cpy().sub(pos)).setLength(speed);
                break;
            }
        }
    }

    public void deleteManeuver(int pointer) {
        touchDeration.remove(pointer);
        updateManeuver();
    }

    public void addManeuver(int pointer, Vector2 vector) {
        touchDeration.put(pointer, vector);
        updateManeuver();
    }

    public boolean keyDown(int keycode) {
        Direction direction = Direction.directionOnKeycode(keycode);
        if (direction != Direction.NOTDIRECTION) {
            updateManeuver(direction);
        }
        return false;
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

    public boolean keyUp(int keycode) {
        Direction direction = Direction.directionOnKeycode(keycode);
        if (direction != Direction.NOTDIRECTION) {
            deleteManeuver(direction);
        }
        return false;
    }

    @Override
    public boolean isBulletCollision(Bullet bullet){
        // попадает в нижнюю половину корабля
        return (
                bullet.getRight()>getLeft()
                        && bullet.getLeft()<getRight()
                        && bullet.getTop()< getBottom()
                        && bullet.getBottom()< pos.y
        );
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

    public void startNewGame(){
        hp = HP;
        this.pos.x = worldBounds.pos.x;
        directions.clear();
        touchDeration.clear();
        touch.set(0,0);
        vDeraction.set(0,0);
        flushDestroy();
    }

}

package gb.ru.sprite;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

import gb.ru.base.Sprite;
import gb.ru.math.Rect;
import gb.ru.pool.BulletPool;


public class FriendShip extends Sprite {


    // измеряется в процентах от размера корабля
    private float speed;
    private HashSet<Direction> directions = new HashSet<>();
    private LinkedHashMap<Integer, Vector2> touchDeration = new LinkedHashMap<>();
    private Iterator<Direction> iterator;
    private Vector2 vDeraction, touch;
    private Rect worldBounds;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;
    private float bulletHeight;
    private int bulletDamage;
    public FriendShip(TextureAtlas atlas,BulletPool bulletPool) {
        super(new TextureRegion(atlas.findRegion("main_ship")), 1, 2, 2, 1);
        touch = new Vector2();
        vDeraction = new Vector2();
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0,0.5f);
        bulletHeight = 0.01f;
        bulletDamage = 1;
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

    public void updateManeuver() {
        // если нажаты кнопки, мышь не работает
        if (touchDeration.isEmpty()) {
            this.touch.set(pos.x, pos.y);
            vDeraction.set(this.touch.cpy().sub(pos)).setLength(speed);
        } else {
            for (Integer key: touchDeration.keySet()
            ) {
                // т.к. в touchDown всегда работает с 1 ссылкой, пришлось в первый элемент мапы записывать свой вектор,
                // и если первая кнопка отжимается, всегда управление переходит к самому первому.
                this.touch.set(touchDeration.get(key));
                touchDeration.put(key,this.touch);
                vDeraction.set(this.touch.cpy().sub(pos)).setLength(speed);
                break;
            }
        }
    }

    public void deleteManeuver(int pointer) {
        touchDeration.remove(pointer);
        updateManeuver();
    }
    public void addManeuver(int pointer,Vector2 vector) {
        touchDeration.put(pointer,vector);
        updateManeuver();
    }


    public boolean keyDown(int keycode) {
        Direction direction = Direction.directionOnKeycode(keycode);
        if (direction != Direction.NOTDIRECTION) {
            updateManeuver(direction);
        }
        if (keycode == Input.Keys.E){
            shoot();
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
    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,bulletRegion,pos,bulletV,bulletHeight,worldBounds,bulletDamage);

    }
}

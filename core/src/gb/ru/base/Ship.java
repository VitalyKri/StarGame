package gb.ru.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gb.ru.math.Rect;
import gb.ru.pool.BulletPool;
import gb.ru.sprite.Bullet;

public class Ship extends Sprite {

    protected Vector2 v0;
    protected float speed;
    protected Vector2 vDeraction, touch;
    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV,bulletPos;
    protected float bulletHeight,reloadTimer,reloadInterval;
    protected int bulletDamage;
    protected Sound bulletSound,laserSound;
    protected int hp;
    protected int collisionDamage;

    public Ship() {
        super();
        this.vDeraction = new Vector2();
        this.v0 = new Vector2();
        this.bulletPos = new Vector2();
        this.bulletV = new Vector2(0,0.5f);
    }

    public Ship(TextureRegion region, int rows, int cols, int frames, int frame) {
        super(region, rows, cols, frames, frame);
        vDeraction = new Vector2();
        v0 = new Vector2();
        bulletPos = new Vector2();
        bulletV = new Vector2(0,0.5f);
    }

    public void setBulletSound(Sound bulletSound) {
        this.bulletSound = bulletSound;
    }

    public void setLaserSound(Sound laserSound) {
        this.laserSound = laserSound;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (touch.dst(pos) > speed) {
            pos.add(vDeraction);
        } else {
            pos.set(touch);
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
        }

        reloadTimer +=delta;

        if (reloadTimer> reloadInterval){
            reloadTimer = 0;
            shoot();
        }

    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public void setBulletDamage(int bulletDamage) {
        this.bulletDamage = bulletDamage;
    }

    public int getCollisionDamage() {
        return collisionDamage;
    }

    public void setCollisionDamage(int collisionDamage) {
        this.collisionDamage = collisionDamage;
    }

    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        bulletSound.play(0.1f);
        bullet.set(this,bulletRegion,pos,bulletV,bulletHeight,worldBounds,bulletDamage);

    }

    public void damage(int damage){
        this.hp -= damage;
        if (hp <= 0){
            hp = 0;
            destroy();
        }
    }
}

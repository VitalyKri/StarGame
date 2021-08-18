package gb.ru.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gb.ru.base.Ship;
import gb.ru.math.Rect;
import gb.ru.pool.BulletPool;

public class EnemyShip extends Ship {

    public EnemyShip(Rect worldBounds, BulletPool bulletPool) {
        super();
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
        if (getTop() < worldBounds.getTop()){
            this.vDeraction.set(v0);
        }

    }



    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            Vector2 bulletV,
            float bulletHeight,
            int bulletDamage,
            Sound bulletSound,
            float reloadInterval,
            float height,
            int hp,
            float speed,
            int collisionDamage
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletV.set(bulletV);
        this.bulletHeight = bulletHeight;
        this.bulletDamage = bulletDamage;
        this.bulletSound = bulletSound;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        this.speed = speed * getWidth() / 100;
        this.v0.set(v0).setLength(this.speed);
        this.vDeraction.set(v0).setLength(-0.005f);
        this.touch = new Vector2(0, -4f);
        this.collisionDamage = collisionDamage;

    }

    public boolean isBulletCollision(Bullet bullet){
        // попадает в верхную половину корабля
        return (
                bullet.getRight()>getLeft()
                && bullet.getLeft()<getRight()
                && bullet.getBottom()< getTop()
                && bullet.getTop()> pos.y
                );
    }

}

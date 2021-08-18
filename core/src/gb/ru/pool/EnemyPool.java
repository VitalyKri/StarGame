package gb.ru.pool;

import gb.ru.base.SpritePool;
import gb.ru.math.Rect;
import gb.ru.sprite.EnemyShip;

public class EnemyPool extends SpritePool<EnemyShip> {

    private final Rect worldBounds;
    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;
    public EnemyPool(Rect worldBounds, BulletPool bulletPool,ExplosionPool explosionPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    protected EnemyShip newSprite() {
        return new EnemyShip(worldBounds,bulletPool,explosionPool);
    }
}

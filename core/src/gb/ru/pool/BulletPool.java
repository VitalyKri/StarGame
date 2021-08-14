package gb.ru.pool;

import gb.ru.base.SpritePool;
import gb.ru.sprite.Bullet;

public class BulletPool extends SpritePool<Bullet> {
    @Override
    protected Bullet newSprite() {
        return new Bullet();
    }
}

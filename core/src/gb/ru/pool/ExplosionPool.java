package gb.ru.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gb.ru.base.SpritePool;
import gb.ru.sprite.Explosion;

public class ExplosionPool extends SpritePool<Explosion> {

    private final TextureAtlas atlas;
    private final Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas, Sound explosionSound) {
        this.atlas = atlas;
        this.explosionSound = explosionSound;
    }


    @Override
    protected Explosion newSprite() {
        return new Explosion(atlas, explosionSound);
    }
}

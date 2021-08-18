package gb.ru.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gb.ru.math.Rect;
import gb.ru.math.Rnd;
import gb.ru.pool.EnemyPool;
import gb.ru.sprite.EnemyShip;

public class EnemyEmitter {

    private static final float GENERATE_INTERVAL = 4f;

    private final Rect worldBounds;
    private final Sound bulletSound;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMediumRegions;
    private final TextureRegion[] enemyBigRegions;
    private final TextureRegion bulletRegion;
    private final EnemyPool enemyPool;


    private float generateTimer;

    public EnemyEmitter(Rect worldBounds, Sound bulletSound, EnemyPool enemyPool, TextureAtlas atlas) {
        this.worldBounds = worldBounds;
        this.bulletSound = bulletSound;
        this.enemyPool = enemyPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        this.enemyMediumRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        this.enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
    }

    public void generate(float detla) {
        generateTimer += detla;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            float type = Rnd.nextFloat(0, 100);
            if (type <= 60) {
                TypeShip.SMALL.createRandomShip(
                        this.enemyPool,
                        this.bulletSound,
                        this.bulletRegion,
                        this.enemySmallRegions,
                        this.worldBounds
                );
            } else if (type <= 90) {
                TypeShip.MEDIUM.createRandomShip(
                        this.enemyPool,
                        this.bulletSound,
                        this.bulletRegion,
                        this.enemyMediumRegions,
                        this.worldBounds
                );
            } else {
                TypeShip.BIG.createRandomShip(
                        this.enemyPool,
                        this.bulletSound,
                        this.bulletRegion,
                        this.enemyBigRegions,
                        this.worldBounds
                );
            }

        }
    }
}

enum TypeShip {
    SMALL(0.1f, 0.01f,
            1, 3f,
            1, new Vector2(0f, -0.02f), new Vector2(0, -0.3f),3f,1),
    MEDIUM(0.15f, 0.02f,
            5, 4f,
            5, new Vector2(0f, -0.03f), new Vector2(0, -0.25f),1.5f,2),
    BIG(0.2f, 0.03f,
            10, 1f,
            10, new Vector2(0f, -0.005f), new Vector2(0, -0.15f),0.5f,3);

    private final float ENEMY_HEIGHT;
    private final float ENEMY_BULLET_HEIGHT;
    private final int ENEMY_BULLET_DAMAGE;
    private final float ENEMYL_RELOAD_INTERVAL;
    private final int ENEMY_HP;
    private final Vector2 enemyV;
    private final Vector2 enemyBulletVY;
    private final float speed;
    private final int collisionDamage;

    TypeShip(float ENEMY_HEIGHT,
             float ENEMY_BULLET_HEIGHT,
             int ENEMY_BULLET_DAMAG,
             float ENEMYL_RELOAD_INTERVAL,
             int ENEMY_HP,
             Vector2 enemyV,
             Vector2 enemyBulletVY,
             float speed,
             int collisionDamage) {
        this.ENEMY_HEIGHT = ENEMY_HEIGHT;
        this.ENEMY_BULLET_HEIGHT = ENEMY_BULLET_HEIGHT;
        this.ENEMY_BULLET_DAMAGE = ENEMY_BULLET_DAMAG;
        this.ENEMYL_RELOAD_INTERVAL = ENEMYL_RELOAD_INTERVAL;
        this.ENEMY_HP = ENEMY_HP;
        this.enemyV = enemyV;
        this.enemyBulletVY = enemyBulletVY;
        this.speed = speed;
        this.collisionDamage = collisionDamage;
    }

    public void createRandomShip(EnemyPool enemyPool,
                                 Sound bulletSound,
                                 TextureRegion bulletRegion,
                                 TextureRegion[] enemyRegions,
                                 Rect worldBounds) {
        EnemyShip enemyShip = enemyPool.obtain();
        enemyShip.set(
                enemyRegions,
                this.enemyV,
                bulletRegion,
                this.enemyBulletVY,
                this.ENEMY_BULLET_HEIGHT,
                this.ENEMY_BULLET_DAMAGE,
                bulletSound,
                this.ENEMYL_RELOAD_INTERVAL,
                this.ENEMY_HEIGHT,
                this.ENEMY_HP,
                this.speed,
                this.collisionDamage
        );
        float x = Rnd.nextFloat(
                worldBounds.getLeft() + enemyShip.getHalfWidth(),
                worldBounds.getRight() - enemyShip.getHalfWidth());
        enemyShip.pos.x = x;

        enemyShip.setBottom(worldBounds.getTop());

    }
}

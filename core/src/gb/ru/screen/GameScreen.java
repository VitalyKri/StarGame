package gb.ru.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import gb.ru.base.BaseScreen;
import gb.ru.base.Playlist;
import gb.ru.base.Sprite;
import gb.ru.math.Rect;
import gb.ru.pool.BulletPool;
import gb.ru.pool.EnemyPool;
import gb.ru.sprite.Background;
import gb.ru.sprite.Bullet;
import gb.ru.sprite.Direction;
import gb.ru.sprite.EnemyShip;
import gb.ru.sprite.FriendShip;
import gb.ru.sprite.Star;
import gb.ru.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUTN = 128;

    private Texture bg;
    private Background background;
    private FriendShip myShip;
    private TextureAtlas atlas, atlasMenu;
    private Star[] stars;

    private BulletPool bulletPoll;
    private EnemyPool enemyPool;

    private Playlist playlist;

    private EnemyEmitter enemyEmitter;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        background = new Background(bg);
        bulletPoll = new BulletPool();
        enemyPool = new EnemyPool(worldBounds,bulletPoll);
        myShip = new FriendShip(atlas, bulletPoll);
        playlist = new Playlist("background3");
        myShip.setBulletSound(playlist.getLaserSound());
        stars = new Star[STAR_COUTN];

        for (int i = 0; i < STAR_COUTN; i++) {
            stars[i] = new Star(atlas);
        }

        enemyEmitter= new EnemyEmitter(
                worldBounds,
                playlist.getBulletSound(),
                enemyPool,
                atlas
        );

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        myShip.resize(worldBounds);
        myShip.setSpeed(7);
    }

    public void update(float delta) {
        myShip.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }
        playlist.update(delta);
        checkСollision();
        bulletPoll.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
    }

    public void freeAllDestroyed() {
        bulletPoll.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletPoll.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        myShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPoll.dispose();
        playlist.dispose();
        enemyPool.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        myShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        myShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        myShip.addManeuver(pointer, touch);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        myShip.deleteManeuver(pointer);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        myShip.addManeuver(pointer, touch);
        return false;
    }

    public void checkСollision(){

        List<Bullet> bulletList =  bulletPoll.getActiveSprites();
        for (EnemyShip  enemyShip : enemyPool.getActiveSprites()
        ) {
            if (enemyShip.isDestroyed()){
                continue;
            }

            if(enemyShip.isInsideDst(myShip)){
                enemyShip.damage(myShip.getCollisionDamage());
                if (enemyShip.getHp() <0){
                    playlist.getExplosionSound().play(0.1f);
                }

            }
            for ( Bullet  bullet :  bulletList
            ) {
                if (bullet.isDestroyed() || bullet.getOwner() != myShip){
                    continue;
                }
                if(enemyShip.isBulletCollision(bullet)){
                    bullet.destroy();
                    enemyShip.damage(myShip.getBulletDamage());
                    if (enemyShip.getHp() <=0){
                        playlist.getExplosionSound().play(0.1f);
                    }

                }
            }



        }
    }



}

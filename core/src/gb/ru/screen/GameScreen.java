package gb.ru.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;
import java.util.Random;

import gb.ru.base.BaseScreen;
import gb.ru.base.Font;
import gb.ru.base.Playlist;
import gb.ru.base.Sprite;
import gb.ru.math.Rect;
import gb.ru.math.Rnd;
import gb.ru.pool.BulletPool;
import gb.ru.pool.EnemyPool;
import gb.ru.pool.ExplosionPool;
import gb.ru.sprite.Background;
import gb.ru.sprite.Bullet;
import gb.ru.sprite.Direction;
import gb.ru.sprite.EnemyShip;
import gb.ru.sprite.FriendShip;
import gb.ru.sprite.GameOver;
import gb.ru.sprite.NewGameButtom;
import gb.ru.sprite.Star;
import gb.ru.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUTN = 128;

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";
    private static final float PADDING = 0.01f;

    private Texture bg;
    private Background background;
    private FriendShip myShip;
    private TextureAtlas atlas, atlasMenu;
    private Star[] stars;

    private BulletPool bulletPoll;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private Playlist playlist;

    private EnemyEmitter enemyEmitter;

    private GameOver gameOver;
    private NewGameButtom newGameButtom;

    private int frags;
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;
    private Font font;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        background = new Background(bg);
        bulletPoll = new BulletPool();
        playlist = new Playlist("background3");
        explosionPool = new ExplosionPool(atlas,playlist.getExplosionSound());
        enemyPool = new EnemyPool(worldBounds, bulletPoll,explosionPool);
        myShip = new FriendShip(atlas, bulletPoll,explosionPool);

        myShip.setBulletSound(playlist.getLaserSound());
        stars = new Star[STAR_COUTN];

        for (int i = 0; i < STAR_COUTN; i++) {
            stars[i] = new Star(atlas);
        }

        enemyEmitter = new EnemyEmitter(
                worldBounds,
                playlist.getBulletSound(),
                enemyPool,
                atlas
        );

        gameOver = new GameOver(atlas);
        newGameButtom = new NewGameButtom(atlas,this);

        frags = 0;
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();
        font = new Font("font/font.fnt","font/font.png");
        font.setSize(0.02f);
    }

    public void startNewGame(){
        bulletPoll.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
        myShip.startNewGame();
        frags = 0;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkСollision();
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
        myShip.setSpeed(20);
        gameOver.resize(worldBounds);
        newGameButtom.resize(worldBounds);
    }

    public void update(float delta) {

        for (Star star : stars) {
            star.update(delta);
        }
        if (!myShip.isDestroyed()){
            myShip.update(delta);
            bulletPoll.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
        }
        explosionPool.updateActiveSprites(delta);
        playlist.update(delta);
        boolean isNewLevel = enemyEmitter.generateUpdateLevel(delta,frags);
        if (isNewLevel){
            Music music = playlist.GetMP3Music("background"+String.valueOf(enemyEmitter.getLevel()%3+1));
            playlist.setNewMusic(music);
        }
    }

    public void freeAllDestroyed() {
        bulletPoll.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (!myShip.isDestroyed()){
            myShip.draw(batch);
            bulletPoll.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
            newGameButtom.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo(){
        sbFrags.setLength(0);
        font.draw(batch,sbFrags.append(FRAGS).append(frags), worldBounds.getLeft()+PADDING, worldBounds.getTop()-PADDING);
        sbHP.setLength(0);
        font.draw(batch,sbHP.append(HP).append(myShip.getHp()), worldBounds.pos.x, worldBounds.getTop()-PADDING,Align.center);
        sbLevel.setLength(0);
        font.draw(batch,sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight()-PADDING, worldBounds.getTop()-PADDING,Align.right);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPoll.dispose();
        playlist.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
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
        if (myShip.isDestroyed()){
            newGameButtom.touchDown(touch, pointer, button);
        } else {
            myShip.addManeuver(pointer, touch);
        }

        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (myShip.isDestroyed()){
            newGameButtom.touchUp(touch, pointer, button);
        } else {
            myShip.deleteManeuver(pointer);
        }
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        myShip.addManeuver(pointer, touch);
        return false;
    }

    public void checkСollision() {
        if (myShip.isDestroyed()){
            return;
        }
        List<Bullet> bulletList = bulletPoll.getActiveSprites();
        List<EnemyShip> enemyShipList = enemyPool.getActiveSprites();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }

            if (enemyShip.isInsideDst(myShip)) {
                enemyShip.damage(myShip.getCollisionDamage());
                myShip.damage(enemyShip.getCollisionDamage());
            }
        }

        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() ) {
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isDestroyed()|| bullet.getOwner() != myShip) {
                    continue;
                }

                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemyShip.isDestroyed()) {frags++;};
                }
            }
            if (bullet.getOwner() != myShip && myShip.isBulletCollision(bullet)){
                myShip.damage(bullet.getDamage());
                bullet.destroy();
            }

        }

    }


}

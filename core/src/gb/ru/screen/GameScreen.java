package gb.ru.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import gb.ru.base.BaseScreen;
import gb.ru.math.Rect;
import gb.ru.pool.BulletPool;
import gb.ru.sprite.Background;
import gb.ru.sprite.Direction;
import gb.ru.sprite.FriendShip;
import gb.ru.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUTN = 128;

    private Texture bg;
    private Background background;
    private FriendShip myShip;
    private BulletPool bulletPoll;
    private TextureAtlas atlas, atlasMenu;

    private Star[] stars;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        background = new Background(bg);
        bulletPoll = new BulletPool();
        myShip = new FriendShip(atlas, bulletPoll);

        stars = new Star[STAR_COUTN];

        for (int i = 0; i < STAR_COUTN; i++) {
            stars[i] = new Star(atlas);
        }
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
        bulletPoll.updateActiveSprites(delta);

    }

    public void freeAllDestroyed() {
        bulletPoll.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletPoll.drawActiveSprites(batch);
        myShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPoll.dispose();
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


}

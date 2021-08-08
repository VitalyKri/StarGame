package gb.ru.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gb.ru.base.BaseScreen;
import gb.ru.math.Rect;
import gb.ru.sprite.Background;
import gb.ru.sprite.FriendShip;
import gb.ru.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUTN  = 128;

    private Texture bg;
    private Background background;
    private FriendShip myShip;
    private TextureAtlas atlas,atlasMenu;

    private Star[] stars;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        background = new Background(bg);
        myShip = new FriendShip(atlas);

        stars = new Star[STAR_COUTN];

        for (int i = 0; i < STAR_COUTN; i++) {
            stars[i] = new Star(atlas);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star :stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public void update(float delta) {
        for (Star star :stars) {
            star.update(delta);
        }

    }
    public void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star :stars) {
            star.draw(batch);
        }
        batch.end();
    }
}

package gb.ru.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import gb.ru.base.BaseScreen;
import gb.ru.math.Rect;
import gb.ru.sprite.Background;
import gb.ru.sprite.ExitButtom;
import gb.ru.sprite.FriendShip;
import gb.ru.sprite.PlayButtom;
import gb.ru.sprite.Star;

public class MenuScreen extends BaseScreen {

    private static final int STAR_COUTN  = 256;


    private Texture bg;
    private Background background;
    private TextureAtlas atlas,atlasMenu;
    private FriendShip myShip;
    private Star[] stars;

    private ExitButtom exitButtom;
    private PlayButtom playButtom;

    private final Game game;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        atlasMenu = new TextureAtlas("textures/menuAtlas.tpack");
        background = new Background(bg);
       // myShip = new FriendShip(bad);

        stars = new Star[STAR_COUTN];

        for (int i = 0; i < STAR_COUTN; i++) {
            stars[i] = new Star(atlas);
        }

        exitButtom = new ExitButtom(atlasMenu);
        playButtom = new PlayButtom(atlasMenu, game);


        batch.getProjectionMatrix().idt();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star :stars) {
            star.resize(worldBounds);
        }
        exitButtom.resize(worldBounds);
        playButtom.resize(worldBounds);

    }

    @Override
    public void render(float delta) {
      super.render(delta);
      update(delta);
      draw(delta);

    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
    }

    public void update(float delta) {
        for (Star star :stars) {
            star.update(delta);
        }


    }
    public void draw(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        for (Star star :stars) {
            star.draw(batch);
        }
        exitButtom.draw(batch);
        playButtom.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButtom.touchDown(touch,pointer,button);
        playButtom.touchDown(touch,pointer,button);

        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButtom.touchUp(touch,pointer,button);
        playButtom.touchUp(touch,pointer,button);
        return false;
    }
}

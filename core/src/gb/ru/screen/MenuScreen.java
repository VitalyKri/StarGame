package gb.ru.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gb.ru.base.BaseScreen;
import gb.ru.math.Rect;
import gb.ru.sprite.Background;
import gb.ru.sprite.FriendShip;

public class MenuScreen extends BaseScreen {

    private Texture bg,bad;
    private Background background;
    private FriendShip myShip;
    private Vector2 pos;

    @Override
    public void show() {
        super.show();
        pos = new Vector2(0,0);
        bg = new Texture("textures/wallpaper.jpg");
        bad = new Texture("badlogic.jpg");
        background = new Background(bg);
        myShip = new FriendShip(bad);
        batch.getProjectionMatrix().idt();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        myShip.resize(worldBounds);
        myShip.setSpeed(5);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        myShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        bad.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        myShip.updateManeuver(touch);
        return super.touchDown(touch, pointer, button);
    }
}

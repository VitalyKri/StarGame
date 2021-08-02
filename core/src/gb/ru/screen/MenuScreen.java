package gb.ru.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gb.ru.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img,bad;
    private Vector2 pos,v,touch;
    private static final float V_LEN = 1;
    @Override
    public void show() {
        super.show();
        pos = new Vector2(0,0);
        v = new Vector2(0,0);
        img = new Texture("wallpaper.jpg");
        bad = new Texture("badlogic.jpg");
        img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(img, 0, 0, 1080, 1920);
        touch = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(bad, pos.x, pos.y);
        if (touch.dst(pos)>V_LEN){
            pos.add(v);
        }
        else {
            pos.set(touch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight()- screenY);
        v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
}

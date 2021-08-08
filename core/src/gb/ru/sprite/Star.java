package gb.ru.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import gb.ru.base.Sprite;
import gb.ru.math.Rect;
import gb.ru.math.Rnd;

public class Star extends Sprite {

    private Vector2 v;
    private Rect worldBounds;


    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        v = new Vector2();

    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        checkAndHandelBounds();
    }


    public void checkAndHandelBounds() {
        if (getRight()<worldBounds.getLeft() ){
            setLeft(worldBounds.getRight());
        }
        if (getLeft()>worldBounds.getRight() ){
            setRight(worldBounds.getLeft());
        }

        if (getTop()<worldBounds.getBottom() ){
            setBottom(worldBounds.getTop());
        }

        if (getBottom()>worldBounds.getTop() ){
            setTop(worldBounds.getBottom());
        }

    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float height = Rnd.nextFloat(0.005f,0.015f);
        setHeightProportion(height);
        float x = Rnd.nextFloat(worldBounds.getLeft(),worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(),worldBounds.getTop());
        pos.set(x,y);
        v.set(getHalfHeight()*Rnd.nextFloat(-0.5f,0.5f),getHalfHeight()*Rnd.nextFloat(-5,-10));
    }
}

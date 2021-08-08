package gb.ru.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gb.ru.base.BaseButtom;
import gb.ru.math.Rect;

public class ExitButtom extends BaseButtom {

    private static final float PADDING = 0.025f;

    public ExitButtom(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {

        setHeightProportion(0.10f);
        setBottom(worldBounds.getBottom()+PADDING);
        setRight(worldBounds.getRight()-PADDING);
        //super.resize(worldBounds);
    }

    @Override
    public void action() {

        Gdx.app.exit();

    }
}

package gb.ru.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gb.ru.base.Sprite;
import gb.ru.math.Rect;

public class GameOver extends Sprite {

    private static final float HEITHT = 0.07f;
    private static final float BOTTOM_MARGIN = 0.1f;

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEITHT);
        setBottom(BOTTOM_MARGIN);

    }
}

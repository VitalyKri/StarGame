package gb.ru.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gb.ru.base.BaseButtom;
import gb.ru.math.Rect;
import gb.ru.screen.GameScreen;

public class NewGameButtom extends BaseButtom {

    private static final float HEITHT = 0.05f;
    private static final float TOP_MARGIN = 0.1f;

    private final GameScreen gameScreen;

    public NewGameButtom(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;

    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEITHT);
        setTop(TOP_MARGIN);
    }
}

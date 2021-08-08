package gb.ru.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import gb.ru.base.BaseButtom;
import gb.ru.math.Rect;
import gb.ru.screen.GameScreen;
import gb.ru.screen.MenuScreen;

public class PlayButtom extends BaseButtom {

    private static final float PADDING = 0.025f;

    private final Game game;

    public PlayButtom(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }


    @Override
    public void resize(Rect worldBounds) {

        setHeightProportion(0.14f);
        setBottom(worldBounds.getBottom()+PADDING);
        setLeft(worldBounds.getLeft()+PADDING);
        //super.resize(worldBounds);
    }


    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}

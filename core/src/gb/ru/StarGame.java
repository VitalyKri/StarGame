package gb.ru;

import com.badlogic.gdx.Game;

import gb.ru.base.Playlist;
import gb.ru.screen.MenuScreen;
import gb.ru.sprite.PlayButtom;

public class StarGame extends Game {


	@Override
	public void create () {
		setScreen(new MenuScreen(this));

	}


}

package gb.ru.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.List;

public class Playlist {

    private Sound bulletSound;
    private Sound laserSound;
    private Sound explosionSound;

    private Music mainMusic;
    private Music newMusic;
    float Volume = 100, delta = 10;

    public Music GetMP3Music(String name) {
        return Gdx.audio.newMusic(Gdx.files.internal("sounds/" + name + ".mp3"));
    }

    public Playlist(String name) {
        this.mainMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/" + name + ".mp3"));
        this.mainMusic.play();
        this.newMusic = this.mainMusic;
        this.bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        this.laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        this.explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

    }

    public Sound getExplosionSound() {
        return explosionSound;
    }

    public void setExplosionSound(Sound explosionSound) {
        this.explosionSound = explosionSound;
    }

    public Sound getLaserSound() {
        return laserSound;
    }

    public Sound getBulletSound() {
        return bulletSound;
    }

    public void setNewMusic(Music newMusic) {
        this.newMusic = newMusic;
    }

    public void update(float delta) {

        // смена музыки если будут боссы, реализую на них
        if (this.mainMusic != this.newMusic) {
            int old = (int)this.delta;
            this.delta-=delta;

            if (!newMusic.isPlaying()) {
                newMusic.play();
            }
            if (old != (int) this.delta && this.delta>0) {
                mainMusic.setVolume((this.delta/10)*(this.delta/10));
                newMusic.setVolume(((10 - this.delta/10) / (10 * delta)));
            }
            if (this.delta < 0) {
                this.delta = 10;
                this.mainMusic.stop();
                this.mainMusic.dispose();
                this.mainMusic = this.newMusic;
            }

        }

    }

    public void dispose() {
        this.mainMusic.dispose();
        if (this.mainMusic != this.newMusic) {
            this.newMusic.dispose();
        }
        this.bulletSound.dispose();
    }

}


package coffeefoxstudios;

import coffeefoxstudios.model.states.GameState;
import coffeefoxstudios.model.utils.SpriteVault;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.apache.log4j.BasicConfigurator;


public class Application extends ApplicationAdapter {
	SpriteBatch batch;
	GameState gameState = null;

	@Override
	public void create() {
		//Configure Logging For remainder of Application
		BasicConfigurator.configure();
		batch = new SpriteBatch();
		gameState = new GameState(batch);
	}

	@Override
	public void resize(int width, int height) {
		gameState.resize(width, height);
	}

	@Override
	public void render() {


		//Call Game State Update Loop:
		gameState.update();


		//Clear Screen
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Call Game State Render Loop:
		gameState.render();
	}


}

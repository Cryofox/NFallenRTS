
package coffeefox;

import coffeefox.model.Utils.SpriteVault;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class HelloApp extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img,img2;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//Setup SpriteVault
		SpriteVault.getInstance();
	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

//		int mouseX = Gdx.input.getX();
//		int mouseY = Gdx.input.getY();
//		System.out.println("X:"+mouseX +" Y:"+mouseY);

		batch.begin();
		batch.draw(SpriteVault.getInstance().getTexture("HeroPH"),10,10);
		batch.draw(SpriteVault.getInstance().getTexture("HeroPH"),30,10);
//		batch.draw(img, 0, 0);
		batch.end();
	}
}

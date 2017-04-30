package coffeefoxstudios.model.actors.buildings;

import coffeefoxstudios.model.actors.Actor;
import coffeefoxstudios.model.utils.RenderUtil;
import coffeefoxstudios.model.utils.Renderable;
import coffeefoxstudios.model.utils.SelectedTypes;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Ryder Stancescu on 4/23/2017.
 */
public class Building extends Actor implements Renderable {
    @Override
    public Vector3 getPosition() {
        return null;
    }

    @Override
    public void setPosition(Vector3 position) {

    }

    @Override
    public Rectangle getBoundingBox() {
        return null;
    }

    @Override
    public void update(float timeDelta) {

    }

    @Override
    public void render(RenderUtil renderer) {

    }

    @Override
    public void renderDebug(RenderUtil renderer) {

    }

    @Override
    public void setSelectedType(SelectedTypes type) {

    }

    @Override
    public SelectedTypes getSelectedType() {
        return null;
    }
}

package coffeefoxstudios.model;

/**
 * Created by Ryder Stancescu on 4/16/2017.
 */

import coffeefoxstudios.model.utils.RenderUtil;
import coffeefoxstudios.model.utils.Renderable;
import coffeefoxstudios.model.utils.SelectedTypes;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 *  Actors are the Base Interactable Types in the World.
 *  Squads and Buildings are selectable, thereby they extend Actor.
 *  Units (inside squads) are not interactable (by a Player) and as implement Renderable (Since they exist in the world),
 *  but can noto be selected.
 */
public class Actor implements Renderable {
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

package coffeefoxstudios.model.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Ryder Stancescu on 4/23/2017.
 */
public interface Renderable {

    Vector3 getPosition();

    void setPosition(Vector3 position);

    Rectangle getBoundingBox();


    void update(float timeDelta);

    void render(RenderUtil renderer);

    void renderDebug(RenderUtil renderer);

    void setSelectedType(SelectedTypes type);

    SelectedTypes getSelectedType();
}

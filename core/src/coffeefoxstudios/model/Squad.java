package coffeefoxstudios.model;

/**
 * Created by Ryder Stancescu on 4/23/2017.
 */

import coffeefoxstudios.model.states.commands.SquadOrder;
import coffeefoxstudios.model.states.commands.SquadOrderType;
import coffeefoxstudios.model.utils.RenderUtil;
import coffeefoxstudios.model.utils.Renderable;
import coffeefoxstudios.model.utils.SelectedTypes;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * This is what the player will instantiate.
 * Players give orders to Squads, who then relay the order to their units.
 */
public class Squad extends Actor implements Renderable {
    private static final Log log = LogFactory.getLog(Squad.class);

    private Vector3 position = new Vector3(0, 0, 0);
    SelectedTypes selectedType = SelectedTypes.None;

    List<SquadOrder> orders = new ArrayList<>();

    boolean holdFire = false;

    @Override
    public Rectangle getBoundingBox() {
        //Find smallestX and smallest y in list, largest X, and largest Y.
        //That's the Group.

        //For Now the bounding box is 10;
        int width = 15;

        return new Rectangle(position.x - width / 2, position.y - width / 2, width, width);
    }


    @Override
    public Vector3 getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector3 position) {
        if (this.position != null) {
            this.position.set(position);
        } else {
            this.position = new Vector3(position.x, position.y, position.z);
        }
    }

    public void update(float deltaTime) {
        if (orders.size() > 0) {
            //Follo Orders

        } else {
            //Default Behaviour (Stand and shoot enemies that we see.
        }
    }

    @Override
    public void render(RenderUtil renderer) {

    }

    @Override
    public void renderDebug(RenderUtil renderer) {
//        log.info("RenderPosition:" + position.toString());
        //Render the Position in Blue
        ShapeRenderer shapes = renderer.getShapeRenderer();

        Color color = new Color();
        switch (selectedType) {
            case Selected:
                color.set(0, 1, 0, 1);
                break;
            case Highlighted:
                color.set(1, 1, 0, 1);
                break;
            case None:
                color.set(1, 1, 1, 1);
                break;
            default:
                break;
        }
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(color);
        shapes.circle(position.x, position.y, 5);
        shapes.end();
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(color);
        Rectangle rectangle = getBoundingBox();
        shapes.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        shapes.end();

        //Render Orders
        shapes.begin(ShapeRenderer.ShapeType.Line);
        Vector3 lastPositionDebugPath = new Vector3(position); //Create a Temp Vector for Debugging Path
        for (SquadOrder order : orders) {
            if (order.getCommandType() == SquadOrderType.Location) {
                //Draw Path
                shapes.setColor(1, 1, 1, 1);
                shapes.line(lastPositionDebugPath.x, lastPositionDebugPath.y, order.getPosition().x, order.getPosition().y);
                //Draw Point
                shapes.circle(order.getPosition().x, order.getPosition().y, 5);

                lastPositionDebugPath.set(order.getPosition());
            }
        }
        shapes.end();
    }


    @Override
    public void setSelectedType(SelectedTypes type) {
        selectedType = type;
    }

    @Override
    public SelectedTypes getSelectedType() {
        return selectedType;
    }


    public void setOrder(SquadOrder order) {
        orders.clear();
        orders.add(order);
    }

    public void queueOrder(SquadOrder order) {
        orders.add(order);
    }
}
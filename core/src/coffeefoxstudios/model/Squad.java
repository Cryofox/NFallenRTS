package coffeefoxstudios.model;

/**
 * Created by Ryder Stancescu on 4/23/2017.
 */

import coffeefoxstudios.model.states.commands.SquadOrder;
import coffeefoxstudios.model.states.commands.SquadOrderCommand;
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

import javax.swing.text.Position;
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

    public int getMovePriority() {
        return movePriority;
    }


    int movePriority = 0;

    Vector3 currentVelocity = new Vector3(0, 0, 0);
    float personalSpaceRadius = 18;
    float detectionRadius = 30;
    float boxWidth = 10;

    @Override
    public Rectangle getBoundingBox() {
        //Find smallestX and smallest y in list, largest X, and largest Y.
        //That's the Group.

        //For Now the bounding box is 10;
        return new Rectangle(position.x - boxWidth / 2, position.y - boxWidth / 2, boxWidth, boxWidth);
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
            //Follow Orders
            if (orders.get(0).getCommandType() == SquadOrderType.Location) {
//                moveTowards(orders.get(0).getPosition());

                Vector3 movePos = orders.get(0).getPosition();
                seek(movePos);
                if (Vector3.dst(position.x, position.y, position.z, movePos.x, movePos.y, movePos.z) < detectionRadius) {
                    orders.remove(0);
                }
            }
        } else {
            //Default Behaviour (Stand and shoot enemies that we see.
        }
        //UpdateLocomotion.
        updateLocomotion(deltaTime);
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
        //Draw Position Circle
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(color);
        shapes.circle(position.x, position.y, 2);
        shapes.end();

        //Bounding box for object used for combat detection
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(color);
        Rectangle rectangle = getBoundingBox();
        shapes.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        shapes.end();

        //Personal Space
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(1, 1, 0, 1);
        shapes.circle(position.x, position.y, personalSpaceRadius);
        shapes.end();

        //Detection Space
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0, 1, 1, 1);
        shapes.circle(position.x, position.y, detectionRadius);
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
                shapes.circle(order.getPosition().x, order.getPosition().y, 2);

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


    /*
        Locomotion Logic
    */
    void moveTowards(Vector3 position) {


    }

    void updateLocomotion(float timeDelta) {
        //Scale velocity by timeDelta
        Vector3 timeScaledVelocity = new Vector3(currentVelocity.x*timeDelta,currentVelocity.y*timeDelta, 0);
        position.add(timeScaledVelocity); //Add the timeScaled Velocity
    }


    float maxForce =10.4f;
    float maxSpeed =10;
    float mass =1;


    //Locomotion LowLevel
    void seek(Vector3 target)
    {
        Vector3 steering = Vector3.Zero;
        Vector3 desiredVelocity =  new Vector3(target);
        desiredVelocity.sub(position);
        desiredVelocity.nor();
        desiredVelocity.x*=maxSpeed;
        desiredVelocity.y*=maxSpeed;

        //Set the steering values to our desiredVelocity, then subtract the current velocity.
        steering.set(desiredVelocity).sub(currentVelocity);

        // Truncate Steering to maxForce
        if(steering.len() > maxForce)
        {
            steering.nor();
            steering.x *= maxForce;
            steering.y *= maxForce;
        }
        //Scale Steering via Mass
        steering.x /=mass;
        steering.y /=mass;
        steering.z /=mass;

        //Modify Velocity
        currentVelocity.add(steering);
        // Truncate Velocity to maxSpeed
        if(currentVelocity.len() > maxSpeed)
        {
            currentVelocity.nor();
            currentVelocity.x *= maxSpeed;
            currentVelocity.y *= maxSpeed;
        }
    }

}

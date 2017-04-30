package coffeefoxstudios.model.managers;

import coffeefoxstudios.model.actors.squads.Squad;
import coffeefoxstudios.model.actors.squads.commands.SquadOrder;
import coffeefoxstudios.model.actors.squads.commands.SquadOrderType;
import coffeefoxstudios.model.utils.RenderUtil;
import coffeefoxstudios.model.utils.Tuple;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryder Stancescu on 4/23/2017.
 */
public class SquadManager {
    private final static SquadManager instance = new SquadManager();

    public static SquadManager getInstance() {
        return instance;
    }

    List<Squad> squadList;

    private SquadManager() {
        squadList = new ArrayList<>();
    }


    public Squad spawnSquad(Vector3 position) {
        Squad squad = new Squad();
        squad.setPosition(position);

        //Store Squad in Squadlist for Update Cycle
        squadList.add(squad);

        //Add to CollisionManager
        CollisionManager.getInstance().addSquad(squad);
        return squad;
    }


    public void update(float deltaTime) {
        //Update ALL squads regardless of visibility
        for (Squad squad : squadList) {
            squad.update(deltaTime);
        }
    }


    public void render(RenderUtil renderer) {
        for (Squad squad : squadList) {
            squad.render(renderer);
        }
    }

    public void renderDebug(RenderUtil renderer) {
        for (Squad squad : squadList) {
            squad.renderDebug(renderer);
        }
    }


    public void setOrder(List<Squad> squads, SquadOrder order) {
        //Location based movements require offsetting
        if(order.getCommandType() == SquadOrderType.Location)
        {
            List<Tuple<Squad,Vector3>> squadSpecificOrders = CollisionManager.getInstance().getFinalPosition(squads, order.getPosition());
            for(Tuple<Squad,Vector3> tuple : squadSpecificOrders)
            {
                SquadOrder newOrder= new SquadOrder(order.getCommand(),tuple.getElement2());
                tuple.getElement1().setOrder(newOrder);
            }
        }
        else {
            for (Squad squad : squads) {
                //Apply conditional logic for Formation
                squad.setOrder(order);
            }
        }
    }

    public void queueOrder(List<Squad> squads, SquadOrder order) {
        //Location based movements require offsetting
        if(order.getCommandType() == SquadOrderType.Location)
        {
            List<Tuple<Squad,Vector3>> squadSpecificOrders = CollisionManager.getInstance().getFinalPosition(squads, order.getPosition());
            for(Tuple<Squad,Vector3> tuple : squadSpecificOrders)
            {
                SquadOrder newOrder= new SquadOrder(order.getCommand(),tuple.getElement2());
                tuple.getElement1().queueOrder(newOrder);
            }
        }
        else {
            for (Squad squad : squads) {
                //Apply conditional logic for Formation
                squad.queueOrder(order);
            }
        }
    }





}

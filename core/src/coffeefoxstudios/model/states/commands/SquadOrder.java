package coffeefoxstudios.model.states.commands;

import coffeefoxstudios.model.Actor;
import com.badlogic.gdx.math.Vector3;


public class SquadOrder {

    SquadOrderType orderType = SquadOrderType.Self;

    public Vector3 getPosition() {
        return position;
    }

    public Actor getTarget() {
        return target;
    }

    Vector3 position = null;
    Actor target = null;

    public SquadOrder(SquadOrderCommand order) {
        //Nothing Changes
        orderType = SquadOrderType.Self;
    }

    public SquadOrder(SquadOrderCommand order, Vector3 position) {
        orderType = SquadOrderType.Location;
        this.position = new Vector3(position); //Create a NEW position for this Order
    }

    public SquadOrder(SquadOrderCommand order, Actor actor) {
        orderType = SquadOrderType.Target;
        target = actor;
    }


    public SquadOrderType getCommandType() {
        return orderType;
    }


}

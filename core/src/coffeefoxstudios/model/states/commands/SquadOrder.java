package coffeefoxstudios.model.states.commands;

import coffeefoxstudios.model.actors.Actor;
import com.badlogic.gdx.math.Vector3;


public class SquadOrder {

    SquadOrderType orderType = SquadOrderType.Self;
    SquadOrderCommand orderCommand = SquadOrderCommand.Move;
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
        this.orderCommand=order;
    }

    public SquadOrder(SquadOrderCommand order, Vector3 position) {
        orderType = SquadOrderType.Location;
        this.position = new Vector3(position); //Create a NEW position for this Order
        this.orderCommand=order;
    }

    public SquadOrder(SquadOrderCommand order, Actor actor) {
        orderType = SquadOrderType.Target;
        target = actor;
        this.orderCommand=order;
    }


    public SquadOrderType getCommandType() {
        return orderType;
    }
    public SquadOrderCommand getCommand() {
        return orderCommand;
    }

}

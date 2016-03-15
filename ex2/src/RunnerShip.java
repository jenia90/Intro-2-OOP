import oop.ex2.SpaceShipPhysics;

import java.awt.*;

/**
 * Created by jenia on 13/03/2016.
 */
public class RunnerShip extends SpaceShip{

    public RunnerShip(){
        reset();
    }
    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        runnerStrategy(game);
    }
}

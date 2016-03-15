import oop.ex2.SpaceShipPhysics;

/**
 * Created by jenia on 13/03/2016.
 */
public class AggressiveShip extends SpaceShip{

    public AggressiveShip(){
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
        aggressiveStrategy(game);
    }
}

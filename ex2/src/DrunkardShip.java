import oop.ex2.SpaceShipPhysics;

/**
 * Created by jenia on 13/03/2016.
 */
public class DrunkardShip extends SpaceShip {

    private SpaceShipPhysics shipPhysics;

    public DrunkardShip(){
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

    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    @Override
    public void reset() {
        shipPhysics = new SpaceShipPhysics();
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    @Override
    public SpaceShipPhysics getPhysics() {
        return shipPhysics;
    }
}

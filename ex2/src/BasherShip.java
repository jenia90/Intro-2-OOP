import oop.ex2.SpaceShipPhysics;

import java.awt.*;

/**
 * Created by jenia on 13/03/2016.
 */
public class BasherShip extends SpaceShip{
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
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    @Override
    public Image getImage() {
        return null;
    }
}

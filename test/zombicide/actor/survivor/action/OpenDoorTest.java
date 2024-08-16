package zombicide.actor.survivor.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.board.Board;
import zombicide.board.type.TrainingBoard;
import zombicide.item.Weapon;
import zombicide.item.items.MasterKey;
import zombicide.item.weapons.WeaponType;
import zombicide.util.Direction;
import zombicide.util.Position;
import zombicide.zone.Zone;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenDoorTest {
    private Survivor survivor;
    private Board board;
    private Zone baseZone;
    private Zone openZone;
    private OpenDoor action;
    @BeforeEach
    void initEnv(){
        this.board = new TrainingBoard();

        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.BASE);
        this.survivor = new Survivor("Valentin", roles);

        this.baseZone = this.board.getZone(new Position(3,3));
        this.openZone = this.board.getZone(new Position(3,4));
        this.baseZone.addActorInZone(this.survivor);

        this.action = new OpenDoor();
    }
    @Test
    void testSpawnZombieWithItem(){
        MasterKey key = new MasterKey(survivor);
        this.survivor.addItem(key);
        this.survivor.setItemInHand(key);

        this.action.executeAction(this.survivor, Direction.SOUTH);

        // After survivor opened the door, because openZone didn't have at least one door opened, zombies should spawn
        assertTrue(this.openZone.nbZombieInZone() > 0);
    }

    @Test
    void testSpawnZombieWithWeapon(){
        Weapon Axe = new Weapon(WeaponType.AXE, this.survivor);
        this.survivor.addItem(Axe);
        this.survivor.setItemInHand(Axe);

        this.action.executeAction(survivor, Direction.SOUTH);

        // After survivor opened the door, because openZone didn't have at least one door opened, zombies should spawn
        assertTrue(this.openZone.nbZombieInZone() > 0);
    }
}

package zombicide.actor.survivor.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zombicide.actor.survivor.Role;
import zombicide.actor.survivor.Survivor;
import zombicide.board.Board;
import zombicide.board.type.RandomBoard;
import zombicide.board.type.TrainingBoard;
import zombicide.item.Weapon;
import zombicide.item.weapons.WeaponType;
import zombicide.util.Direction;
import zombicide.util.Position;
import zombicide.zone.Zone;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttackTest {

    private Board board;
    private Survivor survivor;
    private Weapon crowbar;
    private Weapon rifle;
    @Test
    void doAction() {
    }

    @BeforeEach
    void initEnv() {
        this.board = new TrainingBoard();
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.BASE);
        this.survivor = new Survivor("Jean", role);
        this.survivor.move(board.getZone(new Position(2, 3)));
        this.crowbar = new Weapon(WeaponType.CROWBAR, survivor);
        this.rifle = new Weapon(WeaponType.RIFLE, survivor);
    }

    @Test
    void reachableZonesWithoutDoors() {
        Attack attack = new Attack();

        List<Zone> crowbarReachableZones = attack.reachableZones(Direction.NORTH, crowbar);
        List<Zone> rifleReachableZonesNorth = attack.reachableZones(Direction.NORTH, rifle);

        assertEquals(1, crowbarReachableZones.size());
        assertEquals(3, rifleReachableZonesNorth.size());

        assertTrue(crowbarReachableZones.contains(this.survivor.getZone()));
        assertFalse(rifleReachableZonesNorth.contains(this.survivor.getZone()));

        assertTrue(rifleReachableZonesNorth.contains(board.getZone(new Position(2, 2))));
        assertTrue(rifleReachableZonesNorth.contains(board.getZone(new Position(2, 1))));
        assertTrue(rifleReachableZonesNorth.contains(board.getZone(new Position(2, 0))));
    }

    @Test
    void reachableZonesWithDoors() {
        Attack attack = new Attack();

        List<Zone> rifleReachableZonesEast = attack.reachableZones(Direction.EAST, rifle);

        assertEquals(0, rifleReachableZonesEast.size());

        this.survivor.getZone().setBooleanInDirection(Direction.EAST, true);

        List<Zone> newRifleReachableZonesEast = attack.reachableZones(Direction.EAST, rifle);

        assertEquals(1, newRifleReachableZonesEast.size());
        assertTrue(newRifleReachableZonesEast.contains(board.getZone(new Position(3, 3))));

    }
}
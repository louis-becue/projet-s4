package zombicide.util;

import zombicide.board.*;
import zombicide.zone.*;
import zombicide.zone.room.*;
import zombicide.zone.street.*;

import java.util.ArrayList;
import java.util.List;

public class Display {
    protected final int dx;
    protected final int dy;

    protected Zone[][] board;

    /**
     * Create a display for the board.
     *
     * @param board the board to display.
     */
    public Display(Board board) {
        this.board = board.getBoardList();
        this.dx = board.getDx();
        this.dy = board.getDy();
    }

    /**
     * Display the board automatically with the zombies.
     */
    public void display() {
        this.display(true);
    }

    /**
     * Display the board.
     *
     * @param displayZombie - true if the zombie will be display.
     *                      - false otherwise.
     */
    public void display(boolean displayZombie){
        System.out.println("  __ __ __ ".repeat(this.dx));
        String[] tabBoard = buildDisplayTab(displayZombie);

        for (String s : tabBoard) {
            System.out.println(s.replace("null", "") + "|");
        }
    }

    /**
     * Display a zone with these neighbours.
     *
     * @param zone the zone who will be display.
     * @param displayZombie - true if the zombie will be display.
     *                      - false otherwise.
     */
    public void displayNeighbours(Zone zone, boolean displayZombie){
        int n;
        n = (zone.getPosition().getX() == 0 || zone.getPosition().getX() == this.dx-1) ? 2 : 3;

        System.out.println("  __ __ __ ".repeat(n));
        String[] tabBoard = buildDisplayTabNeighbours(zone, displayZombie);

        for (String s : tabBoard) {
            System.out.println(s.replace("null", "") + "|");
        }
    }

    /**
     * Make a representation of a room in 2d.
     *
     * @param zone the zone.
     * @param displayZombie - true if the zombie will be display.
     *                      - false otherwise.
     *
     * @return a list of string.
     */
    public ArrayList<String> roomImage(Zone zone, boolean displayZombie){
        ArrayList<String> out = new ArrayList<>();
        char first;
        String open, nbZombie, nbPlayer, type;

        first = (zone.getBooleanInDirection(Direction.WEST)) ? ' ' : '|';
        nbZombie = (zone.nbZombieInZone() >=1 && displayZombie && !(zone instanceof Continental)) ? (zone.nbZombieInZone() + "Z") : "  ";
        nbPlayer = (zone.nbPlayerInZone() >=1 && !(zone instanceof Continental)) ? (zone.nbPlayerInZone() + "S") : "  ";
        if (zone instanceof Pharmacy)
            type = "⚕";
        else if (zone instanceof Continental)
            type = "\uD83C\uDFE8";
        else
            type = " ";

        out.add("|     " + nbZombie + "   ");
        out.add(first + "     " + nbPlayer + "   ");

        if (zone.getBooleanInDirection(Direction.SOUTH))
            open = "  ";
        else
            open = "__";
        out.add("| __ " + open + " __ ");
        return out;
    }

    /**
     * Return a representation of normal street in 2d.
     *
     * @param zone the zone
     * @param streetOnBottom - true if there is a street on the bottom of the zone.
     *                       - false otherwise.
     * @param streetOnLeft - true if there is a street on the left of the zone.
     *                      - false otherwise.
     * @param displayZombie - true if the zombie will be display.
     *                      - false otherwise.
     *
     * @return a list of string.
     */
    public ArrayList<String> normalStreetImage(Zone zone, boolean streetOnBottom, boolean streetOnLeft, boolean displayZombie){
        ArrayList<String> out = new ArrayList<>();
        char first;
        String open, nbZombie, nbPlayer;

        first = (streetOnLeft) ? ' ' : '|';
        nbZombie = (zone.nbZombieInZone() >=1 && displayZombie) ? (zone.nbZombieInZone() + "Z") : "  ";
        nbPlayer = (zone.nbPlayerInZone() >=1) ? (zone.nbPlayerInZone() + "S") : "  ";

        out.add(first + "     " + nbZombie + "   ");
        out.add(first + "     " + nbPlayer + "   ");

        if (streetOnBottom)
            out.add(first + "          ");
        else {
            open = (zone.getBooleanInDirection(Direction.SOUTH)) ? "  " : "__";
            out.add(first + " __ " + open + " __ ");
        }
        return out;
    }

    /**
     * Return a representation of the manhole in 2d.
     *
     * @param zone the zone
     * @param RoomOnBottom - true if there is a room on the bottom of the zone.
     *                     - false otherwise.
     * @param RoomOnLeft - true if there is a room on the left of the zone.
     *                   - false otherwise.
     * @param displayZombie - true if the zombie will be display.
     *                      - false otherwise.
     *
     * @return a list of string.
     */
    public ArrayList<String> manholeImage(Zone zone, boolean RoomOnBottom, boolean RoomOnLeft, boolean displayZombie){
        ArrayList<String> out = new ArrayList<>();
        char first;
        String open, nbZombie, nbPlayer;

        first = (RoomOnLeft) ? '|' : ' ';
        nbZombie = (zone.nbZombieInZone() >=1 && displayZombie) ? (zone.nbZombieInZone() + "Z") : "  ";
        nbPlayer = (zone.nbPlayerInZone() >=1) ? (zone.nbPlayerInZone() + "S") : "  ";

        out.add(first + "    " + nbZombie + "    ");
        out.add(first + "    X" + nbPlayer +"   ");

        if (!RoomOnBottom)
            out.add(first + "          ");
        else {
            open = (zone.getBooleanInDirection(Direction.SOUTH)) ? "  " : "__";
            out.add(first + " __ " + open + " __ ");
        }
        return out;
    }

    /**
     * Build a list of the representation of the zone with these neighbours by line.
     *
     * @param zone the zone who will be display with these neighbours.
     * @param displayZombie - true if the zombie will be display.
     *                      - false otherwise.
     *
     * @return a tab of line of the zone and these neighbours.
     */
    public String[] buildDisplayTabNeighbours(Zone zone, boolean displayZombie) {
        int positionY = zone.getPosition().getY();
        int positionX = zone.getPosition().getX();
        int ny, nx, startX, startY;

        ny = (positionY == 0 || positionY == this.dy-1) ? 2: 3;
        nx = (positionX == 0 || positionX == this.dx-1) ? 2: 3;
        startX = (positionX == 0) ? 0 : positionX-1;
        startY = (positionY == 0) ? 0: positionY-1;
        String[] tab = new String[ny * 3];

        for (int y = startY; y < (startY+ny); y++) {
            for (int x = startX; x < (startX+nx); x++) {
                List<String> zoneImage = buildZone(x, y, startX+nx, startY+ny, displayZombie);
                for (int z = 0; z < 3; z++) {
                    tab[(y-startY)*3+z] += zoneImage.get(z);
                }
            }
        }
        return tab;
    }

    /**
     * Build a representation of the zone.
     *
     * @param x the position x of the zone.
     * @param y the position y of the zone.
     * @param maxX the size max of the position X .
     * @param maxY the size max of the position X .
     * @param displayZombie - true if the zombie will be display.
     *                      - false otherwise.
     *
     * @return a List of string who represent the zone.
     */
    List<String> buildZone(int x, int y, int maxX, int maxY, boolean displayZombie){
        Zone Zone = this.board[y][x];
        ArrayList<String> zoneImage;

        if (Zone instanceof Room) {
            zoneImage = roomImage(Zone, displayZombie);
        }
        else if (Zone instanceof NormalStreet) {
            boolean StreetOnBottom = ((y != maxY - 1) && ((this.board[y + 1][x] instanceof NormalStreet) || (this.board[y + 1][x] instanceof Manhole)));
            boolean StreetOnLeft = ((x != 0)  && ((this.board[y][x - 1] instanceof NormalStreet) || (this.board[y][x - 1] instanceof Manhole)));
            zoneImage = normalStreetImage(Zone, StreetOnBottom, StreetOnLeft, displayZombie);
        }
        else {
            boolean RoomOnBottom = (y == maxY - 1 || (this.board[y + 1][x] instanceof Room));
            boolean RoomOnLeft = (x == 0 || (this.board[y][x - 1] instanceof Room));
            zoneImage = manholeImage(Zone, RoomOnBottom, RoomOnLeft, displayZombie);
        }
        return zoneImage;
    }

    /**
     * Build a list of the representation of the map by line.
     *
     * @param displayZombie - true if the zombie will be display.
     *                      - false otherwise.
     *
     * @return an array of line of the map
     */
    public String[] buildDisplayTab(boolean displayZombie){
        String[] tab = new String[this.dy*3];

        for (int y=0; y < this.dy; y++){
            for (int x=0; x < this.dx; x++){
                List<String> zoneImage = buildZone(x,y,this.dx, this.dy, displayZombie);
                for(int z=0; z < 3; z++)
                    tab[y*3+z] += zoneImage.get(z);
            }
        }
        return tab;
    }
}

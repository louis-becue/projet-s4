package zombicide.board.generator;
import zombicide.board.NotEnoughRoomsException;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
    private String seed;
    private int width;
    private int height;

    private SeedGenerator generator;

    private int amountPharmacy;
    private int amountContinental;


    /**
     * Constructor that build the mapGenerator, using a SeedGenerator. The MapGenerator allows us to slightly modify the seed, adding bits that can correspond to special buildings.
     * (For example, it adds pharmacies, continentals, depending on the number we enter in parameters.)
     * @param width The width of the board
     * @param height The height of the board
     * @throws NotEnoughRoomsException If we ask more special buildings than there is rooms
     */
    public MapGenerator(int width, int height,int amountPharmacy, int amountContinental) throws NotEnoughRoomsException{
        this.width = width;
        this.height = height;
        this.generator = new SeedGenerator(width, height);
        this.amountPharmacy = amountPharmacy;
        this.amountContinental = amountContinental;
        this.generateNewSeed();
        this.addSizeInSeed(width,height);
    }
    /**
     * Constructor that build the mapGenerator, using a SeedGenerator. The MapGenerator allows us to slightly modify the seed, adding bits that can correspond to special buildings.
     * In this constructor, we do not precise the amount of special buildings. Base number is 1 for each special building.
     * @param width The width of the board
     * @param height The height of the board
     */
    public MapGenerator(int width, int height){
        this.width = width;
        this.height = height;
        this.generator = new SeedGenerator(width, height);
        this.amountPharmacy = 1;
        this.amountContinental = 1;
        try{
            this.generateNewSeed();
        }
        catch(NotEnoughRoomsException e){
            System.out.println("This never happens :)");
        }
        this.addSizeInSeed(width,height);
    }

    /**
     * Method that puts the size of the board in the seed to know his dimensions.
     * @param width The width of the board
     * @param height The height of the board
     */
    private void addSizeInSeed(int width, int height){
        String strWidth = String.format("%03d",width);
        String strHeight = String.format("%03d",height);
        this.seed += strWidth + strHeight;
    }

    /**
     * Method that generate the base seed with the SeedGenerator (only rooms and streets) and then modify the seed depending on the special rooms we want.
     * It does this second step using the method modifySeed.
     * @throws NotEnoughRoomsException If we ask for more special rooms than there is rooms in the board.
     */
    public void generateNewSeed() throws NotEnoughRoomsException{
        this.seed = generator.generateRandomSeed();
        this.modifySeed(this.amountPharmacy,this.amountContinental);
    }

    /**
     * Method that, from a base seed (with only rooms and streets), adds the special buildings (except the manholes that are done in RandomBoard).
     * @param pharmacyAmount The amount of pharmacies we want in the board
     * @param continentalAmount The amount of continental we want in the board
     * @throws NotEnoughRoomsException If we ask for more special buildings than there is rooms
     */
    private void modifySeed(int pharmacyAmount,int continentalAmount) throws NotEnoughRoomsException{
        this.putSpecialRoom('2', pharmacyAmount);
        this.putSpecialRoom('3',continentalAmount);

    }

    /**
     * Return the seed of the MapGenerator
     * @return the seed of the MapGenerator
     */
    public String getSeed(){
        return this.seed;
    }

    /**
     * Method to count how many rooms there are in the seed of the board. It is used to know if we can put a certain amount of special buildings.
     * @return int corresponding to the number of rooms
     */
    private int countRooms() {
        int countRoom = 0;
        for (char bit : this.seed.toCharArray()) {
            if (bit == '1') {
                countRoom = countRoom + 1;
            }
        }
        return countRoom;
    }
    private boolean canAddThisAmountOfSpecialRoom(){
        return (this.amountPharmacy + this.amountContinental < this.countRooms());
    }
    /**
     * Method that adds special rooms in the board. It is used on a seed that is full of '0' and '1' representing streets and normal rooms.
     * It will change some of the '1' into roomType (that is a char as well) to represent a special building.
     * @param roomType	A character that will correspond to a roomType. ('2' for Pharmacy, '3' for Continental)
     * @param amount The amount of special room we want to add to the seed. Obviously, it has to be less than the amount of rooms in the actual seed.
     * @exception NotEnoughRoomsException When we try to add more special rooms than there is classic rooms in the board
     */
    private void putSpecialRoom(char roomType, int amount) throws NotEnoughRoomsException {
        if (!(this.canAddThisAmountOfSpecialRoom())){
            throw new NotEnoughRoomsException("There is not enough rooms in the board to add this number of special rooms");
        }
        Random rdm = new Random();
        ArrayList<Integer> randomNumbers = new ArrayList<>(amount);
        for (int i=0;i<amount;i++){
            int randomNumber = (rdm.nextInt(this.countRooms()-1));
            while (randomNumbers.contains(randomNumber)){
                randomNumber = (rdm.nextInt(this.countRooms()-1));
            }
            randomNumbers.add(randomNumber);

        }
        char[] seedInArray = this.seed.toCharArray();
        int actualPosition = 0;
        int indexOfSpecialRoom = 0;
        int amountPut = 0;
        for (char bit : seedInArray){
            if (bit=='1'){
                indexOfSpecialRoom = indexOfSpecialRoom +1;
            }
            if (randomNumbers.contains(indexOfSpecialRoom) && amountPut<amount && bit=='1'){
                seedInArray[actualPosition]=roomType;
                amountPut = amountPut+1;

            }

            actualPosition = actualPosition +1;
        }
        this.seed = String.valueOf(seedInArray);
    }
}


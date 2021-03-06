import lib280.list.LinkedList280;
import java.util.Random;

public class CargoSimulator {

    protected LinkedList280<Ship> fleet;

    private static int randomSeed = 42;  // Don't modify this!

    /**
     * Construtor will generate a "fixed random" cargo for each ship using the random seed randomSeed.
     *
     * @param numberOfSacks Total number of sacks of grain to be carried by the fleet.
     *                           These are automatically generated.
     *
     * @postcond The instance variable 'fleet' is a list of ships, each one of which contains a list of sacks of cargo.
     */
    public CargoSimulator(int numberOfSacks) {
        // DO NOT MODIFY THE CONSTRUCTOR -- This is generating the data you need to complete the assignment.

        // Seed the random number generator.
        Random generator = new Random(randomSeed);

        // Create the fleet from the ship names in Ship.ShipNames
        this.fleet = new LinkedList280<Ship>();

        for(int i=0; i < Ship.ShipNames.length; i++) {
            this.fleet.insertFirst(new Ship(Ship.ShipNames[i], generator.nextInt(19500)+500 ));
        }

        // Load each ship with some sacks of various grains.
        for(int i=0; i < numberOfSacks; i++) {
            // Generate a random grain type, sack weight, and create the sack object.
            Grain type = Grain.values()[generator.nextInt(Grain.values().length)];
            float weight = generator.nextFloat() * 100;
            Sack thisSack = new Sack(type, weight);

            // Pick a random ship to load it onto.
            String ship = Ship.ShipNames[generator.nextInt(Ship.ShipNames.length)];

            // Find the ship in the list of ships and load our randomly generated sack of grain.
            this.fleet.goFirst();
            while(this.fleet.itemExists() && this.fleet.item().getName().compareTo(ship) != 0) {
                this.fleet.goForth();
            }
            try {
                this.fleet.item().loadSack(thisSack);
            }
            catch(Exception e) {
                System.out.println("I didn't find a ship named " + ship + ".  That shouldn't happen!");
            }
        }
    }

    /**
     * @return A printable string describing the name of each ship in the fleet and it's contents.
     */
    public String toString() {
        // DO NOT MODIFY THIS METHOD

        String out = "";

        this.fleet.goFirst();
        while(this.fleet.itemExists()) {
            out += this.fleet.item().toString();
            this.fleet.goForth();
        }
        return out;
    }

    public static void main(String args[]) {
        // Create a new cargo simulator object.
        CargoSimulator sim = new CargoSimulator(1000);

        //set the cursor to the start of the fleet
        sim.fleet.goFirst();

        //Outer loop goes through the ships one at a time
        while(sim.fleet.itemExists()) {


             // Ship currentship - current ship is just a readability improvement (preference) for the inner nest
             Ship currentShip = sim.fleet.item();


             // int barleycount - since the barley count is per ship, we will start a counter for each ship
            int barleycount = 0;

            // the inner loop, start at the first cargo "sack"
            currentShip.cargo.goFirst();

            while(currentShip.cargo.itemExists()){
                if(currentShip.cargo.item().getType().equals(Grain.BARLEY))
                {
                    barleycount++;
                }
                //make sure we do all cargo in a a ship before going to the next ship
                currentShip.cargo.goForth();
            }
            System.out.printf("%s is carrying %d sacks of barley.\n", currentShip.getName(), barleycount);
            sim.fleet.goForth();
        }

        //reset the ship cursor
        sim.fleet.goFirst();
        while(sim.fleet.itemExists()) {

            // currentShip, same as before, this is my own preferred readability improvement
            Ship currentShip = sim.fleet.item();
            currentShip.cargo.goFirst();
            if(currentShip.isOverloaded()) {
                System.out.printf("%s is overloaded.\n", currentShip.getName());
            }
            sim.fleet.goForth();
        }



   }

}

import java.io.FileNotFoundException;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

/**
 * Created by James Ferris on 5/22/16.
 */
public class sudoku {

    //A list to hold all of
    private static ArrayList<int[]> numbers = new ArrayList<>(9);

    //the 2-d array representing the sudoku board
    private int[][] grid;

    //the current position on the board (row, column)
    private int[] position = new int[2];


    /**
     * Constructor for a starting sudoku configuration.  Takes a filename
     * to use in order to populate the initial configuration
     *
     * @param filename - the name of the file to search for
     * @throws FileNotFoundException - thrown if the filename isn't found
     */
    public sudoku(String filename) throws FileNotFoundException{
        populateNumbers();
        this.position[0] = 0;
        this.position[1] = 0;
        this.grid = new int[9][9];
        Scanner scan = new Scanner(new File(filename));
        int row = 0;
        while (scan.hasNext()){
            String line = scan.nextLine();
            String[] array = line.split(" ");
            for (int i = 0; i < 9; i++){
                this.grid[row][i] = Integer.parseInt(array[i]);
            }
            row += 1;
        }
    }

    /**
     * Copy Constructor for a sudoku configuration
     *
     * @param other - the configuration to be copied
     */
    public sudoku(sudoku other){
        this.grid = new int[9][9];
        this.position = new int[2];
        this.position[0] = other.position[0];
        this.position[1] = other.position[1];
        for (int r = 0; r < 9; r++){
            for (int c = 0; c < 9; c++){
                this.grid[r][c] = other.grid[r][c];
            }
        }
    }

    /**
     * Method to populate all of the starting positions of each 3 x 3
     * section of the sudoku board
     */
    private void populateNumbers(){
        int[] curr = new int[2];

        curr[0] = 0; curr[1] = 0;
        numbers.add(curr);

        int[] curr1 = new int[2];
        curr1[0] = 0; curr1[1] = 3;
        numbers.add(curr1);

        int[] curr2 = new int[2];
        curr2[0] = 0; curr2[1] = 6;
        numbers.add(curr2);

        int[] curr3 = new int[2];
        curr3[0] = 3; curr3[1] = 0;
        numbers.add(curr3);

        int[] curr4 = new int[2];
        curr4[0] = 3; curr4[1] = 3;
        numbers.add(curr4);

        int[] curr5 = new int[2];
        curr5[0] = 3; curr5[1] = 6;
        numbers.add(curr5);

        int[] curr6 = new int[2];
        curr6[0] = 6; curr6[1] = 0;
        numbers.add(curr6);

        int[] curr7 = new int[2];
        curr7[0] = 6; curr7[1] = 3;
        numbers.add(curr7);

        int[] curr8 = new int[2];
        curr8[0] = 6; curr8[1] = 6;
        numbers.add(curr8);
    }

    /**
     * Method to increment the currentPosition of the solver by one space
     */
    private void incrementPos(){
        //if the row should be incremented
        if (this.position[1] == 8) {
            if (this.position[0] == 8) {
                return;
            } else {
                this.position[0] += 1;
                this.position[1] = 0;
            }
        }
        //if it's the last position on the board
        else if (this.position[0] == 8 && this.position[1] == 8) {
            return;
        } else {
            this.position[1] += 1;
        }
    }

    /**
     * Method to check a certain 3x3 section of the sudoku board
     *
     * @param r - the starting row from the entire grid
     * @param c - the starting column from the entire grid
     *
     * @return - true if the square is valid, false otherwise
     */
    private boolean checkSquare(int r, int c){

        ArrayList<Integer> current = new ArrayList<>();

        //loop from the starting row and column to 3 positions further
        //checks for duplicate numbers within the square
        for (int row = r; row < (r + 3); row++){
            for (int col = c; col < (c + 3); col++){
                if (this.grid[row][col] == 0){
                    continue;
                }
                else if (current.contains(this.grid[row][col])){
                    return false;
                }
                else{
                    current.add(this.grid[row][col]);
                }
            }
        }

        return true;
    }

    /**
     * Method to generate all the possible successors of the current configuration.
     * This means, filling up the next position with all possible options
     *
     * @return - an arraylist containing all of the possible configurations
     */
    public ArrayList<sudoku> getSuccessors(){
        ArrayList<sudoku> ret = new ArrayList<>();
        if (this.grid[this.position[0]][this.position[1]] != 0){
            sudoku nuevo = new sudoku(this);
            nuevo.incrementPos();
            ret.add(nuevo);
            return ret;
        }
        else{
            for (int num = 1; num < 10; num++){
                sudoku copy = new sudoku(this);
                copy.grid[this.position[0]][this.position[1]] = num;
                copy.incrementPos();
                ret.add(copy);
            }

            return ret;
        }
    }


    /**
     * Method to determine if the current configuration of the board is valid according to the
     * specifications of sudoku.
     *
     * @return - true if the current configuration is valid, false otherwise
     */
    public boolean isValid(){


        for (int[] arr : numbers){
            if (!checkSquare(arr[0], arr[1])){
                return false;
            }
        }


        ArrayList<Integer> current;
        for (int r = 0; r < 9; r++){
            current = new ArrayList<>();
            for (int c = 0; c < 9; c++){
                if (this.grid[r][c] == 0){
                    continue;
                }
                else {
                    if (current.contains(this.grid[r][c])){
                        return false;
                    }
                    else{
                        current.add(this.grid[r][c]);
                    }
                }
            }
        }

        for (int r = 0; r < 9; r++){
            current = new ArrayList<>();
            for (int c = 0; c < 9; c++){
                if (this.grid[c][r] == 0){
                    continue;
                }
                else {
                    if (current.contains(this.grid[c][r])){
                        return false;
                    }
                    else{
                        current.add(this.grid[c][r]);
                    }
                }
            }
        }


        return true;


    }

    /**
     * Method to ensure that the entire board is filled.
     * isValid will be called before this method, so no validity checks are necessary.
     * @return - true if the board is filled out, false otherwise
     */
    public boolean isGoal(){
        for (int r = 0; r < 9; r++){
            for (int c = 0; c < 9; c++){
                if (this.grid[r][c] == 0){
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Override of toString method to display a string representation of the sudoku board
     * @return - the string representation of the board
     */
    @Override
    public String toString(){
        String ret = "";
        for (int r = 0; r < 9; r++){
            ret += "\n";
            for (int c = 0; c < 9; c++){
                ret += this.grid[r][c];
                ret += " ";
            }
        }

        return ret;
    }


    public static void main(String[] args) throws FileNotFoundException {
        sudoku s = new sudoku(args[0]);
        System.out.println("Starting Configuration:");
        System.out.println(s);
        long start = System.currentTimeMillis();
        Backtracker bt = new Backtracker();
        Optional op = bt.solve(s);
        if (op.isPresent()){
            System.out.println("Solved Configuration:");
            System.out.println(op.get());
        }

        long end = System.currentTimeMillis();

        long fin = end-start;
        System.out.println("Total Time Elapsed: " + (fin/1000) + " seconds");
    }

}


import java.util.Optional;

/**
 * this class represents the classic recursive backtracking algorithm.
 * It has a solver that can take a valid configuration and return a
 * solution, if one exists.
 *
 * This file comes from the backtracking lab. It should be useful
 * in this project. A second method has been added that you should
 * implement.
 *
 * @author Sean Strout @ RIT CS
 * @author James Heliotis @ RIT CS
 * @author James Ferris
 */
public class Backtracker {

    /**
     * Initialize a new backtracker.
     *
     */
    public Backtracker() {

    }



    /**
     * Try find a solution, if one exists, for a given configuration.
     *
     * @param config A valid configuration
     * @return A solution config, or null if no solution
     */
    public Optional<sudoku> solve(sudoku config) {
        if (config.isGoal()) {
            return Optional.of(config);
        } else {
            for (sudoku child : config.getSuccessors()) {
                if (child.isValid()) {
                    Optional<sudoku> sol = solve(child);
                    if (sol.isPresent()) {
                        return sol;
                    }
                } else {
                }
            }
            // implicit backtracking happens here
        }
        return Optional.empty();
    }

}

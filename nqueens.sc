/**
 * =====================================================================================
 * N-QUEENS PROBLEM SOLVER - Classic Backtracking Algorithm
 * =====================================================================================
 * 
 * PROBLEM DEFINITION:
 * Place N chess queens on an N×N chessboard so that no two queens attack each other.
 * Queens can attack horizontally, vertically, and diagonally.
 * 
 * ALGORITHM APPROACH:
 * - Recursive backtracking with constraint satisfaction
 * - Place queens row by row, checking safety constraints
 * - Use functional programming with immutable data structures
 * 
 * REPRESENTATION:
 * - Each solution is a List[Int] where index = row, value = column
 * - Example: List(2, 0, 3, 1) means queens at positions (0,2), (1,0), (2,3), (3,1)
 * 
 * @param n Size of the chessboard (N×N) and number of queens to place
 * @return Set of all valid solutions, each represented as List[Int]
 */
def queens(n: Int): Set[List[Int]] = {
    
    /**
     * Recursive helper function implementing backtracking algorithm.
     * 
     * ALGORITHM LOGIC:
     * 1. Base case: If k=0 queens to place, return empty solution
     * 2. Recursive case: For each valid column in current row:
     *    - Get all solutions for k-1 queens
     *    - Try placing queen in each column 0 to n-1
     *    - Check if placement is safe using constraint validation
     *    - If safe, add column to front of existing solution
     * 
     * @param k Number of queens remaining to place (counts down from n to 0)
     * @return Set of all valid partial solutions with k queens placed
     */
    def placeQueens(k: Int): Set[List[Int]] = {
        if (k == 0) Set(List())  // Base case: no more queens to place
        else {
            for {
                queens <- placeQueens(k - 1)        // Recursive call: get (k-1)-queen solutions
                col <- 0 until n                    // Try each column in current row
                if isSafe(col, queens)              // Constraint: check if placement is safe
            } yield col :: queens                   // Build solution: prepend column to existing
        }
    }
    placeQueens(n)  // Start with n queens to place
}

/**
 * CONSTRAINT VALIDATION - Safety Check for Queen Placement
 * 
 * Determines if placing a queen at (row, col) conflicts with existing queens.
 * Queens attack along rows, columns, and both diagonals.
 * 
 * CONSTRAINT ANALYSIS:
 * 1. Row conflicts: Impossible (we place one queen per row)
 * 2. Column conflicts: col != existing_col
 * 3. Diagonal conflicts: |col - existing_col| != |row - existing_row|
 * 
 * MATHEMATICAL INSIGHT:
 * Diagonal attack condition: queens on same diagonal if slope = ±1
 * Slope = (row2-row1)/(col2-col1) = ±1
 * Therefore: |row2-row1| = |col2-col1| indicates diagonal attack
 * 
 * @param col Column position where we want to place the new queen
 * @param queens List representing existing queen positions (index=row, value=column)
 * @return true if placement is safe (no conflicts), false if conflicts exist
 */
def isSafe(col: Int, queens: List[Int]): Boolean = {
    // Alternative simple implementation (less efficient):
    // !queens.exists(q => q == col)  // Only checks column conflicts
    
    val row = queens.length  // Current row = number of queens already placed
    
    /**
     * Create (row, column) pairs for all existing queens.
     * Since queens list has newest first, we pair with descending row indices.
     * Example: queens = List(3, 1, 0) with length 3
     * Creates: List((2,3), (1,1), (0,0)) - (row, col) pairs
     */
    val queensWithRow = (row - 1 to 0 by -1) zip queens
    
    /**
     * Check all existing queens for conflicts using universal quantification.
     * For each existing queen at (r, c), verify new queen at (row, col) is safe.
     */
    queensWithRow forall {
        case (r, c) => 
            col != c &&                          // Column conflict check
            math.abs(col - c) != row - r         // Diagonal conflict check
            // Note: row > r always, so row - r = |row - r|
    }
}

/**
 * VISUAL REPRESENTATION - Convert Solution to Chessboard Display
 * 
 * Transforms a mathematical solution (list of column positions) into 
 * a visual ASCII chessboard representation.
 * 
 * TRANSFORMATION LOGIC:
 * 1. Reverse queens list to display row 0 at top (standard chessboard view)
 * 2. For each row, create a vector of empty squares ("* ")
 * 3. Replace the queen's column position with "X "
 * 4. Join all characters in row, then join all rows with newlines
 * 
 * EXAMPLE:
 * Input: List(2, 0, 3, 1) represents queens at:
 * - Row 0: Column 2  →  * * X *
 * - Row 1: Column 0  →  X * * *  
 * - Row 2: Column 3  →  * * * X
 * - Row 3: Column 1  →  * X * *
 * 
 * @param queens List[Int] where index=row, value=column of queen placement
 * @return String containing visual ASCII representation of the chessboard
 */
def show(queens: List[Int]) = {
    val lines = 
        for (col <- queens.reverse)  // Reverse to show row 0 at top
        yield Vector.fill(queens.length)("* ")    // Create row of empty squares
                   .updated(col, "X ")            // Place queen at correct column
                   .mkString                      // Join squares into row string
    "\n" + (lines mkString "\n")  // Join rows with newlines, add leading newline
}

/**
 * =====================================================================================
 * DEMONSTRATION AND TESTING
 * =====================================================================================
 */

/**
 * SOLUTION ANALYSIS FOR 4-QUEENS PROBLEM:
 * 
 * The 4×4 chessboard has exactly 2 fundamental solutions (plus rotations/reflections).
 * Each solution is represented as List[Int] where:
 * - Index represents row number (0-based)
 * - Value represents column number (0-based) 
 * 
 * Expected solutions:
 * - Solution 1: List(2, 0, 3, 1) - Queens at (0,2), (1,0), (2,3), (3,1)
 * - Solution 2: List(1, 3, 0, 2) - Queens at (0,1), (1,3), (2,0), (3,2)
 */

// Print raw mathematical representation of all solutions
println("Raw solutions (List[Int] format):")
println(queens(4))

// Print visual ASCII chessboard representations
println("\nVisual representations of all solutions:")
(queens(4) map show) foreach println

/**
 * ALGORITHM COMPLEXITY ANALYSIS:
 * 
 * TIME COMPLEXITY: O(N!) in worst case
 * - Each level tries N positions, but pruning reduces actual branching
 * - Constraint checking is O(N) per placement
 * 
 * SPACE COMPLEXITY: O(N) for recursion depth + solution storage
 * - Maximum recursion depth is N (one level per row)
 * - Each solution requires O(N) space
 * 
 * MATHEMATICAL PROPERTIES:
 * - N=1: 1 solution
 * - N=2,3: 0 solutions (impossible)  
 * - N=4: 2 unique solutions
 * - N=8: 92 solutions (classic 8-queens problem)
 * - N=15: 2,279,184 solutions
 */
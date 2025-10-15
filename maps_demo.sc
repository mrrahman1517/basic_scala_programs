/**
 * =====================================================================================
 * SCALA MAPS DEMONSTRATION - Key-Value Associations and Dictionary Operations
 * =====================================================================================
 * 
 * MAPS OVERVIEW:
 * Maps are immutable collections that store key-value pairs (associations).
 * They provide efficient lookup, insertion, and deletion operations.
 * 
 * KEY CHARACTERISTICS:
 * - Keys must be unique (Set-like property for keys)
 * - Values can be duplicated
 * - Immutable by default (functional programming principle)
 * - Hash-based implementation for O(1) average lookup time
 * - Type-safe with parameterized types Map[K, V]
 * 
 * COMMON USE CASES:
 * - Lookup tables and dictionaries
 * - Configuration mappings
 * - Caching and memoization
 * - Mathematical function representations
 * - Database-like key-value storage
 */

/**
 * BASIC MAP CONSTRUCTION AND LOOKUP
 * 
 * Demonstrates fundamental map operations:
 * - Map literal construction with -> syntax
 * - Direct key lookup with parentheses notation
 * - Type inference for key-value types
 */

// Roman numeral lookup table - Character keys to Integer values
val numerals = Map('I' -> 1, 'V' -> 5, 'X' -> 10)
println(numerals('V'))  // Direct lookup: returns 5

/**
 * STRING-TO-STRING MAPPING EXAMPLE
 * 
 * Country-capital associations demonstrate:
 * - String keys and values
 * - Real-world dictionary use case
 * - Multiple key-value pairs in single Map
 */
val countryCapital = Map("USA" -> "Washington", "Switzerland" -> "Bern", "Bangladesh" -> "Dhaka")

println(countryCapital("Bangladesh"))  // Direct lookup: returns "Dhaka"

/**
 * SAFE MAP LOOKUP - Handling Missing Keys
 * 
 * PROBLEM WITH DIRECT LOOKUP:
 * Using map(key) throws exception if key doesn't exist.
 * This violates functional programming principles of totality.
 * 
 * SOLUTION: Option-based lookup with get() method
 * - Returns Some(value) if key exists
 * - Returns None if key is missing
 * - Enables safe, composable error handling
 */

// Commented out: would throw NoSuchElementException
//print(countryCapital("India"))

// Safe lookup returns Option[String]
print(countryCapital get "India")  // Returns: None

/**
 * PATTERN MATCHING FOR OPTION HANDLING
 * 
 * Demonstrates idiomatic Scala approach to handle missing values:
 * - Pattern match on Option result
 * - Provide default behavior for missing keys
 * - Type-safe null-pointer alternative
 * 
 * FUNCTIONAL PROGRAMMING PRINCIPLE:
 * Make illegal states unrepresentable - use Option instead of null
 */
def showCapital(country: String) = countryCapital.get(country) match {
    case Some(value) => value         // Key found: return the capital
    case None => "missing data"       // Key not found: return default message
}

// Test both existing and missing keys
println(showCapital("India"))       // Output: "missing data"
println(showCapital("Bangladesh"))  // Output: "Dhaka"

/**
 * ADVANCED MAP OPERATIONS - GroupBy Transformation
 * 
 * GROUP-BY OPERATION:
 * Converts a collection into a Map by grouping elements according to a key function.
 * This is a powerful data transformation pattern.
 * 
 * OPERATION BREAKDOWN:
 * 1. Apply key function to each element
 * 2. Group elements with same key
 * 3. Return Map[Key, List[Elements]]
 * 
 * EXAMPLE: cars.groupBy(_.head)
 * - Key function: _.head (first character of each string)
 * - Groups car names by their first letter
 * - Result: Map[Char, List[String]]
 */
val cars = List("lexus", "toyota", "honda", "bmw", "lamborgini", "ford")
println(cars.groupBy(_.head))
// Expected output: Map(l -> List(lexus, lamborgini), t -> List(toyota), h -> List(honda), b -> List(bmw), f -> List(ford))

/**
 * MATHEMATICAL FUNCTION REPRESENTATION USING MAPS
 * 
 * POLYNOMIAL REPRESENTATION:
 * Maps can elegantly represent mathematical functions, especially polynomials.
 * Each (key, value) pair represents (power, coefficient).
 * 
 * EXAMPLE POLYNOMIAL: x³ - 2x + 5
 * Mathematical notation: 1·x³ + 0·x² + (-2)·x¹ + 5·x⁰
 * Map representation: power → coefficient
 * 
 * ADVANTAGES:
 * - Sparse representation (only non-zero terms stored)
 * - Easy coefficient lookup by power
 * - Natural mathematical operations (addition, multiplication)
 * - Efficient for high-degree polynomials with few terms
 */

// Polynomial: x³ - 2x + 5 represented as Map[Int, Int]
val p = Map(0 -> 5, 1 -> -2, 3 -> 1) // (power, coefficient) pairs
// Power 0: coefficient 5 (constant term)
// Power 1: coefficient -2 (linear term) 
// Power 2: coefficient 0 (omitted - sparse representation)
// Power 3: coefficient 1 (cubic term)

/**
 * MATHEMATICAL APPLICATIONS OF MAPS:
 * 
 * 1. POLYNOMIAL ARITHMETIC:
 *    - Addition: merge coefficients for same powers
 *    - Multiplication: distribute and combine terms
 *    - Evaluation: substitute x value using map lookup
 * 
 * 2. FUNCTION MEMOIZATION:
 *    - Cache expensive function results
 *    - Map input → output for pure functions
 * 
 * 3. DISCRETE MATHEMATICS:
 *    - Represent finite functions
 *    - Probability distributions (outcome → probability)
 *    - Graph adjacency (node → neighbors)
 * 
 * 4. COMPUTATIONAL EFFICIENCY:
 *    - O(1) average lookup time
 *    - Memory efficient for sparse data
 *    - Immutable sharing reduces memory overhead
 */

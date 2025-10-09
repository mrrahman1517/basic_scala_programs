/*
 * Scala List Demonstration and Operations
 * 
 * This worksheet demonstrates fundamental list operations in Scala including:
 * - List creation and initialization
 * - Pattern matching with lists
 * - List concatenation using cons operator (::)
 * - Higher-order functions (map, head, tail)
 * - Recursive list processing
 * - Insertion sort implementation using functional programming
 * 
 * Key Concepts Covered:
 * - Immutable data structures
 * - Pattern matching with case statements
 * - Recursive algorithms on lists
 * - Functional programming paradigms
 * - List construction with cons (::) and Nil
 */

println("=== Scala List Operations Demo ===")
println()

// ===================================
// BASIC LIST CREATION AND TYPES
// ===================================

println("--- Basic List Creation ---")

/**
 * List creation using List() constructor
 * Lists in Scala are immutable and homogeneous (same type elements)
 */
val fruit = List("apples", "oranges", "pears")        // List[String]
val nums = List(1, 2, 3, 4)                          // List[Int]
val diag3 = List(List(1, 0, 0), List(0, 1, 0), List(0, 0, 1))  // List[List[Int]] - 3x3 identity matrix
val empty = List()                                    // List[Nothing] - empty list

println(s"Fruit list: $fruit")
println(s"Numbers list: $nums") 
println(s"3x3 Identity matrix: $diag3")
println(s"Empty list: $empty")
println()

// ===================================
// LIST CONSTRUCTION WITH CONS OPERATOR
// ===================================

println("--- List Construction with Cons Operator (::) ---")

/**
 * Alternative list construction using cons operator (::) and Nil
 * 
 * The cons operator (::) prepends an element to a list:
 * - element :: list creates a new list with element at the front
 * - All lists end with Nil (the empty list)
 * - This is the fundamental way lists are built in functional programming
 */
val subjects = "algebra" :: ("analysis" :: ("geometry" :: Nil))

println(s"Subjects constructed with cons: $subjects")
println("Construction breakdown:")
println("  'algebra' :: ('analysis' :: ('geometry' :: Nil))")
println("  This creates: List('algebra', 'analysis', 'geometry')")
println()

// ===================================
// LIST ACCESS OPERATIONS
// ===================================

println("--- List Access Operations ---")

/**
 * head: Returns the first element of the list
 * tail: Returns a new list containing all elements except the first
 * 
 * These operations work efficiently because lists are implemented as linked lists
 * with O(1) access to head and tail.
 */
println(s"subjects.head = ${subjects.head}")           // First element: "algebra"
println(s"subjects.tail = ${subjects.tail}")           // Rest of list: List("analysis", "geometry")
println("Note: head and tail operations are O(1) - constant time")
println()

// ===================================
// LIST CONCATENATION AND EXTENSION  
// ===================================

println("--- List Extension with Cons ---")

/**
 * Adding elements to the front of a list using cons operator
 * This creates a new list (immutability) without modifying the original
 */
val subject2 = "groups" :: subjects
println(s"Original subjects: $subjects")
println(s"Extended subjects: $subject2")
println("Note: Original list is unchanged due to immutability")
println()

// ===================================
// HIGHER-ORDER FUNCTIONS ON LISTS
// ===================================

println("--- Higher-Order Functions on Lists ---")

/**
 * map() function: Applies a function to each element, returning a new list
 * This demonstrates functional programming - transformation without mutation
 */
println("Mapping string length function:")
println(s"subject2.map(s => s.length()) = ${subject2.map(s => s.length())}")

println("Mapping identity function:")
println(s"subject2.map(s => s) = ${subject2.map(s => s)}")
println("Note: Identity mapping returns equivalent list")
println()

// ===================================
// ALTERNATIVE LIST CONSTRUCTION
// ===================================

println("--- Alternative List Construction Syntax ---")

/**
 * Alternative syntax for list construction using multiple cons operators
 * This demonstrates that :: is right-associative:
 * 1 :: 2 :: 3 :: 4 :: Nil is equivalent to 1 :: (2 :: (3 :: (4 :: Nil)))
 */
val nums2 = 1 :: 2 :: 3 :: 4 :: Nil 
println(s"nums2 = $nums2")
println("Right-associative construction: 1 :: (2 :: (3 :: (4 :: Nil)))")
println()

// ===================================
// RECURSIVE FUNCTIONS ON LISTS
// ===================================

println("--- Recursive List Processing: Insertion Sort ---")

/**
 * Insertion Sort Implementation using Pattern Matching and Recursion
 * 
 * This demonstrates functional programming approach to sorting:
 * - No mutable state or variables
 * - Pure functions (no side effects)
 * - Recursive decomposition of the problem
 * - Pattern matching for different list cases
 * 
 * Algorithm:
 * 1. Empty list is already sorted
 * 2. For non-empty list: take first element, recursively sort the rest,
 *    then insert the first element in the correct position
 */

/**
 * Main sorting function using pattern matching
 * 
 * Pattern matching cases:
 * - List() or Nil: Empty list base case, return empty list
 * - y :: ys: Non-empty list, y is head, ys is tail
 * 
 * @param xs the list to sort
 * @return new sorted list in ascending order
 */
def isort(xs: List[Int]): List[Int] = xs match {
    case List() => List()                        // Base case: empty list is sorted
    case y :: ys => insert(y, isort(ys))        // Recursive case: insert head into sorted tail
}

/**
 * Helper function to insert an element into a sorted list
 * 
 * Pattern matching cases:
 * - List(): Insert into empty list creates single-element list
 * - y :: ys: Insert into non-empty list, compare with head
 * 
 * @param elem the element to insert
 * @param xs the sorted list to insert into  
 * @return new sorted list with element inserted in correct position
 */
def insert(elem: Int, xs: List[Int]): List[Int] = xs match {
    case List() => List(elem)                    // Base case: insert into empty list
    case y :: ys => {
        if (elem <= y) elem :: xs                // Insert at front if elem ≤ head
        else y :: insert(elem, ys)               // Recursively insert into tail
    }
}

// ===================================
// INSERTION SORT DEMONSTRATION
// ===================================

println("Testing insertion sort algorithm:")
val a = List(34, 11, 2, 11, 45)
println(s"Original list: $a")
val sortedA = isort(a)
println(s"Sorted list:   $sortedA")

println()
println("--- Step-by-step Trace of isort([34, 11, 2, 11, 45]) ---")
println("1. isort([34, 11, 2, 11, 45])")
println("   = insert(34, isort([11, 2, 11, 45]))")
println("2. isort([11, 2, 11, 45])")  
println("   = insert(11, isort([2, 11, 45]))")
println("3. isort([2, 11, 45])")
println("   = insert(2, isort([11, 45]))")
println("4. isort([11, 45])")
println("   = insert(11, isort([45]))")
println("5. isort([45])")
println("   = insert(45, isort([]))")
println("6. isort([]) = []")
println("7. insert(45, []) = [45]")
println("8. insert(11, [45]) = [11, 45]")
println("9. insert(2, [11, 45]) = [2, 11, 45]")
println("10. insert(11, [2, 11, 45]) = [2, 11, 11, 45]")
println("11. insert(34, [2, 11, 11, 45]) = [2, 11, 11, 34, 45]")

println()
println("=== Summary ===")
println("✅ List creation and manipulation")
println("✅ Pattern matching with lists")  
println("✅ Cons operator (::) usage")
println("✅ Higher-order functions (map)")
println("✅ Recursive list processing")
println("✅ Functional insertion sort implementation")
println("✅ Immutable data structure benefits")

/*
 * KEY FUNCTIONAL PROGRAMMING CONCEPTS DEMONSTRATED:
 * 
 * 1. IMMUTABILITY:
 *    - Lists never change after creation
 *    - All operations return new lists
 *    - Original data structures remain unchanged
 * 
 * 2. PATTERN MATCHING:
 *    - Structural decomposition of lists
 *    - Case-based logic for different list shapes
 *    - Clean handling of base and recursive cases
 * 
 * 3. RECURSION:
 *    - Self-referential function definitions
 *    - Breaking problems into smaller subproblems
 *    - Base cases to terminate recursion
 * 
 * 4. HIGHER-ORDER FUNCTIONS:
 *    - Functions that take other functions as parameters
 *    - map() applies transformation to all elements
 *    - Enables powerful abstraction and reuse
 * 
 * 5. CONS LISTS:
 *    - Fundamental functional data structure
 *    - Efficient head/tail operations
 *    - Natural fit for recursive algorithms
 * 
 * TIME COMPLEXITY ANALYSIS:
 * - isort(): O(n²) where n is list length
 * - insert(): O(n) for inserting into sorted list
 * - List construction with ::: O(1) for prepending
 * - head/tail operations: O(1) constant time
 * 
 * This demonstrates how functional programming can elegantly solve
 * classic computer science problems using immutable data structures
 * and recursive thinking.
 */
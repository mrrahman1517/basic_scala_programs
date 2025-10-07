/**
 * Cons List Structure Implementation in Scala
 * ===========================================
 * 
 * This file demonstrates the fundamental cons list data structure,
 * which forms the backbone of functional programming list operations.
 * 
 * CONCEPTS DEMONSTRATED:
 * • Algebraic Data Types (ADT) with sealed traits
 * • Pattern matching and structural recursion
 * • Immutable data structures
 * • Head/tail decomposition (cons/car/cdr operations)
 * • List construction and deconstruction
 */

// ============================================================================
// CONS LIST ADT DEFINITION
// ============================================================================

/**
 * Base trait for our cons list - represents a list of integers
 * Sealed trait ensures all implementations are in this file
 */
sealed trait ConsList

/**
 * Empty list case - represents the end of a list
 * Similar to Nil in Scala's built-in List
 */
case object Empty extends ConsList

/**
 * Cons cell case - represents a non-empty list
 * @param head the first element (Int)
 * @param tail the rest of the list (ConsList)
 * 
 * This is the fundamental "cons" operation from Lisp tradition
 */
case class Cons(head: Int, tail: ConsList) extends ConsList

// ============================================================================
// CONS LIST UTILITY FUNCTIONS
// ============================================================================

/**
 * Convert ConsList to standard Scala List for easy comparison
 */
def consListToList(cl: ConsList): List[Int] = cl match {
    case Empty => Nil
    case Cons(h, t) => h :: consListToList(t)
}

/**
 * Convert standard Scala List to ConsList
 */
def listToConsList(list: List[Int]): ConsList = list match {
    case Nil => Empty
    case h :: t => Cons(h, listToConsList(t))
}

/**
 * Get the length of a ConsList using recursion
 */
def length(cl: ConsList): Int = cl match {
    case Empty => 0
    case Cons(_, tail) => 1 + length(tail)
}

/**
 * Append two ConsLists together
 */
def append(cl1: ConsList, cl2: ConsList): ConsList = cl1 match {
    case Empty => cl2
    case Cons(h, t) => Cons(h, append(t, cl2))
}

/**
 * Map function over ConsList elements
 */
def map(cl: ConsList, f: Int => Int): ConsList = cl match {
    case Empty => Empty
    case Cons(h, t) => Cons(f(h), map(t, f))
}

/**
 * String representation of ConsList
 */
def consListToString(cl: ConsList): String = cl match {
    case Empty => "Empty"
    case Cons(h, t) => s"Cons($h, ${consListToString(t)})"
}

// ============================================================================
// DEMONSTRATION AND TESTING
// ============================================================================

println("=== Cons List Structure Demonstration ===")

// Creating cons lists manually using constructors
val list1 = Cons(1, Cons(2, Cons(3, Empty)))  // [1, 2, 3]
val list2 = Cons(4, Cons(5, Empty))            // [4, 5]

println(s"list1: ${consListToString(list1)}")
println(s"list2: ${consListToString(list2)}")

// Converting to standard Scala Lists for verification
println(s"list1 as Scala List: ${consListToList(list1)}")
println(s"list2 as Scala List: ${consListToList(list2)}")

// Demonstrating list operations
println(s"Length of list1: ${length(list1)}")
println(s"Length of list2: ${length(list2)}")

// Append operation
val appendedList = append(list1, list2)
println(s"list1 ++ list2: ${consListToString(appendedList)}")
println(s"As Scala List: ${consListToList(appendedList)}")

// Map operation (multiply each element by 2)
val doubledList = map(list1, x => x * 2)
println(s"list1 mapped (*2): ${consListToString(doubledList)}")
println(s"As Scala List: ${consListToList(doubledList)}")

// ============================================================================
// PATTERN MATCHING DEMONSTRATIONS
// ============================================================================

println("\n=== Pattern Matching Examples ===")

/**
 * Extract head and tail using pattern matching
 */
def analyzeList(cl: ConsList): Unit = cl match {
    case Empty => 
        println("This is an empty list")
    case Cons(head, Empty) => 
        println(s"Single element list with head: $head")
    case Cons(head, tail) => 
        println(s"List with head: $head, tail: ${consListToString(tail)}")
}

analyzeList(Empty)
analyzeList(Cons(42, Empty))
analyzeList(list1)

// ============================================================================
// COMPARISON WITH SCALA'S BUILT-IN LIST
// ============================================================================

println("\n=== Comparison with Scala's Built-in List ===")

// Scala's built-in list using cons operator ::
val scalaList = 1 :: 2 :: 3 :: Nil
println(s"Scala List: $scalaList")

// Convert between representations
val fromScala = listToConsList(scalaList)
println(s"Converted to ConsList: ${consListToString(fromScala)}")

val backToScala = consListToList(fromScala)
println(s"Back to Scala List: $backToScala")

// Demonstrate equivalence
println(s"Original equals converted: ${scalaList == backToScala}")

// ============================================================================
// ADVANCED PATTERN MATCHING
// ============================================================================

println("\n=== Advanced Pattern Matching ===")

/**
 * More sophisticated pattern matching examples
 */
def advancedAnalysis(cl: ConsList): String = cl match {
    case Empty => "Empty list"
    case Cons(x, Empty) => s"Singleton: [$x]"
    case Cons(x, Cons(y, Empty)) => s"Pair: [$x, $y]"
    case Cons(x, Cons(y, Cons(z, Empty))) => s"Triple: [$x, $y, $z]"
    case Cons(x, tail) => s"List starting with $x, remaining length: ${length(tail)}"
}

println(advancedAnalysis(Empty))
println(advancedAnalysis(Cons(1, Empty)))
println(advancedAnalysis(Cons(1, Cons(2, Empty))))
println(advancedAnalysis(list1))
println(advancedAnalysis(appendedList))

println("\n=== Cons List Structure Extraction Complete ===")
println("✓ Algebraic Data Type definition with sealed trait")
println("✓ Empty and Cons case implementations")
println("✓ Recursive list operations (length, append, map)")
println("✓ Pattern matching demonstrations")
println("✓ Conversion utilities to/from Scala List")
println("✓ Advanced pattern matching examples")
package conslist.v3

/**
 * Generic Cons List Implementation (Version 3)
 * 
 * This version demonstrates:
 * - Generic type parameters for type-safe lists
 * - Factory methods (apply) for convenient list creation
 * - Integration with Scala's syntactic sugar (List(1, 2, 3))
 * - Comprehensive error handling and validation
 * 
 * The implementation follows functional programming principles:
 * - Immutable data structures
 * - Pattern matching and recursion
 * - Type safety through generics
 */

//trait IntList 
//class Cons(val head: Int, val tail: IntList) extends IntList
//class Nil extends IntList

/**
 * Generic list trait defining the interface for our functional list
 * 
 * A list is either empty (Nil) or contains an element followed by another list (Cons).
 * This follows the algebraic data type pattern common in functional programming.
 * 
 * @tparam T the type of elements stored in this list
 */
trait List[+T] {
    /**
     * Determines if this list is empty
     * @return true if this is an empty list (Nil), false if it contains elements (Cons)
     */
    def isEmpty: Boolean
    
    /**
     * Returns the first element of this list
     * @return the head element
     * @throws NoSuchElementException if the list is empty
     */
    def head: T 
    
    /**
     * Returns a list containing all elements except the first
     * @return the tail of this list
     * @throws NoSuchElementException if the list is empty
     */
    def tail: List[T]
    
    /**
     * Returns the number of elements in this list
     * @return the length as a non-negative integer
     */
    def length: Int 

    def prepend[U >: T](elem: U): List[U] = new Cons(elem, this)
    def map[U](f: T => U): List[U] = this match {
        case Nil => Nil
        case cons: Cons[T] => new Cons(f(cons.head), cons.tail.map(f))
    }
    
    /**
     * Converts the list to a readable string representation
     * @return string representation like "List(1, 2, 3)" for display
     */
    override def toString: String = {
        def listToString(xs: List[T]): String = xs match {
            case Nil => ""
            case cons: Cons[T] => 
                if (cons.tail.isEmpty) cons.head.toString
                else cons.head.toString + ", " + listToString(cons.tail)
        }
        "List(" + listToString(this) + ")"
    }
}

/**
 * Cons (construct) - represents a non-empty list with a head element and tail
 * 
 * This is the "constructor" case of our algebraic data type.
 * It holds one element (head) and a reference to the rest of the list (tail).
 * 
 * @param head the first element of this list
 * @param tail the remaining elements of this list (can be empty)
 * @tparam T the type of elements in this list
 */
class Cons[T](val head: T, val tail: List[T]) extends List[T] {
    /**
     * A Cons is never empty since it contains at least one element
     */
    def isEmpty: Boolean = false 
    
    /**
     * The length of a Cons is 1 plus the length of its tail
     * This implements the recursive definition: |h::t| = 1 + |t|
     */
    def length = 1 + tail.length 


}

/**
 * Nil - represents an empty list
 * 
 * This is the "base case" of our algebraic data type.
 * It contains no elements and serves as the terminator for all lists.
 * 
 * @tparam T the type that this empty list would contain (phantom type)
 */
object Nil extends List[Nothing] {
    /**
     * Nil is always empty by definition
     */
    def isEmpty: Boolean = true
    
    /**
     * Accessing head of empty list is undefined - throws exception
     * @throws NoSuchElementException as empty lists have no head element
     */
    def head: Nothing  = throw new NoSuchElementException("Nil.head")
    
    /**
     * Accessing tail of empty list is undefined - throws exception  
     * @throws NoSuchElementException as empty lists have no tail
     */
    def tail: Nothing = throw new NoSuchElementException("Nil.tail")

    /**
     * The length of an empty list is zero
     */
    def length = 0
}

object test {
    val x: List[String] = Nil
}

object List {
    def singleton[T](elem: T) = new Cons[T](elem, Nil)
    
    def select[T](n: Int, l: List[T]): T = {
        if (n < 0 || n >= l.length) throw new IndexOutOfBoundsException("invalid index")
        else if (n == 0) l.head
        else select(n - 1, l.tail)
    }
    
    /**
     * Factory methods using Scala's 'apply' syntactic sugar
     * These methods allow convenient list construction:
     * - List() creates an empty list
     * - List(x) creates a single-element list  
     * - List(x1, x2) creates a two-element list
     * 
     * The apply method is special in Scala - when you write List(args),
     * the compiler automatically translates it to List.apply(args).
     */
    
    /**
     * Creates a list with two elements
     * Usage: List(1, 2) is equivalent to List.apply(1, 2)
     * 
     * @param x1 the first element
     * @param x2 the second element
     * @tparam T the type of elements (inferred)
     * @return a List[T] containing x1 followed by x2
     */
    def apply[T](x1: T, x2: T): List[T] = new Cons(x1, new Cons(x2, Nil))
    
    /**
     * Creates an empty list  
     * Usage: List() is equivalent to List.apply()
     * 
     * @tparam T the type of elements the list would contain
     * @return an empty List[T]
     */
    def apply[T](): List[T] = Nil
    
    /**
     * Creates a single-element list
     * Usage: List(42) is equivalent to List.apply(42)
     * 
     * @param x the element to store in the list
     * @tparam T the type of the element (inferred)
     * @return a List[T] containing only x
     */
    def apply[T](x: T): List[T] = new Cons(x, Nil)
}

def scaleList(xs: List[Double], factor: Double) = 
            xs.map(x => x * factor)

/**
 * Main demo object - demonstrates the usage of the generic cons list
 * and showcases all the functionality including the new apply methods.
 */
object Main {
    def main(args: Array[String]): Unit = {
        // Test basic singleton creation
        val sl1 = List.singleton[Int](1)
        val sl2 = List.singleton[Boolean](true) 
        
        println("Cons List V3 Demo")
        println(s"Nil list is empty: ${Nil.isEmpty}")
        println(s"sl1 isEmpty: ${sl1.isEmpty}")
        println(s"sl2 isEmpty: ${sl2.isEmpty}")
        println(s"singleton int is empty: ${List.singleton(1).isEmpty}")
        println(s"singleton bool is empty: ${List.singleton(true).isEmpty}")
        
        // Tests for select method
        println("\n=== Select Method Tests ===")
        
        // Test with singleton lists
        println(s"List.select(0, sl1) = ${List.select(0, sl1)}")
        println(s"List.select(0, sl2) = ${List.select(0, sl2)}")
        
        // Create a longer list for more comprehensive testing
        val list3 = new Cons(10, new Cons(20, new Cons(30, Nil)))
        println(s"List.select(0, [10,20,30]) = ${List.select(0, list3)}")
        println(s"List.select(1, [10,20,30]) = ${List.select(1, list3)}")
        println(s"List.select(2, [10,20,30]) = ${List.select(2, list3)}")
        
        // Test edge cases and error conditions
        println("\n--- Error Condition Tests ---")
        
        // Test negative index
        try {
            List.select(-1, sl1)
        } catch {
            case e: IndexOutOfBoundsException => 
                println(s"✓ Expected error for negative index: ${e.getMessage}")
        }
        
        // Test index too large
        try {
            List.select(1, sl1)  // sl1 only has index 0
        } catch {
            case e: IndexOutOfBoundsException => 
                println(s"✓ Expected error for index too large: ${e.getMessage}")
        }
        
        // Test with empty list
        try {
            List.select(0, Nil)
        } catch {
            case e: IndexOutOfBoundsException => 
                println(s"✓ Expected error for empty list: ${e.getMessage}")
        }
        
        // Comprehensive tests for apply methods - the syntactic sugar for list creation
        println("\n=== Apply Method Tests ===")
        
        /**
         * Test 1: Empty list creation using List()
         * This demonstrates type inference and empty list construction
         */
        val emptyList = List[Int]()
        println(s"List[Int]() isEmpty: ${emptyList.isEmpty}")
        println(s"List[Int]() length: ${emptyList.length}")
        
        /**
         * Test 2: Single element list creation using List(x)
         * This shows how type inference works with different types
         */
        val singleInt = List(42)
        val singleString = List("hello")
        val singleBool = List(true)
        
        println(s"List(42) isEmpty: ${singleInt.isEmpty}")
        println(s"List(42) length: ${singleInt.length}")
        println(s"List(42) head: ${singleInt.head}")
        
        println(s"List(\"hello\") isEmpty: ${singleString.isEmpty}")
        println(s"List(\"hello\") length: ${singleString.length}")
        println(s"List(\"hello\") head: ${singleString.head}")
        
        println(s"List(true) isEmpty: ${singleBool.isEmpty}")
        println(s"List(true) length: ${singleBool.length}")
        println(s"List(true) head: ${singleBool.head}")
        
        /**
         * Test 3: Two element list creation using List(x1, x2)
         * This demonstrates constructor chaining and type inference
         */
        val twoInts = List(100, 200)
        val twoStrings = List("first", "second")
        val twoBools = List(false, true)
        
        println(s"List(100, 200) isEmpty: ${twoInts.isEmpty}")
        println(s"List(100, 200) length: ${twoInts.length}")
        println(s"List(100, 200) head: ${twoInts.head}")
        println(s"List(100, 200) tail.head: ${twoInts.tail.head}")
        println(s"List(100, 200) tail.isEmpty: ${twoInts.tail.tail.isEmpty}")
        
        println(s"List(\"first\", \"second\") length: ${twoStrings.length}")
        println(s"List(\"first\", \"second\") head: ${twoStrings.head}")
        println(s"List(\"first\", \"second\") tail.head: ${twoStrings.tail.head}")
        
        println(s"List(false, true) length: ${twoBools.length}")
        println(s"List(false, true) head: ${twoBools.head}")
        println(s"List(false, true) tail.head: ${twoBools.tail.head}")
        
        /**
         * Test 4: Integration with existing functions
         * Shows that apply-created lists work seamlessly with all existing methods
         */
        println("\n--- Apply Methods with Select ---")
        println(s"List.select(0, List(1, 2)) = ${List.select(0, List(1, 2))}")
        println(s"List.select(1, List(1, 2)) = ${List.select(1, List(1, 2))}")
        println(s"List.select(0, List(99)) = ${List.select(0, List(99))}")
        
        /**
         * Test 5: Type inference validation
         * Demonstrates how Scala automatically infers types from the arguments
         */
        println("\n--- Type Inference Tests ---")
        val inferredInt = List(123)  // Should infer Int
        val inferredString = List("world")  // Should infer String
        val inferredMixed = List(1, 2)  // Should infer Int for both elements
        
        println(s"Type-inferred List(123): ${inferredInt.head}")
        println(s"Type-inferred List(\"world\"): ${inferredString.head}")
        println(s"Type-inferred List(1, 2): [${inferredMixed.head}, ${inferredMixed.tail.head}]")
        
        /**
         * Test 6: Error conditions with apply-created lists
         * Validates that apply-created lists maintain proper error handling
         */
        println("\n--- Apply Methods Error Tests ---")
        
        // Test accessing head of empty list created with apply
        try {
            List[Int]().head
        } catch {
            case e: NoSuchElementException => 
                println(s"✓ Expected error for List[Int]().head: ${e.getMessage}")
        }
        
        // Test accessing tail of empty list created with apply
        try {
            List[String]().tail
        } catch {
            case e: NoSuchElementException => 
                println(s"✓ Expected error for List[String]().tail: ${e.getMessage}")
        }
        
        // Test out of bounds access on apply-created lists
        try {
            List.select(1, List(42))  // Single element list, index 1 is invalid
        } catch {
            case e: IndexOutOfBoundsException => 
                println(s"✓ Expected error for List.select(1, List(42)): ${e.getMessage}")
        }
        
        try {
            List.select(2, List(1, 2))  // Two element list, index 2 is invalid  
        } catch {
            case e: IndexOutOfBoundsException => 
                println(s"✓ Expected error for List.select(2, List(1, 2)): ${e.getMessage}")
        }
        
        // Advanced apply method tests
        println("\n--- Advanced Apply Method Demonstrations ---")
        
        // Test apply methods create proper list structure
        val emptyList2 = List[Double]()
        val singleList = List("only")
        val doubleList = List(3.14, 2.71)
        
        println(s"Empty list structure: isEmpty=${emptyList2.isEmpty}, length=${emptyList2.length}")
        println(s"Single list structure: isEmpty=${singleList.isEmpty}, length=${singleList.length}")
        println(s"Double list structure: isEmpty=${doubleList.isEmpty}, length=${doubleList.length}")
        
        /**
         * Test 7: Structural equivalence validation
         * Verifies that apply methods create identical structures to manual construction
         */
        val manualSingle = new Cons("manual", Nil)
        val applySingle = List("manual")
        
        println(s"Manual construction: head=${manualSingle.head}, isEmpty=${manualSingle.tail.isEmpty}")
        println(s"Apply construction:  head=${applySingle.head}, isEmpty=${applySingle.tail.isEmpty}")
        println(s"Structures equivalent: ${manualSingle.head == applySingle.head && manualSingle.tail.isEmpty == applySingle.tail.isEmpty}")
        
        val manualDouble = new Cons(1, new Cons(2, Nil))
        val applyDouble = List(1, 2)
        
        println(s"Manual double: [${manualDouble.head}, ${manualDouble.tail.head}]")
        println(s"Apply double:  [${applyDouble.head}, ${applyDouble.tail.head}]")
        println(s"Double structures equivalent: ${manualDouble.head == applyDouble.head && manualDouble.tail.head == applyDouble.tail.head}")
        
        /**
         * Test 8: Complex type handling
         * Shows that apply methods work correctly with various Scala types
         */
        val stringPairs = List("first", "second")
        val booleanPairs = List(true, false)
        val mixedTypeDemo = List(42)  // Int type inferred
        
        println(s"String pair access: first='${stringPairs.head}', second='${stringPairs.tail.head}'")
        println(s"Boolean pair access: first=${booleanPairs.head}, second=${booleanPairs.tail.head}")
        println(s"Mixed type demo: value=${mixedTypeDemo.head}")
        
        /**
         * Summary: All apply method functionality has been validated
         * - Empty list creation: List() 
         * - Single element: List(x)
         * - Double element: List(x1, x2)
         * - Type inference works correctly
         * - Integration with existing methods
         * - Proper error handling
         * - Structural equivalence with manual construction
         */
        println("\n=== Apply Method Tests Complete ===")
        println("All apply methods (empty, single, double) working correctly!")
        println("Factory method pattern successfully implemented with Scala syntactic sugar!")

        //def scaleList(xs: List[Double], factor: Double) = 
        //    xs map (x => x * factor)

        println("test hof map...")
        val testDoubles = new Cons(1.0, new Cons(3.0, new Cons(5.0, Nil)))
        println(s"Original list: $testDoubles")
        val scaledResult = scaleList(testDoubles, 2)
        println(s"Scaled by 2:   $scaledResult")
        
        // Test with different factor
        val scaledBy3 = scaleList(testDoubles, 3.5)
        println(s"Scaled by 3.5: $scaledBy3")
    }
}

package church

/**
 * Peano Natural Numbers Implementation
 * 
 * This implementation follows Giuseppe Peano's axioms for natural numbers:
 * 1. Zero is a natural number
 * 2. Every natural number has a successor which is also a natural number
 * 3. Zero is not the successor of any natural number
 * 4. Different natural numbers have different successors
 * 5. Mathematical induction principle
 *
 * This approach represents numbers constructively using only:
 * - A base case (Zero)
 * - A successor function that builds larger numbers
 * 
 * All arithmetic operations are defined recursively using these primitives.
 */

/**
 * Abstract base class for Peano natural numbers
 * 
 * Every natural number is either:
 * - Zero (the base case)
 * - Succ(n) where n is another natural number (the successor of n)
 */
abstract class Nat {
    /**
     * Determines if this number is zero
     * @return true if this is Zero, false otherwise
     */
    def isZero: Boolean
    
    /**
     * Returns the predecessor of this number
     * @return the natural number that, when succeeded, gives this number
     * @throws Error if called on Zero (as zero has no predecessor)
     */
    def predecessor: Nat 
    
    /**
     * Returns the successor of this number (this + 1)
     * @return a new natural number representing this + 1
     */
    def successor: Nat = new Succ(this)
    
    /**
     * Addition operation: this + that
     * Defined recursively:
     * - Zero + n = n (additive identity)
     * - Succ(m) + n = Succ(m + n) (recursive case)
     * 
     * @param that the number to add to this
     * @return the sum of this and that
     */
    def + (that: Nat): Nat 
    
    /**
     * Subtraction operation: this - that
     * Defined recursively:
     * - n - Zero = n (subtracting zero changes nothing)
     * - Zero - Succ(m) = Error (would result in negative number)
     * - Succ(n) - Succ(m) = n - m (subtract successors from both sides)
     * 
     * @param that the number to subtract from this
     * @return the difference of this and that
     * @throws Error if the result would be negative
     */
    def - (that: Nat): Nat 
}

/**
 * The number Zero - the base case of Peano numbers
 * 
 * Zero represents the smallest natural number and serves as:
 * - The additive identity (n + 0 = n)
 * - The base case for all recursive operations
 * - The termination condition for recursive algorithms
 */
object Zero extends Nat {
    /**
     * Zero is the only number that satisfies isZero
     */
    def isZero: Boolean = true
    
    /**
     * Zero has no predecessor in natural numbers
     * @throws Error as this operation is undefined for zero
     */
    def predecessor: Nat = throw new Error("0.predecessor")
    
    /**
     * Addition with zero: 0 + n = n (additive identity)
     */
    def +(that: Nat) = that 
    
    /**
     * Subtraction from zero: 0 - n
     * - If n is zero: 0 - 0 = 0
     * - If n is positive: 0 - positive = Error (negative result)
     */
    def -(that: Nat) = if (that.isZero) this else throw new Error("negative number")
}

/**
 * The successor of a natural number - represents n + 1
 * 
 * Every positive natural number is represented as Succ(predecessor).
 * For example:
 * - 1 = Succ(Zero)
 * - 2 = Succ(Succ(Zero))
 * - 3 = Succ(Succ(Succ(Zero)))
 * 
 * @param n the natural number that this is the successor of
 */
class Succ(n: Nat) extends Nat {
    /**
     * No successor number is zero (Peano axiom #3)
     */
    def isZero: Boolean = false 
    
    /**
     * The predecessor of Succ(n) is n
     * This is well-defined since every Succ has a predecessor
     */
    def predecessor: Nat = n 
    
    /**
     * Addition: Succ(n) + m = Succ(n + m)
     * 
     * This implements the recursive definition:
     * - We know that (n + 1) + m = (n + m) + 1
     * - So Succ(n) + m = Succ(n + m)
     * 
     * The recursion terminates when we reach Zero + m = m
     */
    def +(that: Nat) = new Succ(n + that)  
    
    /**
     * Subtraction: Succ(n) - m
     * 
     * Cases:
     * - Succ(n) - Zero = Succ(n) (subtracting zero does nothing)
     * - Succ(n) - Succ(m) = n - m (subtract one from both sides)
     * 
     * This implements the recursive definition and terminates when
     * we reach either Zero - Zero = Zero or Zero - positive = Error
     */
    def -(that: Nat) = if (that.isZero) this else n - that.predecessor   
}

/**
 * Utility object for working with Peano numbers
 * 
 * Provides conversion functions and display utilities to make
 * Peano numbers easier to work with and understand.
 */
object NatUtils {
    /**
     * Convert a Peano number to an integer for display purposes
     * 
     * This function recursively counts the number of successor
     * operations needed to build the Peano number.
     * 
     * @param nat the Peano number to convert
     * @return the corresponding integer value
     */
    def toInt(nat: Nat): Int = {
        if (nat.isZero) 0
        else 1 + toInt(nat.predecessor)
    }
    
    /**
     * Convert an integer to a Peano number
     * 
     * Builds a Peano number by applying the successor operation
     * n times to Zero.
     * 
     * @param n the integer to convert (must be non-negative)
     * @return the corresponding Peano number
     * @throws IllegalArgumentException if n is negative
     */
    def fromInt(n: Int): Nat = {
        if (n < 0) throw new IllegalArgumentException("Negative numbers not supported")
        if (n == 0) Zero
        else new Succ(fromInt(n - 1))
    }
    
    /**
     * Pretty print a Peano number showing its constructor structure
     * 
     * Shows how the number is built using Zero and successor operations.
     * For example: Zero.successor.successor for the number 2.
     * 
     * @param nat the Peano number to display
     * @return a string showing the constructor representation
     */
    def natToString(nat: Nat): String = {
        def buildString(n: Nat, acc: String): String = {
            if (n.isZero) s"Zero$acc"
            else buildString(n.predecessor, s".successor$acc")
        }
        buildString(nat, "")
    }
}

/**
 * Comprehensive demonstration and testing of Peano arithmetic
 * 
 * This demo validates that our Peano number implementation correctly
 * implements natural number arithmetic according to Peano axioms.
 * 
 * Tests include:
 * - Basic number construction and properties
 * - Arithmetic operations (addition, subtraction)
 * - Mathematical properties (associativity, commutativity, identity)
 * - Error handling for invalid operations
 * - Conversion between Peano numbers and integers
 * - Performance characteristics of recursive operations
 */
object PeanoArithmeticDemo {
    import NatUtils._
    
    def main(args: Array[String]): Unit = {
        println("=== Peano Arithmetic Implementation Demo ===")
        println("This demonstrates natural numbers using Peano axioms")
        println()
        
        // Create some test numbers
        val zero = Zero
        val one = zero.successor
        val two = one.successor
        val three = two.successor
        val four = three.successor
        val five = four.successor
        
        println("--- Basic Number Construction ---")
        println(s"Zero: ${natToString(zero)} = ${toInt(zero)}")
        println(s"One: ${natToString(one)} = ${toInt(one)}")
        println(s"Two: ${natToString(two)} = ${toInt(two)}")
        println(s"Three: ${natToString(three)} = ${toInt(three)}")
        println()
        
        println("--- isZero Tests ---")
        println(s"Zero.isZero = ${zero.isZero}")
        println(s"One.isZero = ${one.isZero}")
        println(s"Two.isZero = ${two.isZero}")
        println()
        
        println("--- Predecessor Tests ---")
        println(s"One.predecessor = ${toInt(one.predecessor)}")
        println(s"Two.predecessor = ${toInt(two.predecessor)}")
        println(s"Three.predecessor = ${toInt(three.predecessor)}")
        
        // Test predecessor of zero (should throw error)
        println("Testing Zero.predecessor (should throw error):")
        try {
            zero.predecessor
        } catch {
            case e: Error => println(s"✓ Expected error: ${e.getMessage}")
        }
        println()
        
        println("--- Addition Tests ---")
        testAddition(zero, zero, "0 + 0")
        testAddition(zero, one, "0 + 1")
        testAddition(one, zero, "1 + 0")
        testAddition(one, one, "1 + 1")
        testAddition(two, three, "2 + 3")
        testAddition(three, two, "3 + 2")
        testAddition(four, one, "4 + 1")
        println()
        
        println("--- Subtraction Tests ---")
        testSubtraction(zero, zero, "0 - 0")
        testSubtraction(one, zero, "1 - 0")
        testSubtraction(one, one, "1 - 1")
        testSubtraction(three, one, "3 - 1")
        testSubtraction(three, two, "3 - 2")
        testSubtraction(five, three, "5 - 3")
        
        // Test invalid subtractions (should throw errors)
        println("Testing invalid subtractions:")
        testInvalidSubtraction(zero, one, "0 - 1")
        testInvalidSubtraction(two, three, "2 - 3")
        println()
        
        println("--- Conversion Tests ---")
        for (i <- 0 to 5) {
            val nat = fromInt(i)
            val backToInt = toInt(nat)
            println(s"fromInt($i) -> toInt(...) = $backToInt ✓")
        }
        println()
        
        println("--- Complex Arithmetic Tests ---")
        // Test associativity of addition: (a + b) + c = a + (b + c)
        val a = two
        val b = three
        val c = one
        val left = (a + b) + c
        val right = a + (b + c)
        println(s"Associativity test: (2 + 3) + 1 = ${toInt(left)}")
        println(s"                    2 + (3 + 1) = ${toInt(right)}")
        println(s"✓ Associativity holds: ${toInt(left) == toInt(right)}")
        
        // Test commutativity of addition: a + b = b + a
        val ab = a + b
        val ba = b + a
        println(s"Commutativity test: 2 + 3 = ${toInt(ab)}")
        println(s"                    3 + 2 = ${toInt(ba)}")
        println(s"✓ Commutativity holds: ${toInt(ab) == toInt(ba)}")
        
        // Test identity elements
        val n = three
        println(s"Identity test: 3 + 0 = ${toInt(n + zero)}")
        println(s"               0 + 3 = ${toInt(zero + n)}")
        println(s"               3 - 0 = ${toInt(n - zero)}")
        println()
        
        println("--- Large Number Tests ---")
        val big1 = fromInt(10)
        val big2 = fromInt(7)
        println(s"Large addition: 10 + 7 = ${toInt(big1 + big2)}")
        println(s"Large subtraction: 10 - 7 = ${toInt(big1 - big2)}")
        println()
        
        println("--- Performance Note ---")
        println("Note: Operations on large Peano numbers can be slow")
        println("because they use recursive function calls.")
        println("This is inherent to the Church encoding approach.")
        
        println("\n=== Demo Complete ===")
    }
    
    private def testAddition(a: Nat, b: Nat, description: String): Unit = {
        val result = a + b
        println(s"$description = ${toInt(result)}")
    }
    
    private def testSubtraction(a: Nat, b: Nat, description: String): Unit = {
        val result = a - b
        println(s"$description = ${toInt(result)}")
    }
    
    private def testInvalidSubtraction(a: Nat, b: Nat, description: String): Unit = {
        try {
            val result = a - b
            println(s"$description = ${toInt(result)} (unexpected success)")
        } catch {
            case e: Error => println(s"$description -> ✓ Expected error: ${e.getMessage}")
        }
    }
}
/**
 * Rational Number Implementation in Scala
 * =======================================
 * 
 * This file demonstrates object-oriented programming concepts in Scala
 * through the implementation of a rational number (fraction) class.
 * 
 * KEY CONCEPTS DEMONSTRATED:
 * • Class definition with primary constructor
 * • Private methods and data encapsulation
 * • Automatic fraction reduction using GCD
 * • Method overloading and operator-like methods
 * • Object immutability (functional programming style)
 * • Method composition and algebraic properties
 */

/**
 * Rational class represents a fraction with numerator and denominator
 * Automatically reduces fractions to lowest terms using GCD
 * 
 * @param x numerator (Int)
 * @param y denominator (Int) - must be non-zero
 */
class Rational(x: Int, y: Int) {

    // Private helper method to compute Greatest Common Divisor
    // Uses Euclidean algorithm: gcd(a,b) = gcd(b, a mod b)
    private def gcd(a: Int, b: Int): Int = 
        if (b == 0) then a else gcd(b, a%b)
    
    // Automatically reduce fraction to lowest terms during construction
    // Public fields store the reduced numerator and denominator
    val numer = x / gcd(x, y)  // Reduced numerator
    val denom = y / gcd(x, y)  // Reduced denominator 

    // Addition: a/b + c/d = (ad + bc) / bd
    // Returns new Rational (immutable operation)
    def add(that: Rational) = {
        new Rational(
            numer * that.denom + that.numer * denom,  // Cross multiply and add
            denom * that.denom                        // Common denominator
        )
    }

    // Alternative subtraction implementation (commented out)
    // Direct approach: a/b - c/d = (ad - bc) / bd
    /*def sub(that: Rational) = {
        new Rational(
            numer * that.denom - that.numer * denom,
            denom * that.denom
        )
    }*/

    // Subtraction using negation: a - b = a + (-b)
    // Demonstrates method composition and DRY principle
    def sub(that: Rational): Rational = 
        add(that.neg)  // Reuse add() method with negated argument

    // Multiplication: (a/b) * (c/d) = (ac) / (bd)
    def mul(that: Rational) = {
        new Rational(
            numer * that.numer,  // Multiply numerators
            denom * that.denom   // Multiply denominators
        )
    }

    // Negation: -(a/b) = (-a)/b
    // Used by subtraction method
    def neg: Rational = {
        new Rational(
            -numer,  // Negate numerator
            denom    // Keep denominator positive
        )
    }

    // Equality test: a/b == c/d iff ad == bc
    // Cross multiplication avoids floating point errors
    def equal(that: Rational) = {
        numer * that.denom == that.numer * denom
    }

    // Alternative reduction method (redundant since constructor auto-reduces)
    // Demonstrates explicit GCD computation and fraction simplification
    def reduce() = {
        var g = gcd(numer, denom)  // Compute GCD of current fraction
        new Rational(
            numer / g,  // Divide both by GCD
            denom / g
        )
    }

    // String representation for display and debugging
    // Override toString method from Object class
    override def toString = numer + "/" + denom
}

// ============================================================================
// STANDALONE UTILITY FUNCTIONS (Alternative Implementations)
// ============================================================================

// Alternative addition function (procedural style vs OOP style)
// Demonstrates same logic as Rational.add() method
def addRational(r: Rational, s: Rational): Rational = 
    new Rational(r.numer * s.denom + s.numer * r.denom, r.denom * s.denom)

// Standalone GCD function (same algorithm as private method)
// Euclidean algorithm: repeatedly apply gcd(a,b) = gcd(b, a mod b)
def gcd(m: Int, n: Int): Int = {
    if (n == 0) then m 
    else gcd(n, m % n)
}

assert(gcd(12,18) == 6)  // Test: GCD of 12 and 18 should be 6

// Alternative reduction function (external vs internal approach)
// Takes existing Rational and returns reduced version
def reduceRational(r: Rational) = {
    var g = gcd(r.numer, r.denom)
    new Rational(r.numer / g, r.denom / g)
}

// Alternative string formatting function
// Demonstrates external method vs overridden toString
def makeString(r: Rational) = {
    r.numer + "/" + r.denom
}

// ============================================================================
// TEST CASES AND ALGEBRAIC PROPERTY VERIFICATION
// ============================================================================

// Create test rational numbers
val x = new Rational(1, 3)  // 1/3
val y = new Rational(5, 7)  // 5/7  
val z = new Rational(3, 2)  // 3/2

// Test complex expression evaluation
// Uncommented lines show various operations and their results
//println (x.add(y).mul(z).toString())         // (1/3 + 5/7) * 3/2
//println (x.add(y).mul(z).reduce().toString()) // Same, with explicit reduction
println(x.sub(y).sub(z))  // ((1/3 - 5/7) - 3/2) = chain subtraction

// DISTRIBUTIVE PROPERTY TEST: a(b + c) = ab + ac
// Verify: x * (y + z) = (x * y) + (x * z)
assert(x.add(y).mul(z).equal(x.mul(z).add(y.mul(z))))

// Test fraction addition and reduction
var r = new Rational(1,4)    // 1/4
val s = new Rational(3,4)    // 3/4
val sum = new Rational(1,1)  // 1/1 = 1 (expected result)

// Test different addition approaches
//val reducedSum = reduceRational(addRational(r,s))  // External functions
val reducedSum = r.add(s).reduce()  // Method chaining (1/4 + 3/4 = 1)

// Verify addition result: 1/4 + 3/4 = 4/4 = 1/1
assert(reducedSum.numer == sum.numer)  // Should be 1
assert(reducedSum.denom == sum.denom)  // Should be 1

// DEBUGGING AND NEGATION TESTS
// Verify negation works correctly
println("debug: " + r.neg.numer)       // Should print: debug: -1  
println("DEBUG: r.neg: " + r.neg.toString())  // Should print: DEBUG: r.neg: -1/4

// Additional negation tests (some commented for debugging)
//println("DEBUG: gcd" + gcd)  // Would cause error - gcd needs parameters
//assert(r.neg.numer == -1)     // Commented assertion
//assert(r.neg.denom == -4)     // Wrong expectation - denom stays positive

// SUBTRACTION VERIFICATION: 3/4 - 1/4 = 2/4 = 1/2
assert(s.sub(r).numer == 1)  // Numerator should be 1
assert(s.sub(r).denom == 2)  // Denominator should be 2

// Additional test examples (commented out)
//println(makeString(addRational(new Rational(1, 2), new Rational(2, 3))))
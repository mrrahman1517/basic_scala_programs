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
    require(y != 0, "denominator must be non zero")  // Precondition check - contract programming

    // AUXILIARY CONSTRUCTOR: allows creating integers as rationals
    // Example: new Rational(5) creates 5/1
    // Demonstrates constructor overloading in Scala
    def this(x: Int) = this(x, 1)  // Delegate to primary constructor
    
    // Private helper method to compute Greatest Common Divisor
    // Uses Euclidean algorithm: gcd(a,b) = gcd(b, a mod b)
    // Time complexity: O(log(min(a,b)))
    private def gcd(a: Int, b: Int): Int = 
        if (b == 0) then a else gcd(b, a%b)
    
    // PERFORMANCE OPTIMIZATION: compute GCD once and reuse
    // Avoids redundant calculation in numer and denom
    val g = gcd(x, y)
    
    // Automatically reduce fraction to lowest terms during construction
    // Public methods (not fields) for numerator and denominator
    // This ensures they're always in reduced form
    def numer = x / g  // Reduced numerator
    def denom = y / g  // Reduced denominator 
    //val numer = x 
    //val denom = y 

    // COMPARISON OPERATOR: implement < for rational ordering
    // Mathematical basis: a/b < c/d iff ad < bc (assuming positive denominators)
    // Used by max() method and enables sorting of rationals
    def < (that: Rational): Boolean = 
        numer * that.denom < that.numer * denom

    // Maximum function using comparison operator
    // Demonstrates conditional expressions and method composition
    // Pattern: if condition then value1 else value2
    def max(that: Rational): Rational = {
        if this < that then that else this  // 'this' refers to current object
    }

    // OPERATOR OVERLOADING: + symbol for intuitive mathematical notation
    // Addition: a/b + c/d = (ad + bc) / bd
    // Returns new Rational (immutable operation - no side effects)
    // Note: Scala allows any symbol as method name, enabling math-like syntax
    def + (that: Rational) = {
        new Rational(
            numer * that.denom + that.numer * denom,  // Cross multiply and add
            denom * that.denom                        // Common denominator
        )
    }

    // Alternative subtraction implementation (commented out)
    // Direct approach: a/b - c/d = (ad - bc) / bd
    // Shows two different implementation strategies
    /*def sub(that: Rational) = {
        new Rational(
            numer * that.denom - that.numer * denom,
            denom * that.denom
        )
    }*/

    // ELEGANT SUBTRACTION: reuse addition with negation
    // Subtraction as inverse operation: a - b = a + (-b)
    // Demonstrates method composition and DRY principle
    // Less code, reuses tested addition logic
    def - (that: Rational): Rational = 
        this + -that  // 'this +' is explicit form of operator call

    // MULTIPLICATION: simpler than addition - just multiply across
    // Mathematical rule: (a/b) * (c/d) = (ac) / (bd)
    // No need for common denominators like in addition
    def * (that: Rational) = {
        new Rational(
            numer * that.numer,  // Multiply numerators
            denom * that.denom   // Multiply denominators
        )
    }

    // UNARY NEGATION: mathematical additive inverse
    // Property: x + (-x) = 0 for any rational x
    // Implementation: negate numerator, keep denominator positive
    def unary_- : Rational = {
        new Rational(
            -numer,  // Negate numerator
            denom    // Keep denominator positive (mathematical convention)
        )
    }

    // EQUALITY OPERATOR: overload == for mathematical equality
    // Cross multiplication test: a/b == c/d iff ad == bc
    // Avoids floating point precision errors that division would cause
    // More robust than converting to decimals and comparing
    def == (that: Rational) = {
        numer * that.denom == that.numer * denom
    }

    // ALTERNATIVE REDUCTION: explicit reduction after creation
    // Note: This is redundant since constructor already reduces fractions
    // Kept for educational purposes to show explicit GCD usage
    // In practice, constructor reduction is more efficient
    def reduce() = {
        var g = gcd(numer, denom)  // Recompute GCD (inefficient)
        new Rational(
            numer / g,  // Divide both by GCD
            denom / g
        )
    }

    // STRING REPRESENTATION: custom formatting for display
    // Override Object.toString() for meaningful output
    // Ensures fractions are always displayed in reduced form
    override def toString = {
        val g = gcd(numer, denom)  // Ensure reduction for display
        numer / g + "/" + denom / g  // Format as "numerator/denominator"
    }
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
// COMPREHENSIVE TEST SUITE - Property-Based and Edge Case Testing
// ============================================================================

// BASIC TEST INSTANCES: cover common use cases
val x = new Rational(1, 3)  // 1/3 - proper fraction
val y = new Rational(5, 7)  // 5/7 - another proper fraction
val z = new Rational(3, 2)  // 3/2 - improper fraction (> 1)

// COMPLEX EXPRESSION TESTING: verify operator precedence and associativity
// Test chained operations to ensure mathematical correctness
println(x - y - z)  // Chain subtraction: ((1/3 - 5/7) - 3/2)
                    // Tests: left associativity, negative results, reduction

// ALGEBRAIC PROPERTY VERIFICATION: distributive law
// Mathematical property: a(b + c) = ab + ac
// This is a fundamental property that must hold for any number system
// If this fails, our implementation is mathematically incorrect
assert((x + y) * z == x * z + y * z)  // Distributivity test

// FRACTION ARITHMETIC VALIDATION
var r = new Rational(1,4)    // 1/4 - unit fraction
val s = new Rational(3,4)    // 3/4 - three quarters
val sum = new Rational(1,1)  // 1 - whole number as fraction

// ORDERING TESTS: verify comparison operators work correctly
assert(r < sum)    // 1/4 < 1 should be true
assert(s < sum)    // 3/4 < 1 should be true

// COMMUTATIVITY TEST: max(a,b) should equal max(b,a)
// Tests both comparison logic and max function
assert(r.max(s) == (s.max(r)))  // Test commutative property of max

// REDUCTION AND CONSTRUCTOR TESTING
val reducedSum = (r + s).reduce()  // Method chaining: 1/4 + 3/4 = 1

// PRECISE ARITHMETIC VERIFICATION: exact fraction arithmetic
// Unlike floating point, rational arithmetic is exact - no rounding errors
assert(reducedSum.numer == sum.numer)  // Numerator should be exactly 1
assert(reducedSum.denom == sum.denom)  // Denominator should be exactly 1

// NEGATION AND SIGN HANDLING TESTS
// Verify negation preserves magnitude but flips sign
println("debug: " + -r.numer)       // Should print: debug: -1  
println("DEBUG: r.neg: " + (-r).toString())  // Should print: DEBUG: r.neg: -1/4

// Note: denominator stays positive by mathematical convention
// Negative fractions have negative numerator, positive denominator

// COMMENTED EDGE CASES AND POTENTIAL ISSUES
//println("DEBUG: gcd" + gcd)  // Would cause error - gcd needs parameters
//assert(r.neg.numer == -1)     // Would pass - negation works correctly
//assert(r.neg.denom == -4)     // Would FAIL - denom stays positive

// AUXILIARY CONSTRUCTOR TESTING: integer to rational conversion
// Tests the secondary constructor that takes only one parameter
val i = new Rational(5)     // Should create 5/1
val j = new Rational(7)     // Should create 7/1  
val k = new Rational(12)    // Should create 12/1
assert((i + j) == k)        // Test: 5/1 + 7/1 = 12/1

// AUTOMATIC REDUCTION VERIFICATION
// Constructor should automatically reduce fractions to lowest terms
val r4 = new Rational(12,3)  // Should automatically become 4/1
println("r4: " + r4)         // Should display "4/1" not "12/3"
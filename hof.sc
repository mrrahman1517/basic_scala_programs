/**
 * Higher-Order Functions (HOF) Demonstration in Scala
 * =====================================================
 * 
 * This file demonstrates the evolution from specific, repetitive functions
 * to elegant, reusable higher-order functions - a core principle of functional programming.
 * 
 * LEARNING PROGRESSION:
 * 1. Problem: Repetitive functions with similar structure (sumInts, sumSquares, etc.)
 * 2. Solution: Extract common pattern into higher-order function (sum)
 * 3. Enhancement: Use anonymous functions for conciseness
 * 4. Optimization: Apply tail recursion for performance
 * 5. Abstraction: Introduce currying for partial application
 * 6. Generalization: Map-reduce pattern for ultimate flexibility
 * 7. Application: Fixed-point iteration for numerical computation
 * 
 * KEY CONCEPTS DEMONSTRATED:
 * • Functions as first-class values
 * • Higher-order functions (functions operating on other functions)
 * • Lambda expressions (anonymous functions)
 * • Currying and partial application
 * • Tail recursion optimization
 * • Function composition and abstraction
 * • DRY principle (Don't Repeat Yourself)
 */

import scala.annotation.tailrec

// ============================================================================
// PART 1: THE PROBLEM - Code Duplication
// ============================================================================

// Basic recursive function to sum integers from a to b
// Recursive pattern: base case (empty range) + recursive case (current + rest)
def sumInts(a: Int, b: Int): Int = 
    if (a > b) 0 else a + sumInts(a+1, b)

assert(sumInts(5,3) == 0)              // Empty range test
assert(sumInts(1,10) == 55)            // 1+2+...+10 = 55 (arithmetic series)

// Pure mathematical functions - deterministic, no side effects
def cube(x: Int): Int = x * x * x
def square(x: Int): Int = x * x

// THE PROBLEM: Code duplication violates DRY principle
// Notice the identical recursive structure in these functions:

def sumSquares(a: Int, b: Int): Int = 
    if (a > b) 0 else square(a) + sumSquares(a+1, b)

def sumCubes(a: Int, b: Int): Int = 
    if (a > b) 0 else cube(a) + sumCubes(a+1, b)

assert(sumSquares(1,10) == 385)  // Sum of squares: 1² + 2² + ... + 10²

// Tail-recursive factorial: efficient implementation preventing stack overflow
// Key insight: accumulate result going DOWN the recursion, not coming back UP
def factorial(n: Int): Int = {
    @tailrec  // Compiler verifies and optimizes to iterative loop
    def factorialHelper(n: Int, acc: Int): Int = 
        if (n == 0) acc 
        else factorialHelper(n - 1, n * acc)  // Tail position: last operation
    
    factorialHelper(n, 1)  // Initialize with multiplicative identity
}

// Specific function to sum factorials from a to b
def sumFactorials(a: Int, b: Int): Int = 
    if (a > b) 0 else factorial(a) + sumFactorials(a+1,b)

assert(sumFactorials(1,3) == factorial(1) + factorial(2) + factorial(3))

// ============================================================================
// PART 2: THE SOLUTION - Higher-Order Functions
// ============================================================================

// The breakthrough: parameterize the operation!
// Instead of hardcoding square(), cube(), etc., pass the function as a parameter
def sum(f: Int => Int, a: Int, b: Int): Int = 
    if (a > b) 0
    else f(a) + sum(f, a + 1, b)  // Apply f, then recurse

// Identity function for completeness
def id(x: Int): Int = x 

// Now we can recreate all previous functions using the generic sum!
def hsumInts(a: Int, b: Int) = sum(id, a, b)           // sum with identity
def hsumSquares(a: Int, b: Int) = sum(square, a, b)    // sum with squaring  
def hsumCubes(a: Int, b: Int) = sum(cube, a, b)        // sum with cubing
def hsumFactorials(a: Int, b: Int) = sum(factorial, a, b) // sum with factorial

// Verification: higher-order versions produce identical results
assert(hsumInts(5,3) == 0)                    // Empty range
assert(hsumInts(1,10) == 55)                  // Same as sumInts
assert(hsumSquares(1,10) == 385)              // Same as sumSquares
assert(hsumCubes(2,4) == cube(2) + cube(3) + cube(4))  // Manual verification
assert(hsumFactorials(1,3) == factorial(1) + factorial(2) + factorial(3))

// ============================================================================
// PART 3: ANONYMOUS FUNCTIONS - Inline Function Definitions
// ============================================================================

// Why define and name simple functions? Use lambda expressions instead!
// Syntax: parameter => expression

def ahsumInts(a: Int, b: Int) = sum(x => x, a, b)        // inline identity
def ahsumSquares(a: Int, b: Int) = sum(x => x*x, a, b)   // inline squaring
def ahsumCubes(a: Int, b: Int) = sum(x => x*x*x, a, b)   // inline cubing
def ahsumFactorials(a: Int, b: Int) = sum(x => factorial(x), a, b) // wrap factorial

// Same results, more concise code
assert(ahsumInts(1,10) == 55)
assert(ahsumSquares(1,10) == 385)
assert(ahsumCubes(2,4) == cube(2) + cube(3) + cube(4))
assert(ahsumFactorials(1,3) == factorial(1) + factorial(2) + factorial(3))

// ============================================================================
// PART 4: TAIL RECURSION - Performance Optimization
// ============================================================================

// Problem: Previous sum() builds up stack frames for large ranges
// Solution: Tail-recursive version using accumulator pattern
def sumTailRec(f: Int => Int, a: Int, b: Int): Int = {
    @tailrec
    def loop(current: Int, acc: Int): Int = {
        if (current > b) acc  
        else loop(current + 1, f(current) + acc)  // Tail position
    }
    loop(a, 0)  // Start with additive identity
}

// Tail-recursive versions - same interface, better performance
def ahtsumInts(a: Int, b: Int) = sumTailRec(x => x, a, b)
def ahtsumSquares(a: Int, b: Int) = sumTailRec(x => x*x, a, b)
def ahtsumCubes(a: Int, b: Int) = sumTailRec(x => x*x*x, a, b)
def ahtsumFactorials(a: Int, b: Int) = sumTailRec(x => factorial(x), a, b)

// Performance verified with same correctness
assert(ahtsumInts(1,10) == 55)
assert(ahtsumSquares(1,10) == 385)
assert(ahtsumCubes(2,4) == cube(2) + cube(3) + cube(4))
assert(ahtsumFactorials(1,3) == factorial(1) + factorial(2) + factorial(3))

// CURRYING EXAMPLES
// Currying: transforming function with multiple parameters into sequence of single-parameter functions
// Benefit: enables partial application - can "pre-configure" functions with some arguments

// Manual currying: function that returns another function - demonstrates currying concept
// Type signature: (Int => Int) => ((Int, Int) => Int)
// "Give me a function f, and I'll give you back a sum function specialized for f"
def currySum(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int = {
        if (a > b) 0
        else f(a) + sumF(a+1, b)
    }
    sumF  // Return the specialized sum function
}

assert(currySum(x=>x*x)(1,3) == square(1) + square(2) + square(3))

// Syntactic sugar for currying - Scala's built-in support for curried functions
// Multiple parameter lists automatically enable currying
// Usage: sugarCurrySum(someFunction)(start, end)
def sugarCurrySum(f: Int => Int)(a: Int, b: Int): Int = 
    if (a >b) 0 else f(a) + sugarCurrySum(f)(a+1,b)

assert(sugarCurrySum(x=>x*x)(1,3) == square(1) + square(2) + square(3))

// PRODUCT FUNCTION - GENERALIZATION BEYOND SUMMATION
// Higher-order function for multiplication instead of addition
// Note: base case is 1 (multiplicative identity) vs 0 for sum (additive identity)
def product(f: Int => Int)(a: Int, b: Int): Int = 
    if (a > b) 1 else f(a) * product(f)(a+1, b)  // Base case is 1 (multiplicative identity)

// Factorial using product function
def formFactorial(n: Int): Int = product(x => x)(1, n)
assert(formFactorial(5) == 5 * formFactorial(4))

// ============================================================================
// PART 5: MAP-REDUCE - Ultimate Generalization
// ============================================================================

// Why stop at sum? Let's generalize to ANY binary operation!
// Map-Reduce pattern: transform elements (map), then combine them (reduce)
// 
// Algebraic structure: (Set, operation, identity) forms a monoid
// • Associative operation: (a op b) op c = a op (b op c) 
// • Identity element: identity op a = a op identity = a
//
// Examples:
// • (Int, +, 0) - addition with zero
// • (Int, *, 1) - multiplication with one  
// • (Int, max, Int.MinValue) - maximum with minimum value

def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = 
    if (a > b) zero 
    else combine(f(a), mapReduce(f, combine, zero)(a + 1, b))

// Sum and product are just special cases of mapReduce!
def groupSum(f: Int => Int)(a: Int, b: Int) = mapReduce(f, (x, y) => x + y, 0)(a, b)
def groupProduct(f: Int => Int)(a: Int, b: Int) = mapReduce(f, (x, y) => x * y, 1)(a, b)

// Factorial using the generalized mapReduce approach
def groupFactorial(n: Int) = groupProduct(x=>x)(1,n)
assert(groupFactorial(5) == 120)

// ============================================================================
// PART 6: NUMERICAL COMPUTATION - Fixed-Point Iteration
// ============================================================================

// Mathematical concept: find x where f(x) = x (fixed point of function f)
// Many numerical algorithms can be expressed as fixed-point problems

val tolerance = 0.0001  // Convergence threshold

def abs(x: Double): Double = if x >= 0 then x else -x

// Convergence test using relative error to handle different magnitudes
def isCloseEnough(x: Double, y: Double): Boolean = 
    abs((x - y) / x) < tolerance

// Fixed-point iteration with average damping for stability
def fixedPoint(f: Double => Double)(firstGuess: Double): Double = {
    @tailrec
    def iterate(guess: Double): Double = {
        val next = f(guess)  // Apply damping to prevent oscillation
        if (isCloseEnough(guess, next)) next
        else iterate(next)
    }
    iterate(firstGuess)
}

// Average damping: f(x) becomes (x + f(x))/2 for better convergence
def averageDamp(f: Double => Double)(x: Double): Double = (x + f(x)) / 2

// Square root via fixed-point: sqrt(a) is fixed point of x => a/x
// But x => a/x oscillates, so we use damped version: x => (x + a/x)/2
def sqrt(a: Double): Double = fixedPoint(averageDamp(x => a / x))(1.0)

// Test convergence
println(s"sqrt(16) = ${sqrt(16)}")     // Should be 4.0
println(s"sqrt(2) = ${sqrt(2)}")       // Should be ~1.414
println(s"sqrt(144) = ${sqrt(144)}")   // Should be 12.0







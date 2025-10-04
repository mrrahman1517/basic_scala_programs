/*
 * Higher-Order Functions (HOF) demonstration in Scala
 * 
 * LEARNING PROGRESSION:
 * 1. Start with specific, repetitive functions (sumInts, sumSquares, etc.)
 * 2. Identify common patterns and extract them into higher-order functions
 * 3. Show how HOFs eliminate code duplication and increase abstraction
 * 4. Demonstrate different ways to pass functions (named vs anonymous)
 * 5. Introduce tail recursion for performance optimization
 * 6. Explore currying for partial application
 * 7. Generalize further with map-reduce pattern
 * 8. Apply concepts to numerical computation (fixed-point iteration)
 * 
 * KEY FUNCTIONAL PROGRAMMING CONCEPTS DEMONSTRATED:
 * - Functions as first-class values
 * - Higher-order functions (functions that take/return other functions)
 * - Lambda expressions (anonymous functions)
 * - Currying and partial application
 * - Tail recursion optimization
 * - Function composition and abstraction
 */

// Higher-Order Functions (HOF) demonstration in Scala
// This file shows the evolution from specific functions to generic higher-order functions

import scala.annotation.tailrec

// Basic recursive function to sum integers from a to b
// Pattern: if range empty (a > b) return base case (0)
//          else add current value (a) to sum of rest (a+1 to b)
def sumInts(a: Int, b: Int): Int = 
    if (a >b) 0 else a + sumInts(a+1,b)

assert(sumInts(5,3) == 0)  // Empty range returns 0
assert(sumInts(1,10) == (1+10)*10/2)  // Arithmetic series formula: n(first+last)/2

// Helper functions for mathematical operations
// Notice these are pure functions - no side effects, same input always gives same output
def cube(x:Int): Int = x * x * x

def square(x:Int):Int = x * x

// PROBLEM: Code duplication in the following functions
// All follow same pattern: recursive structure with different operations
// This violates DRY principle (Don't Repeat Yourself)

// Specific function to sum squares from a to b
// Same recursive structure as sumInts, just applies square() to each element
def sumSquares(a: Int, b: Int): Int = 
    if (a > b) 0 else square(a) + sumSquares(a+1,b)

assert(sumSquares(1,10) == 10*(10+1)*(2*10+1)/6)  // Sum of squares formula

// Specific function to sum cubes from a to b
def sumCubes(a: Int, b: Int): Int = 
    if (a > b) 0 else cube(a) + sumCubes(a+1,b)

// Tail-recursive factorial implementation for efficiency
// @tailrec annotation ensures compiler optimization to avoid stack overflow
// Uses accumulator pattern: multiply as we go down, not on the way back up
def factorial(n: Int): Int = {
    @tailrec
    def factorialHelper(n: Int, acc: Int): Int = 
        if (n == 0) acc else factorialHelper(n-1, n * acc)  // acc accumulates result
    
    factorialHelper(n, 1)  // Start with accumulator = 1 (multiplicative identity)
}

// Specific function to sum factorials from a to b
def sumFactorials(a: Int, b: Int): Int = 
    if (a > b) 0 else factorial(a) + sumFactorials(a+1,b)

assert(sumFactorials(1,3) == factorial(1) + factorial(2) + factorial(3))

// HIGHER-ORDER FUNCTION APPROACH
// Generic sum function that takes a function as parameter
// f: Int => Int means f is a function that takes Int and returns Int
// This eliminates code duplication from the specific sum functions above
def sum(f: Int => Int, a: Int, b: Int): Int = 
    if (a > b) 0
    else f(a) + sum(f, a + 1, b)  // Apply f to current value, recurse on rest

// Identity function - returns input unchanged
// Useful for cases where we need a function but want no transformation
def id(x: Int): Int = x 

// SOLUTION: Higher-order versions using the generic sum function with named functions
// Now we can reuse the same sum logic with different operations
// Each function is just sum() composed with a specific mathematical function
def hsumInts(a: Int, b: Int) = sum(id, a, b)        // sum with identity = regular sum
def hsumSquares(a: Int, b: Int) = sum(square, a, b)  // sum with squaring
def hsumCubes(a: Int, b: Int) = sum(cube, a, b)      // sum with cubing
def hsumFactorials(a: Int, b: Int) = sum(factorial, a, b)  // sum with factorial

// VERIFICATION: Test the higher-order versions produce same results as original functions
// This demonstrates that refactoring preserved correctness
assert(hsumInts(5,3) == 0)                    // Edge case: empty range
assert(hsumInts(1,10) == (1+10)*10/2)         // Known formula verification

assert(hsumSquares(1,10) == 10*(10+1)*(2*10+1)/6)  // Sum of squares formula
assert(hsumCubes(2,4) == cube(2) + cube(3) + cube(4))  // Manual calculation check
assert(hsumFactorials(1,3) == factorial(1) + factorial(2) + factorial(3))  // Explicit verification

// ANONYMOUS FUNCTIONS (LAMBDA EXPRESSIONS)
// Alternative approach: define functions inline instead of naming them
// Syntax: x => expression  means "function that takes x and returns expression"
// Benefits: concise, no namespace pollution, clear intent at point of use

def ahsumInts(a: Int, b: Int) = sum(x => x, a, b)  // Lambda equivalent of id function
assert(ahsumInts(1,10) == 55)  // 1+2+...+10 = 55

def ahsumSquares(a: Int, b: Int) = sum(x=> x*x, a, b)  // Lambda equivalent of square function
assert(ahsumSquares(1,10) == 10*(10+1)*(2*10+1)/6)

def ahsumCubes(a: Int, b: Int): Int = sum(x => x*x*x, a, b)  // Lambda equivalent of cube function
assert(ahsumCubes(2,4) == cube(2) + cube(3) + cube(4))

def ahsumFactorials(a: Int, b: Int): Int = sum(x => factorial(x), a, b)  // Lambda wrapping factorial
assert(ahsumFactorials(1,3) == factorial(1) + factorial(2) + factorial(3))

// TAIL-RECURSIVE VERSION FOR BETTER PERFORMANCE
// Generic tail-recursive sum function to avoid stack overflow for large ranges
def sumTailRec(f: Int => Int, a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
        if (a > b) acc  
            else loop(a+1, f(a) + acc)  // Accumulator pattern
    }
    loop(a, 0)
}

// Tail-recursive versions using anonymous functions
def ahtsumInts(a: Int, b: Int) = sumTailRec(x => x, a, b)
assert(ahtsumInts(1,10) == 55)

def ahtsumSquares(a: Int, b: Int) = sumTailRec(x=> x*x, a, b)
assert(ahtsumSquares(1,10) == 10*(10+1)*(2*10+1)/6)

def ahtsumCubes(a: Int, b: Int): Int = sumTailRec(x => x*x*x, a, b)
assert(ahtsumCubes(2,4) == cube(2) + cube(3) + cube(4))

def ahtsumFactorials(a: Int, b: Int): Int = sumTailRec(x => factorial(x), a, b)
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

// MAP-REDUCE PATTERN
// Ultimate generalization: map function f over range, then reduce with combine operation
// Parameters:
//   f: transformation function (Int => Int)
//   combine: reduction function ((Int, Int) => Int) 
//   zero: identity element for the combine operation
// This pattern can express sum, product, min, max, and many other aggregate operations
def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = 
    if (a > b) zero 
    else combine(f(a), mapReduce(f, combine, zero)(a + 1, b))

// Examples of mapReduce specialization:
// Sum: mapReduce(f, (x,y) => x+y, 0)
// Product: mapReduce(f, (x,y) => x*y, 1)
// Max: mapReduce(f, (x,y) => if (x>y) x else y, Int.MinValue)

// Product function implemented using mapReduce
// Shows how specific operations are just mapReduce with different combine functions
def groupProduct(f: Int => Int)(a: Int, b: Int) = mapReduce(f, (x, y) => x * y, 1)(a,b)

// Factorial using the generalized mapReduce approach
def groupFactorial(n: Int) = groupProduct(x=>x)(1,n)
assert(groupFactorial(5) == 120)

// FIXED-POINT COMPUTATION AND NEWTON'S METHOD
// Mathematical concept: finding x where f(x) = x

// Tolerance for numerical precision - determines when we're "close enough"
val tolerance = 0.0001

// Absolute value function - returns positive magnitude of a number
def abs (x: Double): Double = {
    if x >= 0 then x else -x  // Scala 3 syntax for if-then-else
}

// Check if two values are sufficiently close for convergence
// Uses relative error: |x-y|/x to handle different magnitudes
def isCloseEnough(x: Double, y: Double) = 
    abs((x-y) / x) / x < tolerance

// Fixed-point algorithm: repeatedly applies function f until convergence
// A fixed-point of f is a value x such that f(x) = x
// Many mathematical problems can be expressed as fixed-point problems
def fixedPoint(f: Double => Double)(firstGuess: Double) = {
    def iterate(guess: Double): Double = {
        println("guess = " + guess)  // Track convergence progress
        //val next = f(guess)          // Apply function f to current guess
        val next = averageDamp(f)(guess)
        if (isCloseEnough(guess, next)) next  // Check for convergence
        else iterate(next)                    // Continue iteration if not converged
    }
    iterate(firstGuess)  // Start iteration with initial guess
}

// Example: simple fixed-point convergence test
// println(fixedPoint(x => 1 + x / 2)(1))  // Converges to 2

// SQUARE ROOT USING BABYLONIAN METHOD (HERON'S METHOD)
// Mathematical insight: sqrt(x) is fixed-point of y => (y + x/y) / 2
// This uses "average damping" - averaging current guess with x/guess
// Prevents oscillation and ensures convergence
def sqrt(x: Double): Double = fixedPoint(y => (y + x / y) / 2)(1)

// Test: square root of 16 should converge to 4
println(sqrt(16))

// AVERAGE DAMPING TECHNIQUE
// General technique to improve convergence of fixed-point iteration
// Instead of using f(x) directly, use (x + f(x))/2 to prevent oscillation
// This "damps" the changes between iterations for better numerical stability
def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2

// Square root using average damping applied to the simple y => x/y transformation
// Without damping: y => x/y oscillates and doesn't converge
// With damping: y => (y + x/y)/2 converges smoothly to sqrt(x)
def dampedSqrt(x: Double): Double = fixedPoint(y => x/y)(1)  // Uses averageDamp in fixedPoint
println(dampedSqrt(2))    // Should converge to ~1.414
println(dampedSqrt(144))  // Should converge to 12







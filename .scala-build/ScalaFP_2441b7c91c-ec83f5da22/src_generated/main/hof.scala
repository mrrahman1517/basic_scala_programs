

final class hof$_ {
def args = hof_sc.args$
def scriptPath = """/Users/muntasirraihanrahman/Documents/ScalaFP/hof.sc"""
/*<script>*/
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
def cube(x:Int): Int = x * x * x

def square(x:Int):Int = x * x

// Specific function to sum squares from a to b
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
def id(x: Int): Int = x 

// Higher-order versions using the generic sum function with named functions
def hsumInts(a: Int, b: Int) = sum(id, a, b)
def hsumSquares(a: Int, b: Int) = sum(square, a, b)
def hsumCubes(a: Int, b: Int) = sum(cube, a, b)
def hsumFactorials(a: Int, b: Int) = sum(factorial, a, b)

// Test the higher-order versions produce same results
assert(hsumInts(5,3) == 0)
assert(hsumInts(1,10) == (1+10)*10/2)

assert(hsumSquares(1,10) == 10*(10+1)*(2*10+1)/6)
assert(hsumCubes(2,4) == cube(2) + cube(3) + cube(4))
assert(hsumFactorials(1,3) == factorial(1) + factorial(2) + factorial(3))

// ANONYMOUS FUNCTIONS (LAMBDA EXPRESSIONS)
// Same functionality using anonymous functions instead of named functions
def ahsumInts(a: Int, b: Int) = sum(x => x, a, b)  // Lambda equivalent of id function
assert(ahsumInts(1,10) == 55)

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
// Function that returns another function - demonstrates currying concept
def currySum(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int = {
        if (a > b) 0
        else f(a) + sumF(a+1, b)
    }
    sumF
}

assert(currySum(x=>x*x)(1,3) == square(1) + square(2) + square(3))

// Syntactic sugar for currying - more concise syntax
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
// Most general form: applies function f and reduces with combine operation
def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = 
    if (a > b) zero 
    else combine(f(a), mapReduce(f, combine, zero)(a + 1, b))

// Product function implemented using mapReduce
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

def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2

def dampedSqrt(x: Double): Double = fixedPoint(y => x/y)(1)
println(dampedSqrt(2))
println(dampedSqrt(144))







/*</script>*/ /*<generated>*//*</generated>*/
}

object hof_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new hof$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export hof_sc.script as `hof`


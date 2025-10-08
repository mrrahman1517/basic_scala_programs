/*
 * Function Objects in Scala - Comprehensive Demonstration
 * 
 * This worksheet explores Scala's Function traits and demonstrates how to create
 * reusable function objects using the object-oriented approach to functional programming.
 * 
 * Key Concepts Covered:
 * - Function traits (Function1, Function2, Function3, etc.)
 * - The apply method and its special role in Scala
 * - Function objects vs anonymous functions
 * - Type safety in function definitions
 * - Functional programming patterns using objects
 * 
 * Function traits in Scala are part of the standard library and provide a way
 * to treat functions as first-class objects that can be passed around, stored,
 * and manipulated like any other value.
 */

println("=== Function Objects in Scala ===")
println()

/*
 * PART 1: Basic Function Objects with Function2
 * 
 * Function2[A, B, C] represents a function that takes two parameters of types A and B
 * and returns a value of type C. The apply method is the key - it's what gets called
 * when you use the object like a function.
 */

println("--- Part 1: Function2 Examples ---")

/**
 * Multiply - A function object that multiplies two integers
 * 
 * This class extends Function2[Int, Int, Int], meaning:
 * - It takes two Int parameters  
 * - It returns an Int result
 * - It implements the apply method to define the function behavior
 */
class Multiply extends Function2[Int, Int, Int] {
    /**
     * The apply method defines what happens when this object is called like a function
     * @param v1 the first integer operand
     * @param v2 the second integer operand  
     * @return the product of v1 and v2
     */
    def apply(v1: Int, v2: Int): Int = v1 * v2
}

// Create an instance of our function object
val mult = new Multiply()

// Use it like a function - this calls mult.apply(2, 5) behind the scenes
println(s"mult(2, 5) = ${mult(2, 5)}")
println(s"mult(-3, 4) = ${mult(-3, 4)}")
println(s"mult(0, 100) = ${mult(0, 100)}")

/**
 * Add - Another Function2 example for addition
 */
class Add extends Function2[Int, Int, Int] {
    def apply(x: Int, y: Int): Int = x + y
}

val adder = new Add()
println(s"adder(10, 15) = ${adder(10, 15)}")
println(s"adder(-5, 3) = ${adder(-5, 3)}")

/**
 * Power - Function2 for exponentiation
 */
class Power extends Function2[Int, Int, Int] {
    def apply(base: Int, exponent: Int): Int = {
        if (exponent == 0) 1
        else if (exponent > 0) base * apply(base, exponent - 1)
        else throw new IllegalArgumentException("Negative exponents not supported")
    }
}

val power = new Power()
println(s"power(2, 3) = ${power(2, 3)}")
println(s"power(5, 0) = ${power(5, 0)}")
println(s"power(3, 4) = ${power(3, 4)}")
println()

/*
 * PART 2: Function1 Examples (Single Parameter Functions)
 * 
 * Function1[A, B] represents a function that takes one parameter of type A
 * and returns a value of type B. These are very common in functional programming
 * for transformations, predicates, and computations.
 */

println("--- Part 2: Function1 Examples ---")

/**
 * Square - Function1 that squares a number
 */
class Square extends Function1[Int, Int] {
    def apply(x: Int): Int = x * x
}

val square = new Square()
println(s"square(4) = ${square(4)}")
println(s"square(-3) = ${square(-3)}")
println(s"square(0) = ${square(0)}")

/**
 * StringLength - Function1 that computes string length
 */
class StringLength extends Function1[String, Int] {
    def apply(s: String): Int = s.length
}

val strlen = new StringLength()
println(s"strlen(\"hello\") = ${strlen("hello")}")
println(s"strlen(\"\") = ${strlen("")}")
println(s"strlen(\"Scala\") = ${strlen("Scala")}")

/**
 * IsEven - Predicate function that checks if a number is even
 */
class IsEven extends Function1[Int, Boolean] {
    def apply(n: Int): Boolean = n % 2 == 0
}

val isEven = new IsEven()
println(s"isEven(4) = ${isEven(4)}")
println(s"isEven(7) = ${isEven(7)}")
println(s"isEven(0) = ${isEven(0)}")

/**
 * Factorial - Recursive Function1 for computing factorials
 */
class Factorial extends Function1[Int, Int] {
    def apply(n: Int): Int = {
        if (n < 0) throw new IllegalArgumentException("Factorial undefined for negative numbers")
        else if (n <= 1) 1
        else n * apply(n - 1)
    }
}

val factorial = new Factorial()
println(s"factorial(5) = ${factorial(5)}")
println(s"factorial(0) = ${factorial(0)}")
println(s"factorial(3) = ${factorial(3)}")
println()

/*
 * PART 3: Function3 and Higher Arity Functions
 * 
 * Function3[A, B, C, D] takes three parameters of types A, B, C
 * and returns a value of type D. Higher arity functions follow the same pattern.
 */

println("--- Part 3: Function3 and Higher Arity Examples ---")

/**
 * TripleSum - Function3 that adds three numbers
 */
class TripleSum extends Function3[Int, Int, Int, Int] {
    def apply(x: Int, y: Int, z: Int): Int = x + y + z
}

val tripleSum = new TripleSum()
println(s"tripleSum(1, 2, 3) = ${tripleSum(1, 2, 3)}")
println(s"tripleSum(10, -5, 8) = ${tripleSum(10, -5, 8)}")

/**
 * StringConcatenator - Function3 that concatenates three strings with separators
 */
class StringConcatenator extends Function3[String, String, String, String] {
    def apply(first: String, separator: String, second: String): String = {
        first + separator + second
    }
}

val concat = new StringConcatenator()
println(s"concat(\"Hello\", \" \", \"World\") = ${concat("Hello", " ", "World")}")
println(s"concat(\"A\", \"-\", \"B\") = ${concat("A", "-", "B")}")

/**
 * Average - Function3 that computes the average of three numbers
 */
class Average extends Function3[Double, Double, Double, Double] {
    def apply(x: Double, y: Double, z: Double): Double = (x + y + z) / 3.0
}

val average = new Average()
println(s"average(1.0, 2.0, 3.0) = ${average(1.0, 2.0, 3.0)}")
println(s"average(10.5, 20.5, 30.0) = ${average(10.5, 20.5, 30.0)}")
println()

/*
 * PART 4: Functions with Mixed Types
 * 
 * Function objects can work with any types, not just primitives.
 * This demonstrates the flexibility of Scala's type system.
 */

println("--- Part 4: Mixed Type Functions ---")

/**
 * ListToString - Function1 that converts a list to a formatted string
 */
class ListToString extends Function1[List[Int], String] {
    def apply(list: List[Int]): String = list.mkString("[", ", ", "]")
}

val listToString = new ListToString()
println(s"listToString(List(1, 2, 3)) = ${listToString(List(1, 2, 3))}")
println(s"listToString(List()) = ${listToString(List())}")

/**
 * Contains - Function2 that checks if a list contains a specific element
 */
class Contains extends Function2[List[String], String, Boolean] {
    def apply(list: List[String], element: String): Boolean = list.contains(element)
}

val contains = new Contains()
println(s"contains(List(\"a\", \"b\", \"c\"), \"b\") = ${contains(List("a", "b", "c"), "b")}")
println(s"contains(List(\"x\", \"y\"), \"z\") = ${contains(List("x", "y"), "z")}")
println()

/*
 * PART 5: Comprehensive Testing Suite
 * 
 * This section thoroughly tests all the function objects we've created,
 * including edge cases, error conditions, and mathematical properties.
 */

println("--- Part 5: Comprehensive Testing ---")

/**
 * Testing mathematical properties and edge cases
 */
println("=== Mathematical Properties Tests ===")

// Test multiplication properties
println("Multiplication Tests:")
println(s"Commutative: mult(3, 4) == mult(4, 3) → ${mult(3, 4) == mult(4, 3)}")
println(s"Identity: mult(7, 1) == 7 → ${mult(7, 1) == 7}")
println(s"Zero property: mult(99, 0) == 0 → ${mult(99, 0) == 0}")

// Test addition properties  
println("\nAddition Tests:")
println(s"Commutative: adder(5, 8) == adder(8, 5) → ${adder(5, 8) == adder(8, 5)}")
println(s"Identity: adder(42, 0) == 42 → ${adder(42, 0) == 42}")
println(s"Associative check: adder(adder(1, 2), 3) == 6 → ${adder(adder(1, 2), 3) == 6}")

// Test power function properties
println("\nPower Tests:")
println(s"Power of 1: power(5, 1) == 5 → ${power(5, 1) == 5}")
println(s"Power of 0: power(99, 0) == 1 → ${power(99, 0) == 1}")
println(s"Square check: power(4, 2) == square(4) → ${power(4, 2) == square(4)}")

// Test square function properties
println("\nSquare Tests:")
println(s"Positive square: square(5) == 25 → ${square(5) == 25}")
println(s"Negative square: square(-3) == 9 → ${square(-3) == 9}")
println(s"Zero square: square(0) == 0 → ${square(0) == 0}")

// Test factorial properties
println("\nFactorial Tests:")
println(s"Factorial 1: factorial(1) == 1 → ${factorial(1) == 1}")
println(s"Factorial recurrence: factorial(4) == 4 * factorial(3) → ${factorial(4) == 4 * factorial(3)}")

println("\n=== Edge Case and Error Handling Tests ===")

// Test error conditions
println("Error Handling Tests:")

// Test negative factorial
try {
    factorial(-1)
    println("✗ Failed: factorial(-1) should throw exception")
} catch {
    case e: IllegalArgumentException => 
        println(s"✓ Correct: factorial(-1) throws exception: ${e.getMessage}")
}

// Test negative power exponent
try {
    power(2, -1)
    println("✗ Failed: power(2, -1) should throw exception")
} catch {
    case e: IllegalArgumentException => 
        println(s"✓ Correct: power(2, -1) throws exception: ${e.getMessage}")
}

// Test string length with various inputs
println("\nString Function Tests:")
println(s"Empty string length: strlen(\"\") == 0 → ${strlen("") == 0}")
println(s"Single char: strlen(\"a\") == 1 → ${strlen("a") == 1}")
println(s"Unicode string: strlen(\"café\") == 4 → ${strlen("café") == 4}")

// Test predicate functions
println("\nPredicate Function Tests:")
println(s"Even number: isEven(2) == true → ${isEven(2) == true}")
println(s"Odd number: isEven(3) == false → ${isEven(3) == false}")
println(s"Zero is even: isEven(0) == true → ${isEven(0) == true}")
println(s"Negative even: isEven(-4) == true → ${isEven(-4) == true}")
println(s"Negative odd: isEven(-3) == false → ${isEven(-3) == false}")

// Test higher arity functions
println("\nHigher Arity Function Tests:")
println(s"Triple sum positive: tripleSum(1, 2, 3) == 6 → ${tripleSum(1, 2, 3) == 6}")
println(s"Triple sum with negatives: tripleSum(-1, 2, -3) == -2 → ${tripleSum(-1, 2, -3) == -2}")
println(s"Triple sum zeros: tripleSum(0, 0, 0) == 0 → ${tripleSum(0, 0, 0) == 0}")

// Test average function precision
println(s"Average test 1: average(1.0, 2.0, 3.0) ≈ 2.0 → ${math.abs(average(1.0, 2.0, 3.0) - 2.0) < 0.001}")
println(s"Average test 2: average(0.0, 0.0, 0.0) == 0.0 → ${average(0.0, 0.0, 0.0) == 0.0}")

// Test mixed type functions
println("\nMixed Type Function Tests:")
val testList = List(1, 2, 3, 4, 5)
val emptyList = List[Int]()
println(s"List formatting: listToString(List(1,2,3)) contains brackets → ${listToString(List(1, 2, 3)).startsWith("[")}")
println(s"Empty list formatting: listToString(List()) == \"[]\" → ${listToString(emptyList) == "[]"}")

val fruits = List("apple", "banana", "cherry")
println(s"Contains existing: contains(fruits, \"banana\") == true → ${contains(fruits, "banana") == true}")
println(s"Contains missing: contains(fruits, \"grape\") == false → ${contains(fruits, "grape") == false}")
println(s"Contains in empty: contains(List(), \"anything\") == false → ${contains(List[String](), "anything") == false}")

println("\n=== Type Safety Verification ===")
// Demonstrate compile-time type safety
println("Type Safety Examples:")
// These would cause compile errors if uncommented:
// mult("hello", "world")  // Error: String is not Int
// square("text")          // Error: String is not Int  
// isEven(3.14)           // Error: Double is not Int
println("✓ All function objects maintain type safety at compile time")
println()

/*
 * PART 6: Function Objects vs Anonymous Functions
 * 
 * This section compares function objects with Scala's anonymous function literals
 * and lambda expressions to show the different approaches to functional programming.
 */

println("--- Part 6: Function Objects vs Anonymous Functions ---")

println("=== Equivalent Implementations Comparison ===")

// Function object approach (what we've been doing)
val multiplyObj = new Multiply()

// Anonymous function approaches
val multiplyFn1: (Int, Int) => Int = (x, y) => x * y
val multiplyFn2 = (x: Int, y: Int) => x * y
val multiplyFn3: Function2[Int, Int, Int] = _ * _

println("Multiplication Comparisons:")
val testX = 6
val testY = 7
println(s"Function object:     multiplyObj($testX, $testY) = ${multiplyObj(testX, testY)}")
println(s"Anonymous function:  multiplyFn1($testX, $testY) = ${multiplyFn1(testX, testY)}")
println(s"Lambda with types:   multiplyFn2($testX, $testY) = ${multiplyFn2(testX, testY)}")
println(s"Underscore syntax:   multiplyFn3($testX, $testY) = ${multiplyFn3(testX, testY)}")
println(s"All equivalent: ${multiplyObj(testX, testY) == multiplyFn1(testX, testY) && multiplyFn1(testX, testY) == multiplyFn2(testX, testY) && multiplyFn2(testX, testY) == multiplyFn3(testX, testY)}")

println("\nSquare Function Comparisons:")
val squareObj = new Square()
val squareFn1: Int => Int = x => x * x
val squareFn2 = (x: Int) => x * x
val squareFn3: Function1[Int, Int] = x => x * x

val testNum = 8
println(s"Function object:     squareObj($testNum) = ${squareObj(testNum)}")
println(s"Anonymous function:  squareFn1($testNum) = ${squareFn1(testNum)}")
println(s"Lambda with types:   squareFn2($testNum) = ${squareFn2(testNum)}")
println(s"Explicit Function1:  squareFn3($testNum) = ${squareFn3(testNum)}")

println("\nPredicate Function Comparisons:")
val isEvenObj = new IsEven()
val isEvenFn1: Int => Boolean = x => x % 2 == 0
val isEvenFn2 = (x: Int) => x % 2 == 0
val isEvenFn3: Function1[Int, Boolean] = _ % 2 == 0

val testPred = 12
println(s"Function object:     isEvenObj($testPred) = ${isEvenObj(testPred)}")
println(s"Anonymous function:  isEvenFn1($testPred) = ${isEvenFn1(testPred)}")
println(s"Lambda with types:   isEvenFn2($testPred) = ${isEvenFn2(testPred)}")
println(s"Underscore syntax:   isEvenFn3($testPred) = ${isEvenFn3(testPred)}")

println("\n=== Advantages and Trade-offs ===")
println("""
Function Objects (class-based):
+ Reusable and can be instantiated multiple times
+ Can have state and additional methods
+ Clear, explicit type definitions
+ Good for complex logic that benefits from OOP structure
+ Can be extended and inherited
- More verbose syntax
- Requires class definition

Anonymous Functions (lambda expressions):
+ Concise and lightweight syntax
+ Great for simple transformations
+ Excellent for use with higher-order functions (map, filter, etc.)
+ Less boilerplate code
- Cannot easily hold state
- Less reusable across different contexts
- Can be harder to test in isolation
""")

println("=== Usage with Higher-Order Functions ===")

val numbers = List(1, 2, 3, 4, 5)

// Using function objects with higher-order functions
println("Using Function Objects:")
println(s"numbers.map(squareObj) = ${numbers.map(squareObj)}")
println(s"numbers.filter(isEvenObj) = ${numbers.filter(isEvenObj)}")

// Using anonymous functions with higher-order functions
println("\nUsing Anonymous Functions:")
println(s"numbers.map(x => x * x) = ${numbers.map(x => x * x)}")
println(s"numbers.filter(_ % 2 == 0) = ${numbers.filter(_ % 2 == 0)}")

// Both approaches produce identical results
val squaredWithObj = numbers.map(squareObj)
val squaredWithLambda = numbers.map(x => x * x)
println(s"\nResults identical: ${squaredWithObj == squaredWithLambda}")

println("\n=== Performance Considerations ===")
println("""
Performance Notes:
- Anonymous functions are typically compiled to function objects behind the scenes
- For simple operations, performance difference is negligible
- Function objects may have slight overhead from instantiation
- Anonymous functions may be optimized better by the compiler for simple cases
- For computationally intensive operations, measure actual performance
""")

println("=== When to Use Each Approach ===")
println("""
Use Function Objects When:
- You need reusable, stateful functions
- The logic is complex and benefits from OOP structure
- You want to extend or inherit function behavior
- You need multiple related functions in a class hierarchy
- Testing isolation is important

Use Anonymous Functions When:
- You need simple, one-off transformations
- Working with collections and higher-order functions
- The function logic is straightforward
- You want minimal boilerplate code
- The function is used only in one specific context
""")
println()

/*
 * SUMMARY: Function Objects in Scala
 * 
 * This worksheet has demonstrated the comprehensive use of function objects in Scala,
 * covering fundamental concepts of functional programming through object-oriented design.
 */

println("=== FINAL SUMMARY ===")
println("""
🎯 Key Concepts Covered:
✓ Function traits (Function1, Function2, Function3) and their usage
✓ The apply method as the core of function objects
✓ Type safety and compile-time error prevention
✓ Mathematical properties and their validation
✓ Error handling and edge case management
✓ Comparison with anonymous functions and lambda expressions
✓ Integration with higher-order functions
✓ Performance considerations and trade-offs

📊 Function Objects Created:
- Multiply (Function2[Int, Int, Int])
- Add (Function2[Int, Int, Int]) 
- Power (Function2[Int, Int, Int])
- Square (Function1[Int, Int])
- StringLength (Function1[String, Int])
- IsEven (Function1[Int, Boolean])
- Factorial (Function1[Int, Int])
- TripleSum (Function3[Int, Int, Int, Int])
- StringConcatenator (Function3[String, String, String, String])
- Average (Function3[Double, Double, Double, Double])
- ListToString (Function1[List[Int], String])
- Contains (Function2[List[String], String, Boolean])

🧪 Testing Achievements:
✓ Mathematical property verification (commutative, identity, associative)
✓ Edge case handling (zero, negative numbers, empty collections)
✓ Error condition testing with proper exception handling
✓ Type inference validation across different types
✓ Structural equivalence between different implementation approaches
✓ Performance and usage pattern analysis

💡 Design Patterns Demonstrated:
- Function objects as first-class citizens
- Polymorphic function behavior through inheritance
- Type-safe functional programming
- Integration of OOP and FP paradigms
- Reusable function components
- Scalable function composition patterns

This comprehensive exploration shows how Scala bridges object-oriented and
functional programming paradigms, providing multiple approaches to solve
problems while maintaining type safety and code clarity.
""")

println("🚀 Function Objects Workshop Complete!")
println(s"Total demonstrations: ${6} major sections with comprehensive testing")
println("Ready for practical application in larger Scala projects!")
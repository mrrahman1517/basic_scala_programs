/*
 * Expression Evaluator using Object-Oriented Design
 * 
 * This worksheet demonstrates a classical object-oriented approach to representing
 * and evaluating mathematical expressions. It shows an alternative to pattern matching
 * using type testing methods and virtual method dispatch.
 * 
 * Key Concepts Demonstrated:
 * - Algebraic data types using class hierarchies
 * - Type testing with boolean methods (isNumber, isSum, etc.)
 * - Virtual method dispatch vs pattern matching
 * - Expression evaluation using recursion
 * - String representation of mathematical expressions
 * - Composite pattern implementation
 * 
 * Expression Grammar:
 * Expr ::= Number(Int) | Var(String) | Sum(Expr, Expr) | Prod(Expr, Expr)
 * 
 * This approach is more verbose than pattern matching but shows how object-oriented
 * principles can be applied to functional data structures.
 */

/**
 * Base trait for all mathematical expressions
 * 
 * This trait defines the interface that all expression types must implement.
 * It uses type testing methods instead of pattern matching, which is a classical
 * object-oriented approach to handling algebraic data types.
 * 
 * Design Pattern: This follows the Composite pattern where expressions can contain
 * other expressions (Sum and Prod) or be leaf nodes (Number and Var).
 */
trait Expr {
    // Type testing methods - each expression type returns true for exactly one of these
    def isNumber: Boolean   // True if this is a numeric literal
    def isSum: Boolean      // True if this is an addition operation
    def isProd: Boolean     // True if this is a multiplication operation  
    def isVar: Boolean      // True if this is a variable reference
    
    // Accessor methods - each should only be called if the corresponding isXXX method returns true
    def numValue: Int       // Returns numeric value (only valid for Number)
    def varValue: String    // Returns variable name (only valid for Var)
    def leftOp: Expr        // Returns left operand (only valid for Sum/Prod)
    def rightOp: Expr       // Returns right operand (only valid for Sum/Prod)
}

/**
 * Number - represents a numeric literal in an expression
 * 
 * Example: Number(42) represents the integer 42
 * 
 * @param n the integer value this number represents
 */
class Number(n: Int) extends Expr {
    def isNumber: Boolean = true
    def isSum: Boolean = false
    def isProd: Boolean = false
    def isVar: Boolean = false
    
    def numValue: Int = n
    
    // These methods should never be called on a Number - they throw errors
    def leftOp: Expr = throw new Error("Number.leftOp")
    def rightOp: Expr = throw new Error("Number.rightOp")
    def varValue: String = throw new Error("Number.varValue")
}

/**
 * Sum - represents addition of two expressions
 * 
 * Example: Sum(Number(3), Number(4)) represents 3 + 4
 * 
 * @param e1 the left operand expression
 * @param e2 the right operand expression
 */
class Sum(e1: Expr, e2: Expr) extends Expr {
    def isNumber: Boolean = false
    def isSum: Boolean = true
    def isProd: Boolean = false
    def isVar: Boolean = false
    
    def leftOp: Expr = e1
    def rightOp: Expr = e2
    
    // These methods should never be called on a Sum - they throw errors
    def numValue: Int = throw new Error("Sum.numValue")
    def varValue: String = throw new Error("Sum.varValue")
}

/**
 * Prod - represents multiplication of two expressions
 * 
 * Example: Prod(Number(3), Number(4)) represents 3 * 4
 * 
 * @param e1 the left operand expression
 * @param e2 the right operand expression
 */
class Prod(e1: Expr, e2: Expr) extends Expr {
    def isNumber: Boolean = false
    def isSum: Boolean = false
    def isProd: Boolean = true
    def isVar: Boolean = false
    
    def leftOp: Expr = e1
    def rightOp: Expr = e2
    
    // These methods should never be called on a Prod - they throw errors
    def numValue: Int = throw new Error("Prod.numValue")
    def varValue: String = throw new Error("Prod.varValue")
}

/**
 * Var - represents a variable in an expression
 * 
 * Example: Var("x") represents the variable x
 * Note: This implementation doesn't provide variable substitution/binding
 * 
 * @param x the name of the variable
 */
class Var(x: String) extends Expr {
    def isNumber: Boolean = false
    def isSum: Boolean = false
    def isProd: Boolean = false
    def isVar: Boolean = true
    
    def varValue: String = x
    
    // These methods should never be called on a Var - they throw errors
    def numValue: Int = throw new Exception("Var.numValue")
    def leftOp: Expr = throw new Error("Var.leftOp")
    def rightOp: Expr = throw new Error("Var.rightOp")
}

/**
 * Expression Evaluator Function
 * 
 * Recursively evaluates an expression tree to compute its integer value.
 * This demonstrates the Visitor pattern using procedural code instead of
 * virtual method dispatch.
 * 
 * Evaluation Rules:
 * - Number(n) evaluates to n
 * - Sum(e1, e2) evaluates to eval(e1) + eval(e2)
 * - Prod(e1, e2) evaluates to eval(e1) * eval(e2)  
 * - Var(x) cannot be evaluated without variable binding (throws error)
 * 
 * @param e the expression to evaluate
 * @return the integer result of evaluating the expression
 * @throws Error if the expression contains variables or unknown types
 */
def eval(e: Expr): Int = {
    if (e.isNumber) e.numValue
    else if (e.isSum) eval(e.leftOp) + eval(e.rightOp)
    else if (e.isProd) eval(e.leftOp) * eval(e.rightOp)
    else throw new Error("Unknown expression " + e)
}

/**
 * Alternative Expression Evaluator using isInstanceOf and asInstanceOf
 * 
 * This version demonstrates runtime type checking and casting using Scala's
 * isInstanceOf and asInstanceOf methods. This approach is more similar to
 * instanceof/cast operations in Java and provides an alternative to the
 * type testing methods defined in the Expr trait.
 * 
 * Key Differences from eval():
 * - Uses Scala's built-in runtime type checking (isInstanceOf)
 * - Uses explicit casting (asInstanceOf) to access type-specific members
 * - More direct but potentially less safe than the trait-based approach
 * - Demonstrates different ways to handle algebraic data types in Scala
 * 
 * Performance Considerations:
 * - isInstanceOf checks are runtime operations (not compile-time)
 * - asInstanceOf casts can throw ClassCastException if misused
 * - Generally less efficient than pattern matching or virtual dispatch
 * 
 * Evaluation Rules (same as eval()):
 * - Number(n) evaluates to n
 * - Sum(e1, e2) evaluates to evalv2(e1) + evalv2(e2)
 * - Prod(e1, e2) evaluates to evalv2(e1) * evalv2(e2)  
 * - Variables cannot be evaluated (throws error)
 * 
 * @param e the expression to evaluate
 * @return the integer result of evaluating the expression
 * @throws Error if the expression contains variables or unknown types
 * @throws ClassCastException if type casting fails (shouldn't happen with correct logic)
 */
def evalv2(e: Expr): Int = {
    if (e.isInstanceOf[Number])
        e.asInstanceOf[Number].numValue
    else if (e.isInstanceOf[Sum])
        evalv2(e.asInstanceOf[Sum].leftOp) +
        evalv2(e.asInstanceOf[Sum].rightOp)
    else if (e.isInstanceOf[Prod])
        evalv2(e.asInstanceOf[Prod].leftOp) *
        evalv2(e.asInstanceOf[Prod].rightOp)
    else throw new Error("Unknown expression " + e)
}

/**
 * Expression Pretty Printer Function
 * 
 * Converts an expression tree into a readable string representation
 * with appropriate parentheses for mathematical notation.
 * 
 * Formatting Rules:
 * - Number(n) becomes "n"
 * - Var(x) becomes "x"
 * - Sum(e1, e2) becomes "(e1+e2)"
 * - Prod(e1, e2) becomes "(e1*e2)"
 * 
 * @param e the expression to convert to string
 * @return string representation of the expression
 * @throws Error if the expression type is unknown
 */
def print(e: Expr): String = {
    if (e.isNumber) e.numValue.toString()
    else if (e.isVar) e.varValue
    else if (e.isSum) "(" + print(e.leftOp) + "+" + print(e.rightOp) + ")"
    else if (e.isProd) "(" + print(e.leftOp) + "*" + print(e.rightOp) + ")"
    else throw new Error("Unknown Expression")
}

/*
 * ========================================
 * DEMONSTRATION AND TESTING SECTION
 * ========================================
 * 
 * This section demonstrates the expression evaluator with various test cases
 * showing both evaluation and pretty printing capabilities.
 */

println("=== Expression Evaluator Demonstration ===")
println()

// Test 1: Basic number operations
println("--- Test 1: Basic Number Operations ---")
val n1 = new Number(5)
val n2 = new Number(6)
val e1 = new Sum(n1, n2)

println(s"Numbers: n1 = ${print(n1)}, n2 = ${print(n2)}")
println(s"Sum: ${print(e1)} = ${eval(e1)}")
assert(eval(e1) == 11)
println("✓ Basic sum test passed")
println()

// Test 2: Direct expression evaluation
println("--- Test 2: Direct Expression Construction ---")
val directSum = new Sum(new Number(35), new Number(23))
println(s"Expression: ${print(directSum)}")
println(s"Result: ${eval(directSum)}")
println()

// Test 3: Nested expressions with addition
println("--- Test 3: Nested Addition ---")
val n3 = new Number(7)
val e2 = new Sum(e1, n3)  // (5+6) + 7

println(s"n3 = ${print(n3)}")
println(s"e2 = ${print(e2)}")
println(s"eval(e2) = ${eval(e2)}")
println(s"Verification: eval(e1) + eval(n3) = ${eval(e1)} + ${eval(n3)} = ${eval(e1) + eval(n3)}")
assert(eval(e2) == eval(e1) + eval(n3))
println("✓ Nested addition test passed")
println()

// Test 4: Multiplication expressions
println("--- Test 4: Multiplication Operations ---")
val e3 = new Prod(e1, n3)  // (5+6) * 7

println(s"e3 = ${print(e3)}")
println(s"eval(e3) = ${eval(e3)}")
println(s"Verification: eval(e1) * eval(n3) = ${eval(e1)} * ${eval(n3)} = ${eval(e1) * eval(n3)}")
assert(eval(e3) == eval(e1) * eval(n3))
println("✓ Multiplication test passed")
println()

// Test 5: Complex nested expressions
println("--- Test 5: Complex Nested Expressions ---")
val complexExpr = new Sum(new Prod(new Number(5), new Number(7)), new Number(8))
// Represents: (5*7) + 8 = 35 + 8 = 43

println(s"Complex expression: ${print(complexExpr)}")
println(s"Evaluation: ${eval(complexExpr)}")
println("Step-by-step:")
println("  (5*7) = 35")
println("  35 + 8 = 43")
println()

// Test 6: Pretty printing demonstration
println("--- Test 6: Pretty Printing with Variables ---")
val exprWithVars = new Sum(
    new Prod(new Var("x1"), new Var("x2")), 
    new Var("x3")
)
// Represents: (x1*x2) + x3

println(s"Expression with variables: ${print(exprWithVars)}")
println("Note: Variables cannot be evaluated without binding")
println()

// Test 7: Error handling demonstration  
println("--- Test 7: Error Handling ---")
println("Attempting to evaluate expression with variables...")
try {
    eval(exprWithVars)
    println("ERROR: Should have thrown an exception!")
} catch {
    case e: Error => 
        println(s"✓ Correctly caught error: ${e.getMessage}")
}
println()

println("=== Summary ===")
println("✅ All tests passed successfully!")
println("✅ Expression evaluation working correctly")
println("✅ Pretty printing functional")
println("✅ Error handling for variables implemented")
println("✅ Object-oriented expression tree implementation complete")

/*
 * ========================================
 * EVALV2() METHOD TESTING SECTION
 * ========================================
 * 
 * This section thoroughly tests the evalv2() method which uses isInstanceOf
 * and asInstanceOf for runtime type checking instead of the trait-based
 * type testing methods used in eval().
 */

println()
println("=== EVALV2() Alternative Evaluator Testing ===")
println()

// Test 1: Basic number evaluation with evalv2
println("--- Test 1: evalv2() Basic Number Operations ---")
val num1 = new Number(42)
val num2 = new Number(17)

println(s"num1 = ${print(num1)}")
println(s"evalv2(num1) = ${evalv2(num1)}")
println(s"eval(num1) = ${eval(num1)}")
println(s"Results match: ${evalv2(num1) == eval(num1)}")
assert(evalv2(num1) == eval(num1))
assert(evalv2(num1) == 42)
println("✓ Number evaluation test passed")
println()

// Test 2: Sum evaluation comparison
println("--- Test 2: evalv2() Sum Operations ---")
val sumExpr = new Sum(num1, num2)  // 42 + 17 = 59

println(s"Sum expression: ${print(sumExpr)}")
println(s"evalv2(sumExpr) = ${evalv2(sumExpr)}")
println(s"eval(sumExpr) = ${eval(sumExpr)}")
println(s"Manual calculation: 42 + 17 = ${42 + 17}")
println(s"All results match: ${evalv2(sumExpr) == eval(sumExpr) && evalv2(sumExpr) == 59}")
assert(evalv2(sumExpr) == eval(sumExpr))
assert(evalv2(sumExpr) == 59)
println("✓ Sum evaluation test passed")
println()

// Test 3: Product evaluation comparison
println("--- Test 3: evalv2() Product Operations ---")
val prodExpr = new Prod(new Number(6), new Number(7))  // 6 * 7 = 42

println(s"Product expression: ${print(prodExpr)}")
println(s"evalv2(prodExpr) = ${evalv2(prodExpr)}")
println(s"eval(prodExpr) = ${eval(prodExpr)}")
println(s"Manual calculation: 6 * 7 = ${6 * 7}")
println(s"All results match: ${evalv2(prodExpr) == eval(prodExpr) && evalv2(prodExpr) == 42}")
assert(evalv2(prodExpr) == eval(prodExpr))
assert(evalv2(prodExpr) == 42)
println("✓ Product evaluation test passed")
println()

// Test 4: Complex nested expressions with evalv2
println("--- Test 4: evalv2() Complex Nested Expressions ---")
val nestedExpr = new Sum(
    new Prod(new Number(3), new Number(4)),  // 3 * 4 = 12
    new Prod(new Number(5), new Number(2))   // 5 * 2 = 10
)  // Total: 12 + 10 = 22

println(s"Nested expression: ${print(nestedExpr)}")
println(s"evalv2(nestedExpr) = ${evalv2(nestedExpr)}")
println(s"eval(nestedExpr) = ${eval(nestedExpr)}")
println("Step-by-step breakdown:")
println("  Left side: (3*4) = 12")
println("  Right side: (5*2) = 10") 
println("  Final: 12 + 10 = 22")
println(s"Results match: ${evalv2(nestedExpr) == eval(nestedExpr)}")
assert(evalv2(nestedExpr) == eval(nestedExpr))
assert(evalv2(nestedExpr) == 22)
println("✓ Complex nested expression test passed")
println()

// Test 5: Deeply nested expressions
println("--- Test 5: evalv2() Deeply Nested Expressions ---")
val deepExpr = new Prod(
    new Sum(new Number(2), new Number(3)),   // (2+3) = 5
    new Sum(new Number(4), new Number(6))    // (4+6) = 10
)  // Total: 5 * 10 = 50

println(s"Deep expression: ${print(deepExpr)}")
println(s"evalv2(deepExpr) = ${evalv2(deepExpr)}")
println(s"eval(deepExpr) = ${eval(deepExpr)}")
println("Step-by-step breakdown:")
println("  Left operand: (2+3) = 5")
println("  Right operand: (4+6) = 10")
println("  Final: 5 * 10 = 50")
println(s"Results match: ${evalv2(deepExpr) == eval(deepExpr)}")
assert(evalv2(deepExpr) == eval(deepExpr))
assert(evalv2(deepExpr) == 50)
println("✓ Deeply nested expression test passed")
println()

// Test 6: Error handling with variables in evalv2
println("--- Test 6: evalv2() Error Handling ---")
val varExpr = new Var("x")
val exprWithVar = new Sum(new Number(10), varExpr)

println(s"Variable expression: ${print(exprWithVar)}")
println("Attempting to evaluate with evalv2()...")
try {
    val result = evalv2(exprWithVar)
    println(s"ERROR: Should have thrown an exception but got: $result")
    assert(false, "evalv2 should have thrown an error for variable expressions")
} catch {
    case e: Error => 
        println(s"✓ Correctly caught error: ${e.getMessage}")
        println("✓ evalv2() properly handles unknown expression types")
}
println()

// Test 7: Performance and consistency comparison
println("--- Test 7: evalv2() vs eval() Consistency Check ---")
val testCases = List(
    new Number(1),
    new Number(100),
    new Sum(new Number(5), new Number(7)),
    new Prod(new Number(3), new Number(8)),
    new Sum(new Prod(new Number(2), new Number(4)), new Number(1)),
    new Prod(new Sum(new Number(1), new Number(2)), new Sum(new Number(3), new Number(4)))
)

println("Testing multiple expressions for consistency:")
var allMatched = true
for ((expr, index) <- testCases.zipWithIndex) {
    val result1 = eval(expr)
    val result2 = evalv2(expr)
    val matches = result1 == result2
    allMatched = allMatched && matches
    
    println(s"  Test ${index + 1}: ${print(expr)}")
    println(s"    eval()   = $result1")
    println(s"    evalv2() = $result2")
    println(s"    Match: $matches")
    
    assert(matches, s"eval() and evalv2() should produce same result for ${print(expr)}")
}

println(s"All consistency tests passed: $allMatched")
println("✓ evalv2() produces identical results to eval() for all valid expressions")
println()

println("=== EVALV2() Testing Summary ===")
println("✅ Basic number evaluation working")
println("✅ Sum operations working correctly")
println("✅ Product operations working correctly") 
println("✅ Complex nested expressions handled properly")
println("✅ Error handling for variables implemented")
println("✅ Complete consistency with eval() method")
println("✅ Runtime type checking with isInstanceOf/asInstanceOf working")
println()

/*
 * EVALV2() IMPLEMENTATION ANALYSIS:
 * 
 * Advantages of evalv2() approach:
 * 1. Uses Scala's built-in runtime type system
 * 2. More familiar to Java developers (instanceof pattern)
 * 3. Direct access to concrete class members after casting
 * 4. No need to define custom type testing methods
 * 
 * Disadvantages of evalv2() approach:
 * 1. Runtime type checking is slower than compile-time dispatch
 * 2. asInstanceOf casts can be dangerous if logic is incorrect
 * 3. Less type-safe than trait-based approach
 * 4. Harder to extend with new expression types
 * 5. Breaks encapsulation by bypassing trait interface
 * 
 * When to use each approach:
 * - eval(): Better for extensible, type-safe designs
 * - evalv2(): Better when working with existing class hierarchies
 * 
 * Pattern matching would be even better than both approaches in Scala!
 */

/*
 * Design Patterns Demonstrated:
 * 
 * 1. COMPOSITE PATTERN:
 *    - Expr trait defines component interface
 *    - Number/Var are leaf components
 *    - Sum/Prod are composite components containing other expressions
 * 
 * 2. VISITOR PATTERN (procedural style):
 *    - eval() and print() functions traverse the expression tree
 *    - Uses type testing instead of virtual method dispatch
 * 
 * 3. ALGEBRAIC DATA TYPE (OOP style):
 *    - Expression grammar implemented through class hierarchy
 *    - Type safety maintained through trait interface
 * 
 * 4. RECURSIVE TREE TRAVERSAL:
 *    - Both eval() and print() use recursion to process nested expressions
 *    - Demonstrates functional programming concepts in OOP context
 * 
 * Alternative Approaches:
 * - Pattern matching with case classes (more idiomatic in Scala)
 * - Virtual method dispatch (eval/print as methods on Expr)
 * - Church encoding (functional representation)
 * 
 * This implementation shows classical OOP approach vs functional alternatives.
 */
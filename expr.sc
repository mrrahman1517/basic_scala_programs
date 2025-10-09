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
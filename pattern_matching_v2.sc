/*
 * Expression Evaluator using Pattern Matching and Case Classes
 * 
 * This worksheet demonstrates the idiomatic Scala approach to representing
 * and evaluating mathematical expressions using case classes and pattern matching.
 * This is a functional programming approach that contrasts with the object-oriented
 * approach shown in expr.sc.
 * 
 * Key Concepts Demonstrated:
 * - Case classes for algebraic data types
 * - Pattern matching with exhaustive case analysis
 * - Functional approach to expression evaluation
 * - Automatic constructor parameter extraction in patterns
 * - Compile-time guarantees for pattern completeness
 * 
 * Expression Grammar:
 * Expr ::= Number(Int) | Var(String) | Sum(Expr, Expr) | Prod(Expr, Expr)
 * 
 * Advantages over OOP approach:
 * - More concise and readable code
 * - Compile-time pattern exhaustiveness checking
 * - No need for manual type testing methods
 * - Automatic extraction of constructor parameters
 * - Better performance (no runtime type checking)
 */

/**
 * Base trait for all mathematical expressions
 * 
 * This trait serves as the common type for all expression variants.
 * Unlike the OOP approach in expr.sc, this trait is minimal and doesn't
 * define type testing methods - pattern matching handles type discrimination.
 */
trait Expr {
    def eval: Int = this match {
        case Number(n) => n
        case Sum(e1, e2) => e1.eval + e2.eval
        case Prod(e1, e2) => e1.eval * e2.eval
        case Var(_) => throw new Error("Cannot evaluate variable without substitution")
    }
    
    /**
     * Basic show method with full parentheses (always safe but verbose)
     */
    def show: String = this match {
        case Number(n) => n.toString()
        case Sum(e1, e2) => "(" + e1.show + "+" + e2.show + ")"
        case Prod(e1, e2) => "(" + e1.show + "*" + e2.show + ")"
        case Var(x) => x 
    }
    
    /**
     * Precedence-aware show method that minimizes parentheses
     * 
     * Operator precedence (higher number = higher precedence):
     * - Multiplication (*): precedence 2
     * - Addition (+): precedence 1
     * - Numbers and Variables: precedence 3 (highest, never need parentheses)
     * 
     * Parentheses are only added when:
     * 1. A lower precedence operation is used as operand to higher precedence operation
     * 2. Left-associativity requires disambiguation
     */
    def showSmart: String = showWithPrecedence(0)
    
    /**
     * Helper method for precedence-aware formatting
     * @param parentPrecedence the precedence of the parent operation
     * @return formatted string with minimal parentheses
     */
    def showWithPrecedence(parentPrecedence: Int): String = this match {
        case Number(n) => n.toString()
        case Var(x) => x
        case Sum(e1, e2) => 
            val precedence = 1  // Addition has precedence 1
            val result = e1.showWithPrecedence(precedence) + "+" + e2.showWithPrecedence(precedence)
            if (parentPrecedence > precedence) "(" + result + ")" else result
        case Prod(e1, e2) => 
            val precedence = 2  // Multiplication has precedence 2
            val result = e1.showWithPrecedence(precedence) + "*" + e2.showWithPrecedence(precedence)
            if (parentPrecedence > precedence) "(" + result + ")" else result
    }
}

/**
 * Number - represents a numeric literal in an expression
 * 
 * Case class automatically provides:
 * - Constructor parameter as public field
 * - Pattern matching support
 * - Structural equality (two Number(5) instances are equal)
 * - toString, hashCode, and copy methods
 * 
 * Example: Number(42) represents the integer 42
 * 
 * @param n the integer value this number represents
 */
case class Number(n: Int) extends Expr 

/**
 * Sum - represents addition of two expressions
 * 
 * Case class benefits:
 * - Automatic extraction in pattern matching: Sum(e1, e2) => ...
 * - Immutable by default
 * - Structural equality based on operands
 * 
 * Example: Sum(Number(3), Number(4)) represents 3 + 4
 * 
 * @param e1 the left operand expression
 * @param e2 the right operand expression
 */
case class Sum(e1: Expr, e2: Expr) extends Expr 

/**
 * Prod - represents multiplication of two expressions
 * 
 * Similar to Sum but for multiplication operations.
 * Pattern matching can distinguish between Sum and Prod automatically.
 * 
 * Example: Prod(Number(3), Number(4)) represents 3 * 4
 * 
 * @param e1 the left operand expression
 * @param e2 the right operand expression
 */
case class Prod(e1: Expr, e2: Expr) extends Expr 

/**
 * Var - represents a variable in an expression
 * 
 * Variables cannot be evaluated without variable binding/substitution.
 * Used primarily for symbolic representation of expressions.
 * 
 * Example: Var("x") represents the variable x
 * 
 * @param x the name of the variable
 */
case class Var(x: String) extends Expr 

/**
 * Expression Evaluator using Pattern Matching
 * 
 * This function demonstrates Scala's pattern matching capabilities for
 * algebraic data types. Each case automatically extracts constructor
 * parameters, eliminating the need for manual type testing and casting.
 * 
 * Pattern Matching Benefits:
 * - Exhaustiveness checking at compile time
 * - Automatic parameter extraction
 * - Clean, readable syntax
 * - No runtime type checking overhead
 * - Compiler warnings for missing cases
 * 
 * Evaluation Rules:
 * - Number(n) evaluates to n
 * - Sum(e1, e2) evaluates to eval(e1) + eval(e2)
 * - Prod(e1, e2) evaluates to eval(e1) * eval(e2)
 * - Var(x) is not handled (would cause MatchError)
 * 
 * Note: This implementation doesn't handle Var cases, so expressions
 * with variables will throw a MatchError at runtime.
 * 
 * @param e the expression to evaluate
 * @return the integer result of evaluating the expression
 * @throws MatchError if the expression contains variables
 */
def eval(e: Expr): Int = e match {
    case Number(n) => n                      // Extract n from Number and return it
    case Sum(e1, e2) => eval(e1) + eval(e2)  // Extract operands, evaluate recursively, add
    case Prod(e1, e2) => eval(e1) * eval(e2) // Extract operands, evaluate recursively, multiply
    // Note: Var case is missing - will throw MatchError for variables
}

/**
 * Expression Pretty Printer using Pattern Matching (with full parentheses)
 * 
 * Converts expression trees into readable string representations with
 * appropriate mathematical notation and parentheses.
 * 
 * This function demonstrates complete pattern matching with all cases
 * handled, including variables. The compiler can verify exhaustiveness.
 * 
 * Formatting Rules:
 * - Number(n) becomes "n"
 * - Var(x) becomes "x"
 * - Sum(e1, e2) becomes "(e1+e2)"
 * - Prod(e1, e2) becomes "(e1*e2)"
 * 
 * Note: This version always adds parentheses for safety but can be verbose.
 * See showSmart() for precedence-aware formatting.
 * 
 * @param e the expression to convert to string
 * @return string representation of the expression with full parentheses
 */
def show(e: Expr): String = e match {
    case Number(n) => n.toString()                          // Convert number to string
    case Sum(e1, e2) => "(" + show(e1) + "+" + show(e2) + ")" // Recursive formatting for sum
    case Prod(e1, e2) => "(" + show(e1) + "*" + show(e2) + ")" // Recursive formatting for product
    case Var(x) => x                                        // Variables display as their name
}

/**
 * Smart Expression Pretty Printer with Precedence Awareness
 * 
 * This function minimizes parentheses by respecting operator precedence:
 * - Multiplication (*) has higher precedence than addition (+)
 * - Numbers and variables never need parentheses
 * - Parentheses are only added when necessary for correct evaluation
 * 
 * Examples of precedence-aware formatting:
 * - "2*x+y" instead of "(2*x)+y" 
 * - "x+y*z" instead of "x+(y*z)"
 * - "(x+y)*z" when parentheses are actually needed
 * 
 * @param e the expression to convert to string
 * @return string representation with minimal necessary parentheses
 */
def showSmart(e: Expr): String = showSmartWithPrecedence(e, 0)

/**
 * Helper function for precedence-aware formatting
 * 
 * @param e the expression to format
 * @param parentPrecedence the precedence of the parent operation
 * @return formatted string with appropriate parentheses
 */
def showSmartWithPrecedence(e: Expr, parentPrecedence: Int): String = e match {
    case Number(n) => n.toString()
    case Var(x) => x
    case Sum(e1, e2) => 
        val precedence = 1  // Addition has precedence 1
        val result = showSmartWithPrecedence(e1, precedence) + "+" + 
                    showSmartWithPrecedence(e2, precedence)
        if (parentPrecedence > precedence) "(" + result + ")" else result
    case Prod(e1, e2) => 
        val precedence = 2  // Multiplication has precedence 2  
        val result = showSmartWithPrecedence(e1, precedence) + "*" + 
                    showSmartWithPrecedence(e2, precedence)
        if (parentPrecedence > precedence) "(" + result + ")" else result
}

/*
 * ========================================
 * DEMONSTRATION AND TESTING SECTION
 * ========================================
 * 
 * This section demonstrates pattern matching expression evaluation
 * with various test cases showing algebraic identity verification.
 */

println("=== Pattern Matching Expression Evaluator Demo ===")
println()

// Test data: Create basic number expressions
println("--- Creating Basic Expressions ---")
val n1 = Number(5)
val n2 = Number(6) 
val n3 = Number(7)

println(s"n1 = ${show(n1)}")
println(s"n2 = ${show(n2)}")
println(s"n3 = ${show(n3)}")
println()

// Test composite expressions
println("--- Building Composite Expressions ---")
val e1 = Sum(n1, n2)        // (5+6)
val e2 = Prod(e1, n3)       // ((5+6)*7)
val e3 = Prod(n1, n3)       // (5*7)
val e4 = Prod(n2, n3)       // (6*7)
val e5 = Sum(e3, e4)        // ((5*7)+(6*7))

println(s"e1 = Sum(n1, n2) = ${show(e1)}")
println(s"e2 = Prod(e1, n3) = ${show(e2)}")
println(s"e3 = Prod(n1, n3) = ${show(e3)}")
println(s"e4 = Prod(n2, n3) = ${show(e4)}")
println(s"e5 = Sum(e3, e4) = ${show(e5)}")
println()

// Demonstrate distributive property: (a+b)*c = (a*c)+(b*c)
println("--- Algebraic Identity Verification ---")
println("Testing distributive property: (a+b)*c = (a*c)+(b*c)")
println(s"Left side:  ${show(e2)} = ${eval(e2)}")
println(s"Right side: ${show(e5)} = ${eval(e5)}")
println(s"Identity holds: ${eval(e2) == eval(e5)}")

// Verify the assertion
assert(eval(e2) == eval(e5), "Distributive property should hold")
println("✓ Distributive property verified using pattern matching!")
println()

// Test with variables (symbolic expressions)
println("--- Symbolic Expressions with Variables ---")
val v1 = Var("x1")
val v2 = Var("x2")
val v3 = Var("x3")

println(s"Variables: v1=${show(v1)}, v2=${show(v2)}, v3=${show(v3)}")

val e6 = Sum(v1, v2)        // (x1+x2)
val e7 = Prod(e6, v3)       // ((x1+x2)*x3)
val e8 = Prod(v1, v3)       // (x1*x3)
val e9 = Prod(v2, v3)       // (x2*x3)
val e10 = Sum(e8, e9)       // ((x1*x3)+(x2*x3))

println(s"e6 = Sum(v1, v2) = ${show(e6)}")
println(s"e7 = Prod(e6, v3) = ${show(e7)}")
println(s"e8 = Prod(v1, v3) = ${show(e8)}")
println(s"e9 = Prod(v2, v3) = ${show(e9)}")
println(s"e10 = Sum(e8, e9) = ${show(e10)}")
println()

println("--- Symbolic Distributive Property ---")
print("Symbolic identity: " + show(e7) + " == ")
println(show(e10))
println("This shows: (x1+x2)*x3 == (x1*x3)+(x2*x3)")
println("✓ Pattern matching enables clean symbolic manipulation!")
println()

// Note about evaluation limitations
println("--- Evaluation Limitations ---")
println("Note: Variables cannot be evaluated without substitution")
println("Attempting to eval() expressions with variables would throw MatchError")
println("This demonstrates the importance of complete pattern matching")

println()
println("=== Precedence-Aware Pretty Printing ===")
println("Demonstrating the difference between full parentheses vs smart formatting:")
println()

// Create test expressions to show precedence handling
val expr1 = Sum(Prod(Number(2), Var("x")), Var("y"))           // 2*x + y
val expr2 = Prod(Sum(Number(2), Var("x")), Var("y"))           // (2+x)*y  
val expr3 = Sum(Var("a"), Prod(Var("b"), Var("c")))           // a + b*c
val expr4 = Prod(Var("a"), Sum(Var("b"), Var("c")))           // a*(b+c)
val expr5 = Sum(Prod(Number(3), Number(4)), Prod(Number(5), Number(6))) // 3*4 + 5*6

println("--- Comparison: Full Parentheses vs Smart Formatting ---")
val testExprs = List(
    ("2*x + y", expr1),
    ("(2+x)*y", expr2), 
    ("a + b*c", expr3),
    ("a*(b+c)", expr4),
    ("3*4 + 5*6", expr5)
)

for ((description, expr) <- testExprs) {
    println(s"Expression: $description")
    println(s"  Full parentheses: ${show(expr)}")
    println(s"  Smart formatting: ${showSmart(expr)}")
    println(s"  Method call:      ${expr.showSmart}")
    println()
}

println("--- Benefits of Smart Formatting ---")
println("✅ More natural mathematical notation")
println("✅ Follows standard operator precedence rules")
println("✅ Reduces visual clutter while maintaining correctness")
println("✅ Easier to read complex expressions")
println("✅ Matches how mathematicians write expressions")

println()
println("--- Method vs Function Call Demonstration ---")
val exampleExpr = Sum(Prod(Number(2), Var("x")), Var("y"))
println(s"Full parentheses - Function: ${show(exampleExpr)}")
println(s"Full parentheses - Method:   ${exampleExpr.show}")
println(s"Smart formatting - Function: ${showSmart(exampleExpr)}")
println(s"Smart formatting - Method:   ${exampleExpr.showSmart}")
println("Both method calls and function calls work for pattern matching!")

println()
println("=== Pattern Matching vs OOP Comparison ===")
println("✅ Pattern matching approach benefits:")
println("  • More concise and readable code")
println("  • Compile-time exhaustiveness checking")
println("  • Automatic parameter extraction")
println("  • Better performance (no runtime type checking)")
println("  • Functional programming style")
println()
println("✅ OOP approach benefits (from expr.sc):")
println("  • Easier to add new operations without modifying existing code")
println("  • Encapsulation of type-specific behavior")
println("  • More familiar to OOP developers")
println("  • Better for extensible designs")

/*
 * PATTERN MATCHING DESIGN ANALYSIS:
 * 
 * Case Classes vs Regular Classes:
 * - Case classes provide automatic pattern matching support
 * - Structural equality instead of reference equality
 * - Immutable by default (constructor parameters are vals)
 * - Automatic toString, equals, hashCode, and copy methods
 * 
 * Pattern Matching vs Type Testing:
 * - Pattern matching: Compile-time verification, parameter extraction
 * - Type testing: Runtime checks, manual casting required
 * - Pattern matching is generally preferred in functional programming
 * 
 * Exhaustiveness Checking:
 * - Compiler warns about missing cases in pattern matching
 * - Helps prevent runtime errors from unhandled cases
 * - Can be disabled with catch-all case: case _ => ...
 * 
 * Performance Considerations:
 * - Pattern matching compiles to efficient bytecode
 * - No runtime type checking overhead
 * - Case class construction is lightweight
 * 
 * When to use each approach:
 * - Pattern matching: When you have a closed set of types and many operations
 * - OOP with virtual dispatch: When you have many types and few operations
 * - This is known as the "Expression Problem" in programming language design
 */
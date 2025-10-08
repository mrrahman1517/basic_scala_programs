package idealized.scala

/**
 * Pure Boolean Implementation using Church Encoding
 * 
 * This implementation represents boolean values as functions that choose 
 * between two alternatives, following Church encoding principles.
 * 
 * Key concepts:
 * - Boolean values are functions, not primitive data types
 * - ifThenElse is the fundamental operation that defines boolean behavior
 * - All other boolean operations are derived from ifThenElse
 * - Uses call-by-name parameters (=>) for lazy evaluation
 */
abstract class Boolean {
    /**
     * The fundamental boolean operation: conditional selection
     * If this boolean is "true", return t; otherwise return e
     * 
     * @param t the value to return if this boolean represents "true"
     * @param e the value to return if this boolean represents "false"
     * @return either t or e depending on the boolean value
     */
    def ifThenElse[T](t: => T, e: => T): T 

    /**
     * Logical AND operation
     * true && x = x, false && x = false
     * Uses lazy evaluation - if this is false, x is never evaluated
     */
    def && (x: => Boolean): Boolean = ifThenElse(x, False)
    
    /**
     * Logical OR operation  
     * true || x = true, false || x = x
     * Uses lazy evaluation - if this is true, x is never evaluated
     */
    def || (x: => Boolean): Boolean = ifThenElse(True, x)
    
    /**
     * Logical NOT operation
     * !true = false, !false = true
     */
    def unary_!(): Boolean = ifThenElse(False, True)
    
    /**
     * Equality comparison
     * true == x = x, false == x = !x
     */
    def == (x: Boolean): Boolean = ifThenElse(x, x.unary_!())
    
    /**
     * Inequality comparison  
     * true != x = !x, false != x = x
     */
    def != (x: Boolean): Boolean = ifThenElse(x.unary_!(), x)
    
    /**
     * Less-than comparison (false < true, treating false as 0 and true as 1)
     * true < x = false (true is not less than anything)
     * false < x = x (false is less than true, equal to false)
     */
    def < (x: Boolean): Boolean = ifThenElse(False, x)
}

/**
 * The "true" boolean value - always selects the first alternative
 * When ifThenElse(t, e) is called, it returns t
 */
object True extends Boolean {
    def ifThenElse[T](t: => T, e: => T): T = t
}

/**
 * The "false" boolean value - always selects the second alternative  
 * When ifThenElse(t, e) is called, it returns e
 */
object False extends Boolean {
    def ifThenElse[T](t: => T, e: => T): T = e
}

/**
 * Demonstration program for the pure boolean implementation
 * Shows all boolean operations, truth tables, and practical examples
 */
object PureBooleanDemo {
    def main(args: Array[String]): Unit = {
        println("=== Pure Boolean Implementation Demo ===")
        println("This demonstrates Church encoding of booleans as functions")
        
        // Test basic conditional behavior
        println(s"True.ifThenElse('yes', 'no') = ${True.ifThenElse("yes", "no")}")
        println(s"False.ifThenElse('yes', 'no') = ${False.ifThenElse("yes", "no")}")
        
        // Test logical operations
        println("\n--- Logical AND (&&) ---")
        println(s"True && True = ${(True && True).ifThenElse("true", "false")}")
        println(s"True && False = ${(True && False).ifThenElse("true", "false")}")
        println(s"False && True = ${(False && True).ifThenElse("true", "false")}")
        println(s"False && False = ${(False && False).ifThenElse("true", "false")}")
        
        println("\n--- Logical OR (||) ---")
        println(s"True || True = ${(True || True).ifThenElse("true", "false")}")
        println(s"True || False = ${(True || False).ifThenElse("true", "false")}")
        println(s"False || True = ${(False || True).ifThenElse("true", "false")}")
        println(s"False || False = ${(False || False).ifThenElse("true", "false")}")
        
        println("\n--- Logical NOT (!) ---")
        println(s"!True = ${True.unary_!().ifThenElse("true", "false")}")
        println(s"!False = ${False.unary_!().ifThenElse("true", "false")}")
        
        println("\n--- Equality (==) ---")
        println(s"True == True = ${(True == True).ifThenElse("true", "false")}")
        println(s"True == False = ${(True == False).ifThenElse("true", "false")}")
        println(s"False == True = ${(False == True).ifThenElse("true", "false")}")
        println(s"False == False = ${(False == False).ifThenElse("true", "false")}")
        
        println("\n--- Inequality (!=) ---")
        println(s"True != True = ${(True != True).ifThenElse("true", "false")}")
        println(s"True != False = ${(True != False).ifThenElse("true", "false")}")
        println(s"False != True = ${(False != True).ifThenElse("true", "false")}")
        println(s"False != False = ${(False != False).ifThenElse("true", "false")}")
        
        println("\n--- Less Than (<) ---")
        println("Note: false < true (treating false as 0, true as 1)")
        println(s"True < True = ${(True < True).ifThenElse("true", "false")}")
        println(s"True < False = ${(True < False).ifThenElse("true", "false")}")
        println(s"False < True = ${(False < True).ifThenElse("true", "false")}")
        println(s"False < False = ${(False < False).ifThenElse("true", "false")}")
        
        // Additional ordering demonstrations
        println("\n--- Boolean Ordering Examples ---")
        val booleans = List((False, "False"), (True, "True"))
        for ((b1, name1) <- booleans; (b2, name2) <- booleans) {
            val isLess = (b1 < b2).ifThenElse("true", "false")
            println(s"$name1 < $name2 = $isLess")
        }
        
        // Demonstrate sorting with pure booleans
        println("\n--- Sorting with Pure Booleans ---")
        val values = List(True, False, True, False)
        val valueNames = values.map(b => b.ifThenElse("True", "False"))
        println(s"Original: ${valueNames.mkString(", ")}")
        
        // Simple bubble sort demonstration using < operator
        def isLessThan(a: Boolean, b: Boolean): scala.Boolean = 
            (a < b).ifThenElse(true, false)
        
        val sorted = values.sortWith(isLessThan)
        val sortedNames = sorted.map(b => b.ifThenElse("True", "False"))
        println(s"Sorted:   ${sortedNames.mkString(", ")}")
        
        // Demonstrate that False < True enables ordering
        println("\n--- Min/Max Operations ---")
        def min(a: Boolean, b: Boolean): Boolean = (a < b).ifThenElse(a, b)
        def max(a: Boolean, b: Boolean): Boolean = (a < b).ifThenElse(b, a)
        
        println(s"min(True, False) = ${min(True, False).ifThenElse("True", "False")}")
        println(s"max(True, False) = ${max(True, False).ifThenElse("True", "False")}")
        println(s"min(False, True) = ${min(False, True).ifThenElse("True", "False")}")
        println(s"max(False, True) = ${max(False, True).ifThenElse("True", "False")}")
        
        println("\n--- Practical Examples ---")
        val condition = True
        val result = condition.ifThenElse("Access Granted", "Access Denied")
        println(s"Security check result: $result")
        
        val isEven = (5 % 2 == 0) match {
            case true => True
            case false => False
        }
        val message = isEven.ifThenElse("Number is even", "Number is odd")
        println(s"5 is even? $message")
        
        // Demonstrate lazy evaluation
        println("\n--- Lazy Evaluation Demo ---")
        def expensiveTrue(): Boolean = { 
            println("  Computing expensive true operation..."); True 
        }
        def expensiveFalse(): Boolean = { 
            println("  Computing expensive false operation..."); False 
        }
        
        print("True && expensiveTrue(): ")
        val result1 = True && expensiveTrue()
        println(result1.ifThenElse("true", "false"))
        
        print("False && expensiveTrue(): ")
        val result2 = False && expensiveTrue()
        println(result2.ifThenElse("true", "false"))
    }
}
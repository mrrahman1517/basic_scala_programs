package idealized.scala

abstract class Boolean {
    def ifThenElse[T](t: => T, e: => T): T 
    def && (x: => Boolean): Boolean = ifThenElse(x, False)
    def || (x: => Boolean): Boolean = ifThenElse(True, x)
    def unary_!(): Boolean = ifThenElse(False, True)
    def == (x: Boolean): Boolean = ifThenElse(x, x.unary_!())
    def != (x: Boolean): Boolean = ifThenElse(x.unary_!(), x)
}

object True extends Boolean {
    def ifThenElse[T](t: => T, e: => T): T = t
}

object False extends Boolean {
    def ifThenElse[T](t: => T, e: => T): T = e
}

object PureBooleanDemo {
    def main(args: Array[String]): Unit = {
        println("=== Pure Boolean Implementation Demo ===")
        
        // Test basic values
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
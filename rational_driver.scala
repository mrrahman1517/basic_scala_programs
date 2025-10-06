import algebra.Rational

/**
 * Rational Driver Program
 * ======================
 * 
 * This program demonstrates basic usage of the Rational class
 * from the algebra package. It tests fraction arithmetic to verify
 * that 1/4 + 3/4 = 1.
 */
object RationalDriver {
    def main(args: Array[String]): Unit = {
        println("=== Rational Driver Test ===")
        
        // Create test rational numbers
        val r1 = new Rational(1, 4)  // 1/4
        val r2 = new Rational(3, 4)  // 3/4  
        val r3 = new Rational(1)     // 1/1 (using auxiliary constructor)
        
        // Display the values
        println(s"r1 = $r1")
        println(s"r2 = $r2") 
        println(s"r3 = $r3")
        println(s"r1 + r2 = ${r1 + r2}")
        
        // Test arithmetic: 1/4 + 3/4 should equal 1
        assert(r3 == r1 + r2, "Test failed: 1/4 + 3/4 should equal 1")
        
        println("✓ Test passed: 1/4 + 3/4 = 1")
        println("=== Test completed successfully ===")
    }
}
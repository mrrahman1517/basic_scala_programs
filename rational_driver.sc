// Simple script version that can run independently
// This version embeds a minimal Rational class for standalone execution

class Rational(x: Int, y: Int) {
    require(y != 0, "denominator must be non zero")
    
    def this(x: Int) = this(x, 1)
    
    private def gcd(a: Int, b: Int): Int = 
        if (b == 0) a else gcd(b, a % b)
    
    val g = gcd(x, y)
    def numer = x / g
    def denom = y / g
    
    def + (that: Rational) = 
        new Rational(numer * that.denom + that.numer * denom, denom * that.denom)
    
    def == (that: Rational) = 
        numer * that.denom == that.numer * denom
    
    override def toString = numer + "/" + denom
}

// Test code
val r1 = new Rational(1, 4)  // 1/4
val r2 = new Rational(3, 4)  // 3/4  
val r3 = new Rational(1)     // 1/1

println("=== Simple Rational Test ===")
println(s"r1 = $r1")
println(s"r2 = $r2") 
println(s"r3 = $r3")
println(s"r1 + r2 = ${r1 + r2}")

assert(r3 == r1 + r2, "Test failed: 1/4 + 3/4 should equal 1")
println("✓ Test passed: 1/4 + 3/4 = 1")
println("=== Test completed successfully ===")
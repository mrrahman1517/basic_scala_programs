import scala.compiletime.ops.int
val factorial_of_10 = (1 to 10).product

//println("first fact method")
//println(factorial_of_10)

def factorial(n: Int): Int = {
    (1 to n).product
}

val f = factorial(3)

//println("second fact method")
//println(f)

def prime(n: Int): Boolean = {
    // for all i such that 2 <= i < n: n != 0 mod i
    if (n <= 1) false
    else if (n == 2) true
    else !(2 to math.sqrt(n).toInt).exists(i => n % i == 0)
}

//println((2 to 20).map(i => prime(i)))

//(2 to 20).foreach(i => println(s"$i -> ${{prime(i)}}"))

var capital = Map("US" -> "Washington", "France" -> "Paris")
capital += ("Japan" -> "Tokyo")
capital += ("Bangladesh" -> "Dhaka")

//println(capital("Bangladesh"))

//println(capital)

def factorialb(x: BigInt): BigInt = if (x == 0) 1 else x * factorialb(x-1)

//println(factorial(300))
//println(factorialb(300))

def power (x: Double, y: Int):Double = if (y == 0) 1 else x * power(x, y-1)
println(power(4,3))

def abs (x: Double) = if (x >= 0) x else -x 

println(abs(1))
println(abs(-3))

def loop: Boolean = loop

def and (x: Boolean, y: => Boolean): Boolean = 
                            if (x) y else false
//println (and (false, false))
//println (and (false, true))
//println (and (true, false))
//println (and (true, true))

def or (x: Boolean, y: Boolean): Boolean = if x == true then true else y
//println (or (false, false))
//println (or (false, true))
//println (or (true, false))
//println (or (true, true))
//println(and ())
println(and (false, loop))
def sqrt (x: Double) : Double = {
    def sqrtIter(guess: Double): Double = 
        if (isGoodEnough(guess)) guess
        else sqrtIter(improve(guess))

    def isGoodEnough(guess: Double) =
        abs(guess * guess - x) /x < 0.001

    def improve(guess: Double): Double = 
        (guess + x / guess) /2

    sqrtIter(1.0)
}
println(sqrt(16))
println(sqrt(1e-6))
println(sqrt(1e60))
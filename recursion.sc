import scala.annotation.tailrec
@tailrec
def gcd(a: Int, b: Int): Int = 
    if (b == 0) a else gcd (b, a%b)

//println(gcd(3,4))
//println(gcd(12,18))
//println(gcd(14,21))
assert(gcd(14,21) == 7)

def factorial(n: Int): Int = {
    @tailrec
    def factorialHelper(n: Int, acc: Int): Int = 
        if (n == 0) acc else factorialHelper(n-1, n * acc)
    
    factorialHelper(n, 1)
}

//println(factorial(6))
assert(factorial(6) == 720)
assert(factorial(4) == 24)
assert(factorial(10) == 10 * factorial(9))
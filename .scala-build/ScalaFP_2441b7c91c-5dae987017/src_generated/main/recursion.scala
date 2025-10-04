

final class recursion$_ {
def args = recursion_sc.args$
def scriptPath = """/Users/muntasirraihanrahman/Documents/ScalaFP/recursion.sc"""
/*<script>*/
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
/*</script>*/ /*<generated>*//*</generated>*/
}

object recursion_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new recursion$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export recursion_sc.script as `recursion`


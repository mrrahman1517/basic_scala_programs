

final class hof$_ {
def args = hof_sc.args$
def scriptPath = """/Users/muntasirraihanrahman/Documents/ScalaFP/hof.sc"""
/*<script>*/
import scala.annotation.tailrec

def sumInts(a: Int, b: Int): Int = 
    if (a >b) 0 else a + sumInts(a+1,b)

assert(sumInts(5,3) == 0)
assert(sumInts(1,10) == (1+10)*10/2)

def cube(x:Int): Int = x * x * x

def square(x:Int):Int = x * x

def sumSquares(a: Int, b: Int): Int = 
    if (a > b) 0 else square(a) + sumSquares(a+1,b)

assert(sumSquares(1,10) == 10*(10+1)*(2*10+1)/6)

def sumCubes(a: Int, b: Int): Int = 
    if (a > b) 0 else cube(a) + sumCubes(a+1,b)

def factorial(n: Int): Int = {
    @tailrec
    def factorialHelper(n: Int, acc: Int): Int = 
        if (n == 0) acc else factorialHelper(n-1, n * acc)
    
    factorialHelper(n, 1)
}
def sumFactorials(a: Int, b: Int): Int = 
    if (a > b) 0 else factorial(a) + sumFactorials(a+1,b)

//def sumList

assert(sumFactorials(1,3) == factorial(1) + factorial(2) + factorial(3))

// special cases of sum from n=a to n=b of f(n)

def sum(f: Int => Int, a: Int, b: Int): Int = 
    if (a > b) 0
    else f(a) + sum(f, a + 1, b)

def id(x: Int): Int = x 

def hsumInts(a: Int, b: Int) = sum(id, a, b)
def hsumSquares(a: Int, b: Int) = sum(square, a, b)
def hsumCubes(a: Int, b: Int) = sum(cube, a, b)
def hsumFactorials(a: Int, b: Int) = sum(factorial, a, b)


assert(hsumInts(5,3) == 0)
assert(hsumInts(1,10) == (1+10)*10/2)

assert(hsumSquares(1,10) == 10*(10+1)*(2*10+1)/6)
assert(hsumCubes(2,4) == cube(2) + cube(3) + cube(4))
assert(hsumFactorials(1,3) == factorial(1) + factorial(2) + factorial(3))

def ahsumInts(a: Int, b: Int) = sum(x => x, a, b)
assert(ahsumInts(1,10) == 55)

def ahsumSquares(a: Int, b: Int) = sum(x=> x*x, a, b)
assert(ahsumSquares(1,10) == 10*(10+1)*(2*10+1)/6)

def ahsumCubes(a: Int, b: Int): Int = sum(x => x*x*x, a, b)
assert(ahsumCubes(2,4) == cube(2) + cube(3) + cube(4))

def ahsumFactorials(a: Int, b: Int): Int = sum(x => factorial(x), a, b)

assert(ahsumFactorials(1,3) == factorial(1) + factorial(2) + factorial(3))

def sumTailRec(f: Int => Int, a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
        if (a > b) acc  
            else loop(a+1, f(a) + acc)
    }
    loop(a, 0)
}

def ahtsumInts(a: Int, b: Int) = sumTailRec(x => x, a, b)
assert(ahtsumInts(1,10) == 55)

def ahtsumSquares(a: Int, b: Int) = sumTailRec(x=> x*x, a, b)
assert(ahtsumSquares(1,10) == 10*(10+1)*(2*10+1)/6)

def ahtsumCubes(a: Int, b: Int): Int = sumTailRec(x => x*x*x, a, b)
assert(ahtsumCubes(2,4) == cube(2) + cube(3) + cube(4))

def ahtsumFactorials(a: Int, b: Int): Int = sumTailRec(x => factorial(x), a, b)

assert(ahtsumFactorials(1,3) == factorial(1) + factorial(2) + factorial(3))

def currySum(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int = {
        if (a > b) 0
        else f(a) + sumF(a+1, b)
    }
    sumF
}

assert(currySum(x=>x*x)(1,3) == square(1) + square(2) + square(3))

def sugarCurrySum(f: Int => Int)(a: Int, b: Int): Int = 
    if (a >b) 0 else f(a) + sugarCurrySum(f)(a+1,b)

assert(sugarCurrySum(x=>x*x)(1,3) == square(1) + square(2) + square(3))




/*</script>*/ /*<generated>*//*</generated>*/
}

object hof_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new hof$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export hof_sc.script as `hof`


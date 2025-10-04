error id: file://<WORKSPACE>/demo.sc:`<none>`.
file://<WORKSPACE>/demo.sc
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -scala/Predef.
	 -scala/Predef#
	 -scala/Predef().
offset: 182
uri: file://<WORKSPACE>/demo.sc
text:
```scala
import scala.compiletime.ops.int
val factorial_of_10 = (1 to 10).product

//println("first fact method")
//println(factorial_of_10)

def factorial(n: Int): Int = {
    (1 to n).produ@@ct
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

println(capital("Bangladesh"))

println(capital)


```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.
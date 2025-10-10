
def scaleList(xs: List[Double], factor: Double): List[Double] = xs match {
    case Nil => xs
    case y :: ys => y * factor :: scaleList(ys, factor)
}

println(scaleList(List(1, 2, 3), 3))

def mscaleList(xs: List[Double], factor: Double): List[Double] = 
    xs.map(x => x * factor)

println(scaleList(List(1, 2, 3), 3))

def squareList(xs: List[Int]): List[Int] = xs match {
    case Nil => xs
    case y :: ys => y * y :: squareList(ys)
}

println(squareList(List(1,2,3,4,5)))

def msquareList(xs: List[Int]): List[Int] = 
    xs.map(x => x * x)

println(msquareList(List(1,2,3,4,5)))

assert(squareList(List(1,2,3,4,5)) == msquareList(List(1,2,3,4,5)))

def posElems(xs: List[Int]): List[Int] = xs match {
    case Nil => xs
    case y :: ys => if ( y > 0) y :: posElems(ys) else posElems(ys)
}

def mposElems(xs: List[Int]): List[Int] = 
    xs.filter(x => x > 0)

assert(posElems(List(1, -1, 2, -2, 3, -3)) == List(1, 2, 3))
assert(mposElems(List(1, -1, 2, -2, 3, -3)) == List(1, 2, 3))

val nums = List(2, -4, 5, 7, 1)
println(nums)
//println (msort(nums)((x: Int, y: Int) => x < y))

val fruits = List("apple", "orange", "banana", "jackfruit", "mango")
println(fruits)
//println(msort(fruits)((x: String, y: String) => x.compareTo(y) < 0))


println(nums.filter(x => x > 0))
println(nums.filterNot(x => x > 0))
println(nums.partition(x => x > 0))

println(nums.takeWhile(x => x > 0))
println(nums.dropWhile(x => x > 0))
println(nums.span(x => x > 0))

def pack[T](xs: List[T]): List[List[T]] = xs match {
    case Nil => Nil
    case x :: xs1 => {
         //(xs.takeWhile(y => y == x)) :: pack(xs.dropWhile(y => y == x))
         val (first, rest) = xs span (y => y == x)
         first :: pack (rest)
    }
}

println(pack(List("a", "a", "a", "b", "c", "c", "a")))

def encode[T](xs: List[T]): List[(T, Int)] = 
    pack(xs).map(ys => (ys.head, ys.length) )

println(encode(List("a", "a", "a", "b", "c", "c", "a")))

def sum(xs: List[Int]): Int = xs match {
    case Nil => 0
    case head :: next => head + sum(next)
}

def product(xs: List[Int]): Int = xs match {
    case Nil => 1
    case head :: next => head * product(next)
}

def rsum(xs: List[Int]) = 
    //(0 :: xs) reduceLeft ((x, y) => x + y)
    (0 :: xs) reduceLeft (_ + _)

def rproduct(xs: List[Int]) = 
    //(1 :: xs) reduceLeft ((x, y) => x * y)
    (1 ::xs) reduceLeft (_ * _)

println(nums)
println(sum(nums))
println(product(nums))
assert(product(nums) == nums.head * product(nums.tail))
println(rsum(nums))
println(rproduct(nums))
assert(rproduct(nums) == nums.head * rproduct(nums.tail))





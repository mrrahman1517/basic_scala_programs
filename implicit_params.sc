
def msort[T](xs: List[T])(lt: (T, T) => Boolean): List[T] = {
    val n = xs.length / 2                              // Calculate midpoint for splitting
    
    if (n == 0) xs                                     // Base case: lists of length 0 or 1 are sorted
    else {
        def merge(xs: List[T], ys: List[T]): List[T] = 
            (xs, ys) match {
                case (Nil, ys) => ys
                case (xs, Nil) => xs
                case (x :: xs1, y :: ys1) => {
                    if (lt(x, y)) x :: merge(xs1, ys)
                    else y :: merge(xs, ys1)
                }
            }    
        val (fst, snd) = xs splitAt n                  // Split list at midpoint using built-in splitAt
        merge(msort(fst)(lt), msort(snd)(lt))                  // Recursively sort halves and merge results
    }
}

val nums = List(2, -4, 5, 7, 1)
println (msort(nums)((x: Int, y: Int) => x < y))

val fruits = List("apple", "orange", "banana", "jackfruit", "mango")
println(msort(fruits)((x: String, y: String) => x.compareTo(y) < 0))


// use built in ordering function

def msort2[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
    val n = xs.length / 2                              // Calculate midpoint for splitting
    
    if (n == 0) xs                                     // Base case: lists of length 0 or 1 are sorted
    else {
        def merge(xs: List[T], ys: List[T]): List[T] = 
            (xs, ys) match {
                case (Nil, ys) => ys
                case (xs, Nil) => xs
                case (x :: xs1, y :: ys1) => {
                    if (ord.lt(x, y)) x :: merge(xs1, ys)
                    else y :: merge(xs, ys1)
                }
            }    
        val (fst, snd) = xs splitAt n                  // Split list at midpoint using built-in splitAt
        merge(msort2(fst), msort2(snd))                  // Recursively sort halves and merge results
    }
}

val nums2 = List(2, -4, 5, 7, 1)
//println (msort2(nums2)((x: Int, y: Int) => x - y))
println (msort2(nums2))


val fruits2 = List("apple", "orange", "banana", "jackfruit", "mango")
//println(msort2(fruits2)((x: String, y: String) => x.compareTo(y)))
println(msort2(fruits2))

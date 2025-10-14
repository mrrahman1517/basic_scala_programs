// ===================================
// SCALA COLLECTIONS DEMONSTRATION
// Arrays and Collection Operations
// ===================================

/**
 * This file demonstrates working with Scala collections, particularly Arrays,
 * and shows important gotchas when comparing collections.
 */

val xs: Array[Int] = Array(1, 2, 3)

// Apply map transformation
val mapped = xs.map(x => 2 * x)

// IMPORTANT: Arrays cannot be compared with == for content equality!
// == compares object references, not content for Arrays
// assert(xs.map(x => 2 * x) == Array(2, 4, 6))  // This would FAIL!

// Correct way: Use sameElements to compare array contents
assert(xs.map(x => 2 * x).sameElements(Array(2, 4, 6)))

// Alternative: Convert to List for content comparison
assert(xs.map(x => 2 * x).toList == List(2, 4, 6))

// Let's demonstrate the difference
val arr1 = Array(1, 2, 3)
val arr2 = Array(1, 2, 3)
println(s"arr1 == arr2: ${arr1 == arr2}")  // false (different references)
println(s"arr1.sameElements(arr2): ${arr1.sameElements(arr2)}")  // true (same content)

// Print results
println(s"Original array: ${xs.mkString("Array(", ", ", ")")}")
println(s"Mapped array:   ${mapped.mkString("Array(", ", ", ")")}")

// More collection operations
println(s"Array sum: ${xs.sum}")
println(s"Array length: ${xs.length}")
println(s"Array max: ${xs.max}")
println(s"Array min: ${xs.min}")

// Demonstrate filter operation
val evens = xs.filter(_ % 2 == 0)
println(s"Even numbers: ${evens.mkString("Array(", ", ", ")")}")

// Demonstrate fold operation
val product = xs.fold(1)(_ * _)
println(s"Product of elements: $product")

// ===================================
// ADDITIONAL COLLECTION DEMONSTRATIONS
// ===================================

// Working with Lists (immutable, content-comparable)
val list1 = List(1, 2, 3, 4, 5)
val list2 = List(1, 2, 3, 4, 5)
println(s"\nList comparison:")
println(s"list1 == list2: ${list1 == list2}")  // true for Lists!

// List operations
println(s"List doubled: ${list1.map(_ * 2)}")
println(s"List filtered (>3): ${list1.filter(_ > 3)}")
println(s"List take 3: ${list1.take(3)}")
println(s"List drop 2: ${list1.drop(2)}")

// Working with Vectors (indexed sequences)
val vec = Vector(10, 20, 30, 40)
println(s"\nVector operations:")
println(s"Vector: $vec")
println(s"Vector(1): ${vec(1)}")  // indexed access
println(s"Vector updated: ${vec.updated(1, 99)}")  // immutable update

// Working with Sets (unique elements)
val set1 = Set(1, 2, 3, 2, 1)  // duplicates removed
val set2 = Set(3, 4, 5)
println(s"\nSet operations:")
println(s"Set with duplicates: $set1")
println(s"Set union: ${set1 ++ set2}")
println(s"Set intersection: ${set1.intersect(set2)}")
println(s"Set difference: ${set1.diff(set2)}")

// Working with Maps (key-value pairs)
val grades = Map("Alice" -> 95, "Bob" -> 87, "Charlie" -> 92)
println(s"\nMap operations:")
println(s"Grades: $grades")
println(s"Alice's grade: ${grades("Alice")}")
println(s"All names: ${grades.keys}")
println(s"All grades: ${grades.values}")
println(s"High achievers: ${grades.filter(_._2 > 90)}")

// Collection conversion examples
println(s"\nCollection conversions:")
println(s"Array to List: ${xs.toList}")
println(s"List to Vector: ${list1.toVector}")
println(s"List to Set: ${list1.toSet}")
println(s"Array to Set: ${xs.toSet}")

println(s"\n=== Collections Demo Complete ===")
println("Key takeaway: Arrays use sameElements(), Lists/Vectors/Sets use == for content comparison!") 

// ===================================
// ADVANCED STRING AND COLLECTION OPERATIONS
// ===================================

/**
 * Strings in Scala are sequences of characters, so collection operations work on them too.
 * This section demonstrates advanced operations like filtering, zipping, and higher-order functions.
 */

// Working with strings as character sequences
val ys: String = "Functional Analysis Topology"

// Filter characters based on predicates
ys.filter(_.isUpper)  // Expression result (not printed)

// Print uppercase characters only
println(ys.filter(_.isUpper))

// Check if any character satisfies a condition (existential quantification)
println(ys.exists(c => c.isUpper))  // true - at least one uppercase

// Check if all characters satisfy a condition (universal quantification)  
println(ys.forall(c => c.isUpper))  // false - not all are uppercase

// ===================================
// ZIPPING AND UNZIPPING OPERATIONS
// ===================================

/**
 * Zip combines two collections element-wise into pairs.
 * Useful for parallel processing of collections.
 */

// Zip a list with a string (creates pairs of (Int, Char))
val pairs = List(1, 2, 3) zip ys
println(pairs)

// Unzip separates pairs back into two collections
println(pairs.unzip)

// ===================================
// BIGRAM GENERATION (SLIDING WINDOW)
// ===================================

/**
 * Bigrams are consecutive character pairs, useful in text analysis and linguistics.
 * This demonstrates the sliding window pattern using zip with tail.
 */

// Create bigrams by zipping string with its tail (all consecutive character pairs)
val bigrams = ys zip ys.tail
println(bigrams)

/**
 * Reusable bigram generator function.
 * Takes any string and returns all consecutive character pairs.
 */
def bigramBuilder(word: String) = {
    word zip word.tail 
}

// Test bigram generation with a simple word
println(bigramBuilder("hello"))

// ===================================
// FLATMAP AND ADVANCED TRANSFORMATIONS
// ===================================

/**
 * FlatMap applies a function that returns a collection and flattens the result.
 * Useful for one-to-many transformations.
 */

val s = "hello"

// Insert dots between characters using flatMap
println(s.flatMap(c => List('.', c)).toString())

// ===================================
// RANGE OPERATIONS
// ===================================

/**
 * Ranges are efficient sequences of numbers with arithmetic progressions.
 * They support all collection operations without storing all elements.
 */

// Range with step: Range(start, end, step) - end is exclusive
val r = Range(1, 10, 2)  // 1, 3, 5, 7, 9
println(r.sum)

// Simple range: Range(start, end) - default step is 1
val r2 = Range(1, 6)     // 1, 2, 3, 4, 5
println(r2.product)

// ===================================
// CARTESIAN PRODUCTS AND COMBINATORICS
// ===================================

/**
 * Generates all combinations (Cartesian product) of two ranges.
 * Demonstrates nested flatMap/map for combinatorial operations.
 */
def allComb(M: Int, N: Int) = {
    (1 to M).flatMap(x => (1 to N).map(y => (x,y)))
} 

// Generate all pairs from 1x1 to 2x2 grid
println(allComb(2, 2))

// ===================================
// VECTOR OPERATIONS (LINEAR ALGEBRA)
// ===================================

/**
 * Mathematical vector operations using Scala collections.
 * Demonstrates practical applications of zip and map.
 */

/**
 * Scalar (dot) product implementation using tuple access.
 * Formula: (x₁, x₂) · (y₁, y₂) = x₁×y₁ + x₂×y₂
 */
def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
    (xs zip ys).map(xy => xy._1 * xy._2).sum
}

// Test with vectors (1,2) and (3,4): 1×3 + 2×4 = 11
println(scalarProduct(Vector(1,2), Vector(3,4)))

/**
 * Alternative dot product implementation using pattern matching.
 * More idiomatic Scala style with case destructuring.
 */
def dotProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
    (xs zip ys).map{ case (x, y) => x * y}.sum
}

// Test the alternative implementation
println(dotProduct(Vector(1,2), Vector(3,4)))

// Verify both implementations produce identical results
assert(dotProduct(Vector(1,2), Vector(3,4)) == scalarProduct(Vector(1,2), Vector(3,4)))

// ===================================
// PRIME NUMBER ALGORITHMS
// ===================================

/**
 * Prime number testing using collection operations and quantifiers.
 * Demonstrates the power of exists/forall for mathematical proofs.
 */

/**
 * Determines if a number is prime using universal quantification.
 * A prime number is a natural number greater than 1 that has no positive divisors 
 * other than 1 and itself.
 * 
 * Mathematical definition:
 * - 1 is NOT prime (by definition)
 * - n > 1 and no number from 2 to n-1 divides n evenly
 * 
 * Alternative implementation (commented):
 * !(2 to n-1).exists(x => n % x == 0) - uses negation of existential
 * 
 * Current implementation uses forall (universal quantification):
 * All potential divisors must NOT divide n evenly.
 */
def isPrime(n: Int): Boolean = {
    if (n <= 1) false  // 1 and numbers ≤ 1 are not prime by definition
    else if (n == 2) true  // 2 is the only even prime
    else if (n % 2 == 0) false  // eliminate even numbers > 2
    else (3 to math.sqrt(n).toInt by 2).forall(d => n % d != 0)  // check odd divisors up to √n
    
    // Original simple but inefficient version:
    // else (2 until n).forall(d => n % d != 0)
}

// Test: 13 is prime (only divisible by 1 and 13)
assert(isPrime(13))

// Additional tests for edge cases
assert(!isPrime(1))  // 1 is not prime by definition
assert(isPrime(2))   // 2 is the only even prime
assert(!isPrime(4))  // 4 = 2×2, not prime

// Generate all prime numbers from 1 to 20
// Expected: 2, 3, 5, 7, 11, 13, 17, 19 (8 primes)
println((1 to 20).filter(x => isPrime(x)))

// ===================================
// DEMONSTRATION COMPLETE
// ===================================

/**
 * This comprehensive demonstration covers:
 * 1. Basic collections (Arrays, Lists, Vectors, Sets, Maps)
 * 2. Collection comparison gotchas (== vs sameElements)
 * 3. String operations as character sequences  
 * 4. Zipping and sliding window patterns
 * 5. FlatMap for one-to-many transformations
 * 6. Range operations and arithmetic progressions
 * 7. Cartesian products and combinatorics
 * 8. Vector mathematics and linear algebra
 * 9. Mathematical algorithms using quantifiers
 * 
 * Key functional programming concepts demonstrated:
 * - Higher-order functions (map, filter, flatMap, forall, exists)
 * - Immutable data structures
 * - Collection transformation pipelines
 * - Mathematical reasoning with code
 */

val n = 7

println((1 until n).map(i=> (1 until i).map(j => (i, j))).flatten)

// ===================================
// FLATMAP EQUIVALENCE VERIFICATION
// ===================================

/**
 * FUNDAMENTAL EQUATION: xs flatMap f = (xs map f).flatten
 * 
 * This equivalence shows that flatMap is the composition of map and flatten.
 * Let's verify this with concrete examples.
 */

// Example 1: String to character list transformation
val words = List("hello", "world", "scala")
val charFunction = (s: String) => s.toList  // String => List[Char]

println("=== Example 1: String to Characters ===")
println(s"words: $words")
println(s"Function: String => List[Char] (toList)")

val flatMapResult1 = words.flatMap(charFunction)
val mapFlattenResult1 = words.map(charFunction).flatten

println(s"words.flatMap(_.toList):           $flatMapResult1")
println(s"words.map(_.toList).flatten:       $mapFlattenResult1") 
println(s"Results equal: ${flatMapResult1 == mapFlattenResult1}")

// Example 2: Number to range transformation
val numbers = List(1, 2, 3, 4)
val rangeFunction = (n: Int) => 1 to n  // Int => Range

println(s"\n=== Example 2: Number to Ranges ===")
println(s"numbers: $numbers")
println(s"Function: Int => Range (1 to n)")

val flatMapResult2 = numbers.flatMap(rangeFunction)
val mapFlattenResult2 = numbers.map(rangeFunction).flatten

println(s"numbers.flatMap(n => 1 to n):      $flatMapResult2")
println(s"numbers.map(n => 1 to n).flatten:  $mapFlattenResult2")
println(s"Results equal: ${flatMapResult2 == mapFlattenResult2}")

// Example 3: Nested list processing  
val nestedData = List(List(1, 2), List(3, 4, 5), List(6))
val doubleFunction = (xs: List[Int]) => xs.map(_ * 2)  // List[Int] => List[Int]

println(s"\n=== Example 3: Nested List Processing ===")
println(s"nestedData: $nestedData")
println(s"Function: List[Int] => List[Int] (double each element)")

val flatMapResult3 = nestedData.flatMap(doubleFunction)
val mapFlattenResult3 = nestedData.map(doubleFunction).flatten

println(s"nestedData.flatMap(_.map(_ * 2)):        $flatMapResult3")
println(s"nestedData.map(_.map(_ * 2)).flatten:    $mapFlattenResult3")
println(s"Results equal: ${flatMapResult3 == mapFlattenResult3}")

// Example 4: Option/Maybe pattern (handling potential null/empty values)
val optionalNumbers = List(Some(1), None, Some(2), Some(3), None)
val safeDoubleFunction = (opt: Option[Int]) => opt.map(_ * 2).toList  // Option[Int] => List[Int]

println(s"\n=== Example 4: Option Processing ===")
println(s"optionalNumbers: $optionalNumbers") 
println(s"Function: Option[Int] => List[Int] (safely double if present)")

val flatMapResult4 = optionalNumbers.flatMap(safeDoubleFunction)
val mapFlattenResult4 = optionalNumbers.map(safeDoubleFunction).flatten

println(s"optionalNumbers.flatMap(_.map(_ * 2).toList):       $flatMapResult4")
println(s"optionalNumbers.map(_.map(_ * 2).toList).flatten:   $mapFlattenResult4")
println(s"Results equal: ${flatMapResult4 == mapFlattenResult4}")

println(s"\n✅ EQUATION VERIFIED: xs flatMap f ≡ (xs map f).flatten")
println("This fundamental equivalence demonstrates that flatMap is the composition of map and flatten!")

val xs1 = Vector(1, 2, 3, 4)
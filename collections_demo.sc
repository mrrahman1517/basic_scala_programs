// ===================================
// SCALA COLLECTIONS DEMONSTRATION
// Arrays and Collection Operations
// ===================================

/**
 * This file demonstrates working with Scala collections, particularly Arrays,
 * and shows important gotchas when comparing collections.
 * 
 * LEARNING OBJECTIVES:
 * - Understand different collection types (Arrays, Lists, Vectors, Sets, Maps)
 * - Learn collection comparison pitfalls and correct approaches
 * - Master higher-order functions (map, filter, flatMap, fold, etc.)
 * - Grasp immutability principles in functional programming
 * - Apply mathematical operations using collection methods
 * - Understand performance characteristics of different collections
 */

// ===================================
// ARRAY BASICS AND TRANSFORMATIONS
// ===================================

/**
 * Arrays in Scala are mutable, indexed collections with O(1) random access.
 * They correspond directly to Java arrays and offer excellent performance
 * for numerical computations and when you need direct indexing.
 */
val xs: Array[Int] = Array(1, 2, 3)

/**
 * The map function applies a transformation to every element, returning a new Array.
 * Syntax: collection.map(element => transformation)
 * Alternative syntax: collection.map(_ * 2) using placeholder syntax
 */
val mapped = xs.map(x => 2 * x)  // Explicit parameter naming
// val mapped = xs.map(_ * 2)    // Equivalent using placeholder syntax

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

// ===================================
// ARRAY AGGREGATE OPERATIONS
// ===================================

/**
 * Aggregate operations reduce collections to single values.
 * These operations traverse the entire collection once.
 */
println(s"Array sum: ${xs.sum}")        // Adds all elements: 1 + 2 + 3 = 6
println(s"Array length: ${xs.length}")  // Number of elements: 3
println(s"Array max: ${xs.max}")        // Largest element: 3
println(s"Array min: ${xs.min}")        // Smallest element: 1

/**
 * Filter operation: keeps only elements that satisfy a predicate.
 * Predicate: A function that returns true/false for each element.
 * Here we use modulo operator (%) to find even numbers.
 */
val evens = xs.filter(_ % 2 == 0)  // Keep elements where remainder of division by 2 is 0
println(s"Even numbers: ${evens.mkString("Array(", ", ", ")")}")

/**
 * Fold operation: reduces collection using a binary operation.
 * Syntax: collection.fold(initialValue)(binaryOperation)
 * fold(1)(_ * _) means: start with 1, then multiply all elements together
 * This computes the factorial-like product: 1 * 1 * 2 * 3 = 6
 */
val product = xs.fold(1)(_ * _)
println(s"Product of elements: $product")

// ===================================
// IMMUTABLE COLLECTIONS: LISTS
// ===================================

/**
 * Lists are immutable, linked lists - the fundamental collection in functional programming.
 * Key characteristics:
 * - Immutable: operations create new lists, never modify existing ones
 * - Linked structure: efficient prepending (O(1)), less efficient random access (O(n))
 * - Content equality: == compares actual elements, not references
 * - Recursive structure: perfect for recursive algorithms
 */
val list1 = List(1, 2, 3, 4, 5)
val list2 = List(1, 2, 3, 4, 5)
println(s"\nList comparison:")
println(s"list1 == list2: ${list1 == list2}")  // true for Lists! (unlike Arrays)

/**
 * Common List operations - all return NEW lists (immutability principle)
 */
println(s"List doubled: ${list1.map(_ * 2)}")           // Transform: multiply each by 2
println(s"List filtered (>3): ${list1.filter(_ > 3)}") // Filter: keep elements > 3  
println(s"List take 3: ${list1.take(3)}")              // Take: first 3 elements
println(s"List drop 2: ${list1.drop(2)}")              // Drop: skip first 2 elements

// ===================================
// INDEXED SEQUENCES: VECTORS  
// ===================================

/**
 * Vectors are immutable, indexed collections with excellent performance.
 * Key advantages:
 * - Efficient random access: O(log32 n) ≈ O(1) for practical purposes
 * - Efficient updates: structural sharing makes copies fast
 * - Balanced performance: good for both sequential and random access
 * - Immutable: like Lists, operations return new Vectors
 */
val vec = Vector(10, 20, 30, 40)
println(s"\nVector operations:")
println(s"Vector: $vec")
println(s"Vector(1): ${vec(1)}")  // Fast indexed access - O(log32 n)
println(s"Vector updated: ${vec.updated(1, 99)}")  // Returns NEW vector with change

// ===================================
// SETS: UNIQUE ELEMENT COLLECTIONS
// ===================================

/**
 * Sets are collections that contain no duplicate elements.
 * Mathematical set operations are naturally supported.
 * Key characteristics:
 * - No duplicates: automatically removes duplicate elements
 * - Unordered: elements have no specific order (though some implementations may appear ordered)
 * - Fast membership testing: O(1) for HashSet, O(log n) for TreeSet
 * - Set algebra operations: union, intersection, difference
 */
val set1 = Set(1, 2, 3, 2, 1)  // duplicates automatically removed → Set(1, 2, 3)
val set2 = Set(3, 4, 5)
println(s"\nSet operations:")
println(s"Set with duplicates: $set1")                    // Only unique elements remain
println(s"Set union: ${set1 ++ set2}")                   // All elements from both sets
println(s"Set intersection: ${set1.intersect(set2)}")    // Common elements only  
println(s"Set difference: ${set1.diff(set2)}")           // Elements in set1 but not set2

// ===================================
// MAPS: KEY-VALUE ASSOCIATIONS
// ===================================

/**
 * Maps store key-value pairs, like dictionaries or hash tables.
 * Key characteristics:
 * - Immutable: operations return new Maps
 * - Unique keys: each key appears at most once
 * - Fast lookup: O(1) for HashMap, O(log n) for TreeMap
 * - Functional operations: can be filtered, mapped, folded like other collections
 * - Tuple syntax: "key" -> value creates a tuple (key, value)
 */
val grades = Map("Alice" -> 95, "Bob" -> 87, "Charlie" -> 92)
println(s"\nMap operations:")
println(s"Grades: $grades")
println(s"Alice's grade: ${grades("Alice")}")              // Direct key lookup
println(s"All names: ${grades.keys}")                      // Set of all keys
println(s"All grades: ${grades.values}")                   // Collection of all values
println(s"High achievers: ${grades.filter(_._2 > 90)}")    // Filter by value using tuple syntax

// ===================================
// COLLECTION CONVERSIONS
// ===================================

/**
 * Scala collections can be easily converted between different types.
 * Each collection type has toX methods for conversion.
 * Conversions may change performance characteristics and behavior.
 */
println(s"\nCollection conversions:")
println(s"Array to List: ${xs.toList}")        // Mutable array → immutable list
println(s"List to Vector: ${list1.toVector}")  // Linked list → indexed vector  
println(s"List to Set: ${list1.toSet}")        // Ordered list → unordered set (removes duplicates)
println(s"Array to Set: ${xs.toSet}")          // Array → set (removes duplicates, changes access pattern)

println(s"\n=== Collections Demo Complete ===")
println("Key takeaway: Arrays use sameElements(), Lists/Vectors/Sets use == for content comparison!") 

// ===================================
// ADVANCED STRING AND COLLECTION OPERATIONS
// ===================================

/**
 * Strings in Scala are sequences of characters, so collection operations work on them too.
 * This section demonstrates advanced operations like filtering, zipping, and higher-order functions.
 * 
 * STRING AS COLLECTION CONCEPT:
 * - String implements Seq[Char] - it's a sequence of characters
 * - All collection methods (map, filter, exists, forall) work on strings
 * - Results can be strings or collections depending on the operation
 * - This enables powerful text processing using functional programming
 */

// Working with strings as character sequences
val ys: String = "Functional Analysis Topology"

/**
 * Character filtering operations - strings support all collection predicates
 */
ys.filter(_.isUpper)  // Expression result (not printed) - returns String with uppercase chars

// Print uppercase characters only
println(ys.filter(_.isUpper))  // Result: "FAT" - all uppercase characters

/**
 * Quantifier operations - mathematical logic applied to characters
 * - exists: ∃ (existential quantifier) - "there exists at least one"  
 * - forall: ∀ (universal quantifier) - "for all" or "every element"
 */
println(ys.exists(c => c.isUpper))  // true - ∃ at least one uppercase letter
println(ys.forall(c => c.isUpper))  // false - ∀ characters are NOT uppercase

// ===================================
// ZIPPING AND UNZIPPING OPERATIONS
// ===================================

/**
 * ZIPPING OPERATIONS - Parallel Collection Combination
 * 
 * Zipping combines two collections element-wise into pairs (tuples).
 * This is particularly useful for:
 * - Creating associations between related data
 * - Parallel processing of multiple sequences  
 * - Coordinate pairs, key-value associations
 * - Mathematical vector operations
 * 
 * BEHAVIOR NOTES:
 * - Zip stops at the shorter collection's length
 * - Creates List[(A,B)] where A and B are element types
 * - Preserves order from both collections
 */

// Zip a list with a string (creates pairs of (Int, Char))
// Note: Will only create pairs up to List.length (3), ignoring remaining chars
val pairs = List(1, 2, 3) zip ys
println(pairs)  // List((1,F), (2,u), (3,n)) - Int-Char pairs

/**
 * UNZIPPING OPERATIONS - Separating Paired Collections
 * Unzip is the inverse of zip - separates pairs back into two collections
 * Returns a tuple (Collection[A], Collection[B])
 */
println(pairs.unzip)  // (List(1, 2, 3), List(F, u, n)) - separated back

// ===================================
// BIGRAM GENERATION (SLIDING WINDOW)
// ===================================

/**
 * SLIDING WINDOW PATTERN - Consecutive Element Analysis
 * 
 * Bigrams are consecutive character pairs, essential in:
 * - Natural language processing (NLP)
 * - Text analysis and linguistics  
 * - Pattern recognition in sequences
 * - Markov chain analysis
 * 
 * IMPLEMENTATION TECHNIQUE:
 * - zip string with its tail (string.drop(1))
 * - Creates overlapping pairs: "abc" → (a,b), (b,c)
 * - Result length = original.length - 1
 */

// Create bigrams by zipping string with its tail (all consecutive character pairs)
val bigrams = ys zip ys.tail
println(bigrams)  // All consecutive character pairs from the string

/**
 * Reusable bigram generator function.
 * Takes any string and returns all consecutive character pairs.
 * Useful for text analysis pipelines.
 */
def bigramBuilder(word: String) = {
    word zip word.tail  // Generic sliding window of size 2
}

// Test bigram generation with a simple word
println(bigramBuilder("hello"))  // List((h,e), (e,l), (l,l), (l,o)) - sliding pairs

// ===================================
// FLATMAP AND ADVANCED TRANSFORMATIONS
// ===================================

/**
 * FLATMAP - One-to-Many Transformation and Flattening
 * 
 * FlatMap combines map + flatten operations:
 * 1. Applies function that returns a collection for each element
 * 2. Flattens the resulting collection of collections
 * 
 * COMMON USE CASES:
 * - Text transformation (insert separators, expand abbreviations)
 * - Data denormalization (one record → multiple records)
 * - Option/Either monadic operations
 * - Graph traversal (node → connected nodes)
 * 
 * MATHEMATICAL NOTATION:
 * flatMap(f) ≡ map(f).flatten
 */

val s = "hello"

/**
 * Character expansion example - insert dots between characters
 * Each character 'c' becomes List('.', c), then flattened
 * "hello" → List('.','h','.','e','.','l','.','l','.','o')
 */
println(s.flatMap(c => List('.', c)).toString())  // Dot-separated characters

// ===================================
// RANGE OPERATIONS AND ARITHMETIC PROGRESSIONS
// ===================================

/**
 * RANGES - Lazy Arithmetic Sequences
 * 
 * Ranges are memory-efficient sequences representing arithmetic progressions.
 * Key characteristics:
 * - Lazy evaluation - elements computed on-demand
 * - Constant memory usage regardless of size
 * - Support all collection operations (map, filter, fold, etc.)
 * - Mathematical notation: {start, start+step, start+2*step, ..., end-1}
 * 
 * SYNTAX VARIATIONS:
 * - Range(start, end, step) - explicit constructor
 * - start until end by step - infix notation
 * - start to end by step - inclusive end
 */

// Range with step: Range(start, end, step) - end is exclusive
val r = Range(1, 10, 2)  // Arithmetic progression: 1, 3, 5, 7, 9
println(r.sum)  // Mathematical sum of arithmetic sequence = 25

// Simple range: Range(start, end) - default step is 1  
val r2 = Range(1, 6)     // Sequential: 1, 2, 3, 4, 5
println(r2.product)  // Mathematical product = 1×2×3×4×5 = 120 (factorial-like)

// ===================================
// CARTESIAN PRODUCTS AND COMBINATORICS
// ===================================

/**
 * CARTESIAN PRODUCT GENERATION
 * 
 * Generates all combinations (Cartesian product) of two ranges.
 * Mathematical definition: A × B = {(a,b) | a ∈ A, b ∈ B}
 * 
 * APPLICATIONS:
 * - Grid coordinate generation
 * - Combinatorial optimization
 * - Game board positions
 * - Database join operations
 * 
 * IMPLEMENTATION PATTERN:
 * - Outer flatMap iterates first set
 * - Inner map creates pairs with second set  
 * - Result: flattened collection of all combinations
 */
def allComb(M: Int, N: Int) = {
    (1 to M).flatMap(x => (1 to N).map(y => (x,y)))
    // Mathematical interpretation: ∀x∈{1..M}, ∀y∈{1..N} → (x,y)
} 

// Generate all pairs from 1×1 to 2×2 grid
println(allComb(2, 2))  // List((1,1), (1,2), (2,1), (2,2)) - complete 2×2 grid

// ===================================
// VECTOR OPERATIONS (LINEAR ALGEBRA)
// ===================================

/**
 * MATHEMATICAL VECTOR OPERATIONS
 * 
 * Demonstrates practical applications of functional programming in linear algebra.
 * Vectors are represented as collections, operations use zip/map patterns.
 * 
 * KEY CONCEPTS:
 * - Scalar (Dot) Product: v⃗ · w⃗ = Σᵢ(vᵢ × wᵢ)
 * - Element-wise operations via zip
 * - Functional composition for complex operations
 */

/**
 * Scalar (dot) product implementation using tuple access.
 * 
 * Mathematical Formula: v⃗ · w⃗ = v₁×w₁ + v₂×w₂ + ... + vₙ×wₙ
 * 
 * IMPLEMENTATION STEPS:
 * 1. Zip vectors to create coordinate pairs
 * 2. Map each pair to its product
 * 3. Sum all products
 */
def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
    (xs zip ys).map(xy => xy._1 * xy._2).sum
}

// Test with vectors (1,2) and (3,4): 1×3 + 2×4 = 11
println(scalarProduct(Vector(1,2), Vector(3,4)))  // Expected: 11.0

/**
 * Alternative dot product implementation using pattern matching.
 * More idiomatic Scala style with case destructuring.
 * Functionally equivalent but more readable.
 */
def dotProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
    (xs zip ys).map{ case (x, y) => x * y}.sum  // Pattern matching on tuples
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
 * =====================================================================================
 * COMPREHENSIVE SCALA COLLECTIONS TUTORIAL SUMMARY
 * =====================================================================================
 * 
 * This educational demonstration systematically covers all essential aspects of 
 * Scala collections and functional programming patterns:
 * 
 * COLLECTION FUNDAMENTALS:
 * 1. Basic collections (Arrays, Lists, Vectors, Sets, Maps)
 *    - Mutability vs immutability characteristics
 *    - Performance implications and use cases
 *    - Syntax and construction patterns
 * 
 * 2. Collection comparison gotchas (== vs sameElements)
 *    - Reference equality vs structural equality
 *    - Array-specific comparison behaviors
 * 
 * ADVANCED OPERATIONS:
 * 3. String operations as character sequences
 *    - Treating strings as Seq[Char]
 *    - Text processing with functional methods
 * 
 * 4. Zipping and sliding window patterns
 *    - Parallel collection processing
 *    - Bigram generation for text analysis
 * 
 * 5. FlatMap for one-to-many transformations
 *    - Monadic operations and flattening
 *    - Data denormalization patterns
 * 
 * 6. Range operations and arithmetic progressions
 *    - Lazy evaluation and memory efficiency
 *    - Mathematical sequence generation
 * 
 * 7. Cartesian products and combinatorics
 *    - Nested flatMap/map patterns
 *    - Systematic combination generation
 * 
 * 8. Vector mathematics and linear algebra
 *    - Dot product implementations
 *    - Functional approach to numerical computing
 * 
 * 9. Mathematical algorithms using quantifiers
 *    - Prime number testing with forall/exists
 *    - Mathematical proof techniques in code
 * 
 * CORE FUNCTIONAL PROGRAMMING CONCEPTS DEMONSTRATED:
 * - Higher-order functions (map, filter, flatMap, fold, forall, exists)
 * - Immutable data structures and persistence
 * - Collection transformation pipelines and composition
 * - Mathematical reasoning expressed through code
 * - Lazy evaluation and performance optimization
 * - Type safety and generic programming
 * - Pattern matching and case destructuring
 * 
 * PEDAGOGICAL VALUE:
 * This tutorial bridges mathematical concepts with practical programming,
 * demonstrating how functional programming enables elegant solutions to
 * complex problems through composition of simple operations.
 * =====================================================================================
 */

val n = 7

println((1 until n).map(i=> (1 until i).map(j => (i, j))).flatten)
// equivalent
println((1 until n).flatMap(i => (1 until i).map(j => (i, j))).filter(pair => 
    isPrime(pair._1 + pair._2)))

assert((1 until n).map(i=> (1 until i).map(j => (i, j))).flatten ==
    (1 until n).flatMap(i => (1 until i).map(j => (i, j))))

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
val mapResult1 = words.map(charFunction)

println(s"words.flatMap(_.toList):           $flatMapResult1")
println(s"words.map(_.toList):               $mapResult1")
println(s"words.map(_.toList).flatten:       $mapFlattenResult1") 
println(s"Results equal: ${flatMapResult1 == mapFlattenResult1}")

// Example 2: Number to range transformation
val numbers = List(1, 2, 3, 4)
val rangeFunction = (n: Int) => 1 to n  // Int => Range

println(s"\n=== Example 2: Number to Ranges ===")
println(s"numbers: $numbers")
println(s"Function: Int => Range (1 to n)")

val flatMapResult2 = numbers.flatMap(rangeFunction)
val mapResult2 = numbers.map(rangeFunction)
val mapFlattenResult2 = numbers.map(rangeFunction).flatten

println(s"numbers.flatMap(n => 1 to n):      $flatMapResult2")
println(s"numbers.map(n => 1 to n):  ${mapResult2}")
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

println((1 until n).flatMap(i => (1 until i).map(j => (i, j))).filter(pair => 
    isPrime(pair._1 + pair._2)))

case class Person(name: String, age: Int)

val p1 = Person("RogerPenrose", 90)
val p2 = Person("Paul Dirac", 100)
val p3 = Person("John Doe", 10)

val persons = Vector(p1, p2, p3)
println(persons.filter(p => p.age > 20).map(p => p.name))
println(for (p <- persons if p.age > 20) yield p.name)

val m = 11

println(
for {
    i <- 1 until m
    j <- 1 until i 
    if isPrime(i + j) 
} yield (i, j))

def innerProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
    (for {
        (x,y) <- xs zip ys
    } yield x * y).sum
}

val xs11 = Vector(1.0, 2.0)
val ys11 = Vector(3.0, 4.0)

println(innerProduct(xs11, ys11))

assert(innerProduct(xs11, ys11) == dotProduct(xs11, ys11))

println((for {
        (x,y) <- xs11 zip ys11
    } yield x * y).sum)

// Sets

val fruit = Set("apple", "banana", "pear", "mango")
print(fruit)

val s1 = (1 to 6).toSet

// IMPORTANT: Sets are IMMUTABLE in Scala!
// map() creates a NEW set, it doesn't modify the original
println(s"Original set s1: $s1")
println(s"s1.map(_ + 2): ${s1.map(_ + 2)}")  // Creates new set with +2
println(s"s1 after map operation: $s1")      // Original unchanged!

// To "update" the set, you need to assign the result to a new variable
val s1Plus2 = s1.map(_ + 2)
println(s"New set s1Plus2: $s1Plus2")

// Alternative: reassign to the same variable (shadows the original)
// val s1 = s1.map(_ + 2)  // This would "update" s1 by creating a new binding

// ===================================
// IMMUTABILITY DEMONSTRATION
// ===================================

/**
 * Key insight: This demonstrates IMMUTABILITY in functional programming!
 * - Original collections are never modified
 * - Operations return NEW collections with the transformations applied
 * - This prevents side effects and makes code more predictable
 * - Multiple operations can be chained safely: s1.map(_ + 2).filter(_ > 5).map(_ * 2)
 */

// Chaining operations (all return new collections)
val chainedResult = s1.map(_ + 2).filter(_ > 5).map(_ * 2)
println(s"Chained operations s1.map(_ + 2).filter(_ > 5).map(_ * 2): $chainedResult")
println(s"Original s1 still unchanged: $s1")

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
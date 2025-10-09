/*
 * Subtypes and Array Covariance Problem Demonstration
 * 
 * This file demonstrates a classic problem in Scala with array covariance
 * and mutability that can lead to runtime type errors.
 * 
 * The original code had several issues:
 * 1. Missing class definitions
 * 2. Unsafe array covariance with mutable operations
 * 3. Runtime type safety violations
 */

// First, let's define the missing classes to make the code compile
abstract class IntSet {
  def contains(x: Int): Boolean
  def incl(x: Int): IntSet
}

object Empty extends IntSet {
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x, Empty, Empty)
  override def toString = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean = 
    if (x < elem) left.contains(x)
    else if (x > elem) right.contains(x)
    else true
    
  def incl(x: Int): IntSet =
    if (x < elem) new NonEmpty(elem, left.incl(x), right)
    else if (x > elem) new NonEmpty(elem, left, right.incl(x))
    else this
    
  override def toString = "{" + left + elem + right + "}"
}

println("=== Demonstrating the Array Covariance Problem ===")
println()

// ORIGINAL PROBLEMATIC CODE (with fixes):
println("--- Original Code (PROBLEMATIC) ---")

// ORIGINAL PROBLEMATIC CODE (demonstrating the issue):
println("--- Original Code Analysis ---")

try {
  val a: Array[NonEmpty] = Array(new NonEmpty(1, Empty, Empty))
  println(s"a: Array[NonEmpty] = ${a.mkString("[", ", ", "]")}")
  
  // In older Scala versions, this would compile due to array covariance
  // In Scala 3, this is correctly rejected as a compile error!
  println("Attempting: val b: Array[IntSet] = a")
  println("💡 GOOD NEWS: Scala 3 prevents this dangerous assignment!")
  println("   This would have caused runtime errors in older versions")
  
  // Let's demonstrate what WOULD happen if covariance was allowed:
  println()
  println("--- Simulating the Problem with Manual Casting ---")
  
  // Force the covariance with casting (DON'T DO THIS IN REAL CODE!)
  val b: Array[IntSet] = a.asInstanceOf[Array[IntSet]]
  println("b: Array[IntSet] = a.asInstanceOf[Array[IntSet]]  // ⚠️  DANGEROUS CAST")
  
  // Now we can put Empty into what was originally Array[NonEmpty]
  b(0) = Empty
  println("b(0) = Empty  // ⚠️  MUTATION: Changes original array 'a'")
  
  // This will now cause a ClassCastException
  println("Attempting: val s: NonEmpty = a(0)")
  val s: NonEmpty = a(0)  // Runtime error!
  println(s"s = $s")
  
} catch {
  case e: ClassCastException =>
    println(s"💥 RUNTIME ERROR: ${e.getMessage}")
    println("   This demonstrates why array covariance is dangerous!")
  case e: Exception =>
    println(s"💥 ERROR: ${e.getClass.getSimpleName}: ${e.getMessage}")
}

println()
println("=== Analysis of the Problem ===")
println("""
🚨 THE FUNDAMENTAL ISSUE:

1. ARRAY COVARIANCE:
   - Arrays in Scala are covariant: Array[NonEmpty] <: Array[IntSet]
   - This means Array[NonEmpty] can be treated as Array[IntSet]

2. MUTABILITY CONFLICT:
   - Arrays are mutable (you can change their contents)
   - Covariance + Mutability = Type Safety Violation

3. THE VIOLATION SEQUENCE:
   a) Create Array[NonEmpty] with NonEmpty elements
   b) Assign to Array[IntSet] reference (covariance allows this)
   c) Mutate through IntSet reference by adding Empty
   d) Original Array[NonEmpty] now contains Empty!
   e) Accessing as NonEmpty causes ClassCastException

4. WHY THIS IS BAD:
   - Violates type safety at runtime
   - Code that looks correct can crash
   - The problem is with the type system allowing unsafe operations
""")

println("=== Safe Alternatives ===")
println()

// SOLUTION 1: Use immutable collections
println("--- Solution 1: Use Immutable Collections ---")
val safeList: List[NonEmpty] = List(new NonEmpty(1, Empty, Empty))
val safeListAsIntSet: List[IntSet] = safeList  // Safe because List is immutable
println(s"List[NonEmpty]: $safeList")
println(s"List[IntSet]: $safeListAsIntSet")
println("✅ Safe: Lists are immutable, so covariance doesn't cause mutation problems")

println()

// SOLUTION 2: Use specific types, avoid covariance
println("--- Solution 2: Avoid Covariance ---")
val specificArray: Array[NonEmpty] = Array(new NonEmpty(2, Empty, Empty))
// Don't assign to Array[IntSet] - work with the specific type
specificArray.foreach(nonEmpty => println(s"NonEmpty element: $nonEmpty"))
println("✅ Safe: Working with specific types avoids covariance issues")

println()

// SOLUTION 3: Use variance annotations properly
println("--- Solution 3: Understanding Variance ---")
println("""
VARIANCE IN SCALA:

1. COVARIANT (+T):
   - List[+T] means List[Cat] <: List[Animal]
   - Safe for immutable collections
   - NOT safe for mutable collections

2. CONTRAVARIANT (-T):
   - Function1[-T, R] means Function[Animal, R] <: Function[Cat, R]
   - Safe for consumers of T

3. INVARIANT (T):
   - Array[T] should be invariant
   - Array[Cat] should NOT be subtype of Array[Animal]
   - But Scala Arrays are covariant for Java compatibility

4. THE LESSON:
   - Covariance + Mutability = Danger
   - Prefer immutable collections when possible
   - Be careful with array assignments
""")

println()
println("=== Demonstrating Safe Patterns ===")

// Safe pattern 1: Immutable collections
val nonEmptyElements = List(
  new NonEmpty(1, Empty, Empty),
  new NonEmpty(2, Empty, Empty),
  new NonEmpty(3, Empty, Empty)
)

val intSetElements: List[IntSet] = nonEmptyElements  // Safe covariance
println(s"Safe covariance with immutable List: ${intSetElements.length} elements")

// Safe pattern 2: Proper type handling
def processIntSets(sets: List[IntSet]): Unit = {
  sets.foreach { set =>
    println(s"Processing IntSet: $set")
    println(s"  Contains 1: ${set.contains(1)}")
    println(s"  Contains 2: ${set.contains(2)}")
  }
}

processIntSets(intSetElements)

println()
println("🎯 KEY TAKEAWAYS:")
println("1. Arrays in Scala are covariant but mutable - this is dangerous")
println("2. Prefer immutable collections (List, Vector, Set) for type safety")
println("3. When using arrays, be careful about type assignments")
println("4. The original code demonstrates why covariance + mutability is problematic")
println("5. Always consider variance when designing APIs with generic types")


final class classhierarchy$_ {
def args = classhierarchy_sc.args$
def scriptPath = """/Users/muntasirraihanrahman/Documents/ScalaFP/classhierarchy.sc"""
/*<script>*/
abstract class IntSet {
    def incl(x: Int): IntSet
    def contains(x: Int): Boolean
    def union(other: IntSet): IntSet
}

class Empty extends IntSet {
    override def contains(x: Int): Boolean = false
    override def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
    override def union(other: IntSet): IntSet = other
    override def toString: String = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet { 
    override def contains(x: Int): Boolean = {
        if (x == elem) then true
        else  if (x < elem) then left.contains(x)
        else right.contains(x)
    }

    override def incl(x: Int): IntSet = {
        if (x < elem) then new NonEmpty(elem, left.incl(x), right)
        else if ( x > elem) then new NonEmpty(elem, left, right.incl(x))
        else this
    }
    
    override def union(other: IntSet): IntSet = {
        ((left.union(right)).union(other)).incl(elem)
    }
    
    override def toString: String = "{" + left + elem + right + "}"
}

// ============================================================================
// TERM REWRITING DEMONSTRATION: Building Complex Trees Step by Step
// ============================================================================

println("=== TERM REWRITING SIMULATION ===")
println("Building a Binary Search Tree through step-by-step rewriting\n")

// Start with empty set - our initial term
val empty = new Empty
println(s"Initial term: $empty")

// REWRITE STEP 1: empty.incl(3) → NonEmpty(3, Empty, Empty)
val step1 = empty.incl(3)
println(s"Step 1: empty.incl(3) → $step1")

// REWRITE STEP 2: step1.incl(1) → NonEmpty(3, NonEmpty(1, Empty, Empty), Empty)
val step2 = step1.incl(1)
println(s"Step 2: step1.incl(1) → $step2")

// REWRITE STEP 3: step2.incl(5) → NonEmpty(3, NonEmpty(1, Empty, Empty), NonEmpty(5, Empty, Empty))
val step3 = step2.incl(5)
println(s"Step 3: step2.incl(5) → $step3")

// REWRITE STEP 4: step3.incl(2) → more complex tree
val step4 = step3.incl(2)
println(s"Step 4: step3.incl(2) → $step4")

// REWRITE STEP 5: step4.incl(4) → even more complex
val step5 = step4.incl(4)
println(s"Step 5: step4.incl(4) → $step5")

// REWRITE STEP 6: step5.incl(6) → final complex tree
val finalTree = step5.incl(6)
println(s"Step 6: step5.incl(6) → $finalTree")

println("\n=== FINAL TREE STRUCTURE ===")
println("Tree contains elements: 1, 2, 3, 4, 5, 6")
println("Binary Search Tree property maintained throughout rewriting")

// ============================================================================
// COMPREHENSIVE TESTING SUITE
// ============================================================================

println("\n=== TESTING TREE OPERATIONS ===")

// Test containment - should demonstrate tree traversal
val testValues = List(1, 2, 3, 4, 5, 6, 7, 0)
testValues.foreach { value =>
    val result = finalTree.contains(value)
    println(s"finalTree.contains($value) = $result")
}

// Test tree composition through union operation
println("\n=== TESTING UNION OPERATION (Advanced Term Rewriting) ===")

// Create two separate trees
val leftSubtree = (new Empty).incl(10).incl(8).incl(12)
val rightSubtree = (new Empty).incl(20).incl(15).incl(25)

println(s"Left subtree: $leftSubtree")
println(s"Right subtree: $rightSubtree")

// Union operation demonstrates complex term rewriting
val unionResult = leftSubtree.union(rightSubtree)
println(s"Union result: $unionResult")

// Test containment in union result
val unionTestValues = List(8, 10, 12, 15, 20, 25, 30)
println("\nTesting containment in union result:")
unionTestValues.foreach { value =>
    val result = unionResult.contains(value)
    println(s"unionResult.contains($value) = $result")
}

// ============================================================================
// TERM REWRITING ANALYSIS
// ============================================================================

println("\n=== TERM REWRITING ANALYSIS ===")
println("Each operation demonstrates term rewriting:")
println("1. incl(x) rewrites the tree structure by adding a new node")
println("2. contains(x) traverses and pattern matches against tree structure")
println("3. union(other) recursively rewrites by merging two tree terms")
println("4. Every operation preserves BST invariants through rewriting rules")

println("\n=== REWRITING RULES DEMONSTRATED ===")
println("Empty.incl(x) → NonEmpty(x, Empty, Empty)")
println("NonEmpty(e,l,r).incl(x) → NonEmpty(e, l.incl(x), r) if x < e")
println("NonEmpty(e,l,r).incl(x) → NonEmpty(e, l, r.incl(x)) if x > e") 
println("NonEmpty(e,l,r).incl(x) → NonEmpty(e, l, r) if x = e")
println("NonEmpty(e,l,r).union(t) → ((l union r) union t).incl(e)")

// ============================================================================
// PERFORMANCE AND COMPLEXITY DEMONSTRATION
// ============================================================================

println("\n=== BUILDING LARGER TREE FOR PERFORMANCE ANALYSIS ===")

// Build a larger tree to show scalability
val largeTreeElements = (1 to 15).toList.reverse // Insert in reverse for interesting structure
var largeTree: IntSet = new Empty

println("Building tree by inserting: " + largeTreeElements.mkString(", "))
largeTreeElements.foreach { elem =>
    largeTree = largeTree.incl(elem)
    // Show intermediate steps for first few elements
    if (elem >= 13) println(s"After inserting $elem: $largeTree")
}

println(s"\nFinal large tree: $largeTree")

// Test operations on large tree
println("\nTesting operations on large tree:")
val searchElements = List(1, 8, 15, 16, 0)
searchElements.foreach { elem =>
    val found = largeTree.contains(elem)
    println(s"contains($elem) = $found")
}

val t1 = new NonEmpty(3, new Empty, new Empty)
val t2 = t1.incl(4)
assert(t2.contains(4))
//assert(t1.contains(4))
println(t1.toString())
println(t2.toString())
val t3 = t2.incl(1)
println(t3.toString())
val t4 = t3.incl(10)
println(t4.toString())
val t5 = t4.incl(0)
println(t5.toString())

abstract class Base {
    def foo = 1
    def bar: Int 
}

class Sub extends Base {
    override def foo = 2 
    def bar: Int = 3
}

// Fix: properly instantiate the Sub class instead of declaring uninitialized variable
val s: Sub = new Sub
assert(s.foo == 2)
assert(s.bar == 3)
/*</script>*/ /*<generated>*//*</generated>*/
}

object classhierarchy_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new classhierarchy$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export classhierarchy_sc.script as `classhierarchy`


package conslist

/**
 * Cons List Version 2 - Object-Oriented Implementation
 * ===================================================
 * 
 * This version demonstrates an object-oriented approach to cons lists
 * using classes instead of case classes, with proper encapsulation
 * and method implementations within the trait hierarchy.
 */

trait IntList {
    /**
     * Check if the list is empty
     */
    def isEmpty: Boolean
    
    /**
     * Get the head of the list (first element)
     * Throws exception if list is empty
     */
    def head: Int
    
    /**
     * Get the tail of the list (rest of elements)
     * Throws exception if list is empty
     */
    def tail: IntList
    
    /**
     * Get the length of the list
     */
    def length: Int
    
    /**
     * Prepend an element to the front of the list
     */
    def prepend(elem: Int): IntList = new Cons(elem, this)
    
    /**
     * Append another list to the end of this list
     */
    def append(other: IntList): IntList
    
    /**
     * Apply a function to each element and return a new list
     */
    def map(f: Int => Int): IntList
    
    /**
     * Check if the list contains a specific element
     */
    def contains(elem: Int): Boolean
    
    /**
     * Convert to a readable string representation
     */
    def toStringList: String
}

class Cons(val head: Int, val tail: IntList) extends IntList {
    def isEmpty: Boolean = false
    
    def length: Int = 1 + tail.length
    
    def append(other: IntList): IntList = new Cons(head, tail.append(other))
    
    def map(f: Int => Int): IntList = new Cons(f(head), tail.map(f))
    
    def contains(elem: Int): Boolean = head == elem || tail.contains(elem)
    
    def toStringList: String = {
        def buildString(list: IntList, acc: String): String = list match {
            case empty: Empty => acc + "]"
            case cons: Cons => 
                val separator = if (acc == "[") "" else ", "
                buildString(cons.tail, acc + separator + cons.head)
        }
        buildString(this, "[")
    }
    
    override def toString: String = s"Cons($head, ${tail.toString})"
}

class Empty extends IntList {
    def isEmpty: Boolean = true
    
    def head: Int = throw new NoSuchElementException("Empty list has no head")
    
    def tail: IntList = throw new NoSuchElementException("Empty list has no tail")
    
    def length: Int = 0
    
    def append(other: IntList): IntList = other
    
    def map(f: Int => Int): IntList = this
    
    def contains(elem: Int): Boolean = false
    
    def toStringList: String = "[]"
    
    override def toString: String = "Empty"
}

/**
 * Companion object with utility methods and factory functions
 */
object IntList {
    /**
     * Create an empty list
     */
    def empty: IntList = new Empty
    
    /**
     * Create a list from variable arguments
     */
    def apply(elements: Int*): IntList = {
        elements.foldRight(empty)((elem, acc) => new Cons(elem, acc))
    }
    
    /**
     * Create a list from a Scala List
     */
    def fromList(list: List[Int]): IntList = {
        list.foldRight(empty)((elem, acc) => new Cons(elem, acc))
    }
    
    /**
     * Convert IntList to Scala List
     */
    def toList(intList: IntList): List[Int] = {
        def loop(list: IntList, acc: List[Int]): List[Int] = list match {
            case empty: Empty => acc.reverse
            case cons: Cons => loop(cons.tail, cons.head :: acc)
        }
        loop(intList, List.empty)
    }
}

/**
 * Main driver program demonstrating cons list operations
 */
object ConsListDemo {
    def main(args: Array[String]): Unit = {
        println("=== Cons List V2 - Object-Oriented Implementation Demo ===")
        
        // Test basic construction
        testBasicConstruction()
        
        // Test list operations
        testListOperations()
        
        // Test factory methods
        testFactoryMethods()
        
        // Test edge cases
        testEdgeCases()
        
        // Performance comparison
        performanceDemo()
        
        println("\n=== Demo completed successfully! ===")
    }
    
    def testBasicConstruction(): Unit = {
        println("\n--- Basic Construction ---")
        
        // Manual construction
        val list1 = new Cons(1, new Cons(2, new Cons(3, new Empty)))
        println(s"Manual construction: ${list1.toStringList}")
        println(s"Internal representation: $list1")
        
        // Using prepend
        val list2 = new Empty().prepend(3).prepend(2).prepend(1)
        println(s"Using prepend: ${list2.toStringList}")
        
        // Check equality of structure
        println(s"Length of both lists: ${list1.length}, ${list2.length}")
        println(s"Both contain 2: ${list1.contains(2)}, ${list2.contains(2)}")
    }
    
    def testListOperations(): Unit = {
        println("\n--- List Operations ---")
        
        val list1 = new Cons(1, new Cons(2, new Cons(3, new Empty)))
        val list2 = new Cons(4, new Cons(5, new Empty))
        
        println(s"List 1: ${list1.toStringList}")
        println(s"List 2: ${list2.toStringList}")
        
        // Append operation
        val combined = list1.append(list2)
        println(s"List 1 + List 2: ${combined.toStringList}")
        
        // Map operation
        val doubled = list1.map(_ * 2)
        println(s"List 1 doubled: ${doubled.toStringList}")
        
        val squared = list1.map(x => x * x)
        println(s"List 1 squared: ${squared.toStringList}")
        
        // Contains operation
        println(s"List 1 contains 2: ${list1.contains(2)}")
        println(s"List 1 contains 5: ${list1.contains(5)}")
        
        // Head and tail operations
        println(s"Head of List 1: ${list1.head}")
        println(s"Tail of List 1: ${list1.tail.toStringList}")
    }
    
    def testFactoryMethods(): Unit = {
        println("\n--- Factory Methods ---")
        
        // Using apply method
        val list1 = IntList(1, 2, 3, 4, 5)
        println(s"IntList(1,2,3,4,5): ${list1.toStringList}")
        
        // From Scala List
        val scalaList = List(10, 20, 30)
        val list2 = IntList.fromList(scalaList)
        println(s"From Scala List $scalaList: ${list2.toStringList}")
        
        // Back to Scala List
        val backToScala = IntList.toList(list1)
        println(s"Back to Scala List: $backToScala")
        
        // Empty list
        val emptyList = IntList.empty
        println(s"Empty list: ${emptyList.toStringList}")
        println(s"Empty list length: ${emptyList.length}")
        println(s"Empty list is empty: ${emptyList.isEmpty}")
    }
    
    def testEdgeCases(): Unit = {
        println("\n--- Edge Cases ---")
        
        val emptyList = new Empty
        val singleElement = new Cons(42, new Empty)
        
        println(s"Empty list: ${emptyList.toStringList}")
        println(s"Single element: ${singleElement.toStringList}")
        
        // Test operations on empty list
        println(s"Empty list length: ${emptyList.length}")
        println(s"Empty list contains 1: ${emptyList.contains(1)}")
        println(s"Empty list mapped: ${emptyList.map(_ * 2).toStringList}")
        
        // Test operations on single element
        println(s"Single element length: ${singleElement.length}")
        println(s"Single element head: ${singleElement.head}")
        println(s"Single element tail: ${singleElement.tail.toStringList}")
        
        // Test error cases
        try {
            emptyList.head
        } catch {
            case e: NoSuchElementException => 
                println(s"✓ Expected error for empty head: ${e.getMessage}")
        }
        
        try {
            emptyList.tail
        } catch {
            case e: NoSuchElementException => 
                println(s"✓ Expected error for empty tail: ${e.getMessage}")
        }
    }
    
    def performanceDemo(): Unit = {
        println("\n--- Performance Characteristics ---")
        
        // Create a larger list
        val largeList = IntList.fromList((1 to 1000).toList)
        println(s"Created list of 1000 elements")
        println(s"Length: ${largeList.length}")
        
        // Time prepend operation (should be O(1))
        val start1 = System.nanoTime()
        val prepended = largeList.prepend(0)
        val end1 = System.nanoTime()
        println(s"Prepend operation took: ${(end1 - start1) / 1000} microseconds")
        
        // Time map operation (should be O(n))
        val start2 = System.nanoTime()
        val mapped = largeList.map(_ + 1)
        val end2 = System.nanoTime()
        println(s"Map operation took: ${(end2 - start2) / 1000} microseconds")
        
        println(s"Mapped list first 5 elements: ${IntList.toList(mapped).take(5)}")
    }
}
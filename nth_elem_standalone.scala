// Standalone version with embedded list implementation
// This can run with just: scala nth_elem_standalone.scala

// Embedded list implementation
trait List[+T] {
    def isEmpty: Boolean
    def head: T
    def tail: List[T]
    def length: Int
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
    def isEmpty = false
    def length: Int = 1 + tail.length
}

class Nil[T] extends List[T] {
    def isEmpty = true
    def head: T = throw new NoSuchElementException("head of empty list")
    def tail: List[T] = throw new NoSuchElementException("tail of empty list")  
    def length: Int = 0
}

object ListUtils {
    def singleton[T](elem: T): List[T] = new Cons[T](elem, new Nil[T])
    
    def select[T](n: Int, l: List[T]): T = {
        if (n < 0 || n >= l.length) throw new IndexOutOfBoundsException("invalid index")
        else if (n == 0) l.head
        else select(n - 1, l.tail)
    }
}

object NthElemStandalone {
    def nth[T](n: Int, xs: List[T]): T = {
        if (xs.isEmpty) throw new IndexOutOfBoundsException
        else if (n == 0) xs.head
        else nth(n - 1, xs.tail)
    }

    def main(args: Array[String]): Unit = {
        println("=== Nth Element Demo (Standalone) ===")
        
        // Create test lists
        val list1 = new Cons(10, new Cons(20, new Cons(30, new Nil[Int])))
        val list2 = ListUtils.singleton(42)
        
        println(s"List: [10, 20, 30]")
        println(s"Element at index 0: ${ListUtils.select(0, list1)}")
        println(s"Element at index 1: ${ListUtils.select(1, list1)}")
        println(s"Element at index 2: ${ListUtils.select(2, list1)}")
        
        println(s"\nSingleton list: [42]")
        println(s"Element at index 0: ${ListUtils.select(0, list2)}")
        
        println(s"\nUsing custom nth function:")
        println(s"nth(0, list1) = ${nth(0, list1)}")
        println(s"nth(1, list1) = ${nth(1, list1)}")
        println(s"nth(2, list1) = ${nth(2, list1)}")

        try {
            println(s"negative index: ${nth(-1, list1)}")
        }
        catch {
            case e: IndexOutOfBoundsException =>
                println(s"index negative: ${e.getMessage}")
        }
        
        try {
            println(s"large index: ${nth(5, list1)}")
        }
        catch {
            case e: IndexOutOfBoundsException => 
                println(s"✓ Index too large: ${e.getMessage}")
        }
    }
}
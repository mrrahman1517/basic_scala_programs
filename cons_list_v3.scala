package conslist

//trait IntList 
//class Cons(val head: Int, val tail: IntList) extends IntList
//class Nil extends IntList

trait List[T] {
    def isEmpty: Boolean
    def head: T 
    def tail: List[T]
    def length: Int 
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
    def isEmpty: Boolean = false 
    def length = 1 + tail.length 
}

class Nil[T] extends List[T] {
    def isEmpty: Boolean = true
    def head: Nothing  = throw new NoSuchElementException("Nil.head")
    def tail: Nothing = throw new NoSuchElementException("Nil.tail")

    def length = 0
}

def singleton[T](elem: T) = new Cons[T](elem, new Nil[T])

var sl1 = singleton[Int](1)
var sl2 = singleton[Boolean](true)

def select[T](n: Int, l: List[T]): T = {
    if (n < 0 || n >= l.length) then throw IndexOutOfBoundsException("invalid index")
    else if (n == 0) then l.head
    else select(n - 1, l.tail)
} 

object Main {
    def main(args: Array[String]): Unit = {
        println("Cons List V3 Demo")
        println(s"Nil list is empty: ${new Nil[Int].isEmpty}")
        println(s"sl1 isEmpty: ${sl1.isEmpty}")
        println(s"sl2 isEmpty: ${sl2.isEmpty}")
        println(s"singleton int is empty: ${{singleton(1).isEmpty}}")
        println(s"singleton bool is empty: ${{singleton(true).isEmpty}}")
        
        // Tests for select method
        println("\n=== Select Method Tests ===")
        
        // Test with singleton lists
        println(s"select(0, sl1) = ${select(0, sl1)}")
        println(s"select(0, sl2) = ${select(0, sl2)}")
        
        // Create a longer list for more comprehensive testing
        val list3 = new Cons(10, new Cons(20, new Cons(30, new Nil[Int])))
        println(s"select(0, [10,20,30]) = ${select(0, list3)}")
        println(s"select(1, [10,20,30]) = ${select(1, list3)}")
        println(s"select(2, [10,20,30]) = ${select(2, list3)}")
        
        // Test edge cases and error conditions
        println("\n--- Error Condition Tests ---")
        
        // Test negative index
        try {
            select(-1, sl1)
        } catch {
            case e: IndexOutOfBoundsException => 
                println(s"✓ Expected error for negative index: ${e.getMessage}")
        }
        
        // Test index too large
        try {
            select(1, sl1)  // sl1 only has index 0
        } catch {
            case e: IndexOutOfBoundsException => 
                println(s"✓ Expected error for index too large: ${e.getMessage}")
        }
        
        // Test with empty list
        try {
            select(0, new Nil[Int])
        } catch {
            case e: IndexOutOfBoundsException => 
                println(s"✓ Expected error for empty list: ${e.getMessage}")
        }
    }
}

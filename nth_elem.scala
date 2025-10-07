import conslist.v3._

object NthElemDemo {

    def nth[T](n: Int, xs: conslist.v3.List[T]): T = {
        if (n == 0) xs.head
        else nth(n - 1, xs.tail)
    }

    def main(args: Array[String]): Unit = {
        println("=== Nth Element Demo ===")
        
        // Create test lists
        val list1 = new Cons(10, new Cons(20, new Cons(30, new Nil[Int])))
        val list2 = List.singleton(42)
        val emptyList = new Nil[String]
        
        println(s"List: [10, 20, 30]")
        println(s"Element at index 0: ${List.select(0, list1)}")
        println(s"Element at index 1: ${List.select(1, list1)}")
        println(s"Element at index 2: ${List.select(2, list1)}")
        
        println(s"\nSingleton list: [42]")
        println(s"Element at index 0: ${List.select(0, list2)}")
        
        // Test error conditions
        println("\n=== Error Conditions ===")
        try {
            List.select(3, list1)
        } catch {
            case e: IndexOutOfBoundsException => 
                println(s"✓ Index too large: ${e.getMessage}")
        }
        
        try {
            List.select(0, emptyList)
        } catch {
            case e: IndexOutOfBoundsException => 
                println(s"✓ Empty list: ${e.getMessage}")
        }

        val list = new Cons(1, new Cons(2, new Cons(3, new Nil)))
        println(nth(2, list))
    }
}
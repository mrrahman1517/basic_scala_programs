package conslist

//trait IntList 
//class Cons(val head: Int, val tail: IntList) extends IntList
//class Nil extends IntList

trait List[T]
class Cons[T](val head: T, val tail: List[T]) extends List[T]
class Nil[T] extends List[T]
#!/usr/bin/env amm

// This works if you have ammonite installed
// Install with: brew install ammonite-repl

import $cp.`target/scala-2.13/classes`
import conslist.v3._

println("=== Nth Element Demo (Ammonite Script) ===")

// Create test lists
val list1 = new Cons(10, new Cons(20, new Cons(30, new Nil[Int])))
val list2 = List.singleton(42)

println(s"List: [10, 20, 30]")
println(s"Element at index 0: ${List.select(0, list1)}")
println(s"Element at index 1: ${List.select(1, list1)}")
println(s"Element at index 2: ${List.select(2, list1)}")

println(s"\nSingleton list: [42]")
println(s"Element at index 0: ${List.select(0, list2)}")
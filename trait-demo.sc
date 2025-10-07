trait Planar {
    def height: Int
    def width: Int
    def surface = height * width
}

def error(msg: String) = throw new Error(msg)

// Method 1: Using reflection to get method information
println("=== Type Signature Information ===")

// Method 2: Print the function's class and type information
println(s"error function object: ${error}")
println(s"error function class: ${error.getClass}")

// Method 3: Use Scala's type system info (at runtime)
val errorMethod = error  // Convert to function value
println(s"Function type: ${errorMethod.getClass}")

// Method 4: Manual type annotation to see the inferred type
val errorFunc: String => Nothing = error
println(s"Explicit type annotation: String => Nothing")

// Method 5: Using toString on the function
println(s"Function toString: ${error.toString()}")

// Method 6: Demonstrating the type with a type ascription
def showType[T](x: T): String = x.getClass.toString
println(s"Runtime type info: ${showType(error)}")

println("\n=== Summary ===")
println("The error method has type signature: String => Nothing")
println("- Takes a String parameter (msg)")  
println("- Returns Nothing (because it throws an exception)")
println("- Nothing is the bottom type in Scala's type hierarchy")

println("\n=== How to check type signatures in Scala ===")
println("1. In Scala REPL: ':type methodName'")
println("2. In IDE: hover over method name")
println("3. Type annotation: val x: Type = methodName")
println("4. Use getClass for runtime type information")

println("\n=== Scala 3 If-Then-Else Syntax Examples ===")

// Correct Scala 3 syntax (with 'then')
val result1 = if (true) then 1 else false
println(s"if (true) then 1 else false = $result1 (type: ${result1.getClass})")

// Alternative syntax (parentheses around condition)
val result2 = if true then "yes" else "no"
println(s"if true then \"yes\" else \"no\" = $result2 (type: ${result2.getClass})")

println("\n=== REPL Type Checking Examples ===")
println("Try these in Scala REPL:")
println(":t if (true) then 1 else false    // Should show: Int | Boolean")
println(":t if (true) then 1 else 0        // Should show: Int")
println(":t if (true) then \"yes\" else \"no\"  // Should show: String")

println("\n=== Type System Examples ===")

// Null type examples
val x: Null = null
println(s"x (Null type): $x")

// String can accept Null (Null is subtype of all reference types)
val y: String = x 
println(s"y (String from Null): $y")

// Int cannot accept null (Int is a value type)
// val z: Int = null  // This would cause a compile error

// Union types in if-else
val unionResult = if (true) then 1 else false
println(s"Union type result: $unionResult (type: ${unionResult.getClass})")

println("\n=== Final Summary ===")
println("✓ Scala 3 requires 'then' in if-else expressions")
println("✓ Union types (Int | Boolean) are inferred when branches differ")
println("✓ Null is a subtype of reference types but not value types")
println("✓ Use :t in REPL to check type signatures")

// Commented out to prevent exception during normal execution
// error("test")

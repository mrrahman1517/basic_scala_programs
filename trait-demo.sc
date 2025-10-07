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
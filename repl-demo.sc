// Demonstration of checking type signatures in Scala REPL
// Save this file and run: scala -i repl-demo.sc

def error(msg: String) = throw new Error(msg)

println("=== REPL Commands for Type Checking ===")
println("Load this file with: scala -i repl-demo.sc")
println("Then try these commands in the REPL:")
println("")
println(":type error           // Shows: String => Nothing")
println(":type error(\"test\")   // Shows: Nothing") 
println(":info error           // Shows detailed method info")
println("")
println("The type signature is: String => Nothing")
println("This means: takes String parameter, returns Nothing (never returns normally)")
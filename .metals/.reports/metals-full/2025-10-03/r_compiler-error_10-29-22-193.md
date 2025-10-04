error id: 59D59C6B6F435AF6EC13062CB396CAF6
file://<WORKSPACE>/demo.sc
### java.lang.IndexOutOfBoundsException: 969

occurred in the presentation compiler.



action parameters:
offset: 965
uri: file://<WORKSPACE>/demo.sc
text:
```scala
import scala.compiletime.ops.int
val factorial_of_10 = (1 to 10).product

//println("first fact method")
//println(factorial_of_10)

def factorial(n: Int): Int = {
    (1 to n).product
}

val f = factorial(3)

//println("second fact method")
//println(f)

def prime(n: Int): Boolean = {
    // for all i such that 2 <= i < n: n != 0 mod i
    if (n <= 1) false
    else if (n == 2) true
    else !(2 to math.sqrt(n).toInt).exists(i => n % i == 0)
}

//println((2 to 20).map(i => prime(i)))

//(2 to 20).foreach(i => println(s"$i -> ${{prime(i)}}"))

var capital = Map("US" -> "Washington", "France" -> "Paris")
capital += ("Japan" -> "Tokyo")
capital += ("Bangladesh" -> "Dhaka")

//println(capital("Bangladesh"))

//println(capital)

def factorialb(x: BigInt): BigInt = if (x == 0) 1 else x * factorialb(x-1)

//println(factorial(300))
//println(factorialb(300))

def power (x: Double, y: Int):Double = if (y == 0) 1 else x * power(x, y-1)
println(power(4,3))

def@@
```


presentation compiler configuration:
Scala version: 2.13.16
Classpath:
<HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.16/scala-library-2.13.16.jar [exists ]
Options:





#### Error stacktrace:

```
scala.reflect.internal.util.BatchSourceFile.offsetToLine(SourceFile.scala:213)
	scala.meta.internal.pc.MetalsGlobal$XtensionPositionMetals.toPos(MetalsGlobal.scala:816)
	scala.meta.internal.pc.MetalsGlobal$XtensionPositionMetals.toLsp(MetalsGlobal.scala:829)
	scala.meta.internal.pc.PcDocumentHighlightProvider.collect(PcDocumentHighlightProvider.scala:21)
	scala.meta.internal.pc.PcDocumentHighlightProvider.collect(PcDocumentHighlightProvider.scala:9)
	scala.meta.internal.pc.PcCollector.scala$meta$internal$pc$PcCollector$$collect$1(PcCollector.scala:114)
	scala.meta.internal.pc.PcCollector.traverseWithParent$1(PcCollector.scala:184)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$1(PcCollector.scala:110)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$24(PcCollector.scala:319)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector.traverseWithParent$1(PcCollector.scala:319)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$1(PcCollector.scala:110)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$15(PcCollector.scala:259)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector.traverseWithParent$1(PcCollector.scala:259)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$1(PcCollector.scala:110)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$24(PcCollector.scala:319)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector.traverseWithParent$1(PcCollector.scala:319)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$1(PcCollector.scala:110)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$15(PcCollector.scala:259)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector.traverseWithParent$1(PcCollector.scala:259)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$1(PcCollector.scala:110)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$24(PcCollector.scala:319)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector.traverseWithParent$1(PcCollector.scala:319)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$1(PcCollector.scala:110)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$15(PcCollector.scala:259)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector.traverseWithParent$1(PcCollector.scala:259)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$1(PcCollector.scala:110)
	scala.meta.internal.pc.PcCollector.$anonfun$traverseSought$15(PcCollector.scala:259)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector.traverseWithParent$1(PcCollector.scala:259)
	scala.meta.internal.pc.PcCollector.traverseSought(PcCollector.scala:322)
	scala.meta.internal.pc.PcCollector.traverseSought$(PcCollector.scala:101)
	scala.meta.internal.pc.WithSymbolSearchCollector.traverseSought(PcCollector.scala:353)
	scala.meta.internal.pc.PcCollector.resultWithSought(PcCollector.scala:88)
	scala.meta.internal.pc.PcCollector.resultWithSought$(PcCollector.scala:17)
	scala.meta.internal.pc.WithSymbolSearchCollector.resultWithSought(PcCollector.scala:353)
	scala.meta.internal.pc.WithSymbolSearchCollector.$anonfun$result$1(PcCollector.scala:360)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.pc.WithSymbolSearchCollector.result(PcCollector.scala:360)
	scala.meta.internal.pc.PcDocumentHighlightProvider.highlights(PcDocumentHighlightProvider.scala:30)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$documentHighlight$1(ScalaPresentationCompiler.scala:532)
	scala.meta.internal.pc.CompilerAccess.retryWithCleanCompiler(CompilerAccess.scala:182)
	scala.meta.internal.pc.CompilerAccess.$anonfun$withSharedCompiler$1(CompilerAccess.scala:155)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.pc.CompilerAccess.withSharedCompiler(CompilerAccess.scala:154)
	scala.meta.internal.pc.CompilerAccess.$anonfun$withInterruptableCompiler$1(CompilerAccess.scala:92)
	scala.meta.internal.pc.CompilerAccess.$anonfun$onCompilerJobQueue$1(CompilerAccess.scala:209)
	scala.meta.internal.pc.CompilerJobQueue$Job.run(CompilerJobQueue.scala:152)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	java.base/java.lang.Thread.run(Thread.java:840)
```
#### Short summary: 

java.lang.IndexOutOfBoundsException: 969
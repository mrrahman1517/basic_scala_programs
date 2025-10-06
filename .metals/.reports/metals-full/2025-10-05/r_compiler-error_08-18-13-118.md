error id: 070A509E2380FE501035850AD63641DA
file:///private/var/folders/kq/vh9tlv953418bnzfm2tznzyc0000gn/T/metals-scala-cli2808905010051637371/.scala-build/metals-scala-cli2808905010051637371_a020a4fc86-58da2c31ac/src_generated/main/rational.sc.scala
### java.lang.IndexOutOfBoundsException: 0

occurred in the presentation compiler.



action parameters:
offset: 178
uri: file:///private/var/folders/kq/vh9tlv953418bnzfm2tznzyc0000gn/T/metals-scala-cli2808905010051637371/.scala-build/metals-scala-cli2808905010051637371_a020a4fc86-58da2c31ac/src_generated/main/rational.sc.scala
text:
```scala


final class rational$_ {
def args = rational_sc.args$
def scriptPath = """<WORKSPACE>/rational.sc"""
/*<script>*/
object rationals {
    val x@@new Rational(1, 2)
}

class Rational(x: Int, y: Int) {
    def numer = x
    def denom = y
}

//var r = new Rational(1,2)}
```


presentation compiler configuration:
Scala version: 3.7.2-bin-nonbootstrapped
Classpath:
/private/var/folders/kq/vh9tlv953418bnzfm2tznzyc0000gn/T/metals-scala-cli2808905010051637371/.scala-build/metals-scala-cli2808905010051637371_a020a4fc86-58da2c31ac/classes/main [exists ], /opt/homebrew/Cellar/scala/3.7.2/libexec/maven2/org/scala-lang/scala3-library_3/3.7.2/scala3-library_3-3.7.2.jar [exists ], /opt/homebrew/Cellar/scala/3.7.2/libexec/maven2/org/scala-lang/scala-library/2.13.16/scala-library-2.13.16.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/sourcegraph/semanticdb-javac/0.10.0/semanticdb-javac-0.10.0.jar [exists ], /private/var/folders/kq/vh9tlv953418bnzfm2tznzyc0000gn/T/metals-scala-cli2808905010051637371/.scala-build/metals-scala-cli2808905010051637371_a020a4fc86-58da2c31ac/classes/main/META-INF/best-effort [missing ]
Options:
-Xsemanticdb -sourceroot <WORKSPACE> -Ywith-best-effort-tasty




#### Error stacktrace:

```
scala.collection.LinearSeqOps.apply(LinearSeq.scala:131)
	scala.collection.LinearSeqOps.apply$(LinearSeq.scala:128)
	scala.collection.immutable.List.apply(List.scala:79)
	dotty.tools.pc.InterCompletionType$.inferType(InferExpectedType.scala:94)
	dotty.tools.pc.InterCompletionType$.inferType(InferExpectedType.scala:62)
	dotty.tools.pc.completions.Completions.advancedCompletions(Completions.scala:523)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:122)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:139)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:194)
```
#### Short summary: 

java.lang.IndexOutOfBoundsException: 0
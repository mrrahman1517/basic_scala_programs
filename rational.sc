
class Rational(x: Int, y: Int) {
    def numer = x
    def denom = y

    def add(that: Rational) = {
        new Rational(
            numer * that.denom + that.numer * denom,
            denom * that.denom
        )
    }

    /*def sub(that: Rational) = {
        new Rational(
            numer * that.denom - that.numer * denom,
            denom * that.denom
        )
    }*/

    def sub(that: Rational): Rational = 
        add(that.neg)

    def mul(that: Rational) = {
        new Rational(
            numer * that.numer,
            denom * that.denom
        )
    }

    def neg: Rational = {
        new Rational(
            -numer,
            denom
        )
    }

    def equal(that: Rational) = {
        numer * that.denom == that.numer * denom
    }

    def reduce() = {
        var g = gcd(numer, denom)
        new Rational(
            numer / g,
            denom / g
        )
    }

    override def toString = numer + "/" + denom
}

def addRational(r: Rational, s: Rational): Rational = 
    new Rational(r.numer * s.denom + s.numer * r.denom, r.denom * s.denom)

def gcd(m: Int, n: Int): Int = {
    if (n == 0) then m 
    else gcd(n, m % n)
}

assert(gcd(12,18) == 6)

def reduceRational(r: Rational) = {
    var g = gcd(r.numer, r.denom)
    new Rational(r.numer / g, r.denom / g)
}

def makeString(r: Rational) = {
    r.numer + "/" + r.denom
}

val x = new Rational(1, 3)
val y = new Rational(5, 7)
val z = new Rational(3, 2)

println (x.add(y).mul(z).toString())
println (x.add(y).mul(z).reduce().toString())

assert(x.add(y).mul(z).equal(x.mul(z).add(y.mul(z))))

var r = new Rational(1,4)
val s = new Rational(3,4)
val sum = new Rational(1,1)
//val reducedSum = reduceRational(addRational(r,s))
val reducedSum = r.add(s).reduce()

assert(reducedSum.numer == sum.numer)
assert(reducedSum.denom == sum.denom)

assert(r.neg.numer == -1)
//assert(r.neg.denom == -4)
assert(s.sub(r).reduce().numer == 1)
assert(s.sub(r).reduce().denom == 2)

//println(makeString(addRational(new Rational(1, 2), new Rational(2, 3))))
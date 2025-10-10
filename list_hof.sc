
def scaleList(xs: List[Double], factor: Double): List[Double] = xs match {
    case Nil => xs
    case y :: ys => y * factor :: scaleList(ys, factor)
}

println(scaleList(List(1, 2, 3), 3))

def mscaleList(xs: List[Double], factor: Double): List[Double] = 
    xs.map(x => x * factor)

println(scaleList(List(1, 2, 3), 3))



import org.scalatest._
import org.scalatest.matchers.ShouldMatchers

class LaplaceTest extends FunSuite with Matchers {

  test("(Java) single threaded laplace performance test") {

    runTestsOn("single threaded laplace", JLaplace.laplace)
  }

  test("single threaded laplace performance test") {

    runTestsOn("single threaded laplace", Laplace.laplace)
  }

  test("parallel laplace performance test") {

    runTestsOn("parallel laplace", Laplace.parallelLaplace)
  }

  def runTestsOn(named: String, laplaceFunc: (Array[Array[Double]], Double, Double) => Unit): Unit = {

    val (dx, dy) = (0.1, 0.1)

    val (dx2, dy2) = (dx*dx, dy*dy)

    val size = 15000
    val arr = Array.fill(size)(
      Array.fill(size)(0.0)
    )
    resetArr(arr)

    // Warm up the JIT
    (1 to 10).foreach { _ =>
      laplaceFunc(arr, dx2, dy2)
    }

    resetArr(arr)

    // Run the actual tests
    val iterations = 10
    val duration = timeToRun {
      (1 to iterations).foreach { _ => laplaceFunc(arr, dx2, dy2) }
    }

    print("Avg time per iteration for %s: %2.2f".format(named, duration / iterations.toDouble))
  }

  def resetArr(arr: Array[Array[Double]]): Unit = {

    for (i <- arr.indices;
         j <- arr(i).indices) {

      arr(i)(j) = 0
    }

    // Set the first row to all 1's
    for (i <- arr(0).indices) {arr(0)(i) = 1.0}
  }

  def timeToRun(func: => Unit): Long = {

    val start = System.currentTimeMillis()
    func
    System.currentTimeMillis() - start
  }

}

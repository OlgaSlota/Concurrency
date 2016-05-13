object tutorial {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  ////////////////////////////////////////////
  // 1. Evaluation "by name" and "by value" //
  ////////////////////////////////////////////
  // def vs. val
  def loop: Boolean = true                        //> loop: => Boolean
  def x1 = loop                                   //> x1: => Boolean
  val x2 = loop   // uncomment and try!
  // both parameters are evaluated "by value"
  def sqr(x: Double, y: Boolean) = x * x          //> sqr: (x: Double, y: Boolean)Double
  // "=>" means that the parameter will be evaluated "by name" (when referenced)
  def sqr2(x: Double, y: => Boolean) = x * x      //> sqr2: (x: Double, y: => Boolean)Double
 sqr(2, loop)    // uncomment and try!
  sqr2(2, loop)                                   //> res0: Double = 4.0
  //////////////////////
  // 2. if expression //
  //////////////////////
  // in Scala, "if" is an expression (has value)
  def abs(x: Double) = if (x < 0) -x else x       //> abs: (x: Double)Double
  abs(-2)                                         //> res1: Double = 2.0
  abs(2)                                          //> res2: Double = 2.0
  abs(-5)
  /////////////////////////
  // 3. Blocks: { .... } //
  /////////////////////////
  // Blocks may contain a sequence of definitions or expressions.
  // - Definitions are only visible inside the block
  // - Last element of the block must be an expression whose value is also the value of the block.
  // Example: tail-recursive factorial

  def fact(n: Int): Int = {
    def loop(acc: Int, n: Int): Int =
      if (n == 0) acc
      else loop(acc * n, n - 1)

    loop(1, n)
  }                                               //> fact: (n: Int)Int

  fact(6)                                         //> res3: Int = 720

  /////////////////////////////
  // 4. High-order functions //
  /////////////////////////////

  // Excerise 1: write a function that computes a sum of all integers between a and b (use recursion)
  def sumInts(a: Int, b: Int): Int = {
    def loop(sum: Int, a: Int, b: Int): Int =
      if (a > b ) sum
      else loop(sum + a, a+1 ,b)
    loop(0, a,b)
  }
  sumInts(1,5)//> sumInts: (a: Int, b: Int)Int

  // Excersise 2: now write a function that computes a sum of *squares* of all integers between a and b
  def sumSquares(a: Int, b: Int): Int ={
    def loop(sum: Int, a: Int, b: Int): Int =
      if (a > b ) sum
      else loop(sum +a*a, a+1 ,b)

    loop(0, a,b)
  }
  sumSquares(1,5)     //> sumSquares: (a: Int, b: Int)Int
  // Can this be generalized to general SUM(f(x)) of all integers a <= x <= b
  // We can pass function f as a third argument. Type of this function will be "Int => Int"
  // which means it takes Int and returns Int
  // Excercise 3: implement the generalized function:
  def sum(a: Int, b: Int, f: Int => Int): Int = {
    def loop(sum: Int, a: Int, b: Int): Int =
      if (a > b ) sum
      else loop(sum +f(a), a+1 ,b)

    loop(0, a,b)
  }
                                                    //> sum: (a: Int, b: Int, f: Int => Int)Int
  // now we can write (note how anonymous functions are passed as parameters):
  def sumInts2(a: Int, b: Int) = sum(a, b, (x: Int) => x)

  sumInts2(1,5)
                                                  //> sumInts2: (a: Int, b: Int)Int
  def sumSquares2(a: Int, b: Int) = sum(a, b, (x: Int) => x * x)

  sumSquares2(1,5)                                                //> sumSquares2: (a: Int, b: Int)Int

  def sumFactorials(a: Int, b: Int) = sum(a, b, fact)

  sumFactorials2(1,3)                                                //> sumFactorials: (a: Int, b: Int)Int

  ///////////////////////////////////////
  // 4a. Functions returning functions //
  ///////////////////////////////////////

  // Function "sum" can also *return* a new "specialized" function:
    def sum2(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int =
      if (a > b) 0
      else f(a) + sumF(a + 1, b)
    sumF // returned value is function sumF
  }                                               //> sum2: (f: Int => Int)(Int, Int) => Int

  // the equivalent syntax is:
  // def sum2(f: Int => Int)(Int, Int): Int

  // we can now write:
  def sumInts3 = sum2(x => x)                     //> sumInts3: => (Int, Int) => Int
  def sumSquares3 = sum2(x => x * x)              //> sumSquares3: => (Int, Int) => Int
  def sumFactorials2 = sum2(fact)                 //> sumFactorials2: => (Int, Int) => Int

  // or use sum2 directly:
  sum2(x => x)(1, 4)                              //> res4: Int = 10
  sum2(x => x * x)(1, 4)                          //> res5: Int = 30

  // We could even further generalize this example by writing a function that computes not only sum:
  def mapReduce(mapF: Int => Int, reduceF: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int =
    if (a > b) zero
    else reduceF(mapF(a), mapReduce(mapF, reduceF, zero)(a + 1, b))
                                                  //> mapReduce: (mapF: Int => Int, reduceF: (Int, Int) => Int, zero: Int)(a: Int
                                                  //| , b: Int)Int
  mapReduce(x => x, (x, y) => x + y, 0)(1, 4)     //> res6: Int = 10
}
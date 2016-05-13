import scala.util.{ Try, Success, Failure }

object tutorial3 {

  class Rational(val numer: Int, val denom: Int) {

    require(denom != 0) // precondition for the main constructor

    // another constructor can be added like this:
    def this(x: Int) = this(x, 1)

    // adds two Rational numbers and returns a new Rational which represents the sum
    def +(that: Rational) =
      new Rational(
        this.numer * that.denom + that.denom * this.denom,
        this.denom * that.denom)

    override def toString = numer + "/" + denom
  }

  //////////////////////////////////////
  // 1. Companion objects for classes //
  //////////////////////////////////////

  // let's define a companion object for the class Rational
  object Rational {
    def apply(x: Int, y: Int) = new Rational(x, y)
    def apply(x: Int) = new Rational(x)
  }

  // *** Note ***
  // object with method apply is automatically a function
  // F(x) actually means F.apply(x)
  // In fact, every function is translated to an object of class Function1, Function2, ...
  // depending on the number of arguments.

  // now we can write:
  val r1 = Rational(1, 2)                         //> r1  : tutorial3.Rational = 1/2

  // *** Note ***
  // Companion objects can be used where static classes were used in Java
  // Typical use is an object factory.

  ///////////////////////////////////////////
  // 2. Exception handling: scala.util.Try //
  ///////////////////////////////////////////

  // documentation: http://www.scala-lang.org/files/archive/nightly/docs/library/index.html#scala.util.Try
  
  //val r2 = Rational(1, 0)
  val r3 = Try(Rational(1, 0))                    //> r3  : scala.util.Try[tutorial3.Rational] = Failure(java.lang.IllegalArgumen
                                                  //| tException: requirement failed)

  // pattern matching -> very powerful feature
  r3 match {
    case Success(x) => println(x)
    case Failure(err) => println("Error: " + err)
  }                                               //> Error: java.lang.IllegalArgumentException: requirement failed

  println("Reached!")                             //> Reached!

  //////////////////////////////////
  // 3. Lists and list operations //
  //////////////////////////////////

  // documentation: http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.List

  // Note that in the below:
  // - type of the list is inferred
  // - a List companion object is used to create the list instance
  var l1 = List(2, 1, 3, 5, 4)                    //> l1  : List[Int] = List(2, 1, 3, 5, 4)

  l1.head                                         //> res0: Int = 2
  l1.tail                                         //> res1: List[Int] = List(1, 3, 5, 4)
  l1.sorted                                       //> res2: List[Int] = List(1, 2, 3, 4, 5)

  // note how pattern matching is used to decompose a list into head and tail
  def sum(l: List[Int]): Int = l match {
    case x :: xs => x + sum(xs)
    case _ => 0
  }                                               //> sum: (l: List[Int])Int

  sum(l1)                                         //> res3: Int = 15

  l1 map (x => 2 * x)                             //> res4: List[Int] = List(4, 2, 6, 10, 8)
  l1 map (2 * _)                                  //> res5: List[Int] = List(4, 2, 6, 10, 8)
  l1 map (2 *)                                    //> res6: List[Int] = List(4, 2, 6, 10, 8)

  List(List(1, 2), List(3), List(4, 5)) flatMap (identity)
                                                  //> res7: List[Int] = List(1, 2, 3, 4, 5)

  l1 reduce ((x, y) => x + y)                     //> res8: Int = 15
  l1 reduce (_ + _)                               //> res9: Int = 15

  l1.foldLeft(0)(_ + _)                           //> res10: Int = 15
  l1.sorted.foldLeft(List[Int]())((b, a) => a :: b)
                                                  //> res11: List[Int] = List(5, 4, 3, 2, 1)
  // List(a, b, c, d)
  // foldRight:  (a + (b + (c + (d + 0))))
  // foldLeft:   ((((0 + a) + b) + c) + d)

  l1.sorted.foldRight(0)(_ - _)                   //> res12: Int = 3
  l1.sorted.foldLeft(0)(_ - _)                    //> res13: Int = -15

  ///////////////////////////
  // 4. for comprehensions //
  ///////////////////////////

  val lx = List(1, 2, 3)                          //> lx  : List[Int] = List(1, 2, 3)

  val l2 = for {
    x <- lx
  } yield x * x                                   //> l2  : List[Int] = List(1, 4, 9)

  val ly = List(4, 5, 0)                          //> ly  : List[Int] = List(4, 5, 0)
  
  val l3 = for {
    x <- lx      // generator 1
    y <- ly      // generator 2
    if y != 0    // filter
    r = Rational(x, y) // expression
  } yield r                                       //> l3  : List[tutorial3.Rational] = List(1/4, 1/5, 2/4, 2/5, 3/4, 3/5)
  
  // *** Note
  // for comprehension is just a convenient syntax for complex combinations of map/flatMap/filter operations:
  // for (x <- expr_1 if expr_2) yield expr_3
  //     is translated to:
  // expr_1 filter (x => expr_2) map (x => expr_3)
  // With many generators:
  // for (x <- expr_1; y <- expr_2; seq) yield expr_3     (where seq is arbitrary sequence of generators)
  //     is translated to
  // expr_1.flatMap(x => for (y <- expr_2; seq) yield expr_3)
  //
  // ***** any class which contains map/flatMap/filter can be used in for comprehensions *****

}
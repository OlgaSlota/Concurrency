package twscala
import scala.io.Source
import java.io.File
import scala.concurrent.{ExecutionContext, Await, Future}
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.util.Success

object Futures4 extends App {

  // Exercise 1: using futures, implement counting of lines in all files within a directory tree 
  // - use the same file set as in the node.js exercise (PAM08)
  // - counting of lines in all files should be done in parallel (using futures) 
  // - use code snippets below to traverse a directory tree and read files by line
  // - hint: look at the examples from this class
  
  // get an array of all files in a directory tree
  def recursiveListFiles(f: File): Array[File] = {
    val these = f.listFiles
    these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }

  val files = recursiveListFiles(new java.io.File("../PAM08")).filter(_.isFile)

  // convert to a list of full file paths
  val fileNames = files.toList map (_.getAbsolutePath)
  println(fileNames)

  val n: Int = fileNames.length

  val futures = for {
    i <- 0 to n-1
  } yield Future { lines(i) }


  def lines(i: Int) : Int =  {
    var acc : Int =0
    for  ( line <- Source.fromFile(fileNames(i)).getLines) {
      acc += 1
    }
    acc
  }

  val sumF = Future.fold(futures)(0)(_ + _)

  val sum = Await.result(sumF, 5 seconds)

  println(sum)
}



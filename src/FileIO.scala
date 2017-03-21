import java.io.{File, FileWriter}

import scala.collection.mutable
import scala.io.Source

case class Purchase(customerId: String, date: String, credit: String, cvv: String, category: String) {
  override def toString: String = s"Customer: $customerId, Date: $date"
}

object FileIO {
  val purchases = mutable.MutableList[Purchase]()

  def prompt: String = {
    println("Enter category: (Furniture, Alcohol, Food, Shoes, Jewelery)")
    io.StdIn.readLine()
  }

  def filterPurchases: mutable.MutableList[Purchase] = {
    val category: String = prompt
    purchases.filter(_.category.equalsIgnoreCase(category))
  }

  def printFilteredPurchases(temp: mutable.MutableList[Purchase]) = {
    temp.foreach(x => print(x))
  }

  def readFile(filename: String) = {
    Source
      .fromFile(filename)
      .getLines()
      .drop(1)
      .foreach(line => {
        val Array(customerId, date, credit, cvv, category) = line.split(",").map(_.trim)
        purchases += Purchase(customerId, date, credit, cvv, category)
      })
  }

  def saveResults (temp: mutable.MutableList[Purchase] ) = {
    val file = new File("filtered_purchases.prn")
    val fw = new FileWriter(file)
    fw.write(temp.toString())
    fw.close()
  }

  def main(args: Array[String]): Unit = {

    readFile("be594d3c-purchases.csv")
    val results = filterPurchases
    printFilteredPurchases(results)
    saveResults(results)
  }
}
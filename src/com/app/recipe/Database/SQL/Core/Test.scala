package com.app.recipe.Database.SQL.Core

sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]


object List {
  def sum(ints: List[Int]) : Int = ints match {
    case Nil => 0
    case Cons(x,xs) => x + sum(xs)
  }
  def tail(ints: List[Int] ) : List[Int] = ints match {
    case Cons(h,t) => t
    case _ => Nil
  }
  def setHead(head: Int, list : List[Int]) : List[Int] = list match {
    case Cons(h,t) => Cons(head, t)
    case _ => Nil
  }
  def drop[A](n: Int, list: List[A] ) : List[A] = list match {
    case Cons(h,t) => {
      if ( n > 0 ) drop( n-1, t)
      else list
    }
    case _ => Nil 
  }
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Cons(h,t) => if ( f(h) ) dropWhile(t, f) else Cons(h,dropWhile(t, f))
    case _ => Nil
  }
  def foldRight[A,B]( as: List[A], z: B )( f: (A,B) => B ) : B = as match {
    case Nil => z
    case Cons(x,xs) => f(x, foldRight(xs,z)(f))
  }
  def length[A](as: List[A]):Int = as match {
    case Nil => 0
    case Cons(x,xs) => 1 + foldRight(xs, 0)((_,_) => length(xs))
  } 
  def foldLeft[A,B](as: List[A], z: B)(f: (B, A) => B): B = as match {
    case Nil => z
    case Cons(x,xs) => f(foldLeft(xs,z)(f), x)
  }
}

object Test extends App {
  val ex1 : List[Double] = Nil
  val ex2 : List[Int] = Cons(1, Nil)
  val ex3 : List[String] = Cons("a", Cons("b", Nil))
  
  val x : List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Cons(5,Nil)))))
  val xx = List.foldLeft(x, 0)(_+_)
  
//  println(s"xx = $xx")
println(s"reL "+ ( for ( i <- 0 to 10 ) yield i * i))
  val f = new Function2[Int,Int, Int] {
    def apply(a: Int, b: Int) = {
    val r = for ( i <- 0 to b ) yield i * i
    r.last
    }
  }
  
  println(s"result: 2^4 = ${f(2,4)}")
}

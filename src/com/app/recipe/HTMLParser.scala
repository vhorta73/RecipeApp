//
//
//package com.app.recipe
//
//import scala.io.BufferedSource
//
///**
// * After a string is loaded, this object allows any query about its contents.
// */
//object HTMLParser {
//
//  /**
//   * Given a full HTML String, returns a structured HTML object
//   * tree with all the tags found, meta data and data value.
//   */
//  def getHTMLNodes(src: BufferedSource) : List[Any] = {
//
//    // Cleanup the source.
//    var string = src.mkString
//    var testingString : String = ""
//    do {
//        testingString = string
//        string = string.replaceAll("\t"," ")
//        string = string.replaceAll("  "," ")
//        string = string.replaceAll("\n","")
//        string = string.replaceAll("\r","")
//    } while ( testingString != string )
//   
//    var finalTree : List[Any] = Nil
//    var tempString: String = ""
//
//  sealed trait HTMLNodes
//  case class Node(name : String, meta : String, text : String, inner : List[HTMLNodes] ) extends HTMLNodes
//
//  case class EmptyNode() extends HTMLNodes
//
//  var v : List[HTMLNodes] = 
//    List(
//        Node("html","class=\"this\" something","This is the text part", 
//        List(
//            Node("div","some meta", "some text",
//            List(
//                Node("table","","",
//                List(
//                    Node("tr","","Tr1", 
//                    List(
//                        Node("td","","Row1",Nil),
//                        Node("td","","Row2",Nil))),
//                    Node("tr","","Tr2", 
//                    List(
//                        Node("td","","Row1",Nil),
//                        Node("td","","Row2",Nil))))
//            ))
//        ))
//    ))
//  var finalList : List[HTMLNodes] = Nil
////  find("td",v)
////  println("Finding one node: "+finalList)//find("td",v))
//v.foreach { p => p.asInstanceOf[Node].inner.foreach { pp => println(pp) }; println(p) }
////  def find(search : String, node : HTMLNodes) : List[HTMLNodes] = {
//////      var finalList : List[HTMLNodes] = Nil
////    node match {
////      case Node(name, meta, text, inner) => {
////        if ( name.equals(search) ) {
////          println("1node: "+ node)
////          finalList.+:(node)
////        }
////        else if ( inner.eq(EmptyNode()) ) {
////          println("2node: "+ node)
////          finalList
////        }
////        else {
////          find(search, inner)
////        }
////      }
////      case Node(name,meta,text,inner @_*) => {
////        if ( name.equals(search) ) {
////          println("4node: "+ node)
////          finalList.+:(node)
////        }
////        else if ( inner.eq(EmptyNode()) ) {
////          println("5node: "+ node)
////          finalList
////        }
////        else {
////          println("6node: "+ node)
////          for( nod <- inner.toList ) {
////            println("6Xnode: "+ nod)
////            finalList.+:(find(search,nod))
////          }
////          finalList
////        }
////      }
////      case _ => {
////          println("7node: "+ node)
////          finalList
////      }
////    }
////  }
////    
////  def find(search : String, node : HTMLNodes) : Boolean = {
////    node match {
////      case Node(name, meta, text, inner) => {
////        if ( name.equals(search) ) {
////          println("1node: "+ node)
////          true 
////        }
////        else if ( inner.eq(EmptyNode()) ) {
////          println("2node: "+ node)
////          false
////        }
////        else {
////          println("3node: "+ node)
////          find(search, inner)
////        }
////      }
////      case Node(name,meta,text,inner @_*) => {
////        if ( name.equals(search) ) {
////          println("4node: "+ node)
////          true 
////        }
////        else if ( inner.eq(EmptyNode()) ) {
////          println("5node: "+ node)
////          false
////        }
////        else {
////          println("6node: "+ node)
////          for( nod <- inner.toList ) {
////            println("6Xnode: "+ nod)
////            if ( find(search,nod) ) {
////              return true
////            }
////          }
////          false
////        }
////      }
////      case _ => {
////          println("7node: "+ node)
////          false
////      }
////    }
////  }
//
////  test.foreach { p => println(p) }
////list.foreach { p => println(p.toString()) }
//    return Nil
//
//    
////    var string : String = line
//    val startTagRegex = """(<[a-z]+[^ ])|([a-zA-Z0-9"-= :][^<]*[^/><|^\">])|[^>]([a-z /]+[^<>])""".r
//    val lines = startTagRegex.findAllIn(string)
//
//    val tagStartClosed = """<([^/!-])>""".r
//    val tagStartOpen = """<([^/!-]) """.r
//    val tagMeta = "(class)|([=])".r
//    val tagText = "([a-zA-Z0-9.])[^<]+".r
//    val tagEnd = "<[/]".r
//    
//    for ( l <- lines ) {
//      l match {
//        case x if tagStartClosed.pattern.matcher(x).find() => println("SC x["+x+"]")
//        case x if tagStartOpen.pattern.matcher(x).find() => println("SO x["+x+"]")
////        case x if tagEnd.pattern.matcher(x).find() => Nil //println("E "+x)
////        case x if tagMeta.pattern.matcher(x).find() => Nil //println("M "+x)
////        case x if tagText.pattern.matcher(x).find() => Nil //println("T "+x)
//
//        //        case x if x startsWith("</") => println("E "+x)
////        case endTagRegex(start, meta) => println("E "+meta)
//        case _ => Nil //println(l)
//      }
//    }
//    
//    return Nil
////    // Apply fixes until all is fixed
////    var testingString : String = ""
////    do {
////      testingString = string
////      string = string.replaceAll("\t"," ")
////      string = string.replaceAll("  "," ")
////      string = string.replaceAll("\n","")
////    } while ( testingString != string )
////      
//////    var lines = string.split(" |(?=<[a-zA-Z])|(?<=[a-zA-Z]>)|(?=<)")
//////    getHTMLNodes(lines.toList)  
////      Nil
//  }
//
//  /**
//   * Parse Nodes for supplied list of Strings and returns a list of HTMLNodes.
//   * ReturnList is used for iteration process purposes.
//   */
////  private def getHTMLNodes(lines : List[String]) : List[HTMLNode] = {
////    var finalList : List[HTMLNode] = Nil
////    var valueStack : List[String] = Nil
////    var nodeStack : List[HTMLNode] = Nil
////    for( line <- lines ) { 
////      var node = getNode(line)
////      if ( node.node.isInstanceOf[HTMLNode] & node.isStart ) {
////        valueStack = Nil
////        nodeStack = getHTMLNode(line, finalList) ::: nodeStack
//////        println("S Stack : "+nodeStack)
////      }
////      else if ( node.node.isInstanceOf[HTMLNode] & ! node.isStart ) {
////        "Some value" :: valueStack
//////        nodeStack = nodeStack.tail
//////        println("E Stack : "+nodeStack)
////      }
////    }
////    finalList
////  }
//  
//  /**
//   * Parse Nodes for supplied string and return a list of HTMLNodes.
//   */
//  private def getHTMLNode(str : String, returningList : List[HTMLNode]) : List[HTMLNode] = {
////    str match {
////      // The start of a new tag
////      case s if startTagRegex.findFirstIn(s).isDefined => {
////        // Get the starting tag alone
////        var startingTag = startTagRegex.findFirstIn(s).get.stripPrefix("<") 
////
////        // Return the proper case class for this class with the remaining found tags.
////        return returningList.+:(getCaseClassForString(startingTag, returningList))
////      }
////      // The end of a tag, next isMeta
////      case s if endTagRegex.findFirstIn(s).isDefined => {
////        var endingTag = endTagRegex.findFirstIn(s).get.stripSuffix(">"); 
////        return returningList
////      }
////      case s => return List(Value(s))
////    }
//    Nil
//  }
//  
//  /**
//   * For a given string, if it matches a known node returns the node.
//   * Returns empty otherwise.
//   */
////  private def getNode(str : String) : Node = {
////    str match {
////      case s if startTagRegex.findFirstIn(s).isDefined => {
////        // Get the starting tag alone
////        var startingTag = startTagRegex.findFirstIn(s).get.stripPrefix("<") 
////        return Node(getCaseClassForString(startingTag,Nil), true)
////      }
////      // The end of a tag, next isMeta
////      case s if endTagRegex.findFirstIn(s).isDefined => {
////        var endingTag = endTagRegex.findFirstIn(s).get.stripSuffix(">"); 
////        return Node(getCaseClassForString(endingTag,Nil), false)
////      }
////      case _ => return Node(Nil,true)
////    }
////  }
//
//  /**
//   * Returns the respective node or throws an exception.
//   * All nodes passed in, must be known.
//   */
//  private def getCaseClassForString( caseClass : String, parameters : List[HTMLNode] ) : HTMLNode = {
//    caseClass match {
//      case "a"        => return A(parameters)
//      case "abbr"     => return Abbr(parameters)
//      case "body"     => return Body(parameters)
//      case "br"       => return Br(parameters)
//      case "caption"  => return Caption(parameters)
//      case "div"      => return Div(parameters)
//      case "fieldset" => return Fieldset(parameters)
//      case "form"     => return Form(parameters)
//      case "h"        => return H(parameters)
//      case "head"     => return Head(parameters)
//      case "hr"       => return Hr(parameters)
//      case "html"     => return Html(parameters)
//      case "img"      => return Img(parameters)
//      case "input"    => return Input(parameters)
//      case "label"    => return Label(parameters)
//      case "li"       => return Li(parameters)
//      case "link"     => return Link(parameters)
//      case "meta"     => return Meta(parameters)
//      case "p"        => return P(parameters)
//      case "script"   => return Script(parameters)
//      case "span"     => return Span(parameters)
//      case "strong"   => return Strong(parameters)
//      case "style"    => return Style(parameters)
//      case "table"    => return Table(parameters)
//      case "tbody"    => return Tbody(parameters)
//      case "td"       => return Td(parameters)
//      case "textarea" => return Textarea(parameters)
//      case "th"       => return Th(parameters)
//      case "thead"    => return Thead(parameters)
//      case "title"    => return Title(parameters)
//      case "tr"       => return Tr(parameters)
//      case "ul"       => return Ul(parameters)
//
//      case _ => throw new Exception("No case class defined for ["+caseClass+"]. Please define me.")
//    }
//  }
//}
//
///**
// * The HTML structure.
// */
//
//trait HTMLNode// extends HTMLTreeNode
//case class A(str : List[HTMLNode]) extends HTMLNode
//case class Abbr(str : List[HTMLNode]) extends HTMLNode
//case class Body(str : List[HTMLNode]) extends HTMLNode
//case class Br(str : List[HTMLNode]) extends HTMLNode
//case class Caption(str : List[HTMLNode]) extends HTMLNode
//case class Div(str : List[HTMLNode]) extends HTMLNode
//case class Fieldset(str : List[HTMLNode]) extends HTMLNode
//case class Form(str : List[HTMLNode]) extends HTMLNode
//case class H(str : List[HTMLNode]) extends HTMLNode
//case class Head(str : List[HTMLNode]) extends HTMLNode
//case class Hr(str : List[HTMLNode]) extends HTMLNode
//case class Html(str : List[HTMLNode]) extends HTMLNode
//case class Img(str : List[HTMLNode]) extends HTMLNode
//case class Input(str : List[HTMLNode]) extends HTMLNode
//case class Label(str : List[HTMLNode]) extends HTMLNode
//case class Li(str : List[HTMLNode]) extends HTMLNode
//case class Link(str : List[HTMLNode]) extends HTMLNode
//case class Meta(str : List[HTMLNode]) extends HTMLNode
//case class P(str : List[HTMLNode]) extends HTMLNode
//case class Script(str : List[HTMLNode]) extends HTMLNode
//case class Span(str : List[HTMLNode]) extends HTMLNode
//case class Strong(str : List[HTMLNode]) extends HTMLNode
//case class Style(str : List[HTMLNode]) extends HTMLNode
//case class Table(str : List[HTMLNode]) extends HTMLNode
//case class Tbody(str : List[HTMLNode]) extends HTMLNode
//case class Td(str : List[HTMLNode]) extends HTMLNode
//case class Textarea(str : List[HTMLNode]) extends HTMLNode
//case class Th(str : List[HTMLNode]) extends HTMLNode
//case class Thead(str : List[HTMLNode]) extends HTMLNode
//case class Title(str : List[HTMLNode]) extends HTMLNode
//case class Tr(str : List[HTMLNode]) extends HTMLNode
//case class Ul(str : List[HTMLNode]) extends HTMLNode
//
//case class Value(str : String) extends HTMLNode
//
//// Data class for Node manipulation
////case class Node(node : Any, isStart : Boolean)
//
//
//trait HTMLParserTree
//case class ParserHtml(meta : String, text : String) extends HTMLParserTree
//

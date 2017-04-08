//package com.app.recipe
//
//import com.app.recipe.Import.Model.URLBuilderFactory
//import com.app.recipe.Import.Vendor.URL.Model.VendorEnum
//import java.net.URL
//
//object Labirint extends App {
//  println("Hello")
//  
////  val url = new URL("https://www.poundsterlinglive.com/bank-of-england-spot/historical-spot-exchange-rates/gbp/EUR-to-GBP-2016")
//  var url = new URL("https://www.tesco.com/groceries/product/details/?id=268768873")
//  var tesco_search_ingredient = "https://www.tesco.com/groceries/product/search/default.aspx?newSort=true&search=Search&searchBox="
//  url = new URL(tesco_search_ingredient + "corn")
////  url = new URL("https://www.tesco.com/groceries/product/search/default.aspx?newSort=true&amp;search=Search&amp;searchBox=sugar&amp;N=4293372352")
//
//  url = new URL("https://www.tesco.com/groceries/product/details/?id=293953719")
//  val vendor = URLBuilderFactory.get(VendorEnum.TESCO)
////  val src = scala.io.Source.fromURL(vendor.getURLForSerach("Banana"))
//  val src = scala.io.Source.fromURL(vendor.getURLForProductID("275280804"))
//  println(src.mkString)
////  var string = "<table><tr><td>Values Here</td><td>More</td></tr></table>"
//
//  
////  private val startTagRegex = """[<]([a-z]+[^ ])|[ ]([a-z ='"@,-0-9A-Z:/_]+[^>])|[^>]([a-z ]+[^<])|(</[a-z]+[^>])>""".r//(?<=<)([^<>])*""".r//"""<([a-zA-Z]+)""".r
////  private val startTagRegex = """[<]([a-z]+[^ ])|([a-z ='"@,-012346789A-Z;:/+_(){}&;]+[^/><])|[^>]([a-z /]+[^<])""".r
//
////   val startTagRegex = """(<[a-z]+[^ ])|([a-zA-Z0-9"-= :][^<]*[^/><|\">])|[^>]([a-z /]+[^<>])""".r
////   private val endTagRegex = """([a-zA-Z]+)>""".r
//
////  println(HTMLParser.getHTMLNodes(src))
////  val results = startTagRegex.findAllIn(string).toList.foreach { p => println(p) }
////  println(results)//.foreach { p => println(p) }
////  println("Done")
//}
//
////  string = src.mkString
////  println(HTMLParser.getHTMLNodes(string))
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////  val dimension = new Dimension(300, 300)
////  val mainFrame = new MainFrame
////  mainFrame.preferredSize = dimension
////  mainFrame.centerOnScreen()
////  mainFrame.visible = true
////  mainFrame.centerOnScreen()
////  mainFrame.visible = true
////  var framesPerSecond = 30;
////  var times = 0;
////  var startTime = System.currentTimeMillis()
////  var currentTime = System.currentTimeMillis()
////
////  while(times < 1000) {
////    currentTime = System.currentTimeMillis()
////    if ( ( currentTime - startTime ) >= ( 1000 / framesPerSecond ) ) {
////      mainFrame.contents = new DataPanel(new Data().data)
////      times += 1
////      startTime = System.currentTimeMillis()
////      if ( times % 100 == 0 ) println(times)
////    }
////  }
//
////class Data() {
////  val data = Array.ofDim[Color](25,25)
////  data((Math.random()*10).toInt)((Math.random()*10).toInt) = Color.BLACK
////  data((Math.random()*10).toInt)((Math.random()*10).toInt) = Color.RED
////  data((Math.random()*10).toInt)((Math.random()*10).toInt) = Color.GREEN
////  data((Math.random()*10).toInt)((Math.random()*10).toInt) = Color.BLUE  
////}
////
////
////class DataPanel(data: Array[Array[Color]]) extends Panel {
////  override def paintComponent( g: Graphics2D ) {
////    val dx = g.getClipBounds.width.toFloat / data.length
////    val dy = g.getClipBounds.height.toFloat / data.map(_.length).max
////    for {
////      x <- 0 until data.length
////      y <- 0 until data(x).length
////      x1 = (x * dx).toInt
////      y1 = (y * dy).toInt
////      x2 = ((x + 1) * dx).toInt
////      y2 = ((y + 1) * dy).toInt
////    } {
////      data(x)(y) match {
////        case c : Color => g.setColor(c)
////        case _ => g.setColor(Color.WHITE)
////      }
////      g.fillRect(x1, y1, x2-x1, y2-y1)
////    }
////  }
////}
////

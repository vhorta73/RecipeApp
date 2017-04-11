package com.app.recipe.Import.Vendor.TESCO.ProductMatch.Details.Nutrition

import com.app.recipe.Import.Product.Model.ProductNutrition
import com.app.recipe.Import.Product.Units.Model.StandardUnits.Units
import com.app.recipe.Log.RecipeLogging
import com.app.recipe.String.Utils._
import com.app.recipe.Import.Product.Model.Saturates
import com.app.recipe.Import.Product.Units.Model.StandardUnits
import javax.swing.text.rtf.MockAttributeSet
import com.app.recipe.Import.Product.Model.MonoSaturates
import com.app.recipe.Import.Product.Model.PoliSaturates

/**
 * Class to find match the saturation values from a given line to parse.
 * The line may contain more than one saturation levels, which in case will
 * return more than one element.
 */
class MatchSaturates() extends RecipeLogging {

  /**
   * Setting out the strings that make a line with saturation information.
   */
  private final val SATURATES_STRING : String = "saturates"
  
  private final val IGNORE_STRING : String = "ence intake";

  /**
   * All regex that will retrieve the fat value from a given string.
   */
  // Matching: <th scope="row">Of which saturates (g)</th><td>4.2</td>
  private final val SAT_G_01_REGEX = """(?i)<th scope="row">Of which saturates \(g\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">of which: saturates (g)</th><td>4.3</td>
  private final val SAT_G_02_REGEX = """(?i)<th scope="row">of which: saturates \(g\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates (g)</th><td>2.0</td>
  private final val SAT_G_03_REGEX = """(?i)<th scope="row">of which saturates \(g\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates</th><td>0.1g</td>
  private final val SAT_G_04_REGEX = """(?i)<th scope="row">of which saturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching:  <th scope="row">Saturates</th><td>0g</td>
  private final val SAT_G_05_REGEX = """(?i)<th scope="row">Saturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: egligible 
  private final val SAT_G_06_NEGLIGIBLE_REGEX = """(?i)(egligible)""".r.unanchored
  // Matching: <th scope="row">of which saturates:</th><td>0g</td>
  private final val SAT_G_07_REGEX = """(?i)<th scope="row">of which saturates:</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates</th><td>< 0.5 g</td>
  private final val SAT_G_08_REGEX = """(?i)<th scope="row">of which saturates</th><td>< ([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">Of which Saturates</th><td><0.5 g</td>
  private final val SAT_G_09_REGEX = """(?i)<th scope="row">Of which Saturates</th><td><([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">Saturates</th><td>0g</td>
  private final val SAT_G_10_REGEX = """(?i)<th scope="row">Saturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">Saturates</th><td><0.1g</td>
  private final val SAT_G_11_REGEX = """(?i)<th scope="row">Saturates</th><td><([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which Saturates</th><td>8.1 g</td>
  private final val SAT_G_12_REGEX = """(?i)<th scope="row">of which Saturates</th><td>([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates</th><td><0.1g</td>
  private final val SAT_G_13_REGEX = """(?i)<th scope="row">of which saturates</th><td><([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which Saturates</th><td>4.2g</td>
  private final val SAT_G_14_REGEX = """(?i)<th scope="row">of which Saturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates</th><td>9.6 g</td>
  private final val SAT_G_15_REGEX = """(?i)<th scope="row">of which saturates</th><td>([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">Saturates</th><td>Neglibible amount</td>
  private final val SAT_G_16_NEGLIGIBLE_REGEX = """(?i)<th scope="row">Saturates</th><td>N(eglibible) amount</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates:</th><td>0.1 g</td>
  private final val SAT_G_17_REGEX = """(?i)<th scope="row">of which saturates:</th><td>([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates</th><td><0.5 g</td>
  private final val SAT_G_18_REGEX = """(?i)<th scope="row">of which saturates</th><td><([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">- of which saturates</th><td>0.1 g</td>
  private final val SAT_G_19_REGEX = """(?i)<th scope="row">- of which saturates</th><td>([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">Saturates</th><td>0.1 g</td>
  private final val SAT_G_20_REGEX = """(?i)<th scope="row">Saturates</th><td>([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">Of which saturates</th><td>22.3g</td>
  private final val SAT_G_21_REGEX = """(?i)<th scope="row">Of which saturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates</th><td>< 0.1g</td>
  private final val SAT_G_22_REGEX = """(?i)<th scope="row">of which saturates</th><td>< 0.1g</td>""".r.unanchored
  // Matching: <th scope="row">saturates</th><td>0.9g</td>
  private final val SAT_G_23_REGEX = """(?i)<th scope="row">saturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates</th><td>< 0.1g</td>
  private final val SAT_G_24_REGEX = """(?i)<th scope="row">of which saturates</th><td>< ([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which: saturates</th><td>5.1 g</td>
  private final val SAT_G_25_REGEX = """(?i)<th scope="row">of which: saturates</th><td>([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">(of which saturates</th><td>11.7g</td>
  private final val SAT_G_26_REGEX = """(?i)<th scope="row">\(of which saturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">(of which saturates)</th><td>5.7g</td>
  private final val SAT_G_27_REGEX = """(?i)<th scope="row">\(of which saturates\)</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates, g</th><td>0</td>
  private final val SAT_G_28_REGEX = """(?i)<th scope="row">of which saturates, g</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">- of which saturates</th><td>0.5g</td>
  private final val SAT_G_29_REGEX = """(?i)<th scope="row">- of which saturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates g</th><td>3,5</td>
  private final val SAT_G_30_COMMA_REGEX = """(?i)<th scope="row">of which saturates g</th><td>([0-9.,]+)</td>""".r.unanchored
  // Matching: <th scope="row">Of which saturates</th><td><0.1g</td>
  private final val SAT_G_31_REGEX = """(?i)<th scope="row">Of which saturates</th><td><([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">(of which saturates</th><td>4.2g)</td>
  private final val SAT_G_32_REGEX = """(?i)<th scope="row">\(of which saturates</th><td>([0-9.]+)g\)</td>""".r.unanchored
  // Matching: <th scope="row">(of which saturates</th><td>0 g)</td>
  private final val SAT_G_33_REGEX = """(?i)<th scope="row">\(of which saturates</th><td>([0-9.]+) g\)</td>""".r.unanchored
  // Matching: <th scope="row">of which: saturates</th><td>10.5g</td>
  private final val SAT_G_34_REGEX = """(?i)<th scope="row">of which: saturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">- of which saturates</th><td><0,1 g</td>
  private final val SAT_G_35_COMMA_REGEX = """(?i)<th scope="row">- of which saturates</th><td><([0-9.,]+) g</td>""".r.unanchored
  // Matching: <th scope="row">of which: saturates</th><td>16.6g</td>
  private final val SAT_G_36_REGEX = """(?i)<th scope="row">of which: saturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which Saturates</th><td>0.8g(4%*)</td>
  private final val SAT_G_37_REGEX = """(?i)<th scope="row">of which Saturates</th><td>([0-9.]+)g\([0-9%*]+\)</td>""".r.unanchored
  // Matching: <th scope="row">-of which saturates</th><td>0.1g</td>
  private final val SAT_G_38_REGEX = """(?i)<th scope="row">-of which saturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates</th><td>12,0 g</td>
  private final val SAT_G_39_COMMA_REGEX = """(?i)<th scope="row">of which saturates</th><td>([0-9.,]+) g</td>""".r.unanchored
  // Matching: <th scope="row">of which Saturates</th><td>Trace</td>
  private final val SAT_G_40_TRACE_REGEX = """(?i)<th scope="row">of which Saturates</th><td>(Trace)</td>""".r.unanchored
  // Matching: <th scope="row">- of which saturates:</th><td>1.29g</td>
  private final val SAT_G_41_REGEX = """(?i)<th scope="row">- of which saturates:</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">Of which Saturates</th><td>2.3 g</td>
  private final val SAT_G_42_REGEX = """(?i)<th scope="row">Of which Saturates</th><td>([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">Of which Saturates (g)</th><td>1.8</td>
  private final val SAT_G_43_REGEX = """(?i)<th scope="row">Of which Saturates \(g\)</th><td>([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates</th><td>trace</td>
  private final val SAT_G_44_TRACE_REGEX = """(?i)<th scope="row">of which saturates</th><td>(trace)</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates</th><td>Nil</td>
  private final val SAT_G_45_NIL_REGEX = """(?i)<th scope="row">of which saturates</th><td>(Nil)</td>""".r.unanchored
  // Matching: <th scope="row">of which Saturates</th><td>0,00 g</td>
  private final val SAT_G_46_COMMA_REGEX = """(?i)<th scope="row">of which Saturates</th><td>([0-9.,]+) g</td>""".r.unanchored
  // Matching: <th scope="row">- of which saturates</th><td>Trace</td>
  // Matching: <th scope="row">-of which saturates</th><td>Trace</td>
  private final val SAT_G_47_TRACE_REGEX = """(?i)<th scope="row">[- ]+of which saturates</th><td>(Trace)</td>""".r.unanchored
  // Matching: <th scope="row">(Of which Saturates</th><td>0 g)</td>
  private final val SAT_G_48_REGEX = """(?i)<th scope="row">\(Of which Saturates</th><td>([0-9.]+) g\)</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates:</th><td>Trace</td>
  private final val SAT_G_49_TRACE_REGEX = """(?i)<th scope="row">of which saturates:</th><td>(Trace)</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates (g)</th><td><0.1</td>
  private final val SAT_G_50_REGEX = """(?i)<th scope="row">of which saturates \(g\)</th><td><([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">(of which saturates)</th><td>(Trace)</td>
  private final val SAT_G_51_TRACE_REGEX = """(?i)<th scope="row">\(of which saturates\)</th><td>\((Trace)\)</td>""".r.unanchored
  // Matching: <th scope="row">of which saturates (g)</th><td><0.1</td>
  private final val SAT_G_52_TRACE_REGEX = """(?i)<th scope="row">of which saturates \(g\)</th><td><([0-9.]+)</td>""".r.unanchored
  // Matching: <th scope="row">of which Saturates</th><td>0.3 g (1%*)</td>
  // Matching: <th scope="row">of which saturates</th><td>0.3 g (2%*)</td>
  private final val SAT_G_53_REGEX = """(?i)<th scope="row">of which Saturates</th><td>([0-9.]+) g \([0-9%*]+\)</td>""".r.unanchored
  // Matching: <th scope="row">of which, saturates</th><td>0.6g</td>
  private final val SAT_G_54_REGEX = """(?i)<th scope="row">of which, saturates</th><td>([0-9.]+)g</td>""".r.unanchored

  // Matching: <th scope="row">mono-unsaturates</th><td><0.5 g</td>
  private final val MONO_SAT_G_01_REGEX = """(?i)<th scope="row">mono-unsaturates</th><td><([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">mono-unsaturates</th><td>1.1g</td>
  private final val MONO_SAT_G_02_REGEX = """(?i)<th scope="row">mono-unsaturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">monounsaturates</th><td>1.1g</td>
  private final val MONO_SAT_G_03_REGEX = """(?i)<th scope="row">monounsaturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which Mono-unsaturates</th><td>7.8g</td>
  private final val MONO_SAT_G_04_REGEX = """(?i)<th scope="row">of which Mono-unsaturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">Mono-Unsaturates</th><td>26.5g</td>
  private final val MONO_SAT_G_05_REGEX = """(?i)<th scope="row">Mono-Unsaturates</th><td>([0-9.]+)g</td>""".r.unanchored

  // Matching: <th scope="row">polyunsaturates</th><td>1.0 g</td>
  private final val POLI_SAT_G_01_REGEX = """(?i)<th scope="row">polyunsaturates</th><td>([0-9.]+) g</td>""".r.unanchored
  // Matching: <th scope="row">polyunsaturates</th><td>1.1g</td>
  private final val POLI_SAT_G_02_REGEX = """(?i)<th scope="row">polyunsaturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">of which Polyunsaturates</th><td>1.3g</td>
  private final val POLI_SAT_G_03_REGEX = """(?i)<th scope="row">of which Polyunsaturates</th><td>([0-9.]+)g</td>""".r.unanchored
  // Matching: <th scope="row">Polyunsaturates</th><td>2.3g</td>
  private final val POLI_SAT_G_04_REGEX = """(?i)<th scope="row">Polyunsaturates</th><td>([0-9.]+)g</td>""".r.unanchored

  /**
   * Returns the Saturation case class with the detailed values as displaying on the web page.
   */
  def getMatch(productString : String) : List[ProductNutrition] = {

    // Adding all elements to this list which is to be returned last.
    var finalList : List[ProductNutrition] = Nil
    
    productString match {
      case SAT_G_01_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_02_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_03_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_04_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_05_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_06_NEGLIGIBLE_REGEX(negli)   => finalList = finalList ::: List(Saturates(0,StandardUnits.g))
      case SAT_G_07_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_08_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_09_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_10_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_11_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_12_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_13_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_14_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_15_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_16_NEGLIGIBLE_REGEX(negli)   => finalList = finalList ::: List(Saturates(0,StandardUnits.g))
      case SAT_G_17_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_18_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_19_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_20_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_21_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_22_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_23_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_24_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_25_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_26_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_27_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_28_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_29_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_30_COMMA_REGEX(sat)          => finalList = finalList ::: List(Saturates(sat.replaceAll(",", "").toDouble,StandardUnits.g))
      case SAT_G_31_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_32_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_33_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_34_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_35_COMMA_REGEX(sat)          => finalList = finalList ::: List(Saturates(sat.replaceAll(",", "").toDouble,StandardUnits.g))
      case SAT_G_36_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_37_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_38_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_39_COMMA_REGEX(sat)          => finalList = finalList ::: List(Saturates(sat.replaceAll(",", "").toDouble,StandardUnits.g))
      case SAT_G_40_TRACE_REGEX(trace)        => finalList = finalList ::: List(Saturates(0,StandardUnits.g))
      case SAT_G_41_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_42_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_43_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_44_TRACE_REGEX(trace)        => finalList = finalList ::: List(Saturates(0,StandardUnits.g))
      case SAT_G_45_NIL_REGEX(trace)          => finalList = finalList ::: List(Saturates(0,StandardUnits.g))
      case SAT_G_46_COMMA_REGEX(sat)          => finalList = finalList ::: List(Saturates(sat.replaceAll(",", "").toDouble,StandardUnits.g))
      case SAT_G_47_TRACE_REGEX(trace)        => finalList = finalList ::: List(Saturates(0,StandardUnits.g))
      case SAT_G_48_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_49_TRACE_REGEX(trace)        => finalList = finalList ::: List(Saturates(0,StandardUnits.g))
      case SAT_G_50_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_51_TRACE_REGEX(trace)        => finalList = finalList ::: List(Saturates(0,StandardUnits.g))
      case SAT_G_52_TRACE_REGEX(trace)        => finalList = finalList ::: List(Saturates(0,StandardUnits.g))
      case SAT_G_53_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))
      case SAT_G_54_REGEX(sat)                => finalList = finalList ::: List(Saturates(sat.toDouble,StandardUnits.g))

      case MONO_SAT_G_01_REGEX(sat)           => finalList = finalList ::: List(MonoSaturates(sat.toDouble,StandardUnits.g))
      case MONO_SAT_G_02_REGEX(sat)           => finalList = finalList ::: List(MonoSaturates(sat.toDouble,StandardUnits.g))
      case MONO_SAT_G_03_REGEX(sat)           => finalList = finalList ::: List(MonoSaturates(sat.toDouble,StandardUnits.g))
      case MONO_SAT_G_04_REGEX(sat)           => finalList = finalList ::: List(MonoSaturates(sat.toDouble,StandardUnits.g))
      case MONO_SAT_G_05_REGEX(sat)           => finalList = finalList ::: List(MonoSaturates(sat.toDouble,StandardUnits.g))

      case POLI_SAT_G_01_REGEX(sat)           => finalList = finalList ::: List(PoliSaturates(sat.toDouble,StandardUnits.g))
      case POLI_SAT_G_02_REGEX(sat)           => finalList = finalList ::: List(PoliSaturates(sat.toDouble,StandardUnits.g))
      case POLI_SAT_G_03_REGEX(sat)           => finalList = finalList ::: List(PoliSaturates(sat.toDouble,StandardUnits.g))
      case POLI_SAT_G_04_REGEX(sat)           => finalList = finalList ::: List(PoliSaturates(sat.toDouble,StandardUnits.g))

      case _ => warn(s"No Saturation Matched $productString")
    }
    
    finalList
  }

  /**
   * Matching criteria for fat on a given line. It must match only line that
   * are certain of containing the saturation values. By default returns false.
   */
  def isSaturates(productString : String) : Boolean = {
    productString match {
      case str if str.toLowerCase().contains(IGNORE_STRING) => false
      case str if str.toLowerCase().contains(SATURATES_STRING) => true
      case _ => false
    }
  }
}
package com.app.recipe.Import.Vendor.USDA.NutrientsReport

import scala.collection.immutable.HashMap

/**
 * The Nutrient details based on https://www.ars.usda.gov nutrient food definitions.
 */
case class NutrientDefinition(
    val nutriend_id          : String // Unique nutrient identifier
  , val units                : String // Measure in mg, g ug etc
  , val tag_name             : String // International Network of Food Data Systems standard
  , val nutrient_description : String // Name of nutrient / food
  , val num_rounded_decimals : String // Number of decimal places value is rounded by
  , val sr_order             : String // SR sorting order
)


/**
 * The factory to return the respective id for USDA query, from a Nutrient
 * name supplied.
 */
object NutrientFactory {

  /**
   * All nutrient definitions imported from the USDA definitions table.
   */
  private final val HASH : HashMap[String,NutrientDefinition] = HashMap(
      "203" -> NutrientDefinition("203","g","PROCNT","Protein","2","600")
    , "204" -> NutrientDefinition("204","g","FAT","Total lipid (fat)","2","800")
    , "205" -> NutrientDefinition("205","g","CHOCDF","Carbohydrate, by difference","2","1100")
    , "207" -> NutrientDefinition("207","g","ASH","Ash","2","1000")
    , "208" -> NutrientDefinition("208","kcal","ENERC_KCAL","Energy","0","300")
    , "209" -> NutrientDefinition("209","g","STARCH","Starch","2","2200")
    , "210" -> NutrientDefinition("210","g","SUCS","Sucrose","2","1600")
    , "211" -> NutrientDefinition("211","g","GLUS","Glucose (dextrose)","2","1700")
    , "212" -> NutrientDefinition("212","g","FRUS","Fructose","2","1800")
    , "213" -> NutrientDefinition("213","g","LACS","Lactose","2","1900")
    , "214" -> NutrientDefinition("214","g","MALS","Maltose","2","2000")
    , "221" -> NutrientDefinition("221","g","ALC","Alcohol, ethyl","1","18200")
    , "255" -> NutrientDefinition("255","g","WATER","Water","2","100")
    , "257" -> NutrientDefinition("257","g","","Adjusted Protein","2","700")
    , "262" -> NutrientDefinition("262","mg","CAFFN","Caffeine","0","18300")
    , "263" -> NutrientDefinition("263","mg","THEBRN","Theobromine","0","18400")
    , "268" -> NutrientDefinition("268","kJ","ENERC_KJ","Energy","0","400")
    , "269" -> NutrientDefinition("269","g","SUGAR","Sugars, total","2","1500")
    , "287" -> NutrientDefinition("287","g","GALS","Galactose","2","2100")
    , "291" -> NutrientDefinition("291","g","FIBTG","Fiber, total dietary","1","1200")
    , "301" -> NutrientDefinition("301","mg","CA","Calcium, Ca","0","5300")
    , "303" -> NutrientDefinition("303","mg","FE","Iron, Fe","2","5400")
    , "304" -> NutrientDefinition("304","mg","MG","Magnesium, Mg","0","5500")
    , "305" -> NutrientDefinition("305","mg","P","Phosphorus, P","0","5600")
    , "306" -> NutrientDefinition("306","mg","K","Potassium, K","0","5700")
    , "307" -> NutrientDefinition("307","mg","NA","Sodium, Na","0","5800")
    , "309" -> NutrientDefinition("309","mg","ZN","Zinc, Zn","2","5900")
    , "312" -> NutrientDefinition("312","mg","CU","Copper, Cu","3","6000")
    , "313" -> NutrientDefinition("313","ug","FLD","Fluoride, F","1","6240")
    , "315" -> NutrientDefinition("315","mg","MN","Manganese, Mn","3","6100")
    , "317" -> NutrientDefinition("317","ug","SE","Selenium, Se","1","6200")
    , "318" -> NutrientDefinition("318","IU","VITA_IU","Vitamin A, IU","0","7500")
    , "319" -> NutrientDefinition("319","ug","RETOL","Retinol","0","7430")
    , "320" -> NutrientDefinition("320","ug","VITA_RAE","Vitamin A, RAE","0","7420")
    , "321" -> NutrientDefinition("321","ug","CARTB","Carotene, beta","0","7440")
    , "322" -> NutrientDefinition("322","ug","CARTA","Carotene, alpha","0","7450")
    , "323" -> NutrientDefinition("323","mg","TOCPHA","Vitamin E (alpha-tocopherol)","2","7900")
    , "324" -> NutrientDefinition("324","IU","VITD","Vitamin D","0","8750")
    , "325" -> NutrientDefinition("325","ug","ERGCAL","Vitamin D2 (ergocalciferol)","1","8710")
    , "326" -> NutrientDefinition("326","ug","CHOCAL","Vitamin D3 (cholecalciferol)","1","8720")
    , "328" -> NutrientDefinition("328","ug","VITD","Vitamin D (D2 + D3)","1","8700")
    , "334" -> NutrientDefinition("334","ug","CRYPX","Cryptoxanthin, beta","0","7460")
    , "337" -> NutrientDefinition("337","ug","LYCPN","Lycopene","0","7530")
    , "338" -> NutrientDefinition("338","ug","LUT+ZEA","Lutein + zeaxanthin","0","7560")
    , "341" -> NutrientDefinition("341","mg","TOCPHB","Tocopherol, beta","2","8000")
    , "342" -> NutrientDefinition("342","mg","TOCPHG","Tocopherol, gamma","2","8100")
    , "343" -> NutrientDefinition("343","mg","TOCPHD","Tocopherol, delta","2","8200")
    , "344" -> NutrientDefinition("344","mg","TOCTRA","Tocotrienol, alpha","2","8300")
    , "345" -> NutrientDefinition("345","mg","TOCTRB","Tocotrienol, beta","2","8400")
    , "346" -> NutrientDefinition("346","mg","TOCTRG","Tocotrienol, gamma","2","8500")
    , "347" -> NutrientDefinition("347","mg","TOCTRD","Tocotrienol, delta","2","8600")
    , "401" -> NutrientDefinition("401","mg","VITC","Vitamin C, total ascorbic acid","1","6300")
    , "404" -> NutrientDefinition("404","mg","THIA","Thiamin","3","6400")
    , "405" -> NutrientDefinition("405","mg","RIBF","Riboflavin","3","6500")
    , "406" -> NutrientDefinition("406","mg","NIA","Niacin","3","6600")
    , "410" -> NutrientDefinition("410","mg","PANTAC","Pantothenic acid","3","6700")
    , "415" -> NutrientDefinition("415","mg","VITB6A","Vitamin B-6","3","6800")
    , "417" -> NutrientDefinition("417","ug","FOL","Folate, total","0","6900")
    , "418" -> NutrientDefinition("418","ug","VITB12","Vitamin B-12","2","7300")
    , "421" -> NutrientDefinition("421","mg","CHOLN","Choline, total","1","7220")
    , "428" -> NutrientDefinition("428","ug","MK4","Menaquinone-4","1","8950")
    , "429" -> NutrientDefinition("429","ug","VITK1D","Dihydrophylloquinone","1","8900")
    , "430" -> NutrientDefinition("430","ug","VITK1","Vitamin K (phylloquinone)","1","8800")
    , "431" -> NutrientDefinition("431","ug","FOLAC","Folic acid","0","7000")
    , "432" -> NutrientDefinition("432","ug","FOLFD","Folate, food","0","7100")
    , "435" -> NutrientDefinition("435","ug","FOLDFE","Folate, DFE","0","7200")
    , "454" -> NutrientDefinition("454","mg","BETN","Betaine","1","7270")
    , "501" -> NutrientDefinition("501","g","TRP_G","Tryptophan","3","16300")
    , "502" -> NutrientDefinition("502","g","THR_G","Threonine","3","16400")
    , "503" -> NutrientDefinition("503","g","ILE_G","Isoleucine","3","16500")
    , "504" -> NutrientDefinition("504","g","LEU_G","Leucine","3","16600")
    , "505" -> NutrientDefinition("505","g","LYS_G","Lysine","3","16700")
    , "506" -> NutrientDefinition("506","g","MET_G","Methionine","3","16800")
    , "507" -> NutrientDefinition("507","g","CYS_G","Cystine","3","16900")
    , "508" -> NutrientDefinition("508","g","PHE_G","Phenylalanine","3","17000")
    , "509" -> NutrientDefinition("509","g","TYR_G","Tyrosine","3","17100")
    , "510" -> NutrientDefinition("510","g","VAL_G","Valine","3","17200")
    , "511" -> NutrientDefinition("511","g","ARG_G","Arginine","3","17300")
    , "512" -> NutrientDefinition("512","g","HISTN_G","Histidine","3","17400")
    , "513" -> NutrientDefinition("513","g","ALA_G","Alanine","3","17500")
    , "514" -> NutrientDefinition("514","g","ASP_G","Aspartic acid","3","17600")
    , "515" -> NutrientDefinition("515","g","GLU_G","Glutamic acid","3","17700")
    , "516" -> NutrientDefinition("516","g","GLY_G","Glycine","3","17800")
    , "517" -> NutrientDefinition("517","g","PRO_G","Proline","3","17900")
    , "518" -> NutrientDefinition("518","g","SER_G","Serine","3","18000")
    , "521" -> NutrientDefinition("521","g","HYP","Hydroxyproline","3","18100")
    , "573" -> NutrientDefinition("573","mg","","Vitamin E, added","2","7920")
    , "578" -> NutrientDefinition("578","ug","","Vitamin B-12, added","2","7340")
    , "601" -> NutrientDefinition("601","mg","CHOLE","Cholesterol","0","15700")
    , "605" -> NutrientDefinition("605","g","FATRN","Fatty acids, total trans","3","15400")
    , "606" -> NutrientDefinition("606","g","FASAT","Fatty acids, total saturated","3","9700")
    , "607" -> NutrientDefinition("607","g","F4D0","4:0","3","9800")
    , "608" -> NutrientDefinition("608","g","F6D0","6:0","3","9900")
    , "609" -> NutrientDefinition("609","g","F8D0","8:0","3","10000")
    , "610" -> NutrientDefinition("610","g","F10D0","10:0","3","10100")
    , "611" -> NutrientDefinition("611","g","F12D0","12:0","3","10300")
    , "612" -> NutrientDefinition("612","g","F14D0","14:0","3","10500")
    , "613" -> NutrientDefinition("613","g","F16D0","16:0","3","10700")
    , "614" -> NutrientDefinition("614","g","F18D0","18:0","3","10900")
    , "615" -> NutrientDefinition("615","g","F20D0","20:0","3","11100")
    , "617" -> NutrientDefinition("617","g","F18D1","18:1 undifferentiated","3","12100")
    , "618" -> NutrientDefinition("618","g","F18D2","18:2 undifferentiated","3","13100")
    , "619" -> NutrientDefinition("619","g","F18D3","18:3 undifferentiated","3","13900")
    , "620" -> NutrientDefinition("620","g","F20D4","20:4 undifferentiated","3","14700")
    , "621" -> NutrientDefinition("621","g","F22D6","22:6 n-3 (DHA)","3","15300")
    , "624" -> NutrientDefinition("624","g","F22D0","22:0","3","11200")
    , "625" -> NutrientDefinition("625","g","F14D1","14:1","3","11500")
    , "626" -> NutrientDefinition("626","g","F16D1","16:1 undifferentiated","3","11700")
    , "627" -> NutrientDefinition("627","g","F18D4","18:4","3","14250")
    , "628" -> NutrientDefinition("628","g","F20D1","20:1","3","12400")
    , "629" -> NutrientDefinition("629","g","F20D5","20:5 n-3 (EPA)","3","15000")
    , "630" -> NutrientDefinition("630","g","F22D1","22:1 undifferentiated","3","12500")
    , "631" -> NutrientDefinition("631","g","F22D5","22:5 n-3 (DPA)","3","15200")
    , "636" -> NutrientDefinition("636","mg","PHYSTR","Phytosterols","0","15800")
    , "638" -> NutrientDefinition("638","mg","STID7","Stigmasterol","0","15900")
    , "639" -> NutrientDefinition("639","mg","CAMD5","Campesterol","0","16000")
    , "641" -> NutrientDefinition("641","mg","SITSTR","Beta-sitosterol","0","16200")
    , "645" -> NutrientDefinition("645","g","FAMS","Fatty acids, total monounsaturated","3","11400")
    , "646" -> NutrientDefinition("646","g","FAPU","Fatty acids, total polyunsaturated","3","12900")
    , "652" -> NutrientDefinition("652","g","F15D0","15:0","3","10600")
    , "653" -> NutrientDefinition("653","g","F17D0","17:0","3","10800")
    , "654" -> NutrientDefinition("654","g","F24D0","24:0","3","11300")
    , "662" -> NutrientDefinition("662","g","F16D1T","16:1 t","3","11900")
    , "663" -> NutrientDefinition("663","g","F18D1T","18:1 t","3","12300")
    , "664" -> NutrientDefinition("664","g","F22D1T","22:1 t","3","12700")
    , "665" -> NutrientDefinition("665","g","","18:2 t not further defined","3","13800")
    , "666" -> NutrientDefinition("666","g","","18:2 i","3","13700")
    , "669" -> NutrientDefinition("669","g","F18D2TT","18:2 t,t","3","13600")
    , "670" -> NutrientDefinition("670","g","F18D2CLA","18:2 CLAs","3","13300")
    , "671" -> NutrientDefinition("671","g","F24D1C","24:1 c","3","12800")
    , "672" -> NutrientDefinition("672","g","F20D2CN6","20:2 n-6 c,c","3","14300")
    , "673" -> NutrientDefinition("673","g","F16D1C","16:1 c","3","11800")
    , "674" -> NutrientDefinition("674","g","F18D1C","18:1 c","3","12200")
    , "675" -> NutrientDefinition("675","g","F18D2CN6","18:2 n-6 c,c","3","13200")
    , "676" -> NutrientDefinition("676","g","F22D1C","22:1 c","3","12600")
    , "685" -> NutrientDefinition("685","g","F18D3CN6","18:3 n-6 c,c,c","3","14100")
    , "687" -> NutrientDefinition("687","g","F17D1","17:1","3","12000")
    , "689" -> NutrientDefinition("689","g","F20D3","20:3 undifferentiated","3","14400")
    , "693" -> NutrientDefinition("693","g","FATRNM","Fatty acids, total trans-monoenoic","3","15500")
    , "697" -> NutrientDefinition("695","g","FATRNP","Fatty acids, total trans-polyenoic","3","15600")
    , "698" -> NutrientDefinition("696","g","F13D0","13:0","3","10400")
    , "699" -> NutrientDefinition("697","g","F15D1","15:1","3","11600")
    , "851" -> NutrientDefinition("851","g","F18D3CN3","18:3 n-3 c,c,c (ALA)","3","14000")
    , "852" -> NutrientDefinition("852","g","F20D3N3","20:3 n-3","3","14500")
    , "853" -> NutrientDefinition("853","g","F20D3N6","20:3 n-6","3","14600")
    , "855" -> NutrientDefinition("855","g","F20D4N6","20:4 n-6","3","14900")
    , "856" -> NutrientDefinition("856","g","","18:3i","3","14200")
    , "857" -> NutrientDefinition("857","g","F21D5","21:5","3","15100")
    , "858" -> NutrientDefinition("858","g","F22D4","22:4","3","15160")
    , "859" -> NutrientDefinition("859","g","F18D1TN7","18:1-11 t (18:1t n-7)","3","12310")
  )

  /**
   * Returning the NutrientDefinition by matching supplied nutrient Id.
   * 
   * @return NutrientDefinition
   */
  def get(nutrientId : String) : NutrientDefinition = {
    val nutrient = HASH.get(nutrientId)
    if ( nutrient.isEmpty ) throw new IllegalStateException(s"No nutrient found with value [$nutrientId]")
    nutrient.get
  }

  /**
   * Receiving an enum of NutrientNames, returns the class with all respective
   * nutrient definitions.
   * 
   * @return NutrientDefinition.
   */
  def get(nutrient : NutrientNames.nutrients ) : NutrientDefinition = nutrient match {
    case NutrientNames.PROTEIN                              => get("203")
    case NutrientNames.TOTAL_LIPID_FAT                      => get("204")
    case NutrientNames.CARBOHYDRATE_BY_DIFF                 => get("205")
    case NutrientNames.ASH                                  => get("207")
    case NutrientNames.ENERGY_KCAL                          => get("208")
    case NutrientNames.STARCH                               => get("209")
    case NutrientNames.SUCROSE                              => get("210")
    case NutrientNames.GLUCOSE_DEXTROSE                     => get("211")
    case NutrientNames.FRUCTOSE                             => get("212")
    case NutrientNames.LACTOSE                              => get("213")
    case NutrientNames.MALTOSE                              => get("214")
    case NutrientNames.ALCOHOL_ETHYL                        => get("221")
    case NutrientNames.WATER                                => get("255")
    case NutrientNames.ADJUSTED_PROTEIN                     => get("257")
    case NutrientNames.CAFFEINE                             => get("262")
    case NutrientNames.THEOBROMINE                          => get("263")
    case NutrientNames.ENERGY_KJ                            => get("268")
    case NutrientNames.TOTAL_SUGARS                         => get("269")
    case NutrientNames.GALACTOSE                            => get("270")
    case NutrientNames.TOTAL_DIETARY_FIBER                  => get("291")
    case NutrientNames.CALCIUM                              => get("301")
    case NutrientNames.IRON                                 => get("303")
    case NutrientNames.MAGNESIUM                            => get("304")
    case NutrientNames.PHOSPHORUS                           => get("305")
    case NutrientNames.POTASSIUM                            => get("306")
    case NutrientNames.SODIUM                               => get("307")
    case NutrientNames.ZINC                                 => get("309")
    case NutrientNames.COPPER                               => get("312")
    case NutrientNames.FLUORIDE                             => get("313")
    case NutrientNames.MANGANESE                            => get("315")
    case NutrientNames.SELENIUM                             => get("317")
    case NutrientNames.VITAMIN_A_IU                         => get("318")
    case NutrientNames.RETINOL                              => get("319")
    case NutrientNames.VITAMIN_A_RAE                        => get("320")
    case NutrientNames.CAROTENE_BETA                        => get("321")
    case NutrientNames.CAROTENE_ALPHA                       => get("322")
    case NutrientNames.VITAMIN_E                            => get("323")
    case NutrientNames.VITAMIN_D                            => get("324")
    case NutrientNames.VITAMIN_D2                           => get("325")
    case NutrientNames.VITAMIN_D3                           => get("326")
    case NutrientNames.VITAMIN_D2_D3                        => get("328")
    case NutrientNames.CRYPTOXANTHIN_BETA                   => get("334")
    case NutrientNames.LYCOPENE                             => get("337")
    case NutrientNames.LUTEIN_ZEAXANTHIN                    => get("338")
    case NutrientNames.TOCOPHEROL_BETA                      => get("341")
    case NutrientNames.TOCOPHEROL_GAMMA                     => get("342")
    case NutrientNames.TOCOPHEROL_DELTA                     => get("343")
    case NutrientNames.TOCOTRIENOL_ALPHA                    => get("344")
    case NutrientNames.TOCOTRIENOL_BETA                     => get("345")
    case NutrientNames.TOCOTRIENOL_GAMMA                    => get("346")
    case NutrientNames.TOCOTRIENOL_DELTA                    => get("347")
    case NutrientNames.VITAMIN_C_ASCORBIC                   => get("401")
    case NutrientNames.THIAMIN                              => get("404")
    case NutrientNames.RIBOFLAVIN                           => get("405")
    case NutrientNames.NIACIN                               => get("406")
    case NutrientNames.PANTOTHENIC_ACID                     => get("410")
    case NutrientNames.VITAMIN_B6                           => get("415")
    case NutrientNames.TOTAL_FOLATE                         => get("417")
    case NutrientNames.VITAMIN_B12                          => get("418")
    case NutrientNames.TOTAL_CHOLINE                        => get("421")
    case NutrientNames.MENAQUINONE_4                        => get("428")
    case NutrientNames.DIHYDROPHYLLOQUINONE                 => get("429")
    case NutrientNames.VITAMIN_K                            => get("430")
    case NutrientNames.FOLIC_ACID                           => get("431")
    case NutrientNames.FOLATE_FOOD                          => get("432")
    case NutrientNames.FOLATE_DFE                           => get("435")
    case NutrientNames.BETAINE                              => get("454")
    case NutrientNames.TRYPTOPHAN                           => get("501")
    case NutrientNames.THREONINE                            => get("502")
    case NutrientNames.ISOLEUCINE                           => get("503")
    case NutrientNames.LEUCINE                              => get("504")
    case NutrientNames.LYSINE                               => get("505")
    case NutrientNames.METHIONINE                           => get("506")
    case NutrientNames.CYSTINE                              => get("507")
    case NutrientNames.PHENYLALANINE                        => get("508")
    case NutrientNames.TYROSINE                             => get("509")
    case NutrientNames.VALINE                               => get("510")
    case NutrientNames.ARGININE                             => get("511")
    case NutrientNames.HISTIDINE                            => get("512")
    case NutrientNames.ALANINE                              => get("513")
    case NutrientNames.ASPARTIC_ACID                        => get("514")
    case NutrientNames.GLUTAMIN_ACID                        => get("515")
    case NutrientNames.GLYCINE                              => get("516")
    case NutrientNames.PROLINE                              => get("517")
    case NutrientNames.SERINE                               => get("518")
    case NutrientNames.HYDROXYPROLINE                       => get("521")
    case NutrientNames.VITAMIN_E_ADDED                      => get("573")
    case NutrientNames.VITAMIN_B12_ADDED                    => get("578")
    case NutrientNames.CHOLESTEROL                          => get("601")
    case NutrientNames.TOTAL_TRANS_FATTY_ACIDS              => get("605")
    case NutrientNames.TOTAL_SATURATED_FATTY_ACIDS          => get("606")
    case NutrientNames.F4D0_04_00                           => get("607")
    case NutrientNames.F6D0_06_00                           => get("608")
    case NutrientNames.F8D0_08_00                           => get("609")
    case NutrientNames.F10D0_10_00                          => get("610")
    case NutrientNames.F12D0_12_00                          => get("611")
    case NutrientNames.F14D0_14_00                          => get("612")
    case NutrientNames.F16D0_16_00                          => get("613")
    case NutrientNames.F18D0_18_00                          => get("614")
    case NutrientNames.F20D0_20_00                          => get("615")
    case NutrientNames.F18D1_18_1_UNDIFFERENTIATED          => get("617")
    case NutrientNames.F18D2_18_2_UNDIFFERENTIATED          => get("618")
    case NutrientNames.F18D3_18_3_UNDIFFERENTIATED          => get("619")
    case NutrientNames.F20D4_20_4_UNDIFFERENTIATED          => get("620")
    case NutrientNames.F22D6_22_6_N3_DHA                    => get("621")
    case NutrientNames.F22D0_22_00                          => get("624")
    case NutrientNames.F14D1_14_01                          => get("625")
    case NutrientNames.F16D1_16_1_UNDIFFERENTIATED          => get("626")
    case NutrientNames.F18D4_18_04                          => get("627")
    case NutrientNames.F20D1_20_01                          => get("628")
    case NutrientNames.F20D5_20_5_N3_EPA                    => get("629")
    case NutrientNames.F22D1_22_1_UNDIFFERENTIATED          => get("630")
    case NutrientNames.F22D5_22_5_N3_DPA                    => get("631")
    case NutrientNames.PHYTOSTEROLDS                        => get("636")
    case NutrientNames.STIGMASTEROL                         => get("638")
    case NutrientNames.CAMPESTEROL                          => get("639")
    case NutrientNames.BETA_SITOSTEROL                      => get("641")
    case NutrientNames.TOTAL_MONOUNSATURATED_FATTY_ACIDS    => get("645")
    case NutrientNames.TOTAL_POLYUNSATURATED_FATTY_ACIDS    => get("646")
    case NutrientNames.F15D0_15_00                          => get("652")
    case NutrientNames.F17D0_17_00                          => get("653")
    case NutrientNames.F24D0_24_00_00                       => get("654")
    case NutrientNames.F16D1T_16_1_T                        => get("662")
    case NutrientNames.F18D1T_18_1_T                        => get("663")
    case NutrientNames.F22D1T_22_1_T                        => get("664")
    case NutrientNames.F18_2_T_NOT_FURTHER_DEFINED          => get("665")
    case NutrientNames.F18_2_I                              => get("666")
    case NutrientNames.F18D2TT_18_2_TT                      => get("669")
    case NutrientNames.F18D2CLA_18_2_CLAS                   => get("670")
    case NutrientNames.F24D1C_24_1C                         => get("671")
    case NutrientNames.F20D2CN6_20_2N6CC                    => get("672")
    case NutrientNames.F16D1C_16_1C                         => get("673")
    case NutrientNames.F18D1C_18_1C                         => get("674")
    case NutrientNames.F18D2CN6_18_2_N6CC                   => get("675")
    case NutrientNames.F22D1C_22_1C                         => get("676")
    case NutrientNames.F18D3CN6_18_3_N6CCC                  => get("685")
    case NutrientNames.F17D1_17_01                          => get("687")
    case NutrientNames.F20D3_20_3_UNDIFFERENTIATED          => get("689")
    case NutrientNames.TOTAL_TRANS_MONOENOIC_FATTY_ACIDS    => get("693")
    case NutrientNames.TOTAL_TRANS_POLYENOIC_FATTY_ACIDS    => get("695")
    case NutrientNames.F13D0_13_00                          => get("696")
    case NutrientNames.F15D1_15_01                          => get("697")
    case NutrientNames.F18D3CN3_18_3_N3CCC_ALA              => get("851")
    case NutrientNames.F20D3N3_20_3_N3                      => get("852")
    case NutrientNames.F20D3N6_20_3_N6                      => get("853")
    case NutrientNames.F20D4N6_20_4_N6                      => get("855")
    case NutrientNames.F18_3I                               => get("856")
    case NutrientNames.F21D5_21_05                          => get("857")
    case NutrientNames.F22D4_22_04                          => get("858")
    case NutrientNames.F18D1TN7_18_1_11T                    => get("859")
    case _ => throw new IllegalArgumentException("Nutrient not defined")
  }

  /**
   * The full list of nutrient ids.
   */
  def getList() : List[String] = HASH.keySet.toList
}

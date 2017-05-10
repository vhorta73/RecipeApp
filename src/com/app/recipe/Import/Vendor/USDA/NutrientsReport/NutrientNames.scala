package com.app.recipe.Import.Vendor.USDA.NutrientsReport

object NutrientNames extends Enumeration {
  type nutrients = Value
  val PROTEIN                           // Protein - 203
    , TOTAL_LIPID_FAT                   // Total lipid (fat) - 204
    , CARBOHYDRATE_BY_DIFF              // Carbohydrate, by difference - 205
    , ASH                               // Ash - 207
    , ENERGY_KCAL                       // Energy - 208
    , STARCH                            // Starch - 209
    , SUCROSE                           // Sucrose - 210
    , GLUCOSE_DEXTROSE                  // Glucose (dextrose) - 211
    , FRUCTOSE                          // Fructose - 212
    , LACTOSE                           // Lactose - 213
    , MALTOSE                           // Maltose - 214
    , ALCOHOL_ETHYL                     // Alcohol, ethyl - 221
    , WATER                             // Water - 255
    , ADJUSTED_PROTEIN                  // Adjusted Protein - 257
    , CAFFEINE                          // Caffeine - 262
    , THEOBROMINE                       // Theobromine - 263
    , ENERGY_KJ                         // Energy - 268
    , TOTAL_SUGARS                      // Sugars, total - 269
    , GALACTOSE                         // Galactose - 287
    , TOTAL_DIETARY_FIBER               // Fiber, total dietary - 291
    , CALCIUM                           // Calcium, Ca - 301
    , IRON                              // Iron, Fe - 303
    , MAGNESIUM                         // Magnesium, Mg - 304
    , PHOSPHORUS                        // Phosphorus, P - 305
    , POTASSIUM                         // Potassium, K - 306
    , SODIUM                            // Sodium, Na - 307
    , ZINC                              // Zinc, Zn - 309
    , COPPER                            // Copper, Cu - 312
    , FLUORIDE                          // Fluoride, F - 313
    , MANGANESE                         // Manganese, Mn - 315
    , SELENIUM                          // Selenium, Se - 317
    , VITAMIN_A_IU                      // Vitamin A, IU - 318
    , RETINOL                           // Retinol - 319
    , VITAMIN_A_RAE                     // Vitamin A, RAE - 320
    , CAROTENE_BETA                     // Carotene, beta - 321
    , CAROTENE_ALPHA                    // Carotene, alpha - 322
    , VITAMIN_E                         // Vitamin E (alpha-tocopherol) - 323
    , VITAMIN_D                         // Vitamin D - 324
    , VITAMIN_D2                        // Vitamin D2 (ergocalciferol) - 325
    , VITAMIN_D3                        // Vitamin D3 (cholecalciferol) - 326
    , VITAMIN_D2_D3                     // Vitamin D (D2 + D3) - 328
    , CRYPTOXANTHIN_BETA                // Cryptoxanthin, beta - 334
    , LYCOPENE                          // Lycopene - 337
    , LUTEIN_ZEAXANTHIN                 // Lutein + zeaxanthin - 338
    , TOCOPHEROL_BETA                   // Tocopherol, beta - 341
    , TOCOPHEROL_GAMMA                  // Tocopherol, gamma - 342
    , TOCOPHEROL_DELTA                  // Tocopherol, delta - 343
    , TOCOTRIENOL_ALPHA                 // Tocotrienol, alpha - 344
    , TOCOTRIENOL_BETA                  // Tocotrienol, beta - 345
    , TOCOTRIENOL_GAMMA                 // Tocotrienol, gamma - 346
    , TOCOTRIENOL_DELTA                 // Tocotrienol, delta - 347
    , VITAMIN_C_ASCORBIC                // Vitamin C, total ascorbic acid - 401
    , THIAMIN                           // Thiamin - 404
    , RIBOFLAVIN                        // Riboflavin - 405
    , NIACIN                            // Niacin - 406
    , PANTOTHENIC_ACID                  // Pantothenic acid - 410
    , VITAMIN_B6                        // Vitamin B-6 - 415
    , TOTAL_FOLATE                      // Folate, total - 417
    , VITAMIN_B12                       // Vitamin B-12 - 418
    , TOTAL_CHOLINE                     // Choline, total - 421
    , MENAQUINONE_4                     // Menaquinone-4 - 428
    , DIHYDROPHYLLOQUINONE              // Dihydrophylloquinone - 429
    , VITAMIN_K                         // Vitamin K (phylloquinone) - 430
    , FOLIC_ACID                        // Folic acid - 431
    , FOLATE_FOOD                       // Folate, food - 432
    , FOLATE_DFE                        // Folate, DFE - 435
    , BETAINE                           // Betaine - 454
    , TRYPTOPHAN                        // Tryptophan - 501
    , THREONINE                         // Threonine - 502
    , ISOLEUCINE                        // Isoleucine - 503
    , LEUCINE                           // Leucine - 504
    , LYSINE                            // Lysine - 505
    , METHIONINE                        // Methionine - 506
    , CYSTINE                           // Cystine - 507
    , PHENYLALANINE                     // Phenylalanine - 508
    , TYROSINE                          // Tyrosine - 509
    , VALINE                            // Valine - 510
    , ARGININE                          // Arginine - 511
    , HISTIDINE                         // Histidine - 512
    , ALANINE                           // Alanine - 513
    , ASPARTIC_ACID                     // Aspartic acid - 514
    , GLUTAMIN_ACID                     // Glutamic acid - 515
    , GLYCINE                           // Glycine - 516
    , PROLINE                           // Proline - 517
    , SERINE                            // Serine - 518
    , HYDROXYPROLINE                    // Hydroxyproline - 521
    , VITAMIN_E_ADDED                   // Vitamin E, added - 573
    , VITAMIN_B12_ADDED                 // Vitamin B-12, added - 578
    , CHOLESTEROL                       // Cholesterol - 601
    , TOTAL_TRANS_FATTY_ACIDS           // Fatty acids, total trans - 605
    , TOTAL_SATURATED_FATTY_ACIDS       // Fatty acids, total saturated - 606
    , F4D0_04_00                        // 04:00 - 607
    , F6D0_06_00                        // 06:00 - 608
    , F8D0_08_00                        // 08:00 - 609
    , F10D0_10_00                       // 10:00 - 610
    , F12D0_12_00                       // 12:00 - 611
    , F14D0_14_00                       // 14:00 - 612
    , F16D0_16_00                       // 16:00 - 613
    , F18D0_18_00                       // 18:00 - 614
    , F20D0_20_00                       // 20:00 - 615
    , F18D1_18_1_UNDIFFERENTIATED       // 18:1 undifferentiated - 617
    , F18D2_18_2_UNDIFFERENTIATED       // 18:2 undifferentiated - 618
    , F18D3_18_3_UNDIFFERENTIATED       // 18:3 undifferentiated - 619
    , F20D4_20_4_UNDIFFERENTIATED       // 20:4 undifferentiated - 620
    , F22D6_22_6_N3_DHA                 // 22:6 n-3 (DHA) - 621
    , F22D0_22_00                       // 22:00 - 624
    , F14D1_14_01                       // 14:01 - 625
    , F16D1_16_1_UNDIFFERENTIATED       // 16:1 undifferentiated - 626
    , F18D4_18_04                       // 18:04 - 627
    , F20D1_20_01                       // 20:01 - 628
    , F20D5_20_5_N3_EPA                 // 20:5 n-3 (EPA) - 629
    , F22D1_22_1_UNDIFFERENTIATED       // 22:1 undifferentiated - 630
    , F22D5_22_5_N3_DPA                 // 22:5 n-3 (DPA) - 631
    , PHYTOSTEROLDS                     // Phytosterols - 636
    , STIGMASTEROL                      // Stigmasterol - 638
    , CAMPESTEROL                       // Campesterol - 639
    , BETA_SITOSTEROL                   // Beta-sitosterol - 641
    , TOTAL_MONOUNSATURATED_FATTY_ACIDS // Fatty acids, total monounsaturated - 645
    , TOTAL_POLYUNSATURATED_FATTY_ACIDS // Fatty acids, total polyunsaturated - 646
    , F15D0_15_00                       // 15:00 - 652
    , F17D0_17_00                       // 17:00 - 653
    , F24D0_24_00_00                    // 24:00:00 - 654
    , F16D1T_16_1_T                     // 16:1 t - 662
    , F18D1T_18_1_T                     // 18:1 t - 663
    , F22D1T_22_1_T                     // 22:1 t - 664
    , F18_2_T_NOT_FURTHER_DEFINED       // 18:2 t not further defined - 665
    , F18_2_I                           // 18:2 i - 666
    , F18D2TT_18_2_TT                   // 18:2 t,t - 669
    , F18D2CLA_18_2_CLAS                // 18:2 CLAs - 670
    , F24D1C_24_1C                      // 24:1 c - 671
    , F20D2CN6_20_2N6CC                 // 20:2 n-6 c,c - 672
    , F16D1C_16_1C                      // 16:1 c - 673
    , F18D1C_18_1C                      // 18:1 c - 674
    , F18D2CN6_18_2_N6CC                // 18:2 n-6 c,c - 675
    , F22D1C_22_1C                      // 22:1 c - 676
    , F18D3CN6_18_3_N6CCC               // 18:3 n-6 c,c,c - 685
    , F17D1_17_01                       // 17:01 - 687
    , F20D3_20_3_UNDIFFERENTIATED       // 20:3 undifferentiated - 689
    , TOTAL_TRANS_MONOENOIC_FATTY_ACIDS // Fatty acids, total trans-monoenoic - 693
    , TOTAL_TRANS_POLYENOIC_FATTY_ACIDS // Fatty acids, total trans-polyenoic	695
    , F13D0_13_00                       // 13:00 - 696
    , F15D1_15_01                       // 15:01 - 697
    , F18D3CN3_18_3_N3CCC_ALA           // 18:3 n-3 c,c,c (ALA) - 851
    , F20D3N3_20_3_N3                   // 20:3 n-3 - 852
    , F20D3N6_20_3_N6                   // 20:3 n-6 - 853
    , F20D4N6_20_4_N6                   // 20:4 n-6 - 855
    , F18_3I                            // 18:3i - 856
    , F21D5_21_05                       // 21:05 - 857
    , F22D4_22_04                       // 22:04 - 858
    , F18D1TN7_18_1_11T                 // 18:1-11 t (18:1t n-7) - 859
  = Value
}

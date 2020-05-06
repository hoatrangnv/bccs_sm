package com.viettel.im.common.util;

/*
 * @(#) MultiLanguageNumberToWords.java 1.0  02/03/2010
 *
 * Copyright 2009 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
import java.text.DecimalFormat;

/**
 * Chuyen so thanh chu cho tieng Anh, Tieng Viet, Tieng Phap
 * @author ThinhDD
 * @since 04/12/2010
 */
public class MultiLanguageNumberToWords {

    public static final String[] currencyUsing = {"Gourdes", "Gourdes", "Gourdes", "Metical"};
    public static final String[] currenciesUsing = {"Gourdes", "Gourdes", "Gourdes", "Meticais"};
    public static final String[] centUsing = {"", "", "", "Cento"};
    public static final String[] centsUsing = {"", "", "", "Centavos"};
    public static final String[] andUsing = {"", "", "", " e"};
    private static final String[] dotNames = {" dot ", " phẩy ", " et ", " vírgulas "};
    private static final String[] billionNames = {" billion ", " tỉ ", " milliard ", " bilhão"};
    private static final String[] billionsNames = {" billion ", " tỉ ", " milliard ", " bilhões"};
    private static final String[] millionNames = {" million ", " triệu ", " million ", " milhão"};
    private static final String[] millionsNames = {" million ", " triệu ", " million ", " milhões"};
    private static final String[] thousandNames = {" thousand ", " nghìn ", " mille ", " mil"};
    private static final String[] hundredNames = {" hundred ", " trăm ", " cent ", " cem"};
    private static final String[] portugueseHundredName = {" cento", " duzentos", " trezentos", " quatrocentos",
        " quinhentos", " seiscentos", " setecentos", " oitocentos", " novecentos"};
    private static final String[] etNames = {" one ", " mo^t' ", " et un ", " e um"};
    private static final String[] oddNames = {" one ", " lẻ một", " et un ", " e um"};
    private static final String[][] tensNames = {
        {"", "", "", ""},
        {" ten", " mười", " dix", " dez"},
        {" twenty", " hai mươi", " vingt", " vinte"},
        {" thirty", " ba mươi", " trente", " trinta"},
        {" forty", " bốn mươi", " quarante", " quarenta"},
        {" fifty", " năm mươi", " cinquante", " cinquenta"},
        {" sixty", " sáu mươi", " soixante", " sessenta"},
        {" seventy", " bảy mươi", " soixante dix", " setenta"},
        {" eighty", " tám mươi", " quatre vingt", " oitenta"},
        {" ninety", " chín mươi", " quatre vingt dix", " noventa"}
    };
    private static final String[][] numNames = {
        {"", "", "", ""},
        {" one", " một", " un", " um"},
        {" two", " hai", " deux", " dois"},
        {" three", " ba", " trois", " três"},
        {" four", " bốn", " quatre ", " quatro"},
        {" five", " năm", " cinq", " cinco"},
        {" six", " sáu", " six", " seis"},
        {" seven", " bảy", " sept", " sete"},
        {" eight", " tám", " huit", " oito"},
        {" nine", " chín", " neuf", " nove"},
        {" ten", " mười", " dix", " dez"},
        {" eleven", " mười một", " onze", " onze"},
        {" twelve", " mười hai", " douze", " doze"},
        {" thirteen", " mười ba", " treize", " treze"},
        {" fourteen", " mười bốn", " quatorze", " quatorze"},
        {" fifteen", " mười năm", " quinze", " quinze"},
        {" sixteen", " mười sáu", " seize", " dezesseis"},
        {" seventeen", " mười bảy", " dix sept", " dezessete"},
        {" eighteen", " mười tám", " dix huit", " dezoito"},
        {" nineteen", " mười chín", " dix neuf", " dezenove"}
    };

    private static String convertLessThanOneThousand(int number, int lang, boolean odd, boolean isGreaterThan1000) {
        // use only for portuguse
        boolean greaterThanHundred = false;
        boolean isDivied100 = false;
        boolean greaterThanTen = false;
        if (number > 100) {
            greaterThanHundred = true;
        } else if (number > 10) {
            greaterThanTen = true;
        }
        if (isGreaterThan1000) {
            greaterThanHundred = true;
            greaterThanTen = true;
        }
        if (number % 100 == 0) {
            isDivied100 = true;
        }
        String soFar = "";
        // Doc 1
        if (number == 1) {
            return numNames[1][lang];
        } // Mot tram trong tieng Phap
        else if ((number == 100)) {
            if (lang == 2 || lang == 3) {
                return hundredNames[lang] + soFar;
            } else {
                return numNames[1][lang] + hundredNames[lang] + soFar;
            }
        } else if (number % 100 < 20) {
            if (lang != 3) {
                // 101: mot tram le mot
                if (number % 100 == 1) {
                    soFar = oddNames[lang];
                } else if ((number % 100 < 10) && (number % 100 > 0) && (lang == 1) && odd) {
                    soFar = " lẻ " + numNames[number % 100][1];
                } else {
                    soFar = numNames[number % 100][lang];
                }
                number /= 100;
            } else {
                if (number % 100 == 1) {
                    soFar = oddNames[lang];
                } else {
                    if (greaterThanHundred&&!isDivied100 ) {
                        soFar = andUsing[lang] + numNames[number % 100][lang];
                    } else {
                        soFar = numNames[number % 100][lang];
                    }
                }
                number /= 100;
            }
        } else {
            // xet cho truong hop tieng Phap voi gia tri >70
            if ((lang == 2) && ((number > 70 && number < 80) || (number > 90 && number < 100))) {
                if (number > 70 && number < 80) {
                    soFar = " soixante " + numNames[number - 60][2];
                } else if (number > 90 && number < 100) {
                    soFar = " quatre vingt " + numNames[number - 80][2];
                }
                number /= 100;
            } else {
                // 21: hai muoi mo^t
                if (number % 10 == 1) {
                    soFar = etNames[lang];
                } else {
                    if (number % 10 != 0) {
                        if (number >= 10) {
                            soFar = andUsing[lang] + numNames[number % 10][lang];
                        } else {
                            if (greaterThanHundred) {
                                soFar = andUsing[lang] + numNames[number % 10][lang];
                            } else {
                                soFar = numNames[number % 10][lang];
                            }
                        }
                    }
                }
                number /= 10;
                if (greaterThanHundred) {
                    soFar = andUsing[lang] + tensNames[number % 10][lang] + soFar;
                } else {
                    soFar = tensNames[number % 10][lang] + soFar;
                }
                number /= 10;
            }
        }
        if (number == 0) {
            return soFar;
        }
        // Truong hop >=100 va <200 trong tieng Phap -> bo un
        if (number == 1 && lang == 2) {
            return hundredNames[lang] + soFar;
        } else if (lang == 3) {
            //return hundredNames[lang] + soFar;
            if (isGreaterThan1000 && isDivied100) {
                return andUsing[lang] + portugueseHundredName[number - 1] + soFar;
            } else {
                return portugueseHundredName[number - 1] + soFar;
            }
        } else {
            return numNames[number][lang] + hundredNames[lang] + soFar;
        }
    }

    public static String convert(long number, int lang) {
        // 0 to 999 999 999 999
        if (number == 0) {
            if (lang == 0) {
                return "zero";
            }
            if (lang == 1) {
                return "không";
            }
            if (lang == 2) {
                return "zéro";
            }
            if (lang == 3) {
                return "zero";
            }
        }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        //System.out.println("billions:::" + billions);
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        //System.out.println("millions:::" + millions);
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        //System.out.println("hundredThousands:::" + hundredThousands);
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));
        //System.out.println("thousands:::" + thousands);
        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1:
                if (lang == 2) {
                }
                tradBillions = convertLessThanOneThousand(billions, lang, false, false)
                        + billionNames[lang];
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions, lang, false, false)
                        + billionsNames[lang];
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1:
                if (lang == 2) {
                    tradMillions = millionNames[lang];
                } else {
                    tradMillions = numNames[1][lang] + millionNames[lang];
                }
                break;
            default:
                if (billions >= 1) {
                    tradMillions = convertLessThanOneThousand(millions, lang, false, true)
                            + millionsNames[lang];
                } else {
                    tradMillions = convertLessThanOneThousand(millions, lang, false, false)
                            + millionsNames[lang];
                }
        }
//        if(billions >=1 && millions>=100 && lang ==3){
//            result = result + ", "+tradMillions;
//        }else
            result = result + tradMillions;


        String tradHundredThousands = "";
        switch (hundredThousands) {
            case 0:
                break;
            case 1:
                // Doc 1 nghin trong tieng Phap bo? un
                if (lang == 2) {
                    tradHundredThousands = thousandNames[lang];
                } else {
                    tradHundredThousands = numNames[1][lang] + thousandNames[lang];
                }
                break;
            default:
                if (millions >= 1) {
                    tradHundredThousands = convertLessThanOneThousand(hundredThousands, lang, false, true)
                            + thousandNames[lang];
                } else {
                    tradHundredThousands = convertLessThanOneThousand(hundredThousands, lang, false, false)
                            + thousandNames[lang];
                }

        }
//        if(millions >=1 && hundredThousands>=100 && lang ==3){
//            result = result + ", "+tradHundredThousands;
//        }else
            result = result + tradHundredThousands;


        String tradThousand = "";
        String special10 = "";
        /* Tieng Viet trong truong hop <100 se doc khong tram
         * Tieng Viet se co le 1
         * Tieng phap se co et: trong truong hop hang don vi la 1
         */
        if (number > 100) {
            if (thousands < 100 && thousands > 0 && lang == 1) {
                special10 = " không trăm ";
            }
            if (thousands == 1) {
                if ((thousands == 1) && lang == 2) {
                    special10 += " et ";
                } else if (lang == 1) {
                    special10 += " lẻ ";
                } else if (lang == 3) {
                    special10 += "";
                }
            }
            if (hundredThousands >= 1) {
                tradThousand = convertLessThanOneThousand(thousands, lang, true, true);
            } else {
                tradThousand = convertLessThanOneThousand(thousands, lang, true, false);
            }
        } else {
            tradThousand = convertLessThanOneThousand(thousands, lang, false, false);
        }
//        if(hundredThousands >=1 && thousands>=100 && lang ==3){
//            result = result + ","+tradThousand;
//        }else
            result = result + special10 + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    public static String convert(Double number, String lang) {
        long dollars = (long) Math.floor(number);
        long cent = (long) Math.floor((number * 100 + 0.49 - dollars * 100));
        long test = (long) (number * 100 - dollars * 100);
        long test1 = (long) (number * 100);
        long test2 = (long) (dollars * 100);
        Double test3 = (Double) (number);
        int intLang = 1;
        if (lang.equals("vi")) {
            intLang = 1;
        } else if (lang.equals("en")) {
            intLang = 0;
        } else if (lang.equals("fr")) {
            intLang = 2;
        } else if (lang.equals("pt")) {
            intLang = 3;
        }
        //nói là bao nhiêu đó meticais và bao nhiêu centavos
        if (intLang != 3) {
            String s = MultiLanguageNumberToWords.convert(dollars, intLang);
            if (cent != 0) {
                s += dotNames[intLang]
                        + MultiLanguageNumberToWords.convert(cent, intLang);
            }
            if (dollars == 1) {
                s += " " + currencyUsing[intLang];
            } else if (dollars > 1) {
                s += " " + currenciesUsing[intLang];
            } else {
                s += " " + currencyUsing[intLang];
            }
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        } else {
            String s = MultiLanguageNumberToWords.convert(dollars, intLang);
            if (dollars > 1) {
                s += " " + currenciesUsing[intLang];
            } else {
                s += " " + currencyUsing[intLang];
            }
            if (cent != 0) {
                //s += dotNames[intLang] + MultiLanguageNumberToWords.convert(cent, intLang);
                s += " e " + MultiLanguageNumberToWords.convert(cent, intLang);
                if (cent > 1) {
                    s += " " + centsUsing[intLang];
                } else {
                    s += " " + centUsing[intLang];
                }
            }
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
    }
}

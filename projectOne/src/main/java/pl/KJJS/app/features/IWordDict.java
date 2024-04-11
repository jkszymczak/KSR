package pl.KJJS.app.features;

import java.util.HashMap;

public interface IWordDict {


    // return a single dictionary from given index
    String[][] getWordDict(String index);

    // return all dictionaries
    HashMap<String, String[][]> getAllWordDicts();

    /*
      Legend of index meaning:

    F Nr.  Index
      1  - geo-west-germany
      2  - geo-usa
      3  - geo-france
      4  - geo-uk
      5  - geo-canada
      6  - geo-japan

      7  - obi-west-germany
      8  - obi-usa
      9  - obi-france
      10 - obi-uk
      11 - obi-canada
      12 - obi-japan

      13 - miast-west-germany
      14 - miast-usa
      15 - miast-france
      16 - miast-uk
      17 - miast-canada
      18 - miast-japan

      19 - osob-west-germany
      20 - osob-usa
      21 - osob-france
      22 - osob-uk
      23 - osob-canada
      24 - osob-japan

      25 - inst-west-germany
      26 - inst-usa
      27 - inst-france
      28 - inst-uk
      29 - inst-canada
      30 - inst-japan

      31 - data-west-germany
      32 - data-usa
      33 - data-france
      34 - data-uk
      35 - data-canada
      36 - data-japan

      37 - klucz-west-germany
      38 - klucz-usa
      39 - klucz-france
      40 - klucz-uk
      41 - klucz-canada
      42 - klucz-japan

      43 - kont

      44 - pa

      kont  - dictionary of continents
      pa    - dictionary of countries
     */
}

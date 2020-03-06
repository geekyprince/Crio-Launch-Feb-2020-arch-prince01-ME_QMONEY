package com.crio.warmup.stock;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {

  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Read the json file provided in the argument[0]. The file will be avilable in the classpath.
  //  1. Use #resolveFileFromResources to get actual file from classpath.
  //  2. parse the json file using ObjectMapper provided with #getObjectMapper,
  //  and extract symbols provided in every trade.
  //  return the list of all symbols in the same order as provided in json.
  //  Test the function using gradle commands below
  //   ./gradlew run --args="trades.json"
  //  Make sure that it prints below String on the console -
  //  ["AAPL","MSFT","GOOGL"]
  //  Now, run
  //  ./gradlew build and make sure that the build passes successfully
  //  There can be few unused imports, you will need to fix them to make the build pass.

  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
    
    ObjectMapper objectMapper = new ObjectMapper();
    List<String> l = new ArrayList<String>();
    Employee[] emp = objectMapper.readValue(resolveFileFromResources(args[0]), Employee[].class);
     
    for (Employee x:emp) {
      l.add(x.getSymbol());
    }
    return l;
  }








  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Follow the instructions provided in the task documentation and fill up the correct values for
  //  the variables provided. First value is provided for your reference.
  //  A. Put a breakpoint on the first line inside mainReadFile() which says
  //    return Collections.emptyList();
  //  B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  //  following the instructions to run the test.
  //  Once you are able to run the test, perform following tasks and record the output as a
  //  String in the function below.
  //  Use this link to see how to evaluate expressions -
  //  https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  //  1. evaluate the value of "args[0]" and set the value
  //     to the variable named valueOfArgument0 (This is implemented for your reference.)
  //  2. In the same window, evaluate the value of expression below and set it
  //  to resultOfResolveFilePathArgs0
  //     expression ==> resolveFileFromResources(args[0])
  //  3. In the same window, evaluate the value of expression below and set it
  //  to toStringOfObjectMapper.
  //  You might see some garbage numbers in the output. Dont worry, its expected.
  //    expression ==> getObjectMapper().toString()
  //  4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  //  second place from top to variable functionNameFromTestFileInStackTrace
  //  5. In the same window, you will see the line number of the function in the stack trace window.
  //  assign the same to lineNumberFromTestFileInStackTrace
  //  Once you are done with above, just run the corresponding test and
  //  make sure its working as expected. use below command to do the same.
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {

    String valueOfArgument0 = "trades.json";
    String pathOfArgument0 = "~/workspace/singhalk30-ME_QMONEY/qmoney/bin/main/trades.json";
    String resultOfResolveFilePathArgs0 = pathOfArgument0;
    String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@5a9d6f02";
    String functionNameFromTestFileInStackTrace = "PortfolioManagerApplicationTest.mainReadFile()";
    String lineNumberFromTestFileInStackTrace = "22";


    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace});
  }
  // TODO: CRIO_TASK_MODULE_REST_API
  //  Copy the relavent code from #mainReadFile to parse the Json into PortfolioTrade list.
  //  Now That you have the list of PortfolioTrade already populated in module#1
  //  For each stock symbol in the portfolio trades,
  //  Call Tiingo api (https://api.tiingo.com/tiingo/daily/<ticker>/prices?startDate=&endDate=&token=)
  //  with
  //   1. ticker = symbol in portfolio_trade
  //   2. startDate = purchaseDate in portfolio_trade.
  //   3. endDate = args[1]
  //  Use RestTemplate#getForObject in order to call the API,
  //  and deserialize the results in List<Candle>
  //  Note - You may have to register on Tiingo to get the api_token.
  //    Please refer the the module documentation for the steps.
  //  Find out the closing price of the stock on the end_date and
  //  return the list of all symbols in ascending order by its close value on endDate
  //  Test the function using gradle commands below
  //   ./gradlew run --args="trades.json 2020-01-01"
  //   ./gradlew run --args="trades.json 2019-07-01"
  //   ./gradlew run --args="trades.json 2019-12-03"
  //  And make sure that its printing correct results.

  public static List<String> convertMapToList(HashMap<String, Float> m) {
    List<String> l = new ArrayList<String>();
    m = sortByValue(m);
    l.clear();
    for (String entry : m.keySet()) { 
      l.add(entry);
    }

    return l;

  }
  
  public static JsonData[] getJsonDataList(Employee o, String date)
      throws JsonMappingException, RestClientException, JsonProcessingException {
    final String enddat = "&endDate=" + date;
    final String tok = "&token=" + "aef573290c64a87f6d88a3e0305b628a3e983527";
    final String s = "https://api.tiingo.com/tiingo/daily/" + o.getSymbol() + "/prices?startDate=" + o.getPurchaseDate() + enddat + tok;
 
    return getObjectMapper().readValue((new RestTemplate()).getForObject(s, String.class),
      new TypeReference<JsonData[]>() {
      });
  }

  public static HashMap<String, Float> sortByValue(HashMap<String, Float> hm) { 
    // Create a list from elements of HashMap
    List<Map.Entry<String, Float>> list = new LinkedList<Map.Entry<String, Float>>(hm.entrySet());
 
    // Sort the list
    Collections.sort(list, new Comparator<Map.Entry<String, Float>>() { 
      public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {

        return (o1.getValue()).compareTo(o2.getValue());
      }
    });

    // put data from sorted list to hashmap
    HashMap<String, Float> temp = new LinkedHashMap<String, Float>();
    for (Map.Entry<String, Float> aa : list) {
      temp.put(aa.getKey(), aa.getValue());
    }
    return temp;
  }
  

  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    ObjectMapper objectMapper = new ObjectMapper();
    Employee[] emp = objectMapper.readValue(resolveFileFromResources(args[0]), Employee[].class);
    HashMap<String, Float> m = new HashMap<String, Float>();

    for (Employee o : emp) {
      JsonData[] lst = getJsonDataList(o, args[1]);
      m.put(o.getSymbol(), new Float(lst[lst.length - 1].getclose()));
    }

    return convertMapToList(m);
  }
  


  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Copy the relevant code from #mainReadQuotes to parse the Json into PortfolioTrade list and
  //  Get the latest quotes from TIingo.
  //  Now That you have the list of PortfolioTrade And their data,
  //  With this data, Calculate annualized returns for the stocks provided in the Json
  //  Below are the values to be considered for calculations.
  //  buy_price = open_price on purchase_date and sell_value = close_price on end_date
  //  startDate and endDate are already calculated in module2
  //  using the function you just wrote #calculateAnnualizedReturns
  //  Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.
  //  use gralde command like below to test your code
  //  ./gradlew run --args="trades.json 2020-01-01"
  //  ./gradlew run --args="trades.json 2019-07-01"
  //  ./gradlew run --args="trades.json 2019-12-03"
  //  where trades.json is your json file

  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {
    ObjectMapper objectMapper = new ObjectMapper();
    Employee[] emp = objectMapper.readValue(resolveFileFromResources(args[0]), Employee[].class);
    List<AnnualizedReturn> df = new ArrayList<AnnualizedReturn>();

    for (Employee o : emp) {
      LocalDate localDate = LocalDate.parse(o.getPurchaseDate());
      PortfolioTrade trade = new PortfolioTrade(o.getSymbol(), o.getQuantity(), localDate);
      JsonData[] jd = getJsonDataList(o, args[1]);

      // convert String to LocalDate
      LocalDate localDate2 = LocalDate.parse(args[1]);
      df.add(calculateAnnualizedReturns(localDate2, trade, (double) jd[0].getopen(),
          (double) jd[jd.length - 1].getclose()));

    }
    Collections.sort(df,new Comparator<AnnualizedReturn>() {

      @Override
      public int compare(AnnualizedReturn o1, AnnualizedReturn o2) {
        return o1.getAnnualizedReturn() > o2.getAnnualizedReturn() ? -1 : 1;
      }
      
    });
    return df;
    //return Collections.emptyList();
  }

  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  annualized returns should be calculated in two steps -
  //  1. Calculate totalReturn = (sell_value - buy_value) / buy_value
  //  Store the same as totalReturns
  //  2. calculate extrapolated annualized returns by scaling the same in years span. The formula is
  //  annualized_returns = (1 + total_returns) ^ (1 / total_num_years) - 1
  //  Store the same as annualized_returns
  //  return the populated list of AnnualizedReturn for all stocks,
  //  Test the same using below specified command. The build should be successful
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testCalculateAnnualizedReturn

  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
      PortfolioTrade trade, Double buyPrice,
       Double sellPrice) {
    try {
      double totalReturn = (sellPrice - buyPrice) / buyPrice;
      LocalDate sld = trade.getPurchaseDate();
      double totalnumdays = ChronoUnit.DAYS.between(sld, endDate);
      printJsonObject(sld);
      printJsonObject(totalnumdays);
      double annualizedreturns = Math.pow((1 + totalReturn), (365 / totalnumdays)) - 1;
      return new AnnualizedReturn(trade.getSymbol(), annualizedreturns, totalReturn);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }











  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());

    //printJsonObject(mainReadFile(args));


    //printJsonObject(mainReadQuotes(args));



    printJsonObject(mainCalculateSingleReturn(args));

  }
}
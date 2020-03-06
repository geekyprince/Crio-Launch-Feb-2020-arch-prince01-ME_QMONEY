package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerImpl implements PortfolioManager {



  private RestTemplate restTemplate;
  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
 
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  //TODO: CRIO_TASK_MODULE_REFACTOR
  // Now we want to convert our code into a module, so we will not call it from main anymore.
  // Copy your code from Module#3 PortfolioManagerApplication#calculateAnnualizedReturn
  // into #calculateAnnualizedReturn function here and make sure that it
  // follows the method signature.
  // Logic to read Json file and convert them into Objects will not be required further as our
  // clients will take care of it, going forward.
  // Test your code using Junits provided.
  // Make sure that all of the tests inside PortfolioManagerTest using command below -
  // ./gradlew test --tests PortfolioManagerTest
  // This will guard you against any regressions.
  // run ./gradlew build in order to test yout code, and make sure that
  // the tests and static code quality pass.

  //CHECKSTYLE:OFF
  // private static void printJsonObject(Object object) throws IOException {
  //   Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
  //   ObjectMapper mapper = new ObjectMapper();
  //   logger.info(mapper.writeValueAsString(object));
  // }
  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }



  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo thirdparty APIs to a separate function.
  //  It should be split into fto parts.
  //  Part#1 - Prepare the Url to call Tiingo based on a template constant,
  //  by replacing the placeholders.
  //  Constant should look like
  //  https://api.tiingo.com/tiingo/daily/<ticker>/prices?startDate=?&endDate=?&token=?
  //  Where ? are replaced with something similar to <ticker> and then actual url produced by
  //  replacing the placeholders with actual parameters.
  
  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
      PortfolioTrade trade, Double buyPrice, Double sellPrice) {
      double totalReturn = (sellPrice - buyPrice) / buyPrice;
      LocalDate sld = trade.getPurchaseDate();
      double totalnumdays = ChronoUnit.DAYS.between(sld, endDate);
      double annualizedreturns = Math.pow((1 + totalReturn), (365 / totalnumdays)) - 1;
      return new AnnualizedReturn(trade.getSymbol(), annualizedreturns, totalReturn);
  }

  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException {
    final String tok = "&token=" + "b2bbdf30e93918ce9bbda21ac0197d6cb7dccf1c";
    final String s="https://api.tiingo.com/tiingo/daily/" + symbol + "/prices?startDate=" + from + "&endDate=" + to + tok;
    return  Arrays.asList(getObjectMapper().readValue((restTemplate).getForObject(s, String.class),
    new TypeReference<Candle[]>() {
    }));
  }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
       String uriTemplate = "https://api.tiingo.com/tiingo/daily/$SYMBOL/prices?"
            + "startDate=$STARTDATE&endDate=$ENDDATE&token=$APIKEY";

    return uriTemplate;
  }
  
  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades,
    LocalDate endDate) {
    List<AnnualizedReturn> list = new ArrayList<AnnualizedReturn>();
    for(PortfolioTrade x:portfolioTrades){
      LocalDate ld=x.getPurchaseDate();
      List<Candle> collection ;
    try {
      collection = getStockQuote(x.getSymbol(), ld, endDate);
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
        list.add(calculateAnnualizedReturns(endDate,x,collection.get(0).getOpen(),
            collection.get(collection.size()-1).getClose()));
      }
      // list.sort(c);
      Collections.sort(list,new Comparator<AnnualizedReturn>(){
        public int compare(AnnualizedReturn v1,AnnualizedReturn v2){
          return (v2.getAnnualizedReturn().compareTo(v1.getAnnualizedReturn()));
        }
      });
    return list;
  }

}
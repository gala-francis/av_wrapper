package org.galatea.starter.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.aspect4log.Log;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Slf4j
@Log
@Service
public class TestService {

  private static final String
      REQUEST = "https://www.alphavantage.co/query?function={function}&symbol={symbol}&outputsize={outputsize}&apikey={apikey}";
  private static final String API_KEY = "59OJGP9MN11P9OM3";
  private static final String FUNCTION = "TIME_SERIES_DAILY";
  private static final String SYMBOL = "MSFT";
  private static final String OUTPUTSIZE = "compact";

  @JsonIgnoreProperties(ignoreUnknown = true)
  static private class DayData {
    @JsonProperty("1. open")
    private float open;

    @JsonProperty("2. high")
    private float high;

    @JsonProperty("3. low")
    private float low;

    @JsonProperty("4. close")
    private float close;

    @JsonProperty("5. volume")
    private float volume;

    public DayData() { }

    public float getOpen()   { return this.open; }
    public float getHigh(  ) { return this.high; }
    public float getLow()    { return this.low; }
    public float getClose()  { return this.close; }
    public float getVolume() { return this.volume; }

    public void setOpen(float open)     { this.open = open; }
    public void setHigh(float high)     { this.high = high; }
    public void setLow(float low)       { this.low = low; }
    public void setClose(float close)   { this.close = close; }
    public void setVolume(float volume) { this.volume = volume; }

    @Override
    public String toString() {
      return "DayData{" +
          "open=" + open + ", " +
          "high=" + high + ", " +
          "low=" + low+ ", " +
          "close=" + close+ ", " +
          "volume=" + volume+ "}";
    }


  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  static private class MetaData {
    @JsonProperty("1. Information")
    private String information;

    @JsonProperty("2. Symbol")
    private String symbol;

    @JsonProperty("3. Last Refreshed")
    private String lastRefreshed;

    @JsonProperty("4. Output Size")
    private String outputSize;

    @JsonProperty("5. Time Zone")
    private String timeZone;

    public MetaData() { }

    public String getInformation() { return this.information; }
    public String getSymbol()        { return this.symbol; }
    public String getLastRefreshed() { return this.lastRefreshed; }
    public String getOutputSize()    { return this.outputSize; }
    public String getTimeZone()      { return this.timeZone; }

    public void setInformation(String information)     { this.information = information; }
    public void setSymbol(String symbol)               { this.symbol = symbol; }
    public void setLastRefreshed(String lastRefreshed) { this.lastRefreshed = lastRefreshed; }
    public void setOutputSize(String outputSize)       { this.outputSize = outputSize; }
    public void setTimeZone(String timeZone)           { this.timeZone = timeZone; }

    @Override
    public String toString() {
      return "MetaData{" +
                "dataType=" + information + ", " +
                "symbol=" + symbol + ", " +
                "lastRefreshed=" + lastRefreshed + ", " +
                "outputSize=" + outputSize + ", " +
                "timeZone=" + timeZone + "}";
    }

  }
  @JsonIgnoreProperties(ignoreUnknown = true)
  static private class AVDailyAll {

    @JsonProperty("Meta Data")
    private MetaData metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<String, DayData> timeSeriesDaily;

    public AVDailyAll() { }

    public MetaData getMetaData() { return this.metaData; }
    public Map<String, DayData> getTimeSeriesDaily() { return this.timeSeriesDaily; }

    public void setMetaData(MetaData metaData) { this.metaData = metaData; }
    public void setTimeSeriesDaily(Map<String, DayData> timeSeriesDaily) { this.timeSeriesDaily = timeSeriesDaily; }

    @Override
    public String toString() {
      return "AVDailyAll{" +
                  "metaData=" + metaData + ", " +
                  "timeSeriesDaily=" + timeSeriesDaily + "}";
    }
  }

  @NonNull
  MongoService mongoService;

  /**
   * Process the text from GET command into the appropriate command
   *
   * @param text the full text from the GET command. Wit.ai will break this down
   * @return the result of executing the command with the given parameters
   */
  public String service() {
//    RestTemplate restTemplate = new RestTemplate();
//    Map<String, Object> params = new HashMap<>();
//
//    params.put("apikey", API_KEY);
//    params.put("function", FUNCTION);
//    params.put("symbol", SYMBOL);
//    params.put("outputsize", OUTPUTSIZE);
//
//    AVDailyAll data;
//    //String data;
//    data = restTemplate.getForObject(REQUEST, AVDailyAll.class, params);
//    return data.toString();

//    MongoService mongoService = new MongoService();

//    mongoService.connectionTest();



    Document doc = mongoService.getStockData("TSLA");

    return doc.toJson();


  }
}

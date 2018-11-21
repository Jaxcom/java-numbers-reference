package demo;

import com.bandwidth.sdk.numbers.NumbersClient;
import com.bandwidth.sdk.numbers.NumbersClientImpl;
import com.bandwidth.sdk.numbers.models.AvailableNumberSearchRequest;
import com.bandwidth.sdk.numbers.models.SearchResult;
import com.bandwidth.sdk.numbers.models.orders.AreaCodeSearchAndOrderType;
import com.bandwidth.sdk.numbers.models.orders.CitySearchAndOrderType;
import com.bandwidth.sdk.numbers.models.orders.CombinedSearchAndOrderType;
import com.bandwidth.sdk.numbers.models.orders.ExistingTelephoneNumberOrderType;
import com.bandwidth.sdk.numbers.models.orders.LataSearchAndOrderType;
import com.bandwidth.sdk.numbers.models.orders.Order;
import com.bandwidth.sdk.numbers.models.orders.OrderResponse;
import com.bandwidth.sdk.numbers.models.orders.RateCenterSearchAndOrderType;
import com.bandwidth.sdk.numbers.models.orders.StateSearchAndOrderType;
import com.bandwidth.sdk.numbers.models.orders.TollFreeVanitySearchAndOrderType;
import com.bandwidth.sdk.numbers.models.orders.TollFreeWildCharSearchAndOrderType;
import com.bandwidth.sdk.numbers.models.orders.ZipSearchAndOrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo {

   private static final Logger log = LoggerFactory.getLogger(Demo.class);

   public static void main(String[] args) {

      try (NumbersClientImpl numbersClient = NumbersClientImpl.builder()
         .account("1")
         .username("systemUser")
         .password("systemUser")
         .build()) {

         // Search for available numbers
         AvailableNumberSearchRequest availableNumberSearchRequest = AvailableNumberSearchRequest.builder()
            .state("NC")
            .city("CARY")
            .enableTNDetail(false)
            .quantity(10)
            .build();

         SearchResult searchResult = numbersClient.getAvailableTelephoneNumbers(availableNumberSearchRequest);

         log.info("SearchResult={}", searchResult);

         // putting it all together, place an order for numbers returned from a number search
         ExistingTelephoneNumberOrderType existingTelephoneNumberOrderType =
            ExistingTelephoneNumberOrderType.builder()
               .addAllTelephoneNumberList(searchResult.getTelephoneNumberList())
               .build();

         Order order = Order.builder()
            .siteId("1")
            .peerId("500539")
            .existingTelephoneNumberOrderType(existingTelephoneNumberOrderType)
            .build();

         tryOrder(numbersClient, order);

         AreaCodeSearchAndOrderType areaCodeSearchAndOrderType = AreaCodeSearchAndOrderType.builder()
            .areaCode("919")
            .quantity(2)
            .build();
         order = Order.builder()
            .siteId("1")
            .peerId("500539")
            .areaCodeSearchAndOrderType(areaCodeSearchAndOrderType)
            .build();

         tryOrder(numbersClient, order);

         CombinedSearchAndOrderType combinedSearchAndOrderType = CombinedSearchAndOrderType.builder()
            .rateCenterAbbreviation("RALEIGH")
            .state("NC")
            .quantity(2)
            .build();
         order = Order.builder()
            .siteId("1")
            .peerId("500539")
            .combinedSearchAndOrderType(combinedSearchAndOrderType)
            .build();

         tryOrder(numbersClient, order);

         CitySearchAndOrderType citySearchAndOrderType = CitySearchAndOrderType.builder()
            .city("raleigh")
            .state("NC")
            .quantity(2)
            .build();
         order = Order.builder()
            .siteId("1")
            .peerId("500539")
            .citySearchAndOrderType(citySearchAndOrderType)
            .build();

         tryOrder(numbersClient, order);

         LataSearchAndOrderType lataSearchAndOrderType = LataSearchAndOrderType.builder()
            .lata("224")
            .quantity(2)
            .build();
         order = Order.builder()
            .siteId("1")
            .peerId("500539")
            .lataSearchAndOrderType(lataSearchAndOrderType)
            .build();

         tryOrder(numbersClient, order);

         RateCenterSearchAndOrderType rateCenterSearchAndOrderType = RateCenterSearchAndOrderType.builder()
            .rateCenter("RALEIGH")
            .state("NC")
            .quantity(2)
            .build();
         order = Order.builder()
            .siteId("1")
            .peerId("500539")
            .rateCenterSearchAndOrderType(rateCenterSearchAndOrderType)
            .build();

         tryOrder(numbersClient, order);

         StateSearchAndOrderType stateSearchAndOrderType = StateSearchAndOrderType.builder()
            .state("NC")
            .quantity(2)
            .build();
         order = Order.builder()
            .siteId("1")
            .peerId("500539")
            .stateSearchAndOrderType(stateSearchAndOrderType)
            .build();

         tryOrder(numbersClient, order);

         ZipSearchAndOrderType zipSearchAndOrderType = ZipSearchAndOrderType.builder()
            .zip("27606")
            .quantity(2)
            .build();
         order = Order.builder()
            .siteId("1")
            .peerId("500539")
            .zipSearchAndOrderType(zipSearchAndOrderType)
            .build();

         tryOrder(numbersClient, order);

         TollFreeWildCharSearchAndOrderType tollFreeWildCharSearchAndOrderType = TollFreeWildCharSearchAndOrderType.builder()
            .tollFreeWildCardPattern("87*")
            .quantity(1)
            .build();
         order = Order.builder()
            .siteId("1")
            .peerId("500539")
            .tollFreeWildCharSearchAndOrderType(tollFreeWildCharSearchAndOrderType)
            .build();

         tryOrder(numbersClient, order);

         TollFreeVanitySearchAndOrderType tollFreeVanitySearchAndOrderType = TollFreeVanitySearchAndOrderType.builder()
            .tollFreeVanity("newcars")
            .quantity(1)
            .build();
         order = Order.builder()
            .siteId("1")
            .peerId("500539")
            .tollFreeVanitySearchAndOrderType(tollFreeVanitySearchAndOrderType)
            .build();

         tryOrder(numbersClient, order);
      }
   }

   private static void tryOrder(NumbersClient numbersClient, Order order) {
      try {
         log.info("About to place order, order={}", order);
         OrderResponse orderResponse = numbersClient.orderTelephoneNumbers(order);
         log.info("Order completed, orderResponse={}", orderResponse);

      } catch (Exception e) {
         log.error("Error thrown while ordering! order={}", order, e);
      }
   }

}

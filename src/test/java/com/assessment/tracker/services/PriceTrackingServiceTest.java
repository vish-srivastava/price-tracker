package com.assessment.tracker.services;

import com.assessment.tracker.CommonTestUtils;
import com.assessment.tracker.clients.coindesk.CoinDeskClient;
import com.assessment.tracker.models.HistoricalPriceRequest;
import com.assessment.tracker.models.HistoricalPriceResponse;
import com.assessment.tracker.models.currency.*;
import com.assessment.tracker.services.factories.PriceTrackingFactory;
import com.assessment.tracker.services.implementations.BitcoinPriceTracker;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.http.HttpConnectTimeoutException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PriceTrackingServiceTest {
    @Mock
    private CoinDeskClient coinDeskClient;
    @InjectMocks
    private BitcoinPriceTracker priceTracker;
    private PriceTrackingFactory priceTrackingFactory;
    private final String startDate = "2022-01-01";
    private final String endDate = "2022-02-01";

    private PriceTrackingService priceTrackingService;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @BeforeEach
    private void setup() throws JsonProcessingException, HttpConnectTimeoutException {
        priceTrackingFactory = new PriceTrackingFactory(List.of(priceTracker));
        when(coinDeskClient.getHistoricalBitcoinPriceData(any(), any(), any())).thenReturn(getSuccessResponse());
        priceTrackingService = new PriceTrackingService(priceTrackingFactory);

    }

    @Test
    void testPriceTrackingFactory() {
        CurrencyType currencyType = CurrencyType.CRYPTO;
        Currency currency = Crypto.BTC;
        HistoricalPriceRequest request = getRequest(CurrencyType.CRYPTO, currency.getCurrencyAbbreviation());
        PriceTracker tracker = priceTrackingFactory.fetchPriceTracker(currency.getCurrencyAbbreviation(), currencyType);
        assertTrue(tracker instanceof BitcoinPriceTracker);
    }

    @Test
    void testPriceTrackingFactoryBadRequest() {
        CurrencyType currencyType = CurrencyType.CRYPTO;
        Currency currency = Crypto.ETH;
        assertThrows(NotImplementedException.class, () -> priceTrackingFactory.fetchPriceTracker(currency.getCurrencyAbbreviation(), currencyType));
        priceTrackingFactory = new PriceTrackingFactory(Collections.emptyList());
        assertThrows(NotImplementedException.class, () -> priceTrackingFactory.fetchPriceTracker("", CurrencyType.CRYPTO));
    }

    @Test
    void testHappyCase() throws HttpConnectTimeoutException {
        CurrencyType currencyType = CurrencyType.CRYPTO;
        Currency currency = Crypto.BTC;
        HistoricalPriceRequest request = getRequest(CurrencyType.CRYPTO, currency.getCurrencyAbbreviation());
        PriceTracker tracker = priceTrackingFactory.fetchPriceTracker(currency.getCurrencyAbbreviation(), currencyType);
        HistoricalPriceResponse response = tracker.fetchHistoricalPriceData(request);
        assertTrue(Objects.nonNull(response));
        assertTrue(response.getCurrencyType().equals(currencyType));
    }

    private HistoricalPriceRequest getRequest(CurrencyType currencyType, String currency) {
        return HistoricalPriceRequest.builder()
                .startDate(LocalDate.parse(startDate, dateTimeFormatter))
                .endDate(LocalDate.parse(endDate, dateTimeFormatter))
                .currencyType(currencyType)
                .currency(currency)
                .targetCurrency(Federal.USD.getCurrencyAbbreviation())
                .build();
    }

    private String getSuccessResponse() {
        return "{\"bpi\":{\"2022-01-01\":47742.995,\"2022-01-02\":47310.9233,\"2022-01-03\":46448.0367,\"2022-01-04\":45837.3333,\"2022-01-05\":43430.7067,\"2022-01-06\":43098.685,\"2022-01-07\":41541.2233,\"2022-01-08\":41689.195,\"2022-01-09\":41864.1067,\"2022-01-10\":41838.4617,\"2022-01-11\":42744.29,\"2022-01-12\":43921.2833,\"2022-01-13\":42577.1583,\"2022-01-14\":43095.525,\"2022-01-15\":43096.105,\"2022-01-16\":43091.2567,\"2022-01-17\":42224.7167,\"2022-01-18\":42373.6817,\"2022-01-19\":41676.665,\"2022-01-20\":40705.4417,\"2022-01-21\":36462.6283,\"2022-01-22\":35085.14,\"2022-01-23\":36286.2217,\"2022-01-24\":36703.22,\"2022-01-25\":36981.4917,\"2022-01-26\":36828.1,\"2022-01-27\":37194.41,\"2022-01-28\":37740.7367,\"2022-01-29\":38186.37,\"2022-01-30\":37915.8983,\"2022-01-31\":38499.2683,\"2022-02-01\":38718.8067},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index. BPI value data returned as USD.\",\"time\":{\"updated\":\"Feb 2, 2022 00:03:00 UTC\",\"updatedISO\":\"2022-02-02T00:03:00+00:00\"}}";
    }

    private String getAvailableCurrenciesMock() {
        return "[{\"currency\":\"AED\",\"country\":\"United Arab Emirates Dirham\"},{\"currency\":\"AFN\",\"country\":\"Afghan Afghani\"},{\"currency\":\"ALL\",\"country\":\"Albanian Lek\"},{\"currency\":\"AMD\",\"country\":\"Armenian Dram\"},{\"currency\":\"ANG\",\"country\":\"Netherlands Antillean Guilder\"},{\"currency\":\"AOA\",\"country\":\"Angolan Kwanza\"},{\"currency\":\"ARS\",\"country\":\"Argentine Peso\"},{\"currency\":\"AUD\",\"country\":\"Australian Dollar\"},{\"currency\":\"AWG\",\"country\":\"Aruban Florin\"},{\"currency\":\"AZN\",\"country\":\"Azerbaijani Manat\"},{\"currency\":\"BAM\",\"country\":\"Bosnia-Herzegovina Convertible Mark\"},{\"currency\":\"BBD\",\"country\":\"Barbadian Dollar\"},{\"currency\":\"BDT\",\"country\":\"Bangladeshi Taka\"},{\"currency\":\"BGN\",\"country\":\"Bulgarian Lev\"},{\"currency\":\"BHD\",\"country\":\"Bahraini Dinar\"},{\"currency\":\"BIF\",\"country\":\"Burundian Franc\"},{\"currency\":\"BMD\",\"country\":\"Bermudan Dollar\"},{\"currency\":\"BND\",\"country\":\"Brunei Dollar\"},{\"currency\":\"BOB\",\"country\":\"Bolivian Boliviano\"},{\"currency\":\"BRL\",\"country\":\"Brazilian Real\"},{\"currency\":\"BSD\",\"country\":\"Bahamian Dollar\"},{\"currency\":\"BTC\",\"country\":\"Bitcoin\"},{\"currency\":\"BTN\",\"country\":\"Bhutanese Ngultrum\"},{\"currency\":\"BWP\",\"country\":\"Botswanan Pula\"},{\"currency\":\"BYR\",\"country\":\"Belarusian Ruble\"},{\"currency\":\"BZD\",\"country\":\"Belize Dollar\"},{\"currency\":\"CAD\",\"country\":\"Canadian Dollar\"},{\"currency\":\"CDF\",\"country\":\"Congolese Franc\"},{\"currency\":\"CHF\",\"country\":\"Swiss Franc\"},{\"currency\":\"CLF\",\"country\":\"Chilean Unit of Account (UF)\"},{\"currency\":\"CLP\",\"country\":\"Chilean Peso\"},{\"currency\":\"CNY\",\"country\":\"Chinese Yuan\"},{\"currency\":\"COP\",\"country\":\"Colombian Peso\"},{\"currency\":\"CRC\",\"country\":\"Costa Rican Colón\"},{\"currency\":\"CUP\",\"country\":\"Cuban Peso\"},{\"currency\":\"CVE\",\"country\":\"Cape Verdean Escudo\"},{\"currency\":\"CZK\",\"country\":\"Czech Republic Koruna\"},{\"currency\":\"DJF\",\"country\":\"Djiboutian Franc\"},{\"currency\":\"DKK\",\"country\":\"Danish Krone\"},{\"currency\":\"DOP\",\"country\":\"Dominican Peso\"},{\"currency\":\"DZD\",\"country\":\"Algerian Dinar\"},{\"currency\":\"EEK\",\"country\":\"Estonian Kroon\"},{\"currency\":\"EGP\",\"country\":\"Egyptian Pound\"},{\"currency\":\"ERN\",\"country\":\"Eritrean Nnakfa\"},{\"currency\":\"ETB\",\"country\":\"Ethiopian Birr\"},{\"currency\":\"EUR\",\"country\":\"Euro\"},{\"currency\":\"FJD\",\"country\":\"Fijian Dollar\"},{\"currency\":\"FKP\",\"country\":\"Falkland Islands Pound\"},{\"currency\":\"GBP\",\"country\":\"British Pound Sterling\"},{\"currency\":\"GEL\",\"country\":\"Georgian Lari\"},{\"currency\":\"GHS\",\"country\":\"Ghanaian Cedi\"},{\"currency\":\"GIP\",\"country\":\"Gibraltar Pound\"},{\"currency\":\"GMD\",\"country\":\"Gambian Dalasi\"},{\"currency\":\"GNF\",\"country\":\"Guinean Franc\"},{\"currency\":\"GTQ\",\"country\":\"Guatemalan Quetzal\"},{\"currency\":\"GYD\",\"country\":\"Guyanaese Dollar\"},{\"currency\":\"HKD\",\"country\":\"Hong Kong Dollar\"},{\"currency\":\"HNL\",\"country\":\"Honduran Lempira\"},{\"currency\":\"HRK\",\"country\":\"Croatian Kuna\"},{\"currency\":\"HTG\",\"country\":\"Haitian Gourde\"},{\"currency\":\"HUF\",\"country\":\"Hungarian Forint\"},{\"currency\":\"IDR\",\"country\":\"Indonesian Rupiah\"},{\"currency\":\"ILS\",\"country\":\"Israeli New Sheqel\"},{\"currency\":\"INR\",\"country\":\"Indian Rupee\"},{\"currency\":\"IQD\",\"country\":\"Iraqi Dinar\"},{\"currency\":\"IRR\",\"country\":\"Iranian Rial\"},{\"currency\":\"ISK\",\"country\":\"Icelandic Króna\"},{\"currency\":\"JEP\",\"country\":\"Jersey Pound\"},{\"currency\":\"JMD\",\"country\":\"Jamaican Dollar\"},{\"currency\":\"JOD\",\"country\":\"Jordanian Dinar\"},{\"currency\":\"JPY\",\"country\":\"Japanese Yen\"},{\"currency\":\"KES\",\"country\":\"Kenyan Shilling\"},{\"currency\":\"KGS\",\"country\":\"Kyrgystani Som\"},{\"currency\":\"KHR\",\"country\":\"Cambodian Riel\"},{\"currency\":\"KMF\",\"country\":\"Comorian Franc\"},{\"currency\":\"KPW\",\"country\":\"North Korean Won\"},{\"currency\":\"KRW\",\"country\":\"South Korean Won\"},{\"currency\":\"KWD\",\"country\":\"Kuwaiti Dinar\"},{\"currency\":\"KYD\",\"country\":\"Cayman Islands Dollar\"},{\"currency\":\"KZT\",\"country\":\"Kazakhstani Tenge\"},{\"currency\":\"LAK\",\"country\":\"Laotian Kip\"},{\"currency\":\"LBP\",\"country\":\"Lebanese Pound\"},{\"currency\":\"LKR\",\"country\":\"Sri Lankan Rupee\"},{\"currency\":\"LRD\",\"country\":\"Liberian Dollar\"},{\"currency\":\"LSL\",\"country\":\"Lesotho Loti\"},{\"currency\":\"LTL\",\"country\":\"Lithuanian Litas\"},{\"currency\":\"LVL\",\"country\":\"Latvian Lats\"},{\"currency\":\"LYD\",\"country\":\"Libyan Dinar\"},{\"currency\":\"MAD\",\"country\":\"Moroccan Dirham\"},{\"currency\":\"MDL\",\"country\":\"Moldovan Leu\"},{\"currency\":\"MGA\",\"country\":\"Malagasy Ariary\"},{\"currency\":\"MKD\",\"country\":\"Macedonian Denar\"},{\"currency\":\"MMK\",\"country\":\"Myanma Kyat\"},{\"currency\":\"MNT\",\"country\":\"Mongolian Tugrik\"},{\"currency\":\"MOP\",\"country\":\"Macanese Pataca\"},{\"currency\":\"MRO\",\"country\":\"Mauritanian Ouguiya\"},{\"currency\":\"MTL\",\"country\":\"Maltese Lira\"},{\"currency\":\"MUR\",\"country\":\"Mauritian Rupee\"},{\"currency\":\"MVR\",\"country\":\"Maldivian Rufiyaa\"},{\"currency\":\"MWK\",\"country\":\"Malawian Kwacha\"},{\"currency\":\"MXN\",\"country\":\"Mexican Peso\"},{\"currency\":\"MYR\",\"country\":\"Malaysian Ringgit\"},{\"currency\":\"MZN\",\"country\":\"Mozambican Metical\"},{\"currency\":\"NAD\",\"country\":\"Namibian Dollar\"},{\"currency\":\"NGN\",\"country\":\"Nigerian Naira\"},{\"currency\":\"NIO\",\"country\":\"Nicaraguan Córdoba\"},{\"currency\":\"NOK\",\"country\":\"Norwegian Krone\"},{\"currency\":\"NPR\",\"country\":\"Nepalese Rupee\"},{\"currency\":\"NZD\",\"country\":\"New Zealand Dollar\"},{\"currency\":\"OMR\",\"country\":\"Omani Rial\"},{\"currency\":\"PAB\",\"country\":\"Panamanian Balboa\"},{\"currency\":\"PEN\",\"country\":\"Peruvian Nuevo Sol\"},{\"currency\":\"PGK\",\"country\":\"Papua New Guinean Kina\"},{\"currency\":\"PHP\",\"country\":\"Philippine Peso\"},{\"currency\":\"PKR\",\"country\":\"Pakistani Rupee\"},{\"currency\":\"PLN\",\"country\":\"Polish Zloty\"},{\"currency\":\"PYG\",\"country\":\"Paraguayan Guarani\"},{\"currency\":\"QAR\",\"country\":\"Qatari Rial\"},{\"currency\":\"RON\",\"country\":\"Romanian Leu\"},{\"currency\":\"RSD\",\"country\":\"Serbian Dinar\"},{\"currency\":\"RUB\",\"country\":\"Russian Ruble\"},{\"currency\":\"RWF\",\"country\":\"Rwandan Franc\"},{\"currency\":\"SAR\",\"country\":\"Saudi Riyal\"},{\"currency\":\"SBD\",\"country\":\"Solomon Islands Dollar\"},{\"currency\":\"SCR\",\"country\":\"Seychellois Rupee\"},{\"currency\":\"SDG\",\"country\":\"Sudanese Pound\"},{\"currency\":\"SEK\",\"country\":\"Swedish Krona\"},{\"currency\":\"SGD\",\"country\":\"Singapore Dollar\"},{\"currency\":\"SHP\",\"country\":\"Saint Helena Pound\"},{\"currency\":\"SLL\",\"country\":\"Sierra Leonean Leone\"},{\"currency\":\"SOS\",\"country\":\"Somali Shilling\"},{\"currency\":\"SRD\",\"country\":\"Surinamese Dollar\"},{\"currency\":\"STD\",\"country\":\"São Tomé and Príncipe Dobra\"},{\"currency\":\"SVC\",\"country\":\"Salvadoran Colón\"},{\"currency\":\"SYP\",\"country\":\"Syrian Pound\"},{\"currency\":\"SZL\",\"country\":\"Swazi Lilangeni\"},{\"currency\":\"THB\",\"country\":\"Thai Baht\"},{\"currency\":\"TJS\",\"country\":\"Tajikistani Somoni\"},{\"currency\":\"TMT\",\"country\":\"Turkmenistani Manat\"},{\"currency\":\"TND\",\"country\":\"Tunisian Dinar\"},{\"currency\":\"TOP\",\"country\":\"Tongan Pa?anga\"},{\"currency\":\"TRY\",\"country\":\"Turkish Lira\"},{\"currency\":\"TTD\",\"country\":\"Trinidad and Tobago Dollar\"},{\"currency\":\"TWD\",\"country\":\"New Taiwan Dollar\"},{\"currency\":\"TZS\",\"country\":\"Tanzanian Shilling\"},{\"currency\":\"UAH\",\"country\":\"Ukrainian Hryvnia\"},{\"currency\":\"UGX\",\"country\":\"Ugandan Shilling\"},{\"currency\":\"USD\",\"country\":\"United States Dollar\"},{\"currency\":\"UYU\",\"country\":\"Uruguayan Peso\"},{\"currency\":\"UZS\",\"country\":\"Uzbekistan Som\"},{\"currency\":\"VEF\",\"country\":\"Venezuelan Bolívar Fuerte\"},{\"currency\":\"VND\",\"country\":\"Vietnamese Dong\"},{\"currency\":\"VUV\",\"country\":\"Vanuatu Vatu\"},{\"currency\":\"WST\",\"country\":\"Samoan Tala\"},{\"currency\":\"XAF\",\"country\":\"CFA Franc BEAC\"},{\"currency\":\"XAG\",\"country\":\"Silver (troy ounce)\"},{\"currency\":\"XAU\",\"country\":\"Gold (troy ounce)\"},{\"currency\":\"XBT\",\"country\":\"Bitcoin\"},{\"currency\":\"XCD\",\"country\":\"East Caribbean Dollar\"},{\"currency\":\"XDR\",\"country\":\"Special Drawing Rights\"},{\"currency\":\"XOF\",\"country\":\"CFA Franc BCEAO\"},{\"currency\":\"XPF\",\"country\":\"CFP Franc\"},{\"currency\":\"YER\",\"country\":\"Yemeni Rial\"},{\"currency\":\"ZAR\",\"country\":\"South African Rand\"},{\"currency\":\"ZMK\",\"country\":\"Zambian Kwacha (pre-2013)\"},{\"currency\":\"ZMW\",\"country\":\"Zambian Kwacha\"},{\"currency\":\"ZWL\",\"country\":\"Zimbabwean Dollar\"}]";
    }

    @Test
    public void testGetAvailableCurrencies() throws HttpConnectTimeoutException {
        when(coinDeskClient.getSupportedCurrencies()).thenReturn(getAvailableCurrenciesMock());
        Optional<AvailableCurrencyResponse> response = Optional.ofNullable(priceTracker.fetchAvailableCurrencies());
        Optional<AvailableCurrencyResponse> outerResponse = priceTrackingService.getAvailableCurrencies();
        assertTrue(response.isPresent());
        assertNotNull(response.get());
        assertTrue(outerResponse.isPresent());
        assertNotNull(outerResponse.get());
    }

    @Test
    public void testFetchHistoricalData() throws HttpConnectTimeoutException {
        when(coinDeskClient.getHistoricalBitcoinPriceData(any(), any(), any())).thenReturn(getSuccessResponse());
        HistoricalPriceRequest request = CommonTestUtils.getRequest(CurrencyType.CRYPTO, Crypto.BTC.getCurrencyAbbreviation());
        HistoricalPriceResponse historicalPriceResponse = priceTrackingService.fetchHistoricalData(request);
        assertNotNull(historicalPriceResponse);
    }
}

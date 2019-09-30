package com.application.seb.projet5_mynews.Utils;

public class MyConstants {


    //----------------------------------------------------------------------------------------------
    // Request api values
    //----------------------------------------------------------------------------------------------
    static final String REQUEST_API_KEY_QUERY = "api-key";
    static final String MOST_POPULAR_BODY_REQUEST_1ST_PART = "viewed/{";
    static final String MOST_POPULAR_BODY_REQUEST_2ND_PART = "}.json";
    static final String MOST_POPULAR_PATH = "period";
    static final String MOST_POPULAR_HEAD = "https://api.nytimes.com/svc/mostpopular/v2/";
    static final String TOP_STORIES_BODY_REQUEST = "home.json";
    static final String TOP_STORIES_HEAD_REQUEST = "https://api.nytimes.com/svc/topstories/v2/";
    static final String SEARCH_BODY_REQUEST = "articlesearch.json";
    static final String SEARCH_HEAD_REQUEST = "https://api.nytimes.com/svc/search/v2/";

    //----------------------------------------------------------------------------------------------
    //  Api key
    //----------------------------------------------------------------------------------------------
    public static final String API_KEY ="e4EHaNynYFV5FeqPowUi95AWoLFVB26Y";

    //----------------------------------------------------------------------------------------------
    //  WebView intent key
    //----------------------------------------------------------------------------------------------
    public static final String WEB_VIEW_URL ="webViewUrl";

    //----------------------------------------------------------------------------------------------
    //SearchResultActivity intent value
    //----------------------------------------------------------------------------------------------
    public static final String STRING_RESPONSE = "string_response";

    //----------------------------------------------------------------------------------------------
    //  Notifications Shared preference values
    //----------------------------------------------------------------------------------------------
    public  static  final  String SHARED_PREFERENCES_NAME = "notifications_parameters";
    public static final String SEARCH_REQUEST_QUERY = "search_request_query";
    public static final String SEARCH_REQUEST_QUERY_FILTERS = "search_request_query_filters";
    public static final String NOTIFICATIONS_ON = "notifications_ON";

    //----------------------------------------------------------------------------------------------
    //  HashMap values for search API request
    //----------------------------------------------------------------------------------------------
    public static final String APIKEY = "api-key";
    public static final String BEGIN_DATE = "begin_date";
    public static final String END_DATE = "end_date";
    public static final String QUERY = "query";
    public static final String QUERY_FILTERS = "fq";
    public static final String NEWS_DESK = "news_desk:";

    //----------------------------------------------------------------------------------------------
    //  Section value for search http request
    //----------------------------------------------------------------------------------------------
    public static final String BUSINESS_SECTION = "business";

    //----------------------------------------------------------------------------------------------
    //  Notifications channel
    //----------------------------------------------------------------------------------------------
    static final String CHANNEL_1_ID = "channel1";
    static final String CHANNEL_1_NAME= "Channel 1";
    static final String CHANNEL_1_DESCRIPTION = "This is channel 1";
}

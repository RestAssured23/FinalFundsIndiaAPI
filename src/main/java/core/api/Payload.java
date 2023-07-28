package core.api;

import core.basepath.AccessPropertyFile;


public class Payload extends AccessPropertyFile {


    public static String product_Search()
    {
        return "{\n" +
                "  \"page\": 1,\n" +
                "  \"size\": 10,\n" +
                "  \"orderBy\": \"rating\",\n" +
                "  \"orderType\": \"DESC\",\n" +
                "  \"categories\": [],\n" +
                "  \"subCategories\": [],\n" +
                "  \"query\": \""+schemeSearch+"\",\n" +
                "  \"risk\": [],\n" +
                "  \"ratings\": [],\n" +
                "  \"amcs\": [],\n" +
                "  \"searchCode\": [\n" +
                "    {\n" +
                "      \"value\": \"\",\n" +
                "      \"sort\": true\n" +
                "    }\n" +
                "  ],\n" +
                "  \"sip\": true\n" +
                "}";
                    }
    public static String Select_Funds()
    {
        return """
                {
                  "page": 1,
                  "size": 5,
                  "orderBy": "rating",
                  "orderType": "DESC",
                  "categories": [],
                  "subCategories": [],
                  "query": "",
                  "risk": [],
                  "ratings": [],
                  "amcs": [],
                  "searchCode": [
                    {
                      "value": "recommended",
                      "sort": true
                    }
                  ]
                }""";
    }
    public static String NFO(){
        return """
                {
                  "page": 1,
                  "size": 10,
                  "orderBy": "rating",
                  "orderType": "DESC",
                  "categories": [],
                  "subCategories": [],
                  "query": "",
                  "risk": [],
                  "ratings": [],
                  "amcs": [],
                  "searchCode": [
                    {
                      "value": "nfo",
                      "sort": true
                    }
                  ],
                  "nfo": true,
                  "schemeType": []
                }""";
    }
    public static String Super_Savings(){
        return """
                {
                  "page": 1,
                  "size": 10,
                  "orderBy": "rating",
                  "orderType": "DESC",
                  "categories": [],
                  "subCategories": [],
                  "query": "",
                  "risk": [],
                  "ratings": [],
                  "amcs": [],
                  "searchCode": [
                    {
                      "value": "super_savings",
                      "sort": true
                    }
                  ]
                }""";
    }
    public static String questionnaire() {
        {
            return """
                    {
                      "type": "pre-redemption",
                      "answers": [
                        {
                          "questionId": "4",
                          "answerId": "0",
                          "answer": ""
                        }
                      ]
                    }""";
        }
    }
}

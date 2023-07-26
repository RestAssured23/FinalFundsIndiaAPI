package core.api;

public class Payload {
    public static String product_Search()
    {
        return """
                {
                  "page": 1,
                  "size": 10,
                  "orderBy": "rating",
                  "orderType": "ASC",
                  "categories": [],
                  "subCategories": [],
                  "query": "icici",
                  "risk": [],
                  "ratings": [],
                  "amcs": [],
                  "searchCode": [
                    {
                      "value": "",
                      "sort": true
                    }
                  ],
                  "sip": true
                }""";
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

package core.api;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.Getter;
import lombok.Setter;

public class CommonVariable {

    @Getter@Setter
    public static class Variables{

    }
    public static RequestSpecification req;
    public static ResponseSpecification respec;
    public static String holdingId,InvestorId,response,logResponse,folio, otpRefID, dbOtp, dbRefId,firstReferenceNo,
            qrefId,goalId, goalName, schemeCode,schemeName,schemeOption,Source_SchemeName,
            fromSchemeName,fromSchemeCode,fromOption,
            toSchemeName,toSchemeCode,toOption,amcName,amcCode,unintsformat, dividendoption,
            option, bankId, minunitformat,minuamountformat,CartId,GroupId;

    public static double totalUnits, minUnit, minAmt,availableUnits,currentAmt,units;

}

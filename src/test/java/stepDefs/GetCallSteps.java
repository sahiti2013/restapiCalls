package stepDefs;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;

public class GetCallSteps {

    private ValidatableResponse json;
    private Response response;
    static String baseurl;






    @Given("^the apis are up and running for \"([^\"]*)\"$")
    public void theApisAreUpAndRunningFor(String baseurl) {
        this.baseurl = baseurl;
    }

    @When("^user performs a get request to \"([^\"]*)\"$")
    public void userPerformsAGetRequestTo(String api_url) {
        this.baseurl = this.baseurl + api_url;
        response=get(baseurl);

    }
    @Then("^the status code is (\\d+)$")
    public void theStatusCodeIs(int statusCode){
        json = response.then().statusCode(statusCode);

    }

    @And("^user should see following details in the json response$")
    public void userShouldSeeFollowingDetailsInTheJsonResponse(Map<String, String> responseFields) {
        System.out.println(response.getBody().prettyPrint());
        for (Map.Entry<String, String> field : responseFields.entrySet()) {
            if (StringUtils.isNumeric(field.getValue())) {
                System.out.println(field.getValue());
                json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
            } else {
                json.body(field.getKey(), containsInAnyOrder(field.getValue()));
            }

        }
    }


}






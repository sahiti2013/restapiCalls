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
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;


public class PostCallSteps {

     static String baseurl;
     Map<String,String> body;
     private ValidatableResponse json;
     private Response response;
     Map<String,Object> responsemap;




    @Given("^the apis are up and running for post call \"([^\"]*)\"$")
    public void theApisAreUpAndRunningForPostCall(String baseurl)  {
        this.baseurl = baseurl;
    }
    @When("^user performs a post request to \"([^\"]*)\" with below details$")
    public void userPerformsAPostRequestToWithBelowDetails(String post_url, DataTable dataTable) {
        this.baseurl = this.baseurl + post_url;
        System.out.println(post_url);
        System.out.println(baseurl);
        response = RestAssured.post(baseurl);
        this.body = new LinkedHashMap<String, String>();
        for (DataTableRow row : dataTable.getGherkinRows()) {
            this.body.put(row.getCells().get(0), row.getCells().get(1));

        }
    }

    @Then("^the status code for post call is (\\d+)$")
    public void theStatusCodeForPostCallIs(int statusCode){
        json = response.then();
        response= RestAssured.given().contentType(ContentType.JSON).body(this.body).when().post(this.baseurl);
int code=response.getStatusCode();
        System.out.println(code);
        Assert.assertEquals(201,code);
    }

    @And("^user should see following details in the json response in post call$")
    public void userShouldSeeFollowingDetailsInTheJsonResponseInPostCall(DataTable dataTable){

Map<String,String>query=new LinkedHashMap<String, String>();
        for (DataTableRow row : dataTable.getGherkinRows()) {
            this.body.put(row.getCells().get(0), row.getCells().get(1));
        }
        ObjectReader reader=new ObjectMapper().reader(Map.class);
        try {
            responsemap=reader.readValue(response.asString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(responsemap);
        for(String key:query.keySet()){
            Assert.assertTrue(responsemap.containsKey(key));
            Assert.assertEquals(query.get(key),responsemap.get(key).toString());
        }

    }




}

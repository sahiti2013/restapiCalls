package stepDefs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import cucumber.api.DataTable;
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

public class PutcallSteps {


    static String baseurl;
    Map<String,String> body;
    private ValidatableResponse json;
    private Response response;
    Map<String,Object> responsemap;


    @Given("^the apis are up and running for put call \"([^\"]*)\"$")
    public void the_apis_are_up_and_running_for_put_call_something(String baseurl) {
        this.baseurl = baseurl;
    }

    @When("^user performs a put request to \"([^\"]*)\" with below details$")
    public void user_performs_a_put_request_to_something_with_below_details(String putCall_url, DataTable dataTable) {
        this.baseurl = this.baseurl + putCall_url;
        System.out.println(putCall_url);
        System.out.println(baseurl);
        response = RestAssured.put(baseurl);
        this.body = new LinkedHashMap<String, String>();
        for (DataTableRow row : dataTable.getGherkinRows()) {
            this.body.put(row.getCells().get(0), row.getCells().get(1));

        }
    }
    @Then("^the status code for put call is 200$")
    public void the_status_code_for_put_call_is_200()  {
        json = response.then();
        response= RestAssured.given().contentType(ContentType.JSON).body(this.body).when().put(this.baseurl);
        int code=response.getStatusCode();
        System.out.println(code);
        Assert.assertEquals(200,code);
    }

    @And("^user should see following details in the json response in put call$")
    public void user_should_see_following_details_in_the_json_response_in_put_call(DataTable dataTable) {
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

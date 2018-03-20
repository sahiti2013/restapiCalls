package stepDefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;


public class DeleteCallSteps {
    static String baseurl;
    private Response response;
    private ValidatableResponse json;


    @Given("^the apis are up and running for delete is \"([^\"]*)\"$")
    public void theApisAreUpAndRunningForDeleteIs(String baseurl) {
        this.baseurl = baseurl;
    }

    @When("^user performs a delete request to \"([^\"]*)\"$")
    public void userPerformsADeleteRequestTo(String deleteCall_url) {
        //response=RestAssured.delete(baseurl+deleteCall_url);
        this.baseurl = this.baseurl + deleteCall_url;
        response = RestAssured.delete(baseurl);
        System.out.println(response.getStatusCode());
    }



    @Then("^the status code in delete is (\\d+)$")
    public void theStatusCodeInDeleteIs(int statusCode){

        json = response.then().statusCode(statusCode);
        //System.out.println(json);
    }


}
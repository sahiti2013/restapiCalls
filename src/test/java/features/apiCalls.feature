@ApiCalls
Feature:Happy path tests for GET,POST,PUT and DELETE

@Create(post)
Scenario: create users
  Given the apis are up and running for post call "https://reqres.in"
  When user performs a post request to "/api/users" with below details
    | name | morpheus |
    | job  | leader   |
  Then the status code for post call is 201
  And user should see following details in the json response in post call
    | name      | morpheus                 |
    | job       | leader                   |
    | createdAt | 2018-02-21T13:45:11.557Z |
  @Retreive(get)
  Scenario: Get list of users
    Given the apis are up and running for "https://reqres.in"
    When user performs a get request to "/api/users?page=2"
    Then the status code is 200
    And user should see following details in the json response
      | page     | 2  |
      | total    | 12 |
      | per_page | 3  |

@update(put)
Scenario: update users
  Given the apis are up and running for put call "https://reqres.in"
  When user performs a put request to "/api/users/2" with below details
    | name | morpheus      |
    | job  | zion resident |
  Then the status code for put call is 200
  And user should see following details in the json response in put call
    | name      | morpheus                 |
    | job       | zion resident            |
    | createdAt | 2018-02-21T15:09:25.537Z |


@delete
Scenario: delete user
  Given the apis are up and running for delete is "https://reqres.in"
  When user performs a delete request to "/api/users/2"
  Then the status code in delete is 204

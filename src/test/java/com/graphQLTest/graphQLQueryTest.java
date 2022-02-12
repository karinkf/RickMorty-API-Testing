/**
 * 
 */
package com.graphQLTest;

/**
 * @author karin
 *
 */

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class graphQLQueryTest {

	@Test
	public void getFemaleAlive() {
		
		String query = "{\"operationName\":null,\"variables\":{},\"query\":\"{\\n  characters(filter: {gender: \\\"Female\\\", species: \\\"Human\\\", status: \\\"Alive\\\"}) {\\n    info {\\n      count\\n    }\\n    results {\\n      id\\n      name\\n      status\\n      species\\n      gender\\n    }\\n  }\\n}\\n\"}";
		
		
		given().log().all()
			.contentType("application/json")
			.body(query)
				.when().log().all()
					.post("https://rickandmortyapi.com/graphql")
				.then().log().all()
					.statusCode(200);
	}
	
	@DataProvider
	public Object[][] getQueryData() {
		return new Object[][] {{"Shmlangela Shmlobinson-Shmlower"}};
	}
	
	@Test(dataProvider = "getQueryData")
	public void getShmlangela(String name) {
		
		String query = "{\"operationName\":null,\"variables\":{},\"query\":\"{\\n  characters(filter: {name: \\\""+name+"\\\"}) {\\n    info {\\n      count\\n    }\\n    results {\\n      id\\n      name\\n      status\\n      species\\n      gender\\n      location {\\n        id\\n        name\\n        type\\n        dimension\\n      }\\n      episode {\\n        id\\n        name\\n        episode\\n      }\\n    }\\n  }\\n}\\n\"}";
		
		given().log().all()
			.contentType("application/json")
			.body(query)
				.when().log().all()
					.post("https://rickandmortyapi.com/graphql")
				.then().log().all()
					.statusCode(200)
						.and()
					.body("data.characters.results[0].location.name", equalTo("Interdimensional Cable")
						 ,"data.characters.results[0].location.type", equalTo("TV")
						 ,"data.characters.results[0].location.dimension", equalTo("unknown")
						 ,"data.characters.results[0].episode[0]", hasEntry("episode", "S01E08"));
		
	}
}

import Constants.TestStandEndpoints;
import TestData.CreatingRandomData;
import io.qameta.allure.Step;

import java.util.List;

import static io.restassured.RestAssured.given;

public class IngredientsResponse {
    private Boolean success;
    private List<Ingredient> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Ingredient> getIngredients() {
        return data;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.data = ingredients;
    }

    @Step("Get an ingredient hash")
    public static String getHash() {
        IngredientsResponse ingredientsResponse = given()
                .header("Content-type", "application/json")
                .get(TestStandEndpoints.INGREDIENTS)
                .body().as(IngredientsResponse.class);
        List<Ingredient> data = ingredientsResponse.getIngredients();
        Ingredient ingredient = data.get(CreatingRandomData.getRandomInt(data.size()));
        return ingredient.get_id();
    }
}

import Constants.TestStandEndpoints;
import io.qameta.allure.Step;

import java.util.List;

import static io.restassured.RestAssured.given;

public class IngredientsResponse {
    private String success;
    private List<Ingredient> data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
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
        Ingredient ingredient = data.get(0);
        return ingredient.get_id();
    }
}

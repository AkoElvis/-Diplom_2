import Constants.TestStandEndpoints;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
public class CreateOrderTest {

    private List<Ingredient> ingredients;
    private IngredientsResponse ingredientsResponse;
    private String hash;

    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.hash = IngredientsResponse.getHash();
        //System.out.println(hash);
    }
    @Test
    public void getIngredientsTest(){
    //    this.ingredientsResponse = given()
    //            .header("Content-type", "application/json")
    //            .get(TestStandEndpoints.INGREDIENTS)
    //            .body().as(IngredientsResponse.class);
    //    System.out.println(ingredientsResponse.getSuccess());
    //    this.ingredients = ingredientsResponse.getIngredients();
    //    System.out.println(ingredients.size());
    //    Ingredient ingredient = ingredients.get(0);
    //    System.out.println(ingredient.get_id());
        System.out.println(hash);
    }
}

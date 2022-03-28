import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderDataJson {

    private static List<String> ingredients;

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    private static final List<String> Buns = new ArrayList<>();
    private static final List<String> Sauces = new ArrayList<>();
    private static final List<String> Fillings = new ArrayList<>();

    public OrderDataJson() {
    }

    public OrderDataJson(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static String getRandomBunHash(){
        Random random = new Random();
        return Buns.get(random.nextInt(Buns.size()));
    }

    public static String getRandomSauceHash(){
        Random random = new Random();
        return Sauces.get(random.nextInt(Sauces.size()));
    }

    public static String getRandomFillingHash(){
        Random random = new Random();
        return Fillings.get(random.nextInt(Fillings.size()));
    }

    public static OrderDataJson randomBurger(boolean withBun, int withSauce, int withFillings){
        List<String> ingredients = new ArrayList<String>();
        Buns.add("61c0c5a71d1f82001bdaaa6d");
        Buns.add("61c0c5a71d1f82001bdaaa6c");
        Sauces.add("61c0c5a71d1f82001bdaaa72");
        Sauces.add("61c0c5a71d1f82001bdaaa73");
        Sauces.add("61c0c5a71d1f82001bdaaa74");
        Sauces.add("61c0c5a71d1f82001bdaaa75");
        Fillings.add("61c0c5a71d1f82001bdaaa70");
        Fillings.add("61c0c5a71d1f82001bdaaa71");
        Fillings.add("61c0c5a71d1f82001bdaaa76");
        Fillings.add("61c0c5a71d1f82001bdaaa77");
        Fillings.add("61c0c5a71d1f82001bdaaa78");
        Fillings.add("61c0c5a71d1f82001bdaaa79");
        Fillings.add("61c0c5a71d1f82001bdaaa7a");
        Fillings.add("61c0c5a71d1f82001bdaaa6e");
        Fillings.add("61c0c5a71d1f82001bdaaa6f");


        if(withBun == true){
            ingredients.add(getRandomBunHash());
            if(withSauce > 0){
                for(int i = 0; i <= withSauce; i++){
                    ingredients.add(getRandomSauceHash());
                }
            }
            if(withFillings > 0){
                for(int i = 0; i <= withFillings; i++){
                    ingredients.add(getRandomFillingHash());
                }
            }
        }
        else return new OrderDataJson();
        return new OrderDataJson(ingredients);
    }

    public static OrderDataJson invalidRandomBurger(int ingredientsCount){

        List<String> ingredients = new ArrayList<String>();

        for(int i = 1; i <= ingredientsCount; i++){
            ingredients.add(RandomStringUtils.randomAlphanumeric(24).toLowerCase());
        }
        return new OrderDataJson(ingredients);
    }

    @Override()
    public String toString(){
        return("Burger component hash: \n" + ingredients.toString());
    }
}

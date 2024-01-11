package catalogue;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class BetterBasketTest {
    private BetterBasket basket;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
         basket = new BetterBasket();
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        String basketStatus = basket.getDetails();
        assertTrue(basketStatus.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void testGetDetailsSingleProduct() {
        Product product = new Product("47", "Agent 47", 47.0,47);
        basket.add(product);
        String basketStatus = basket.getDetails();
        assertTrue(basketStatus.contains("Agent 47"));
        assertTrue(basketStatus.contains("Total £2209"));
    } //2209 is the result of 47.0 * 47



    @org.junit.jupiter.api.Test
    void testGetDetailsMultipleProducts() {
        Product product = new Product("47", "Agent 47", 47.0,47);
        Product product2 = new Product("42", "Answer of life", 42.0,42);
        basket.add(product);
        basket.add(product2);
        String basketStatus = basket.getDetails();
        assertTrue(basketStatus.contains("Agent 47"));
        assertTrue(basketStatus.contains("Answer of life"));
        assertTrue(basketStatus.contains("Total £3973"));
    }//3973 is 42^2+47^2


}



package cat.institutmarianao.a1_pbackendalnvol

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun ingredient_defaultValues_areCorrect() {
        val ingredient = Ingredient(
            name = "Eggs",
            quantity = 6
        )
        assertEquals("", ingredient.id)
        assertEquals("Eggs", ingredient.name)
        assertEquals(6, ingredient.quantity)
        assertNull(ingredient.unit)
        assertNull(ingredient.category)
        assertNull(ingredient.expirationDate)
    }

    @Test
    fun ingredient_fullValues_areCorrect() {
        val ingredient = Ingredient(
            id = "doc123",
            name = "Milk",
            quantity = 2,
            unit = "liters",
            category = "Dairy & Eggs",
            expirationDate = "15/12/2026"
        )
        assertEquals("doc123", ingredient.id)
        assertEquals("Milk", ingredient.name)
        assertEquals(2, ingredient.quantity)
        assertEquals("liters", ingredient.unit)
        assertEquals("Dairy & Eggs", ingredient.category)
        assertEquals("15/12/2026", ingredient.expirationDate)
    }
}

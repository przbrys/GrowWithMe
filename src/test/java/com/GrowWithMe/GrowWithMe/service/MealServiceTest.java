package com.GrowWithMe.GrowWithMe.service;

import com.GrowWithMe.GrowWithMe.model.Meal;
import com.GrowWithMe.GrowWithMe.repository.IMealRepository;
import com.GrowWithMe.GrowWithMe.service.impl.MealService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealServiceTest {

    private static final int MEAL_ID = 1;
    private static final Meal MEAL = generateMeal();

    @Mock
    private IMealRepository mealRepository;

    @InjectMocks
    private MealService tested;

    private static Meal generateMeal() {
        Meal meal = new Meal();
        meal.setMealId(MEAL_ID);
        meal.setMealName("Test Meal");
        meal.setMealCaloricValue(500);
        meal.setMealMacroElements("Makro test");
        meal.setMealIngredients("Ingredients");
        meal.setMealPreparationDescription("Additional text");
        return meal;
    }

    @Test
    void getAllMeal() {
        //given
        List<Meal> meals = List.of(MEAL);
        //when
        when(mealRepository.findAll()).thenReturn(meals);
        List<Meal> result = tested.getAllMeal();
        //then
        assertThat(result).isEqualTo(meals);
    }

    @Test
    void getMealById__1() {
        //given
        //when
        when(mealRepository.findById(MEAL_ID)).thenReturn(Optional.of(MEAL));
        Optional<Meal> result = tested.getMealById(MEAL_ID);
        //then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(MEAL);
    }

    @Test
    void getMealById__2() {
        //given
        //when
        when(mealRepository.findById(MEAL_ID)).thenReturn(Optional.empty());
        Optional<Meal> result = tested.getMealById(MEAL_ID);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteMealEntity__1() {
        //given //when
        tested.deleteMealEntity(MEAL_ID);
        //then
        verify(mealRepository).deleteById(MEAL_ID);
    }

    @Test
    void deleteMealEntity__2() {
        //given //when //then
        doThrow(EmptyResultDataAccessException.class).when(mealRepository).deleteById(MEAL_ID);

        assertThatThrownBy(() -> tested.deleteMealEntity(MEAL_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Meal entity with id " + MEAL_ID + " not found");
    }

    @Test
    void deleteMealEntity__3() {
        //given //when //then
        doThrow(RuntimeException.class).when(mealRepository).deleteById(MEAL_ID);

        assertThatThrownBy(() -> tested.deleteMealEntity(MEAL_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error deleting Meal");
    }

    @Test
    void createMealEntity__1() {
        //given //when
        Meal createdMeal = tested.createMealEntity(MEAL);
        //then
        verify(mealRepository).save(MEAL);
        assertThat(createdMeal).isEqualTo(MEAL);
    }

    @Test
    void createMealEntity__2() {
        //given //when //then
        doThrow(RuntimeException.class).when(mealRepository).save(MEAL);

        assertThatThrownBy(() -> tested.createMealEntity(MEAL))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error in creating new Meal.");
    }

    @Test
    void updateMealInformation__1() {
        //given
        Meal mealToUpdate = new Meal();
        mealToUpdate.setMealId(MEAL_ID);
        mealToUpdate.setMealName("Updated Meal");
        mealToUpdate.setMealCaloricValue(600);
        mealToUpdate.setMealMacroElements("Updated Makro");
        mealToUpdate.setMealIngredients("Updated Ingredients");
        mealToUpdate.setMealPreparationDescription("Updated text");

        Meal expectedMeal = generateMeal();
        expectedMeal.setMealId(MEAL_ID);
        expectedMeal.setMealName("Updated Meal");
        expectedMeal.setMealCaloricValue(600);
        expectedMeal.setMealMacroElements("Updated Makro");
        expectedMeal.setMealIngredients("Updated Ingredients");
        expectedMeal.setMealPreparationDescription("Updated text");

        //when
        when(mealRepository.findById(MEAL_ID)).thenReturn(Optional.of(MEAL));
        when(mealRepository.save(any(Meal.class))).thenReturn(expectedMeal);
        Meal updatedMeal = tested.updateMealInformation(mealToUpdate);

        //then
        assertThat(updatedMeal).isNotNull();
        assertThat(updatedMeal.getMealName()).isEqualTo("Updated Meal");
        assertThat(updatedMeal.getMealCaloricValue()).isEqualTo(600);
        assertThat(updatedMeal.getMealMacroElements()).isEqualTo("Updated Makro");
        assertThat(updatedMeal.getMealIngredients()).isEqualTo("Updated Ingredients");
        assertThat(updatedMeal.getMealPreparationDescription()).isEqualTo("Updated text");
        verify(mealRepository).save(argThat(m -> m.getMealName().equals("Updated Meal")
                && m.getMealCaloricValue().equals(600) && m.getMealMacroElements().equals("Updated Makro")
                && m.getMealIngredients().equals("Updated Ingredients") && m.getMealPreparationDescription().equals("Updated text")));
    }

    @Test
    void updateMealInformation__2() {
        //given
        Meal mealToUpdate = new Meal();
        mealToUpdate.setMealId(MEAL_ID);

        //when
        when(mealRepository.findById(MEAL_ID)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> tested.updateMealInformation(mealToUpdate))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Meal entity with id " + MEAL_ID + " not found, update failed");
    }
}
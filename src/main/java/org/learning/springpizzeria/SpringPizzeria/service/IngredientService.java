package org.learning.springpizzeria.SpringPizzeria.service;

import org.learning.springpizzeria.SpringPizzeria.model.Ingredient;
import org.learning.springpizzeria.SpringPizzeria.model.Pizza;
import org.learning.springpizzeria.SpringPizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> getIngredients(){
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Integer id){
        Optional<Ingredient> ingredient = ingredientRepository.findById(id);
        if (ingredient.isPresent()){
            return ingredient.get();
        } else throw new RuntimeException();
    }

    public Ingredient create(Ingredient formIngredient){
        Ingredient newIngredient = new Ingredient();
        newIngredient.setName(formIngredient.getName());
        return ingredientRepository.save(newIngredient);
    }

    public Ingredient update(Ingredient formIngredient, Integer id){
        Ingredient ingredientUpdating = getIngredientById(id);
        ingredientUpdating.setName(formIngredient.getName());
        return ingredientRepository.save(ingredientUpdating);
    }

    public boolean deleteByID(Integer id) throws RuntimeException{
        ingredientRepository.findById(id).orElseThrow(() -> new RuntimeException(Integer.toString(id)));
        try {
            ingredientRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}

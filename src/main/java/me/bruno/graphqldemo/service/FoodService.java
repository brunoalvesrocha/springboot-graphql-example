package me.bruno.graphqldemo.service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import me.bruno.graphqldemo.entity.Food;
import me.bruno.graphqldemo.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@GraphQLApi
public class FoodService {

    private final FoodRepository foodRepository;

    @GraphQLQuery(name = "foods")
    public List<Food> getFoods() {
        return foodRepository.findAll();
    }

    @GraphQLQuery(name = "food")
    public Optional<Food> getFoodById(@GraphQLArgument(name = "id") Long id) {
        return foodRepository.findById(id);
    }

    @GraphQLMutation(name = "saveFood")
    public Food saveFood(@GraphQLArgument(name = "food") Food food) {
        return foodRepository.save(food);
    }

    @GraphQLMutation(name = "deleteFood")
    public void deleteFood(@GraphQLArgument(name = "id") Long id) {
        foodRepository.deleteById(id);
    }

    @GraphQLQuery(name = "isGood")
    public boolean isGood(@GraphQLContext Food food) {
        return !Arrays.asList("Avocado", "Spam")
                .contains(food.getName());
    }

}

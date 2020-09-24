package me.bruno.graphqldemo.entity;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Food {

    @Id
    @GeneratedValue
    @GraphQLQuery(name = "id", description = "A food's id")
    private Long id;
    @GraphQLQuery(name = "name", description = "A food's name")
    private String name;
}

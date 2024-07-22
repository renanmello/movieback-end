package dev.renan.movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private ObjectId id;
    private String body;
    private String imdbId;

    @DBRef // Adicionando a anotação DBRef para criar uma referência ao Movie
    private Movie movie;

    public Review(String body, String imdbId) {

        this.body = body;
        this.imdbId = imdbId;
    }
}

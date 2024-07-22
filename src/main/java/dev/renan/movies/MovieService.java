package dev.renan.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Movie> allMovies(){
        return movieRepository.findAll();

    }

    public Optional<Movie> singleMovie(ObjectId id){
        return movieRepository.findById(id);
    }

    public Optional<Movie> singleMovie_imdb(String imdbId){
        return movieRepository.findMovieByImdbId(imdbId);
    }

    private Optional<Movie> populateReviews(Optional<Movie> movieOptional) {
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("id").is(movie.getId())),
                    Aggregation.lookup("reviews", "reviewsIds", "_id", "reviews")
            );

            AggregationResults<Movie> results = mongoTemplate.aggregate(aggregation, "movies", Movie.class);
            Movie populatedMovie = results.getUniqueMappedResult();

            return Optional.ofNullable(populatedMovie);
        }
        return Optional.empty();
    }
}

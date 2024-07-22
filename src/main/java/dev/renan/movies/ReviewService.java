package dev.renan.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Review> allReviews(){
        return reviewRepository.findAll();

    }

    public List<Review> getReviewsByImdbId(String imdbId) {
        return reviewRepository.findByImdbId(imdbId);
    }

    public Review createReview(String reviewBody, String imdbId){
        Review review =  reviewRepository.insert(new Review(reviewBody, imdbId));;


        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imbdId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;

    }
}

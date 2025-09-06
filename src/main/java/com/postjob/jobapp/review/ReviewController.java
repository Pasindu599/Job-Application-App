package com.postjob.jobapp.review;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}")
public class ReviewController {

    private  ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId) , HttpStatus.OK);
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> addReview(@RequestBody Review review , @PathVariable Long companyId){
        boolean isReviewSaved = reviewService.addReview(review,companyId);
        if ( isReviewSaved) {
            return new ResponseEntity<>("Review Added Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review Not Saved", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long companyId , @PathVariable Long reviewId){

        Review review = reviewService.getReview(companyId , reviewId);

        if (review != null ){
            return  new ResponseEntity<>(review , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long companyId , @PathVariable Long reviewId , @RequestBody Review review){

        boolean isReviewUpdated = reviewService.updateReview(companyId,reviewId,review);
        if (isReviewUpdated) {

            return new ResponseEntity<>("Review Updated Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review Not Found" , HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId , @PathVariable Long reviewId  ){
        boolean isReviewDeleted = reviewService.deleteReview(companyId,reviewId);
        if (isReviewDeleted) {

            return new ResponseEntity<>("Review Deleted Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review Not Found" , HttpStatus.NOT_FOUND);
    }




}

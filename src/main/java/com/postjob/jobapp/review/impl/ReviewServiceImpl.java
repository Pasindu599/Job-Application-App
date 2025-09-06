package com.postjob.jobapp.review.impl;


import com.postjob.jobapp.company.Company;
import com.postjob.jobapp.company.CompanyService;
import com.postjob.jobapp.review.Review;
import com.postjob.jobapp.review.ReviewController;
import com.postjob.jobapp.review.ReviewRepository;
import com.postjob.jobapp.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository , CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return  reviewRepository.findAllByCompanyId(companyId);
    }

    @Override
    public boolean addReview(Review review, Long companyId) {

        Company company = companyService.getCompanyById(companyId);

        if (company != null ){
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findAllByCompanyId(companyId);
        return reviews.stream().filter(review -> review.getId().equals(reviewId)).findFirst().orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review review) {

       if(companyService.getCompanyById(companyId) != null ){
           review.setCompany(companyService.getCompanyById(companyId));
           review.setId(reviewId);
           reviewRepository.save(review);
           return true;
       }
       return false;
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        if (companyService.getCompanyById(companyId) != null && reviewRepository.existsById(reviewId) ){
            Review review = reviewRepository.findById(reviewId).orElse(null);
            Company company = review.getCompany();
            company.getReviews().remove(review);
            review.setCompany(null);
            companyService.updateCompany(company , companyId);
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}

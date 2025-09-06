package com.postjob.jobapp.company.impl;

import com.postjob.jobapp.company.Company;
import com.postjob.jobapp.company.CompanyRepository;
import com.postjob.jobapp.company.CompanyService;
import com.postjob.jobapp.job.Job;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company updatedcompany, Long id) {
        Optional<Company> jobOptional = companyRepository.findById(id);
        if ( jobOptional.isPresent()){
            Company company = jobOptional.get();
            company.setDescription(updatedcompany.getDescription());
            company.setJobs(updatedcompany.getJobs());
            company.setName(updatedcompany.getName());
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);

    }

    @Override
    public boolean deleteCompanyById(Long id) {
        if (companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
}

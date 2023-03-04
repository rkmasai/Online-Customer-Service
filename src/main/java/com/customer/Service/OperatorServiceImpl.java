package com.customer.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.Entity.Customer;
import com.customer.Entity.Issue;
import com.customer.Entity.Status;
import com.customer.Exception.CustomerException;
import com.customer.Exception.IssueException;
import com.customer.Repository.CustomerRepository;
import com.customer.Repository.IssueRepository;
import com.customer.Repository.OperatorRepository;

@Service
public class OperatorServiceImpl implements OperatorService {

	@Autowired
	private OperatorRepository operatorDao;

	@Autowired
	private IssueRepository issueDoa;

	@Autowired
	private CustomerRepository customerDao;

	@Override
	public String AddCustomerIssue(Customer customer) throws CustomerException {

		Customer cus = customerDao.findByEmail(customer.getEmail());

		if (cus == null) {

			customerDao.save(cus);
			return "Customer Register Sucessfully";
		}

		else {
			throw new CustomerException("Email Id Ready Exist");
		}

	}

	@Override
	public String UpdateIssue(Issue issue) throws IssueException {

		Optional<Issue> existIssue = issueDoa.findById(issue.getIssueId());

		if (existIssue.isPresent()) {

			existIssue.get().setIssueType(issue.getIssueType());
			existIssue.get().setStatus(issue.getStatus());
			existIssue.get().setDescription(issue.getDescription());

			issueDoa.save(existIssue.get());

			return "Issue Resolved sucessfully";

		} else {
			throw new IssueException("No Issue found with IssueId : " + issue.getIssueId());
		}

	}

	@Override
	public List<Customer> findAllCustomer() throws CustomerException {

		List<Customer> cus = customerDao.findAll();

		if (cus != null) {

			return cus;
		} else {

			throw new CustomerException("NO Customer Exist Found");
		}

	}

	@Override
	public Customer findByCustomerId(int cusId) throws CustomerException {

		Optional<Customer> cus = customerDao.findById(cusId);

		if (cus != null) {

			return cus.get();
		}

		else {
			throw new CustomerException("Not match any Record with the customer Id " + cusId);
		}

	}

//	@Override
//	public List<Customer> findCustomerByFirstName(String name) throws CustomerException {
//
//		List<Customer> cus =  customerDao.(name);
//
//		if (cus != null) {
//
//			return cus;
//		}
//
//		else {
//
//			throw new CustomerException("No Record found to this Name : " + name);
//		}
//	}

	@Override
	public Customer findCustomerByEmail(String email) throws CustomerException {

		Customer cus = customerDao.findByEmail(email);

		if (cus != null) {

			return cus;
		} else {
			throw new CustomerException("Not record Match with Customer Email" + email);
		}

	}

	@Override
	public Customer findCustomerByMobile(String mobile) throws CustomerException {

		Customer cus = customerDao.findByMobile(mobile);

		if (cus != null) {
			return cus;
		} else {

			throw new CustomerException("No Record Match with Customer Mobile " + mobile);

		}

	}

	@Override
	public String closeCustomerIssue(int IssueId, Status status) throws IssueException {

		Optional<Issue> issue = issueDoa.findById(IssueId);

		if (issue.isPresent()) {

			issue.get().setStatus(status.Closed);
			issueDoa.save(issue.get());
			return "Customer Issue is Resolved";
		} else {

			throw new IssueException("No Record found with the IssueId " + IssueId);
		}

	}

}

package com.customer.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.customer.Entity.Customer;
import com.customer.Entity.Issue;
import com.customer.Entity.Login;
import com.customer.Exception.CustomerException;
import com.customer.Service.CustomerService;

import jakarta.validation.Valid;



@RestController
public class CustomerController {
    @Autowired
	private CustomerService customerService;
    
    @PostMapping("/register")
    public ResponseEntity<Customer> saveCustomerHandler(@Valid@RequestBody Customer customer){
    	Customer customer1 = customerService.registerCustomer(customer);
    	
    	return new ResponseEntity<>(customer1, HttpStatus.ACCEPTED);
    	
    }
    @PutMapping("/changepassword")
    public ResponseEntity<String> changePasswordHandler(@RequestBody Login login) throws CustomerException{
    	
    	String massage=customerService.changePassword(login);
    	
    	return new ResponseEntity<String>(massage, HttpStatus.OK);
    	
    }
    @GetMapping("/forgetPassword/{id}")
    public ResponseEntity<String> forgetPasswordHandler (@PathVariable("id")Integer id)  throws CustomerException{
    	
    	String massage=customerService.forgetPassword(id);
    	
    	return new ResponseEntity<String>(massage, HttpStatus.OK);
    	
    }
    @GetMapping("/emailPassword/{id}/{key}")
    public ResponseEntity<Customer> emailPasswordHandler (@PathVariable("id")Integer id,@PathVariable("key")String key)  throws CustomerException{
    	
    	Customer massage=customerService.emailPassword(id, key);
    	
    	return new ResponseEntity<Customer>(massage, HttpStatus.OK);
    	
    }
    
    @GetMapping("/issue/{id}/{key}")
    public ResponseEntity<Issue> getIssueById(@PathVariable("id") Integer id,@PathVariable("key") String key) throws CustomerException{
    	Issue issue = customerService.viewissue(id, key);
    	
    	return new ResponseEntity<>(issue, HttpStatus.ACCEPTED);
    	
    }
    
    @GetMapping("/Allissue/{id}/{key}")
    public ResponseEntity<List<Issue>> getAllIssues(@PathVariable("id") Integer Id, @PathVariable("key") String key) throws CustomerException{
    	List<Issue> issue1 = customerService.getAllIssue(Id,key);
    	
    	return new ResponseEntity<>(issue1, HttpStatus.ACCEPTED);
    	
    }
    
    @PutMapping("/Openissue/{id}/{cid}/{key}")
    public ResponseEntity<String> OpenIssue(@PathVariable("id") Integer Id,@PathVariable("cid") Integer Cid, @PathVariable("key") String key) throws CustomerException{
    	String s = customerService.reopenIssue(Id, Cid, key);
    	
    	return new ResponseEntity<>(s, HttpStatus.ACCEPTED);
    	
    }

	

}

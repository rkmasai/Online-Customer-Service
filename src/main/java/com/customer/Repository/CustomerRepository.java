package com.customer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.customer.Entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	 Customer findByEmail (String email);
	 Customer findByCustomerId (Integer customerId);
    @Query("SELECT c FROM Customer c WHERE c.mobile = :mobile")
    Customer findByMobile( String mobile);
    
//    @Query("SELECT c FROM Customer c WHERE c.fistName = :name")
//    Customer findByfistName( String name);
    
}

package com.customer.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.customer.DTO.OperatorDTO;
import com.customer.Entity.Department;
import com.customer.Entity.Operator;
import com.customer.Exception.DepartmentException;
import com.customer.Exception.LoginException;
import com.customer.Exception.OperatorException;
import com.customer.Service.AdminService;

import jakarta.validation.Valid;

@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/dept/{key}")
	public ResponseEntity<Department>addDept(@PathVariable("key")String key,@Valid @RequestBody Department d)throws LoginException
	{
		Department dept=adminService.addDepartment(d,key);
		return new ResponseEntity<>(dept,HttpStatus.CREATED);
	}
	@PutMapping("/dept/{key}")
	public ResponseEntity<Department>updDept(@PathVariable("key")String key,@Valid @RequestBody Department d) throws DepartmentException,LoginException
	{
		Department dept=adminService.updateDepartment(d,key);
		return new ResponseEntity<>(dept,HttpStatus.OK);
	}
	@DeleteMapping("/dept/{id}/{key}")
	public ResponseEntity<Department>deleteDept(@PathVariable("id")Integer id,@PathVariable("key")String key) throws LoginException, DepartmentException
	{
		Department dept=adminService.removeDepartment(id, key);
		return new ResponseEntity<>(dept,HttpStatus.OK);
	}
	@GetMapping("/dept/{id}/{key}")
	public ResponseEntity<Department>getDeptById(@PathVariable("id")Integer id,@PathVariable("key")String key) throws LoginException, DepartmentException
	{
		
		Department dept=adminService.getDepartmentById(id, key);
		return new ResponseEntity<>(dept,HttpStatus.OK);
	}
	@PostMapping("/operator/{key}")
	public ResponseEntity<Operator>addOperator(@PathVariable("key")String key,@Valid @RequestBody Operator op)
	{
		Operator o=adminService.addOperator(op, key);
		return new ResponseEntity<>(o,HttpStatus.CREATED);
	}
	@PutMapping("/operator/{oid}/{did}/{key}")
	public ResponseEntity<OperatorDTO>assignDeptToOpt(@PathVariable("oid")Integer oid,@PathVariable("did")Integer did, @PathVariable("key")String key) throws LoginException, DepartmentException, OperatorException
	{
		OperatorDTO opt=adminService.assignDeptToOperator(oid, did, key);
		return new ResponseEntity<>(opt,HttpStatus.CREATED);
	}
	@PutMapping("/operator/{key}")
	public ResponseEntity<Operator>updOperator(@PathVariable("key")String key,@Valid @RequestBody Operator o) throws LoginException, OperatorException
	{
		Operator opt=adminService.updateOperator(o, key);
		return new ResponseEntity<>(opt,HttpStatus.OK);
	}
	@DeleteMapping("/operator/{id}/{key}")
	public ResponseEntity<Operator>deleteOpt(@PathVariable("id")Integer id,@PathVariable("key")String key) throws LoginException, OperatorException
	{ 
		Operator opt=adminService.deleteOperator(id, key);
		return new ResponseEntity<>(opt,HttpStatus.OK);
	}
	@GetMapping("/operators/{key}")
	public ResponseEntity<List<Operator>>getAllOperators(@PathVariable("key")String key)
	{
		List<Operator>list=adminService.getAllOperators(key);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@GetMapping("/operator/{id}/{key}")
	public ResponseEntity<Operator>getOperatorById(@PathVariable("id")Integer id,@PathVariable("key")String key) throws LoginException, OperatorException
	{
		Operator opt=adminService.getOperatorById(id, key);
		return new ResponseEntity<>(opt,HttpStatus.OK);
	}
	@GetMapping("/operatorsInDept/{id}/{key}")
	public ResponseEntity<List<Operator>>getOperatorsInDept(@PathVariable("id")Integer id,@PathVariable("key")String key) throws LoginException, DepartmentException
	{
		List<Operator>list=adminService.getAllOperatorWithDeptId(id, key);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
}

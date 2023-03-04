package com.customer.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.DTO.OperatorDTO;
import com.customer.Entity.Call;
import com.customer.Entity.CurrentUserSession;
import com.customer.Entity.Department;
import com.customer.Entity.Issue;
import com.customer.Entity.Operator;
import com.customer.Exception.DepartmentException;
import com.customer.Exception.LoginException;
import com.customer.Exception.OperatorException;
import com.customer.Repository.CurrentUserSessionRepository;
import com.customer.Repository.DepartmentDao;
import com.customer.Repository.OperatorDao;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private OperatorDao od;
	@Autowired
	private DepartmentDao dd;
	
	@Autowired
	private CurrentUserSessionRepository cSession;
	
	@Override
	public Department addDepartment(Department d,String key) throws LoginException{
		
//		checking if Admin already logged in or not
		CurrentUserSession cAdmin=cSession.findByUuid(key);
		if(cAdmin==null)
		{
//			if not then throw exception 
			throw new LoginException("Admin needs to Login first");
		}
		if(!cAdmin.getUser_Type().equals("ADMIN"))
		{
			throw new LoginException("Invalid UUID for Admin Session");
		}
//		getting list of operators to map to department
		List<Operator>list=d.getOperators();
		for(Operator o:list)
		{
			o.setDepartment(d);
		}
//		getting dept object after calling save method with object d
		Department dept=dd.save(d);
//		returning dept object with updated primary key value
		return dept;
	}

	@Override
	public Department updateDepartment(Department d,String key) throws DepartmentException, LoginException{
//		checking if Admin already logged in or not
		CurrentUserSession cAdmin=cSession.findByUuid(key);
		if(cAdmin==null)
		{
//			if not then throw exception 
			throw new LoginException("Admin needs to Login first");
		}
		if(!cAdmin.getUser_Type().equals("ADMIN"))
		{
			throw new LoginException("Invalid UUID for Admin Session");
		}
//		finding if the current dept exists or not
		Optional<Department>opt=dd.findById(d.getDeptId());
		if(opt.isPresent())
		{
//			if exists then saving the dept object passed in the argument
			Department dept=dd.save(d);
			return dept;
		}
		else
		{
//			since dept id does not exist, throwing excpetion
			throw new DepartmentException("Department with ID "+d.getDeptId()+" does not exist");
		}
	}

	@Override
	public Department removeDepartment(Integer id,String key) throws DepartmentException, LoginException{
//		checking if Admin already logged in or not
		CurrentUserSession cAdmin=cSession.findByUuid(key);
		if(cAdmin==null)
		{
//			if not then throw exception 
			throw new LoginException("Admin needs to Login first");
		}
		if(!cAdmin.getUser_Type().equals("ADMIN"))
		{
			throw new LoginException("Invalid UUID for Admin Session");
		}
//		finding if the current dept exists or not
		Optional<Department>opt=dd.findById(id);
		if(opt.isPresent())
		{
//			if exists then deleting that object from database and returning to controller layer
			Department dept=opt.get();
			dd.delete(dept);
//			getting list of operators to save in child entity table
			List<Operator>list=dept.getOperators();
			for(Operator o:list)
			{
				o.setDepartment(null);
				od.save(o);
			}

			return dept;
		}
		else
		{
//			since dept id does not exist, throwing excpetion
			throw new DepartmentException("Department with ID "+id+" does not exist");
		}
	}

	@Override
	public Department getDepartmentById(Integer id,String key) throws DepartmentException, LoginException{
//		checking if Admin already logged in or not
		CurrentUserSession cAdmin=cSession.findByUuid(key);
		if(cAdmin==null)
		{
//			if not then throw exception 
			throw new LoginException("Admin needs to Login first");
		}
		if(!cAdmin.getUser_Type().equals("ADMIN"))
		{
			throw new LoginException("Invalid UUID for Admin Session");
		}
//		finding if the current dept exists or not
		Optional<Department>opt=dd.findById(id);
		if(opt.isPresent())
		{
//			if exists then deleting that object from database and returning to controller layer
			Department d=opt.get();
			return d;
		}
		else
		{
//			since dept id does not exist, throwing excpetion
			throw new DepartmentException("Department with ID "+id+" does not exist");
		}

	}

	@Override
	public Operator addOperator(Operator o,String key) throws LoginException{
//		checking if Admin already logged in or not
		CurrentUserSession cAdmin=cSession.findByUuid(key);
		if(cAdmin==null)
		{
//			if not then throw exception 
			throw new LoginException("Admin needs to Login first");
		}
		if(!cAdmin.getUser_Type().equals("ADMIN"))
		{
			throw new LoginException("Invalid UUID for Admin Session");
		}
//		getting operator obj after calling save method on operator o
		Operator op=od.save(o);
//		returning operator obj
		return op;
	}

	@Override
	public OperatorDTO assignDeptToOperator(Integer oid, Integer did,String key) throws DepartmentException, OperatorException, LoginException{
//		checking if Admin already logged in or not
		CurrentUserSession cAdmin=cSession.findByUuid(key);
		if(cAdmin==null)
		{
//			if not then throw exception 
			throw new LoginException("Admin needs to Login first");
		}
		if(!cAdmin.getUser_Type().equals("ADMIN"))
		{
			throw new LoginException("Invalid UUID for Admin Session");
		}
//		finding operator by id
		Optional<Operator>opt=od.findById(oid);
		if(opt.isPresent())
		{
//			if operator exists, then need to check if department with given id is present or not
			Operator o=opt.get();
			Optional<Department>opt2=dd.findById(did);
			if(opt2.isPresent())
			{
//				department also present, saving values to the database
				Department d=opt2.get();
				o.setDepartment(d);
				d.getOperators().add(o);
				dd.save(d);
				OperatorDTO od=new OperatorDTO(o.getOperatorId(),o.getDepartment().getDeptId());
//				returning operatorDTO object
				return od;
			}
			else
			{
				throw new DepartmentException("Department with ID "+did+" does not exist");
			}
		}
		else
		{
			throw new OperatorException("Operator with ID "+oid+" does not exist");
		}
	}

	@Override
	public Operator updateOperator(Operator o,String key) throws OperatorException , LoginException{
//		checking if Admin already logged in or not
		CurrentUserSession cAdmin=cSession.findByUuid(key);
		if(cAdmin==null)
		{
//			if not then throw exception 
			throw new LoginException("Admin needs to Login first");
		}
		if(!cAdmin.getUser_Type().equals("ADMIN"))
		{
			throw new LoginException("Invalid UUID for Admin Session");
		}
//		finding operator by given id
		Optional<Operator>opt=od.findById(o.getOperatorId());
		if(opt.isPresent())
		{
//			if present then saving the operator object passed in the parameter
			Operator op=od.save(o);
//			returning operator object
			return op;
		}
		else
		{
			throw new OperatorException("Operator with ID "+o.getOperatorId()+" does not exist");
		}
		
	}

	@Override
	public Operator deleteOperator(Integer id,String key) throws OperatorException, LoginException{
//		checking if Admin already logged in or not
		CurrentUserSession cAdmin=cSession.findByUuid(key);
		if(cAdmin==null)
		{
//			if not then throw exception 
			throw new LoginException("Admin needs to Login first");
		}
		if(!cAdmin.getUser_Type().equals("ADMIN"))
		{
			throw new LoginException("Invalid UUID for Admin Session");
		}
//		finding operator by given id
		Optional<Operator>opt=od.findById(id);
		if(opt.isPresent())
		{
//			getting operator object if present and deleting from database
			Operator o=opt.get();
//			need to remove the same operator from department list as well else it will not be removed
			Department d=o.getDepartment();
			d.getOperators().remove(o);
			od.delete(o);
			dd.save(d);
//			returning operator object
			return o;
		}
		else
		{
			throw new OperatorException("Operator with ID "+id+" does not exist");
		}
	}

	@Override
	public List<Operator> getAllOperators(String key) throws LoginException{
//		checking if Admin already logged in or not
		CurrentUserSession cAdmin=cSession.findByUuid(key);
		if(cAdmin==null)
		{
//			if not then throw exception 
			throw new LoginException("Admin needs to Login first");
		}
		if(!cAdmin.getUser_Type().equals("ADMIN"))
		{
			throw new LoginException("Invalid UUID for Admin Session");
		}
		List<Operator>list=od.findAll();
		return list;
	}

	@Override
	public Operator getOperatorById(Integer id,String key) throws OperatorException, LoginException{
//		checking if Admin already logged in or not
		CurrentUserSession cAdmin=cSession.findByUuid(key);
		if(cAdmin==null)
		{
//			if not then throw exception 
			throw new LoginException("Admin needs to Login first");
		}
		if(!cAdmin.getUser_Type().equals("ADMIN"))
		{
			throw new LoginException("Invalid UUID for Admin Session");
		}
//		finding operator by id
		Optional<Operator>opt=od.findById(id);
		if(opt.isPresent())
		{
//			if present then return the operator obj
			Operator o=opt.get();
			return o;
		}
		else
		{
			throw new OperatorException("Operator with ID "+id+" does not exist");
		}		
	}

	@Override
	public List<Operator> getAllOperatorWithDeptId(Integer id,String key) throws DepartmentException, LoginException{
//		checking if Admin already logged in or not
		CurrentUserSession cAdmin=cSession.findByUuid(key);
		if(cAdmin==null)
		{
//			if not then throw exception 
			throw new LoginException("Admin needs to Login first");
		}
		if(!cAdmin.getUser_Type().equals("ADMIN"))
		{
			throw new LoginException("Invalid UUID for Admin Session");
		}
		Optional<Department>opt=dd.findById(id);
		if(opt.isPresent())
		{
			Department d=opt.get();
			return d.getOperators();
		}
		else
		{
			throw new DepartmentException("Department with ID "+id+" does not exist");
		}
	}


	
	
	
}

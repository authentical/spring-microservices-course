package com.potatospy.photoapp.api.users.dao.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.potatospy.photoapp.api.users.dao.entity.UserEntity;



public interface UsersRepository extends CrudRepository<UserEntity, Long>{
	
	
	
	
}

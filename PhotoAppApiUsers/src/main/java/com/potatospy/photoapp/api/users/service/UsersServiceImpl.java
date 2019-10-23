package com.potatospy.photoapp.api.users.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.potatospy.photoapp.api.users.dao.entity.UserEntity;
import com.potatospy.photoapp.api.users.dao.repo.UsersRepository;
import com.potatospy.photoapp.api.users.shared.UserDto;


@Service // Todo add interface and implement @Service from there
public class UsersServiceImpl {

	
	@Autowired
	UsersRepository usersRepository;
	
	
	
	
	
	public UsersServiceImpl(UsersRepository usersRepository) {
		
		this.usersRepository = usersRepository;
	}
	
	
	
	
	
	public UserDto createUser(UserDto userDetails) {
		
		
		
		userDetails.setUserId(UUID.randomUUID().toString());
		
		
		
		ModelMapper modelMapper = new ModelMapper();
		// MatchingStrategies.STRICT : ModelMapper does a lot of magic and if for some reason
		// it gets confused about member names between classes, it'll blow up. So use STRICT in this example..
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntityToRepository = modelMapper.map(userDetails, UserEntity.class);
		userEntityToRepository.setEncryptedPassword("JUNK");
		
		
		UserEntity savedUserEntity = usersRepository.save(userEntityToRepository);
		
		UserDto savedUserDto = modelMapper.map(savedUserEntity, UserDto.class);
		
		
		return savedUserDto;
	}
	
	
	
	public String goatifyText(String text) {
		

	        // FIRST JOB
	        String[] wordArr = text.split(" ");

	        String goatified = "";


	        // SECOND JOB:
	        for(int i=0; i<wordArr.length;i++){

	            char ch = (wordArr[i].charAt(0));

	            String goatifiedWord="";

	            char[] aStr = new char[i+1];
	            for(int j = 0; j < i+1; j++){

	                aStr[j] = 'a';
	            }


	            if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'){

	                goatified += wordArr[i].toString();
	            } else {

	                goatified +=
	                        wordArr[i].toString().substring(1,wordArr[i].length()) +
	                        wordArr[i].toString().charAt(0);
	            }

	            goatified += "ma" + String.copyValueOf(aStr);

	            if(i==wordArr.length-1){
	                break;
	            }

	            goatified+=" ";
	        }


	        return goatified;
	}
}



































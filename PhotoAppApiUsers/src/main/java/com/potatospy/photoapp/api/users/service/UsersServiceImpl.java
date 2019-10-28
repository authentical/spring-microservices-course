package com.potatospy.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.potatospy.photoapp.api.users.dao.entity.UserEntity;
import com.potatospy.photoapp.api.users.dao.repo.UsersRepository;
import com.potatospy.photoapp.api.users.shared.UserDto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.*;


@Service // Todo add interface and implement @Service from there
public class UsersServiceImpl implements UserDetailsService{

	
	UsersRepository usersRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	
	
	
	
	public UserDto createUser(UserDto userDetails) {
		
		
		// Set userId and encryptedPassword
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		
		// Transfer UserDto to UserEntity
		ModelMapper modelMapper = new ModelMapper();
		// MatchingStrategies.STRICT : ModelMapper does a lot of magic and if for some reason
		// it gets confused about member names between classes, it'll blow up. So use STRICT in this example..
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntityToRepository = modelMapper.map(userDetails, UserEntity.class);
		
		
		// Save the entity to repo, get it back from the repo and transfer it into a new dto
		UserEntity savedUserEntity = usersRepository.save(userEntityToRepository);
		UserDto savedUserDto = modelMapper.map(savedUserEntity, UserDto.class);
		
		
		return savedUserDto;
	}
	
	
	
	
	
	// loadUserByUsername@UserDetailsService routes repository validated users to 
	// the AuthenticationManagerBuilder via WebSecurity
	@Override
	public UserDetails loadUserByUsername(String emailAsUsername) throws UsernameNotFoundException {
		
		
		UserEntity registeredUser =  usersRepository.findByEmail(emailAsUsername);	// NOTE: Added method to usersRepository to find by Email
		
		if(registeredUser==null) throw new UsernameNotFoundException(emailAsUsername);
		
		
		
		/*
		 * public User(String username, String password, boolean enabled, boolean
		 * accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
		 * Collection<? extends GrantedAuthority> authorities) {
		 */
		return new User(
				registeredUser.getEmail(), registeredUser.getEncryptedPassword(), 
				true, true, true, true, new ArrayList<>()
				);
	}



	
	
	public UserDto getUserDetailsByEmail(String email) {
		
		
		UserEntity userEntityFromRepository = usersRepository.findByEmail(email);
		
		if(userEntityFromRepository==null) throw new UsernameNotFoundException(email);
		
		
		ModelMapper modelMapper = new ModelMapper();

		return modelMapper.map(userEntityFromRepository,UserDto.class);
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



































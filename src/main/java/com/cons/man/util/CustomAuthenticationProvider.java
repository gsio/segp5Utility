package com.cons.man.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.cons.man.domain.UserVO;
import com.cons.man.services.UserService;
public class CustomAuthenticationProvider implements AuthenticationProvider {  
	@Autowired
	private UserService userService;
		
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    /**
     * Login직후 1차 id 및 비밀번호체크
     * => LoginSuccessHandler로 넘어감.
     */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String user_id = (String)authentication.getPrincipal();		
		String user_pw = (String)authentication.getCredentials();

		UserVO userVO = new UserVO();
		
		UserVO input = new UserVO();
		input.setUserid(user_id);
		input.setPassword(user_pw);;
		
		userVO = userService.getUserByUserIdAndPW(input);	
		if(userVO == null){
			throw new BadCredentialsException("AbstractUserDetailsAuthenticationProvider.badCredentials");
		}
			
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));//TODO: 세분화필요        

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(user_id, user_pw, roles);
        
		return result;
        
		
	}

}

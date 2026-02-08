package com.sawari.sawari.service;

import com.sawari.sawari.Repository.RiderRepository;
import com.sawari.sawari.entity.Rider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsMpl implements UserDetailsService {

    @Autowired
    private RiderRepository riderRepository;

    @Override
    public UserDetails loadUserByUsername(String UserName)throws UsernameNotFoundException{
        Rider rider = riderRepository.findRiderByUserName(UserName);
        if(rider != null) {
            UserDetails Ud = User.builder()
                    .username(UserName)
                    .password("")
                    .authorities("ROLE_"+rider.getRole())
                    .build();
            return Ud;
        }
        throw new UsernameNotFoundException("User not found" + UserName);
    }
}

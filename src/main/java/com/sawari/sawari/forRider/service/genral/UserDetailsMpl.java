package com.sawari.sawari.forRider.service.genral;

import com.sawari.sawari.forRider.Repository.RiderRepository;
import com.sawari.sawari.forRider.entity.Rider;
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

package com.sawari.sawari.ForDriver.service.general;

import com.sawari.sawari.ForDriver.entity.Driver;
import com.sawari.sawari.ForDriver.repository.DriverRepository;
import com.sawari.sawari.forRider.Repository.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsMpl implements UserDetailsService {

    @Autowired
    private DriverRepository driverRepository;
    @Override
    public UserDetails loadUserByUsername(String UserName)throws UsernameNotFoundException{
        Driver driver = driverRepository.findDriverByUserName(UserName); //just updated to check for driver
        if(driver != null) {
            UserDetails Ud = User.builder()
                    .username(UserName)
                    .password("")
                    .authorities("ROLE_"+"Driver")
                    .build();
            return Ud;
        }
        throw new UsernameNotFoundException("User not found" + UserName);
    }
}

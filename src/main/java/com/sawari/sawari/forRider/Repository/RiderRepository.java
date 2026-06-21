package com.sawari.sawari.forRider.Repository;

import com.sawari.sawari.forRider.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Integer> {
    Rider findRiderByUserName(String username);
    Rider findRiderByPhoneNumber(String phoneNumber);
}

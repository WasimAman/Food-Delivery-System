package org.wasim.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wasim.userservice.entity.Address;


public interface AddressRepository extends JpaRepository<Address, String> {
}

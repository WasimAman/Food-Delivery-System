package org.wasim.menuservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wasim.menuservice.entity.MenuItem;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem,String> {
    List<MenuItem> findByRestaurantId(String restaurantId);
}

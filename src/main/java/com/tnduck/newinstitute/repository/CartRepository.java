package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Cart;
import com.tnduck.newinstitute.entity.CategoryCourse;
import com.tnduck.newinstitute.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Query(value = "SELECT * FROM cart WHERE student_id = ?1", nativeQuery = true)
    List<Cart> getCartListByUserID(UUID userID);
}

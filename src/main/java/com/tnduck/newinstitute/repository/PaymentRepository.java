package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Lesson;
import com.tnduck.newinstitute.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}

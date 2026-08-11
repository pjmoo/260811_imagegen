package org.example.imagegen.repository;

import org.example.imagegen.entity.GenImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenImageJpaRepository extends JpaRepository<GenImage, Long> {
}

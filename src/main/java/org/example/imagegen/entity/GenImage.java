package org.example.imagegen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenImage {
    @Id
    @GeneratedValue
    private Long id;
    private String filename;
    @Column(length = 2000)
    private String prompt;
    @Column(length = 2000)
    private String improved;
}

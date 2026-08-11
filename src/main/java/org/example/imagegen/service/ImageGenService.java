package org.example.imagegen.service;

import org.example.imagegen.dto.GenResultDTO;
import org.springframework.stereotype.Service;

@Service
public class ImageGenService {
    public GenResultDTO generate(String prompt) {
        return new GenResultDTO();
    }
}

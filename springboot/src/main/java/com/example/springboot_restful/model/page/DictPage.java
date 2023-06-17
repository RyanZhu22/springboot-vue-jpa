package com.example.springboot_restful.model.page;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class DictPage {

    private String code;

    @Min(1)
    private int page = 1;

    @Min(1)
    private int size = 5;
}

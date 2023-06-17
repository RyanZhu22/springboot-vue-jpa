package com.example.springboot_restful.model.dto.dict;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DictUpdateDto {

    @NotNull(message = "Id cannot be blank")
    private Integer id;

    @NotBlank(message = "Code cannot be blank")
    private String code;

    @NotBlank(message = "Value cannot be blank")
    private String value;

    @NotBlank(message = "Type cannot be blank")
    private String type;
}

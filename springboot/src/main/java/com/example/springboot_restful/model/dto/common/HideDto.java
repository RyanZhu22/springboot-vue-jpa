package com.example.springboot_restful.model.dto.common;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HideDto {

    @NotNull(message = "Id cannot be blank")
    private Integer id;

    @NotNull(message = "Hide cannot be blank")
    private boolean hide;
}

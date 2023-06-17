package com.example.springboot_restful.model.dto.common;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteDto {

    @NotNull(message = "Id cannot be blank")
    private Integer id;

    @NotNull(message = "Deleted cannot be blank")
    private boolean deleted;
}

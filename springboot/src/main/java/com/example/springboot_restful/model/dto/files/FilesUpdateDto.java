package com.example.springboot_restful.model.dto.files;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FilesUpdateDto {

    @NotNull(message = "Id cannot be blank")
    private Integer id;

    @NotNull(message = "Hide should not be null")
    private Boolean hide;
}

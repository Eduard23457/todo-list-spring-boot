package org.example.todolistspringboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.todolistspringboot.entity.TaskStatus;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDtoRequest {
    @NotBlank
    @Schema(description = "Название задачи", example = "Купить продукты", required = true)
    private String title;

    @NotBlank
    @Schema(description = "Описание задачи", example = "Купить молоко и хлеб", required = true)
    private String description;

    @NotNull
    @Schema(description = "Статус задачи", example = "NEW", required = true)
    private TaskStatus status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Дата создания задачи", example = "2025-08-16T12:34:56", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Дата обновления задачи", example = "2025-08-16T12:34:56", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
}

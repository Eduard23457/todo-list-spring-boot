package org.example.todolistspringboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.todolistspringboot.entity.TaskStatus;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDtoResponse {

    @Schema(description = "ID задачи", example = "1")
    private Long id;

    @Schema(description = "Название задачи", example = "Купить продукты")
    private String title;

    @Schema(description = "Описание задачи", example = "Купить молоко и хлеб")
    private String description;

    @Schema(description = "Статус задачи", example = "NEW")
    private TaskStatus status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Дата создания задачи", example = "2025-08-16T12:34:56", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Дата обновления задачи", example = "2025-08-16T12:34:56", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
}

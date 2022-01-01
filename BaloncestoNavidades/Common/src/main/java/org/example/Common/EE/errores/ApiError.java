package org.example.Common.EE.errores;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiError {

  private String message;
  private LocalDateTime fecha;


}
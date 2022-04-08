package rol.Common.errores;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiError {

    private String message;

}

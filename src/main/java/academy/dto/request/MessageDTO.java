package academy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long userId;
    private Long friendId;
    private String text;
}

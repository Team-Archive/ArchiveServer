package site.archive.dto.v2;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class LikesRequestDto {

    private List<Long> archiveIds;

}

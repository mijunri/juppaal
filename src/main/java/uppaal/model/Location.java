package uppaal.model;

import lombok.Data;
import uppaal.model.label.InvariantLabel;

@Data
public class Location {
    private String id;
    private String name;
    private InvariantLabel invariantLabel;
}

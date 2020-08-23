package uppaal.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Template {
    private String name;
    private Declaration localDeclaration;
    private List<Location> locationList;
    private List<Transition> transitionList;
    private Location initLocation;
}

package uppaal.model;

import lombok.Data;
import uppaal.model.label.AssignmentLabel;
import uppaal.model.label.GuardLabel;
import uppaal.model.label.SynchronizedLabel;

@Data
public class Transition {
    private Location source;
    private Location target;
    private GuardLabel guardLabel;
    private SynchronizedLabel synchronizedLabel;
    private AssignmentLabel assignmentLabel;
}

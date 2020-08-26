package uppaal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import uppaal.constants.UppaalConstants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NTA {
    private Declaration globalDeclaration;
    private List<Template> templateList;
    private String system;

    public void writeToUppaalXml(String path) throws IOException {
        Document document = DocumentHelper.createDocument();
        document.addDocType(UppaalConstants.NTA,UppaalConstants.DOC_TYPE_S1,UppaalConstants.DOC_TYPE_S2);

        Element root = document.addElement(UppaalConstants.NTA);
        Element declaration = root.addElement(UppaalConstants.DECLARATION);
        declaration.addText(getGlobalDeclaration().toString());

        for(Template template: getTemplateList()){

            Element templateElement = root.addElement(UppaalConstants.TEMPLATE);
            Element name = templateElement.addElement(UppaalConstants.NAME);
            name.addText(template.getName());

            Element templateDeclaration = templateElement.addElement(UppaalConstants.TEMPLATE);
            templateDeclaration.addText(template.getLocalDeclaration().toString());

            for(Location uppaalLocation: template.getLocationList()){
                Element location = templateElement.addElement(UppaalConstants.LOCATION);
                location.addAttribute(UppaalConstants.ID,template.getName()+uppaalLocation.getId());
                Element locationName = location.addElement(UppaalConstants.NAME);
                locationName.addText(uppaalLocation.getName());
            }

            Element init = templateElement.addElement(UppaalConstants.INIT);
            init.addAttribute(UppaalConstants.REF,template.getName()+template.getInitLocation().getId());

            List<Transition> uppaalTransitionList = template.getTransitionList();
            for(Transition uppaalTransition: uppaalTransitionList){
                Element transition = templateElement.addElement(UppaalConstants.TRANSITION);

                Element source = transition.addElement(UppaalConstants.SOURCE);
                source.addAttribute(UppaalConstants.REF,template.getName()+uppaalTransition.getSource().getId());

                Element target = transition.addElement(UppaalConstants.TARGET);
                target.addAttribute(UppaalConstants.REF,template.getName()+uppaalTransition.getTarget().getId());

                Element sycnLabel = transition.addElement(UppaalConstants.LABEL);
                sycnLabel.addAttribute(UppaalConstants.KIND, UppaalConstants.SYNCHRONISATION);
                sycnLabel.addText(uppaalTransition.getSynchronizedLabel().getText());

                Element guardLabel = transition.addElement(UppaalConstants.LABEL);
                guardLabel.addAttribute(UppaalConstants.KIND,UppaalConstants.GUARD);
                guardLabel.addText(uppaalTransition.getGuardLabel().getText());

                Element assignmentLabel = transition.addElement(UppaalConstants.LABEL);
                assignmentLabel.addAttribute(UppaalConstants.KIND,UppaalConstants.ASSIGNMENT);
                assignmentLabel.addText(uppaalTransition.getAssignmentLabel().getText());
            }
        }

        Element system = root.addElement(UppaalConstants.SYSTEM);
        system.addText(getSystem());

        XMLWriter writer = new XMLWriter( new FileOutputStream(path),
                OutputFormat.createPrettyPrint());
        writer.write(document);
        writer.close();

    }
}

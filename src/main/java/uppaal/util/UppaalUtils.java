package uppaal.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import uppaal.test.NTA;
import uppaal.test.Template;
import uppaal.test.UppaalLocation;
import uppaal.test.UppaalTransition;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class UppaalUtils {


    public static void writeXml(NTA nta) throws IOException {
        Document document = DocumentHelper.createDocument();
        document.addDocType("nta","-//Uppaal Team//DTD Flat System 1.1//EN","http://www.it.uu.se/research/group/darts/uppaal/flat-1_1.dtd");

        Element root = document.addElement("nta");

        Element declaration = root.addElement("declaration");
        declaration.addText(nta.getDeclaration());

        for(Template template: nta.getTemplateList()){
            Element templateElement = root.addElement("template");

            Element name = templateElement.addElement("name");
            name.addText(template.getName());

            Element templateDeclaration = templateElement.addElement("declaration");
            templateDeclaration.addText(template.getDeclaration());

            for(UppaalLocation uppaalLocation: template.getLocationList()){
                Element location = templateElement.addElement("location");
                location.addAttribute("id",template.getName()+uppaalLocation.getId());
                Element locationName = location.addElement("name");
                locationName.addText("s"+uppaalLocation.getName());
            }

            Element init = templateElement.addElement("init");
            init.addAttribute("ref",template.getName()+template.getInitLocation().getId());

            List<UppaalTransition> uppaalTransitionList = template.getTransitionList();
            for(UppaalTransition uppaalTransition: uppaalTransitionList){
                Element transition = templateElement.addElement("transition");
                Element source = transition.addElement("source");
                source.addAttribute("ref",template.getName()+uppaalTransition.getSource().getId());
                Element target = transition.addElement("target");
                target.addAttribute("ref",template.getName()+uppaalTransition.getTarget().getId());
                Element sycnLabel = transition.addElement("label");
                sycnLabel.addAttribute("kind", "synchronisation");
                sycnLabel.addText(uppaalTransition.getSynchronisation().getText());
                Element guardLabel = transition.addElement("label");
                guardLabel.addAttribute("kind","guard");
                guardLabel.addText(uppaalTransition.getGuard().getText());
                Element assignmentLabel = transition.addElement("label");
                assignmentLabel.addAttribute("kind","assignment");
                assignmentLabel.addText(uppaalTransition.getAssignment().getText());
            }
        }

        Element system = root.addElement("system");
        system.addText(nta.getSystem());

        XMLWriter writer = new XMLWriter( new FileOutputStream(PATH+"nta.xml"),
                OutputFormat.createPrettyPrint());
        writer.write(document);
        writer.close();
    }
}

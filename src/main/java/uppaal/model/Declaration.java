package uppaal.model;

import lombok.Data;

import java.util.Map;

public class Declaration {
    private Map<String,String> map;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,String> entry : map.entrySet()){
            sb.append(entry.getKey()).append(" ").append(entry.getValue()).append(";");
        }
        return sb.toString();
    }
}

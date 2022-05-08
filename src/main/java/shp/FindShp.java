package shp;

import static config.ApplicationProperties.getProperty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindShp {

    private final String shpTypeName;

    public FindShp(String shpTypeName) {
        this.shpTypeName = shpTypeName;
    }

    public List<Shp> getShpList() {
        List<Shp> shpList = new ArrayList<>();
        File[] shpFiles = find();
        for (File file : shpFiles) {
            try {
                shpList.add(new Shp(file));
            } catch (IOException e) {
                System.err.printf("%s 식별 과정에 오류가 발생했습니다.\n", file.getName());
                e.printStackTrace();
            }
        }
        return shpList;
    }

    private File[] find() {
        File directory = new File(getProperty("shp.base") + shpTypeName);
        String extension = "shp";
        return directory.listFiles((dir, name) -> name.endsWith(extension));
    }
}

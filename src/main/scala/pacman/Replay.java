package pacman;

import javafx.scene.layout.Pane;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Replay {
    public int count = 0;
    public ArrayList<ObjectData> screens = new ArrayList<>();
    Replay(){}
    public void addReplayData(double x, double y){
        screens.add(new ObjectData(x, y));
    }
    public void addReplayData(double x, double y, int direction){
        screens.add(new ObjectData(x, y, direction));
    }

    public void outputFile(){
        File dir = new File("./"); //path указывает на директорию
        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);
        int number = 0;
        for (int countFile = 0; countFile<lst.size();countFile++){
            if(lst.get(countFile).toString().contains(".bin")) {
                String fileName = lst.get(countFile).toString().replaceAll(".\\\\", "");
                fileName = fileName.replaceAll(".bin", "");
                if(number < Integer.parseInt(fileName))
                    number = Integer.parseInt(fileName);
            }
        }
        number++;
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(number+".bin",false))) {
            oos.writeObject(screens);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public void inputFile(String path){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            screens=((ArrayList<ObjectData>)ois.readObject());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}

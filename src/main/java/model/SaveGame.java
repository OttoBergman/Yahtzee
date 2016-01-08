package model;

import java.io.*;

/**
 * Created by otto on 2016-01-04.
 */
public class SaveGame implements Serializable {

        public boolean saveGame(Object object, String name){
            try{
                FileOutputStream fileOut =
                        new FileOutputStream(name + ".save");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(object);
                out.close();
                fileOut.close();
                System.out.printf("Game data saved to" + name + ".save");

            }catch(IOException i)
            {
                i.printStackTrace();
            }
            return true;
        }


        public Game loadGame(Game g, String name){
            try
            {
                File file = new File(name);
                FileInputStream loadFile = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(loadFile);
                g = (Game) in.readObject();
                in.close();
                loadFile.close();
                return g;
            } catch (FileNotFoundException d) {
                System.out.println("File with that namce could not be found.");
                d.printStackTrace();
                return null;

            } catch(IOException i)
            {
                i.printStackTrace();
                return null;
            }catch(ClassNotFoundException c)
            {
                System.out.println("File is unreadable.");
                c.printStackTrace();
                return null;
            }
        }
}

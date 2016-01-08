package model;

import java.io.*;

/**
 * Created by otto on 2016-01-04.
 */
public class SaveGame implements Serializable {

        public boolean saveGame(Object object){
            try{
                FileOutputStream fileOut =
                        new FileOutputStream("savedGame.save");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(object);
                out.close();
                fileOut.close();
                System.out.printf("Serialized data saved to savedGame.save");

            }catch(IOException i)
            {
                i.printStackTrace();
            }
            return true;
        }


        public Game loadGame(Game g){
            try
            {
                File file = new File("savedGame.save");
                FileInputStream loadFile = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(loadFile);
                g = (Game) in.readObject();
                in.close();
                loadFile.close();
                return g;
            }catch(IOException i)
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

package entry;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;

public class employee {

    private String Name;
    private int ID;

    public employee () {
    }

    public employee (int ID, byte[] Name) {
        this.ID = ID;
        try {
            this.Name = new String(Name, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(this.Name + "   " + this.ID);
    }
    
    public employee (int ID, String Name) {
        this.ID = ID;
        this.Name = Name;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return getName();
    }
}
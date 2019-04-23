package entry;
import javafx.beans.property.SimpleStringProperty;

public class Activity {

    private SimpleStringProperty Index, Name, Plate, Start;

    public Activity () {
    }

    public Activity (int index, String Name, String Plate, String Start) {
        this.Index = new SimpleStringProperty(String.valueOf(index));
        this.Name = new SimpleStringProperty(Name);
        this.Plate = new SimpleStringProperty(Plate);
        this.Start = new SimpleStringProperty(Start);
    }
    @Override
    public String toString() {
        return (Index.get() + ". " + Plate.get() + ", by " + Name.get() + " at " + Start.get());
    }

    /**
     * @return the Index
     */
    public String getIndex() {
        return Index.get();
    }

    /**
     * @param Index the Index to set
     */
    public void setIndex(String Index) {
        this.Index.set(Index);
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name.get();
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name.set(Name);
    }

    /**
     * @return the Plate
     */
    public String getPlate() {
        return Plate.get();
    }

    /**
     * @param Plate the Plate to set
     */
    public void setPlate(String Plate) {
        this.Plate.set(Plate);
    }

    /**
     * @return the Start
     */
    public String getStart() {
        return Start.get();
    }

    /**
     * @param Start the Start to set
     */
    public void setStart(String Start) {
        this.Start.set(Start);
    }
}
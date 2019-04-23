package entry;
import javafx.beans.property.SimpleStringProperty;

public class print_activity {

    private SimpleStringProperty Index, attendant, plate, carType, washType, phone, price, status, start, end, remark, expence;
    
    public print_activity () {
    }

    public print_activity (int index, String Name, String Plate, String Car_Type, String Wash_Type, String Phone_No, String Price, String Status, String Start, String End, String Remark) {
        this.Index = new SimpleStringProperty(String.valueOf(index));
        this.attendant = new SimpleStringProperty(Name);
        this.plate = new SimpleStringProperty(Plate);
        this.carType = new SimpleStringProperty(Car_Type);
        this.washType = new SimpleStringProperty(Wash_Type);
        this.phone = new SimpleStringProperty(Phone_No);
        this.price = new SimpleStringProperty(Price);
        if(Status.equals("PAID")){
            this.status = new SimpleStringProperty("OK");
        } else if(Status.equals("OFFICE")) {
            this.status = new SimpleStringProperty("OK");
            this.remark = new SimpleStringProperty("OFFCE");
            expence = new SimpleStringProperty(Price);
        }
        this.start = new SimpleStringProperty(Start);
        this.end = new SimpleStringProperty(End);
        this.remark = new SimpleStringProperty(Remark);
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
        this.Index = new SimpleStringProperty(Index);
    }

    /**
     * @return the attendant
     */
    public String getAttendant() {
        return attendant.get();
    }

    /**
     * @param attendant the attendant to set
     */
    public void setAttendant(String attendant) {
        this.attendant = new SimpleStringProperty(attendant);
    }

    /**
     * @return the plate
     */
    public String getPlate() {
        return plate.get();
    }

    /**
     * @param plate the plate to set
     */
    public void setPlate(String plate) {
        this.plate = new SimpleStringProperty(plate);
    }

    /**
     * @return the carType
     */
    public String getCarType() {
        return carType.get();
    }

    /**
     * @param carType the carType to set
     */
    public void setCarType(String carType) {
        this.carType = new SimpleStringProperty(carType);
    }

    /**
     * @return the washType
     */
    public String getWashType() {
        return washType.get();
    }

    /**
     * @param washType the washType to set
     */
    public void setWashType(String washType) {
        this.washType = new SimpleStringProperty(washType);
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone.get();
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = new SimpleStringProperty(phone);
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price.get();
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = new SimpleStringProperty(price);
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status.get();
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = new SimpleStringProperty(status);
    }

    /**
     * @return the start
     */
    public String getStart() {
        return start.get();
    }

    /**
     * @param start the start to set
     */
    public void setStart(String start) {
        this.start = new SimpleStringProperty(start);
    }

    /**
     * @return the end
     */
    public String getEnd() {
        return end.get();
    }

    /**
     * @param end the end to set
     */
    public void setEnd(String end) {
        this.end = new SimpleStringProperty(end);
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark.get();
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = new SimpleStringProperty(remark);
    }

    /**
     * @return the expence
     */
    public String getExpence() {
        return expence.get();
    }

    /**
     * @param expence the expence to set
     */
    public void setExpence(String expence) {
        this.expence = new SimpleStringProperty(expence);
    }
}
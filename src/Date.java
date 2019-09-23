import org.jetbrains.annotations.NotNull;

public class Date {
    private int day;
    private int month;
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDate() {
        return String.format("%d.%d.%d", this.getDay(), this.getMonth(), this.getYear());
    }

    public void setDate(@NotNull String s) {
        String[] elements = s.split("\\.");
        if (elements.length != 3) {
            this.setDay(0);
            this.setMonth(0);
            this.setYear(0);
        } else {
            try {
                this.setDay(Integer.parseInt(elements[0]));
                this.setMonth(Integer.parseInt(elements[1]));
                this.setYear(Integer.parseInt(elements[2]));
            } catch (NumberFormatException e) {
                System.out.println("Error converting date");
            }
        }
    }
}
